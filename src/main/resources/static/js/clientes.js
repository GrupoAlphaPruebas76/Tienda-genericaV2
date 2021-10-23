$(document).ready(function() {

	// The event listener for the file upload
	document.getElementById('txtFileUpload')
		.addEventListener('change', upload, false);

	var tablaClientes = $('#tablaClientes').DataTable({
		"language": { "url": "//cdn.datatables.net/plug-ins/1.10.15/i18n/Spanish.json" },
		"columns": [
			{ "data": "cedulaCliente" },
			{ "data": "nombreCliente" },
			{ "data": "emailCliente" },
			{ "data": "telefonoCliente" },
			{ "data": "direccionCliente" },
			{
				defaultContent: '<button class="btnEditar btn btn-success" data-toggle="modal" data-target="#update_user_modal">Editar</button>&nbsp;&nbsp;<button class="btnBorrar btn btn-danger" type="button">Borrar</button>'
			}
		],
	})

	GetClientList();

	// Obtener lista de clientes
	function GetClientList() {
		return $.ajax({
			url: '/api/clientes/listar',
			type: 'get',
			dataType: "script",
			data: { authorization: localStorage.token },
			success: function(response) {
				const clientes = JSON.parse(response);
				//console.log(usuarios); //<-aqui hago un console para ver lo que devuelve
				clientes.forEach(client => {
					tablaClientes.row.add({
						"cedulaCliente": client.cedulaCliente,
						"nombreCliente": client.nombreCliente,
						"emailCliente": client.emailCliente,
						"telefonoCliente": client.telefonoCliente,
						"direccionCliente": client.direccionCliente,
					}).draw();
				});

			}
		});
	}

	//Funcion asignada al boton editar
	$('#tablaClientes tbody').on('click', '.btnEditar', function() {
		var row = $(this).closest('tr');
		//var cedulaUsuario = tablaUsuarios.row(row).data().cedulaUsuario;
		//var nombreCUsuario = tablaUsuarios.row(row).data().nombreCUsuario;
		//var emailUsuario = tablaUsuarios.row(row).data().emailUsuario;
		//var nickUsuario = tablaUsuarios.row(row).data().nickUsuario;
		$('#txtCedulaClienteUpdate').val(function() {
			return tablaClientes.row(row).data().cedulaCliente;

		});
		$('#txtNombreClienteUpdate').val(function() {
			return tablaClientes.row(row).data().nombreCliente;

		});
		$('#txtEmailClienteUpdate').val(function() {
			return tablaClientes.row(row).data().emailCliente;

		});
		$('#txtTelefonoClienteUpdate').val(function() {
			return tablaClientes.row(row).data().telefonoCliente;

		});
		$('#txtDireccionClienteUpdate').val(function() {
			return tablaClientes.row(row).data().direccionCliente;

		});
	});

	//Funcion asignada al boton eliminar
	$('#tablaClientes tbody').on('click', '.btnBorrar', function() {
		var row = $(this).closest('tr');
		var cedula = tablaClientes.row(row).data().cedulaCliente;
		eliminarCliente(cedula);
	});
});
// Method that checks that the browser supports the HTML5 File API
function browserSupportFileUpload() {
	var isCompatible = false;
	if (window.File && window.FileReader
		&& window.FileList && window.Blob) {
		isCompatible = true;
	}
	return isCompatible;
}

// Method that reads and processes the selected file
function upload(evt) {
	if (!browserSupportFileUpload()) {
		alert('The File APIs are not fully supported in this browser!');
	} else {
		var data = null;
		var file = evt.target.files[0];
		var reader = new FileReader();
		reader.readAsText(file);
		reader.onload = function(event) {
			var csvData = event.target.result;
			//convert to JS array
			//data = $.csv.toArrays(csvData);
			var items = $.csv.toObjects(csvData);
			var jsonobject = JSON.stringify(items);
			alert(jsonobject)
			$.ajax({
				url: '/api/clientes/guardar_lista',
				type: 'POST',
				data: jsonobject,
				contentType: 'application/json',
				success: function(response) {
					alert(response)
				}
			});
			location.reload();
		};
		reader.onerror = function() {
			alert('Unable to read ' + file.fileName);
		};
	}
}

function getHeaders() {
	return {
		'Accept': 'application/json',
		'Content-Type': 'application/json',
		'Authorization': localStorage.token
	};
}

async function eliminarCliente(cedulaCliente) {
	if (!confirm("¿Desea eliminar este cliente?")) {
		return;
	}
	const request = await fetch('api/clientes/eliminar/' + cedulaCliente, {
		method: 'DELETE',
		headers: getHeaders()
	});
	const response = await request.text();
	if (response.includes('ADMIN')) {
		Swal.fire('SOLO ADMINISTRADOR PUEDE ELIMINAR REGISTROS');
	} else if (response.includes('ELIMINADO')) {
		location.reload();
	} else {
		alert(response);
	}
}

async function actualizarCliente() {
	let datos = {};
	datos.cedulaCliente = document.getElementById('txtCedulaClienteUpdate').value;
	datos.nombreCliente = document.getElementById('txtNombreClienteUpdate').value;
	datos.emailCliente = document.getElementById('txtEmailClienteUpdate').value;
	datos.telefonoCliente = document.getElementById('txtTelefonoClienteUpdate').value;
	datos.direccionCliente = document.getElementById('txtDireccionClienteUpdate').value;

	const request = await fetch('api/clientes/actualizar', {
		method: 'PUT',
		headers: {
			'Accept': 'application/json',
			'Content-Type': 'application/json',
			'Authorization': localStorage.token
		},
		body: JSON.stringify(datos)
	});
	const response = await request.text();
	if (response.includes('NO_ADMIN')) {
		alert('SOLO EL ADMINISTRADOR PUEDE ACTUALIZAR');
	} else if (response.includes('ACTUALIZADO')) {
		Swal.fire({
			icon: 'success',
			tittle: 'La cuenta fue actualizada con exito',
			showConfirmButton: false,
		});
		location.reload();
	} else {
		Swal.fire({
			icon: 'error',
			tittle: 'Oops...',
			text: 'Por favor, revise los datos ingresados',
		});
	}
}