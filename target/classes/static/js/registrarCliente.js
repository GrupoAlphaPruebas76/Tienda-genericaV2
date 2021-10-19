$(document).ready(function () {
	//On ready
});

function getHeaders() {
	return {
		'Accept': 'application/json',
		'Content-Type': 'application/json',
		'Authorization': localStorage.token
	}
}

async function registrarCliente() {
	let datos = {};
	datos.cedulaCliente = document.getElementById('txtCedulaCliente').value;
	datos.nombreCliente = document.getElementById('txtNombreCliente').value;
	datos.emailCliente = document.getElementById('txtEmailCliente').value;
	datos.telefonoCliente = document.getElementById('txtTelefonoCliente').value;
	datos.direccionCliente = document.getElementById('txtDireccionCliente').value;

	const request = await fetch('api/clientes/guardar', {
		method: 'POST',
		headers: getHeaders(),
		body: JSON.stringify(datos)
	});
	const response = await request.text();
	if (response.includes('AGREGADO')) {
		Swal.fire({
			icon: 'success',
			tittle: 'La cuenta fue creada con exito',
			showConfirmButton: false,
		});
		location.reload(); 
	}else {
		{
			Swal.fire({
				icon: 'error',
				tittle: 'Oops...',
				text: 'Por favor, revise los datos ingresados',
			});
		}
	}
}