$(document).ready(function() {

	// The event listener for the file upload
	document.getElementById('txtFileUpload')
		.addEventListener('change', upload, false);

	var tablaProveedores = $('#tablaProveedores').DataTable({
		"language": { "url": "//cdn.datatables.net/plug-ins/1.10.15/i18n/Spanish.json" },
		"columns": [
			{ "data": "nitProveedor" },
			{ "data": "nombreProveedor" },
			{ "data": "telefonoProveedor" },
			{ "data": "direccionProveedor" },
			{ "data": "ciudadProveedor" },
			{
				defaultContent: '<button class="btnEditar btn btn-success" data-toggle="modal" data-target="#update_user_modal">Editar</button>&nbsp;&nbsp;<button class="btnBorrar btn btn-danger" type="button">Borrar</button>'
			}
		],
	})

	GetProveedorList();

	// Obtener lista de clientes
	function GetProveedorList() {
		return $.ajax({
			url: '/api/proveedores/listar',
			type: 'get',
			dataType: "script",
			data: { authorization: localStorage.token },
			success: function(response) {
				const proveedores = JSON.parse(response);
				//console.log(usuarios); //<-aqui hago un console para ver lo que devuelve
				proveedores.forEach(proveedor => {
					tablaProveedores.row.add({
						"nitProveedor": proveedor.nitProveedor,
						"nombreProveedor": proveedor.nombreProveedor,
						"telefonoProveedor": proveedor.telefonoProveedor,
						"direccionProveedor": proveedor.direccionProveedor,
						"ciudadProveedor": proveedor.ciudadProveedor,
					}).draw();
				});

			}
		});
	}

	//Funcion asignada al boton editar
	$('#tablaProveedores tbody').on('click', '.btnEditar', function() {
		var row = $(this).closest('tr');
		//var nitUsuario = tablaUsuarios.row(row).data().nitUsuario;
		//var nombreCUsuario = tablaUsuarios.row(row).data().nombreCUsuario;
		//var emailUsuario = tablaUsuarios.row(row).data().emailUsuario;
		//var nickUsuario = tablaUsuarios.row(row).data().nickUsuario;
		$('#txtNitProveedorUpdate').val(function() {
			return tablaProveedores.row(row).data().nitProveedor;

		});
		$('#txtNombreProveedorUpdate').val(function() {
			return tablaProveedores.row(row).data().nombreProveedor;

		});
		$('#txtTelefonoProveedorUpdate').val(function() {
			return tablaProveedores.row(row).data().telefonoProveedor;

		});
		$('#txtDireccionProveedorUpdate').val(function() {
			return tablaProveedores.row(row).data().direccionProveedor;

		});
		$('#txtCiudadProveedorUpdate').val(function() {
			return tablaProveedores.row(row).data().ciudadProveedor;

		});
	});

	//Funcion asignada al boton eliminar
	$('#tablaProveedores tbody').on('click', '.btnBorrar', function() {
		var row = $(this).closest('tr');
		var nit = tablaProveedores.row(row).data().nitProveedor;
		eliminarProveedor(nit);
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
			//alert(jsonobject)
			$.ajax({
				url: '/api/proveedores/guardar_lista',
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

async function eliminarProveedor(nitProveedor) {
	if (!confirm("¿Desea eliminar este proveedor?")) {
		return;
	}
	const request = await fetch('api/proveedores/eliminar/' + nitProveedor, {
		method: 'DELETE',
		headers: getHeaders()
	});
	const response = await request.text();
	if (response.includes('ADMIN')) {
		alert('SOLO ADMINISTRADOR PUEDE ELIMINAR REGISTROS');
	} else if (response.includes('ELIMINADO')) {
		location.reload();
	} else {
		alert(response);
	}
}

async function actualizarProveedor() {
	let datos = {};
	datos.nitProveedor = document.getElementById('txtNitProveedorUpdate').value;
	datos.nombreProveedor = document.getElementById('txtNombreProveedorUpdate').value;
	datos.telefonoProveedor = document.getElementById('txtTelefonoProveedorUpdate').value;
	datos.direccionProveedor = document.getElementById('txtDireccionProveedorUpdate').value;
	datos.ciudadProveedor = document.getElementById('txtCiudadProveedorUpdate').value;

	const request = await fetch('api/proveedores/actualizar', {
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
		alert('La cuenta fue actualizada con exito');
		location.reload();
	} else {
		alert("Revise los campos");
	}
}