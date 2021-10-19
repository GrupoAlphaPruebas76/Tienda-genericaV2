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
@Table(name = "proveedores")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(value = { "hibernateLazyInitializer" })
public class Proveedor {

	@Id
	@Column(name = "nitproveedor", unique = true)
	private long nitProveedor;

	@Column(name = "ciudad_proveedor")
	private String ciudadProveedor;

	@Column(name = "direccion_proveedor")
	private String direccionProveedor;

	@Column(name = "nombre_proveedor")
	private String nombreProveedor;

	@Column(name = "telefono_proveedor")
	private String telefonoProveedor;
}
