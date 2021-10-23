$(document).ready(function() {
	//On ready

	/**
	 * Lista desplegable para autocompletar un campo
	 */
	var url = '/api/proveedores/listar';
	$.ajax({
		url: url,
		type:"GET",
		datatype:"json",
		success: function(data){
			//console.log(data);
			/*
			var fuseOptions = {keys: ["nitProveedor", "ciudadProveedor"]};
			var opciones = {display: "nitProveedor", key:"ciudadProveedor", fuseOptions: fuseOptions};
			// boton al cual le asignare el autocompletado
			$("#txtNitProveedor").fuzzyComplete(data, opciones);
			*/
			$("#txtNitProveedor").fuzzyComplete(data);
		}
	});
});

function getHeaders() {
	return {
		'Accept': 'application/json',
		'Content-Type': 'application/json',
		'Authorization': localStorage.token
	}
}

async function registrarProducto() {
	let datos = {};
    datos.codigoProducto = document.getElementById('txtCodigoProducto').value;
    datos.nombreProducto = document.getElementById('txtNombreProducto').value;
    datos.precioCompra = document.getElementById('txtPrecioCompra').value;
    datos.precioVenta = document.getElementById('txtPrecioVenta').value;
    datos.ivaCompra = document.getElementById('txtIVAProducto').value;
    datos.nitProveedor = document.getElementById('txtNitProveedor').value;

	const request = await fetch('api/productos/guardar', {
		method: 'POST',
		headers: getHeaders(),
		body: JSON.stringify(datos)
	});
	const response = await request.text();
	if (response.includes('AGREGADO')) {
		alert('La cuenta fue creada con exito');
		location.reload();
	} else {
		Swal.fire({
			icon: 'error',
			tittle: 'Oops...',
			text: 'Por favor, revise los datos ingresados',
		});
	}
}
