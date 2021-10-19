package co.edu.unbosque.tiendagenerica.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import co.edu.unbosque.tiendagenerica.model.Producto;

public interface ProductoDAO extends JpaRepository<Producto, Long>{

}
