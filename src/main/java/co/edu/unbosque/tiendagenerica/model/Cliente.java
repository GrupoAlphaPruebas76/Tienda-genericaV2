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
@Table(name = "clientes")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(value = { "hibernateLazyInitializer" })
public class Cliente {
	
	@Id
	@Column(name = "cedula_cliente", unique = true)
	private long cedulaCliente;

	@Column(name = "direccion_cliente")
	private String direccionCliente;
	
	@Column(name = "email_cliente", unique = true)
	private String emailCliente;
	
	@Column(name = "nombre_cliente")
	private String nombreCliente;

	@Column(name = "telefono_cliente")
	private String telefonoCliente;
	
}
