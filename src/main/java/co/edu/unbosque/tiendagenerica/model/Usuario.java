package co.edu.unbosque.tiendagenerica.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "usuarios")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(value = { "hibernateLazyInitializer" })
public class Usuario {

	@Id
	@Column(name = "cedula_usuario", unique = true)
	private long cedulaUsuario;

	@Column(name = "nombrec_usuario")
	private String nombreCUsuario;

	@Column(name = "email_usuario", unique = true)
	private String emailUsuario;

	@Column(name = "nick_usuario", unique = true)
	private String nickUsuario;

	@Column(name = "password_usuario")
	private String passwordUsuario;

}
