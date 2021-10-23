package co.edu.unbosque.tiendagenerica.controller;

import java.util.ArrayList;
import java.util.List;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.edu.unbosque.tiendagenerica.dao.UsuarioDAO;
import co.edu.unbosque.tiendagenerica.model.Usuario;
import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;

@RestController
@RequestMapping(value = "api/usuarios")
public class UsuarioController {

	@Autowired
	private UsuarioDAO usuarioDAO;

	@GetMapping(value = "/{cedula_usuario}")
	public Usuario obtenerUsuario(@PathVariable Long cedula_usuario) {
		return usuarioDAO.existsById(cedula_usuario) ? usuarioDAO.getById(cedula_usuario) : null;
	}

	@PostMapping("/guardar_lista")
	public String guardarListado(@RequestBody ArrayList<Usuario> listado) {
		if (!listado.isEmpty()) {
			for (Usuario prod : listado) {
				usuarioDAO.save(prod);
			}
			return "EXITO";
		} else {
			return "VACIO";
		}
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
	public List<Usuario> listarUsuarios() {
		return usuarioDAO.findAll();
	}

	@DeleteMapping("/eliminar/{cedulaUsuario}")
	public String eliminarUsuario(@PathVariable("cedulaUsuario") Long cedulaUsuario) {
		usuarioDAO.deleteById(cedulaUsuario);
		return "ELIMINADO";

	}

	@PutMapping("/actualizar")
	public String actualizarUsuario(@RequestBody Usuario usuario) {
		try {
			if (!usuario.getPasswordUsuario().equals("")) {
				Argon2 argon = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2id);
				usuario.setPasswordUsuario(argon.hash(1, 1024, 2, usuario.getPasswordUsuario().toCharArray()));
				usuarioDAO.save(usuario);
			} else {
				usuario.setPasswordUsuario(usuarioDAO.getById(usuario.getCedulaUsuario()).getPasswordUsuario());
				usuarioDAO.save(usuario);
			}
			return "ACTUALIZADO";
		} catch (ConstraintViolationException e) {
			// System.err.print(e.getMessage());
			return e.getMessage();
		} catch (Exception e) {
			return "ERROR GENERAL : " + e.getMessage();
		}
	}
}
