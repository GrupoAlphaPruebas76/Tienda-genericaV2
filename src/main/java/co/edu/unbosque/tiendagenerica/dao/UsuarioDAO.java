package co.edu.unbosque.tiendagenerica.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import co.edu.unbosque.tiendagenerica.model.Usuario;

public interface UsuarioDAO extends JpaRepository<Usuario, Long>{
	
}
