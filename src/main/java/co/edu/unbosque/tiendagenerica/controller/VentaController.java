package co.edu.unbosque.tiendagenerica.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.edu.unbosque.tiendagenerica.dao.VentaDAO;
import co.edu.unbosque.tiendagenerica.model.Venta;

@RestController
@RequestMapping(value = "/api/ventas")
public class VentaController {

	@Autowired
	private VentaDAO ventaDAO;

	@PostMapping(value = "/guardar")
	public String guardarVenta(@RequestBody Venta venta) {
		try {
			ventaDAO.save(venta);
			return "AGREGADO";
		} catch (Exception e) {
			System.err.println(e.getMessage());
			return "EXCEPTION: " + e.getMessage();
		}
	}

	@GetMapping(value = "/listar")
	public List<Venta> obtenerVentas() {
		return ventaDAO.findAll();
	}
	
	@DeleteMapping (value = "/eliminar/{codigo_venta}")
	public String eliminarVenta(@PathVariable ("codigo_venta") Long codigoVenta) {
		try {
			ventaDAO.deleteById(codigoVenta);
			return "ELIMINADO";
		}catch(Exception e) {
			System.err.println(e.getMessage());
			return "EXCEPTION: " + e.getMessage();
		}
	}
}
