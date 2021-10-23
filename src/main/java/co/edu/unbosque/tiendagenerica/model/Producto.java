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

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

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
@DynamicInsert
@JsonIgnoreProperties(value = { "hibernateLazyInitializer" })
public class Producto {
	/*
	// Metodo añadido para leer parametros en archivo CSV...
	public Producto(long parseLong, double parseDouble, long parseLong2, String string, double parseDouble2,
			double parseDouble3) {
		// TODO Auto-generated constructor stub
	} */

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "codigo_producto", unique = true)
	@ColumnDefault("'N/A'")
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
