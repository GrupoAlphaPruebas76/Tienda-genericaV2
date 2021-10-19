package co.edu.unbosque.tiendagenerica.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import co.edu.unbosque.tiendagenerica.model.Venta;

public interface VentaDAO extends JpaRepository<Venta, Long> {

}
