package co.edu.unbosque.tiendagenerica.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.edu.unbosque.tiendagenerica.dao.ProductoDAO;
import co.edu.unbosque.tiendagenerica.model.Producto;

@RestController
@RequestMapping(value = "api/productos")
public class ProductoController {

	@Autowired
	private ProductoDAO productoDAO;

	@PostMapping("/guardar_lista")
	public String guardarListado(@RequestBody ArrayList<Producto> listado) {
		if (!listado.isEmpty()) {
			for (Producto prod : listado) {
				productoDAO.save(prod);
			}
			return "EXITO";
		} else {
			return "VACIO";
		}
	}

	@PostMapping(value = "/guardar")
	public String agregarProducto(@RequestBody Producto producto) {
		try {
			productoDAO.save(producto);
			return "AGREGADO";
		} catch (Exception e) {
			System.err.println(e.getMessage());
			return "EXCEPTION: " + e.getMessage();
		}
	}

	@PutMapping(value = "/actualizar")
	public String actualizarProducto(@RequestBody Producto producto) {
		try {
			productoDAO.save(producto);
			return "ACTUALIZADO";
		} catch (Exception e) {
			System.err.println(e.getMessage());
			return "EXCEPTION: " + e.getMessage();
		}
	}

	@DeleteMapping(value = "/eliminar/{codigo_producto}")
	public String eliminarProducto(@PathVariable("codigo_producto") Long codigoProducto) {
		try {
			productoDAO.deleteById(codigoProducto);
			return "ELIMINADO";
		} catch (Exception e) {
			System.err.println(e.getMessage());
			return "EXCEPTION: " + e.getMessage();
		}
	}

	@GetMapping(value = "/listar")
	public List<Producto> obtenerProductos() {
		return productoDAO.findAll();
	}
}
