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

async function registrarProveedor() {
	let datos = {};
	datos.nitProveedor = document.getElementById('txtNitProveedor').value;
	datos.nombreProveedor = document.getElementById('txtNombreProveedor').value;
	datos.telefonoProveedor = document.getElementById('txtTelefonoProveedor').value;
	datos.direccionProveedor = document.getElementById('txtDireccionProveedor').value;
	datos.ciudadProveedor = document.getElementById('txtCiudadProveedor').value;
	
	const request = await fetch('api/proveedores/guardar', {
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
