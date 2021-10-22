package co.edu.unbosque.tiendagenerica.controller;

import java.util.List;

import javax.persistence.EntityManager;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import co.edu.unbosque.tiendagenerica.dao.UsuarioDAO;
import co.edu.unbosque.tiendagenerica.model.Usuario;
import co.edu.unbosque.tiendagenerica.utils.JWTUtil;
import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import io.jsonwebtoken.MalformedJwtException;

@RestController
@RequestMapping(value = "api/usuarios")
public class UsuarioController {

	@Autowired
	private UsuarioDAO usuarioDAO;

	@Autowired
	EntityManager entityManager;

	@Autowired
	private JWTUtil jwtUtil;

	@GetMapping(value = "/{cedula_usuario}")
	public Usuario obtenerUsuario(@PathVariable Long cedula_usuario) {
		return usuarioDAO.existsById(cedula_usuario) ? usuarioDAO.getById(cedula_usuario) : null;
	}

	@PostMapping("/guardar") // Request convierte en un objeto Java desde un JSon
	public String guardarUsuario(@RequestBody Usuario usuario) {
		Argon2 argon = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2id);
		usuario.setPasswordUsuario(argon.hash(1, 1024, 2, usuario.getPasswordUsuario().toCharArray()));
		try {
			usuarioDAO.save(usuario);
			return "AGREGADO";
		} catch (ConstraintViolationException e) {
			// System.err.print(e.getMessage());
			return e.getMessage();
		} catch (Exception e) {
			return "ERROR GENERAL : " + e.getMessage();
		}
	}

	@GetMapping("/listar")
	public List<Usuario> listarUsuarios(@RequestParam(value = "authorization") String token) {
		try {
			String nick = jwtUtil.getValue(token);
			return (nick.equals("admin")) ? usuarioDAO.findAll() : null;
		} catch (MalformedJwtException mjwt) {
			System.err.print(mjwt.getMessage());
			return null;
		}
	}
	
	/*
	 * @DeleteMapping("/eliminar/{id}") public void
	 * eliminarUsuario(@PathVariable("id") Long id) { usuarioDAO.deleteById(id); }
	 */

	@DeleteMapping("/eliminar/{cedulaUsuario}")
	public String eliminarUsuario(@PathVariable("cedulaUsuario") Long cedulaUsuario,
			@RequestHeader(value = "Authorization") String token) {
		try {
			String nick = jwtUtil.getValue(token);
			if (nick.equalsIgnoreCase("admin") && !usuarioDAO.getById(cedulaUsuario).getNickUsuario().equals("admin")) {
				usuarioDAO.deleteById(cedulaUsuario);
				return "ELIMINADO";
			} else if (usuarioDAO.getById(cedulaUsuario).getNickUsuario().equals("admin")) {
				return "ADMIN";
			} else {
				return "NO_ADMIN";
			}
		} catch (MalformedJwtException mjwt) {
			System.err.print(mjwt.getMessage());
			return "TOKEN_ERROR";
		}
	}

	@PutMapping("/actualizar")
	public String actualizarUsuario(@RequestBody Usuario usuario,
			@RequestHeader(value = "Authorization") String token) {
		try {
			String nick = jwtUtil.getValue(token);
			if (nick.equalsIgnoreCase("admin") && !usuario.getNickUsuario().equals("admin")) {
				if(!usuario.getPasswordUsuario().equals("")) {
					Argon2 argon = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2id);
					usuario.setPasswordUsuario(argon.hash(1, 1024, 2, usuario.getPasswordUsuario().toCharArray()));
					usuarioDAO.save(usuario);
				}else {
					usuario.setPasswordUsuario(usuarioDAO.getById(usuario.getCedulaUsuario()).getPasswordUsuario());
					usuarioDAO.save(usuario);
				}
				return "ACTUALIZADO";
			} else if (usuario.getNickUsuario().equals("admin")) {
				return "ADMIN";
			} else {
				return "NO_ADMIN";
			}
		} catch (ConstraintViolationException e) {
			// System.err.print(e.getMessage());
			return e.getMessage();
		} catch (Exception e) {
			return "ERROR GENERAL : " + e.getMessage();
		}
	}

}
