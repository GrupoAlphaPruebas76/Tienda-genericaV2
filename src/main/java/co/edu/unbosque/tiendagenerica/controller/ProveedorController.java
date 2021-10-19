package co.edu.unbosque.tiendagenerica.controller;

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

import co.edu.unbosque.tiendagenerica.dao.ProveedorDAO;
import co.edu.unbosque.tiendagenerica.model.Proveedor;
import co.edu.unbosque.tiendagenerica.utils.JWTUtil;
import io.jsonwebtoken.MalformedJwtException;

@RestController
@RequestMapping(value = "api/proveedores")
public class ProveedorController {
	@Autowired
	private ProveedorDAO proveedorDAO;

	@Autowired
	EntityManager entityManager;

	@Autowired
	private JWTUtil jwtUtil;

	@GetMapping(value = "/{nit_proveedor}")
	public Proveedor obtenerProveedor(@PathVariable Long nit_proveedor) {
		return proveedorDAO.existsById(nit_proveedor) ? proveedorDAO.getById(nit_proveedor) : null;
	}

	@PostMapping("/guardar") // Request convierte en un objeto Java desde un JSon
	public String guardarProveedor(@RequestBody Proveedor proveedor,
			@RequestHeader(value = "Authorization") String token) {
		try {
			String nick = jwtUtil.getValue(token);
			if (nick.equalsIgnoreCase("admin")) {
				proveedorDAO.save(proveedor);
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
	public List<Proveedor> listarProveedores() {
		try {
			return proveedorDAO.findAll();
		} catch (Exception e) {
			// TODO: handle exception
			return null;
		}
	}

	/*
	 * @DeleteMapping("/eliminar/{id}") public void
	 * eliminarUsuario(@PathVariable("id") Long id) { usuarioDAO.deleteById(id); }
	 */

	@DeleteMapping("/eliminar/{nit_proveedor}")
	public String eliminarProveedor(@PathVariable("nit_proveedor") Long nitProveedor,
			@RequestHeader(value = "Authorization") String token) {
		try {
			String nick = jwtUtil.getValue(token);
			if (nick.equalsIgnoreCase("admin")) {
				proveedorDAO.deleteById(nitProveedor);
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
	public String actualizarProveedor(@RequestBody Proveedor proveedor,
			@RequestHeader(value = "Authorization") String token) {
		try {
			String nick = jwtUtil.getValue(token);
			if (nick.equalsIgnoreCase("admin")) {
				proveedorDAO.save(proveedor);
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
