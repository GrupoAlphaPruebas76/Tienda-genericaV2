package co.edu.unbosque.tiendagenerica.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "ventas")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(value = { "hibernateLazyInitializer" })
public class Venta {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "codigo_venta",  unique = true)
	private long codigoVenta;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "cedula_cliente", referencedColumnName = "cedula_cliente", insertable = false, updatable = false)
	Cliente cliente;
	@Column(name = "cedula_cliente", nullable = false)
	private long cedulaCliente;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "cedula_usuario", referencedColumnName = "cedula_usuario", insertable = false, updatable = false)
	Usuario usuario;
	@Column(name = "cedula_usuario", nullable = false)
	private long cedulaUsuario;

	@Column(name = "ivaventa")
	private double ivaVenta;

	@Column(name = "total_venta")
	private double totalVenta;

	@Column(name = "valor_venta")
	private double valorVenta;

}
