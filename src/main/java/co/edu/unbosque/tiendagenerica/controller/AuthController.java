package co.edu.unbosque.tiendagenerica.controller;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.edu.unbosque.tiendagenerica.model.Usuario;
import co.edu.unbosque.tiendagenerica.utils.JWTUtil;
import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;

@RestController
@RequestMapping("api")
public class AuthController {

	@Autowired
	EntityManager entityManager;

	@Autowired
	JWTUtil jwtUtil;

	@PostMapping("/login")
	public String login(@RequestBody Usuario usuario) {
		Argon2 argon = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2id);
		String sql = "FROM Usuario WHERE nickUsuario = :nickUsuario";
		@SuppressWarnings("unchecked")
		List<Usuario> usuarios = entityManager.createQuery(sql).setParameter("nickUsuario", usuario.getNickUsuario())
				.getResultList();
		Usuario user = (usuarios.isEmpty()) ? null : usuarios.get(0);
		return (user != null && argon.verify(user.getPasswordUsuario(), usuario.getPasswordUsuario().toCharArray()))
				? "{\"token\" : " + "\"" + jwtUtil.create(String.valueOf(user.getCedulaUsuario()), user.getNickUsuario())
						+ "\"" + ", \"nombreCUsuario\" : " + "\"" + user.getNombreCUsuario() + "\"" + ", \"cedula\" : "
						+ "\"" + user.getCedulaUsuario() + "\"" + ", \"nickUsuario\" : " + "\"" + user.getNickUsuario()
						+ "\"}"
				: "FAIL";

	}
}
