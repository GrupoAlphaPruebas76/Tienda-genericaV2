package co.edu.unbosque.tiendagenerica.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import co.edu.unbosque.tiendagenerica.dao.UsuarioDAO;
import co.edu.unbosque.tiendagenerica.model.Usuario;
import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;

@Component
public class DataInicial {
	
	@Autowired
	private UsuarioDAO usuarioDAO;
	
	@EventListener
	public void appReady(ApplicationReadyEvent evt) {
		Usuario admin = new Usuario(1l,"ADMINISTRADOR", "admin@mail.com", "admin", "admin123");
		Argon2 argon = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2id);
		admin.setPasswordUsuario(argon.hash(1, 1024, 2, admin.getPasswordUsuario().toCharArray()));
		usuarioDAO.save(admin);
	}
}
