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
@Table(name = "productos")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(value = { "hibernateLazyInitializer" })
public class Producto {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "codigo_producto", unique = true)
	private long codigoProducto;

	@Column(name = "ivacompra")
	private double ivaCompra;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "nitproveedor", referencedColumnName = "nitproveedor", insertable = false, updatable = false)
	Proveedor proveedor;
	@Column(name = "nitproveedor")
	private long nitProveedor;

	@Column(name = "nombre_producto")
	private String nombreProducto;

	@Column(name = "precio_compra")
	private double precioCompra;

	@Column(name = "precio_venta")
	private double precioVenta;

}
