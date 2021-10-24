$(document).ready(function() {
	//On ready
	$.ajax({
		url: '/api/usuarios/listar',
		type:"GET",
		datatype:"json",
		success: function(data){
			$("#txtCedulaUsuario").fuzzyComplete(data);
		}
	});
	$.ajax({
		url: '/api/clientes/listar',
		type:"GET",
		datatype:"json",
		success: function(data){
			$("#txtCedulaCliente").fuzzyComplete(data);
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

async function registrarVenta() {
	let datos = {};
    datos.cedulaCliente = document.getElementById('txtCedulaCliente').value;
    datos.cedulaUsuario = document.getElementById('txtCedulaUsuario').value;
    datos.ivaVenta = document.getElementById('txtIVAVenta').value;
    datos.valorVenta = document.getElementById('txtValorVenta').value;
    datos.totalVenta = document.getElementById('txtTotalVenta').value;

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
