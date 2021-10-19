package co.edu.unbosque.tiendagenerica.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import co.edu.unbosque.tiendagenerica.model.Cliente;

public interface ClienteDAO extends JpaRepository<Cliente, Long>{

}
