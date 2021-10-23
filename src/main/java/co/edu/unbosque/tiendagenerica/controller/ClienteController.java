package co.edu.unbosque.tiendagenerica.controller;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import co.edu.unbosque.tiendagenerica.dao.ClienteDAO;
import co.edu.unbosque.tiendagenerica.model.Cliente;
import co.edu.unbosque.tiendagenerica.model.Proveedor;
import co.edu.unbosque.tiendagenerica.utils.JWTUtil;
import io.jsonwebtoken.MalformedJwtException;

@RestController
@RequestMapping(value = "api/clientes")
public class ClienteController {
	@Autowired
	private ClienteDAO clienteDAO;

	@Autowired
	EntityManager entityManager;

	@Autowired
	private JWTUtil jwtUtil;

	@GetMapping(value = "/{cedula_cliente}")
	public Cliente obtenerCliente(@PathVariable Long cedula_cliente) {
		return clienteDAO.existsById(cedula_cliente) ? clienteDAO.getById(cedula_cliente) : null;
	}

	@PostMapping("/guardar_lista")
	public String guardarListado(@RequestBody ArrayList<Cliente> listado) {
		if (!listado.isEmpty()) {
			for (Cliente prod : listado) {
				clienteDAO.save(prod);
			}
			return "EXITO";
		} else {
			return "VACIO";
		}
	}

	@PostMapping("/guardar") // Request convierte en un objeto Java desde un JSon
	public String guardarCliente(@RequestBody Cliente cliente, @RequestHeader(value = "Authorization") String token) {
		try {
			String nick = jwtUtil.getValue(token);
			if (nick.equalsIgnoreCase("admin")) {
				clienteDAO.save(cliente);
				return "AGREGADO";
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

	@GetMapping("/listar")
	public List<Cliente> listarCliente() {
		try {
			return clienteDAO.findAll();
		} catch (Exception e) {
			// TODO: handle exception
			return null;
		}
	}

	/*
	 * @DeleteMapping("/eliminar/{id}") public void
	 * eliminarUsuario(@PathVariable("id") Long id) { usuarioDAO.deleteById(id); }
	 */

	@DeleteMapping("/eliminar/{cedula_cliente}")
	public String eliminarProveedor(@PathVariable("cedula_cliente") Long cedula_cliente,
			@RequestHeader(value = "Authorization") String token) {
		try {
			String nick = jwtUtil.getValue(token);
			if (nick.equalsIgnoreCase("admin")) {
				clienteDAO.deleteById(cedula_cliente);
				return "ELIMINADO";
			} else {
				return "NO_ADMIN";
			}
		} catch (MalformedJwtException mjwt) {
			System.err.print(mjwt.getMessage());
			return "TOKEN_ERROR";
		}
	}

	@PutMapping("/actualizar")
	public String actualizarProveedor(@RequestBody Cliente cliente,
			@RequestHeader(value = "Authorization") String token) {
		try {
			String nick = jwtUtil.getValue(token);
			if (nick.equalsIgnoreCase("admin")) {
				clienteDAO.save(cliente);
				return "ACTUALIZADO";
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
