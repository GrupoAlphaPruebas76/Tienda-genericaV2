$(document).ready(function() {
	//On ready
});

function getHeaders() {
	return {
		'Accept': 'application/json',
		'Content-Type': 'application/json',
		'Authorization': localStorage.token
	}
}

async function registrarVenta() {
	let datos = {};
    datos.codigoVenta = document.getElementById('txtCodigoVentaUpdate').value;
    datos.cedulaCliente = document.getElementById('txtCedulaClienteUpdate').value;
    datos.cedulaUsuario = document.getElementById('txtCedulaUsuarioUpdate').value;
    datos.ivaVenta = document.getElementById('txtIVAVentaUpdate').value;
    datos.valorVenta = document.getElementById('txtValorVenta').value;
    datos.totalVenta = document.getElementById('txtTotalVentaUpdate').value;

	const request = await fetch('api/ventas/guardar', {
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
