$(document).ready(function() {

	$('#userDropdown').val("Test");

	var tablaUsuarios = $('#tablaUsuarios').DataTable({
		//"language": { "url": "//cdn.datatables.net/plug-ins/1.10.15/i18n/Spanish.json" },
		"columns": [
			{ "data": "cedulaUsuario" },
			{ "data": "nombreCUsuario" },
			{ "data": "emailUsuario" },
			{ "data": "nickUsuario" },
			{
				defaultContent: '<button class="btnEditar btn btn-success" data-toggle="modal" data-target="#update_user_modal">Editar</button>&nbsp;&nbsp;<button class="btnBorrar btn btn-danger" type="button">Borrar</button>'
			}
		],
	})

	GetUserList();

	// Obtener lista de usuarios
	function GetUserList() {
		return $.ajax({
			url: '/api/usuarios/listar',
			type: 'get',
			dataType: "script",
			data: { authorization: localStorage.token },
			success: function(response) {
				const usuarios = JSON.parse(response);
				//console.log(usuarios); //<-aqui hago un console para ver lo que devuelve
				usuarios.forEach(user => {
					tablaUsuarios.row.add({
						"cedulaUsuario": user.cedulaUsuario,
						"nombreCUsuario": user.nombreCUsuario,
						"emailUsuario": user.emailUsuario,
						"nickUsuario": user.nickUsuario,
					}).draw();
					if(user.nickUsuario == 'admin'){
						$('#userDropdown').val(user.nombreCUsuario);
					}
				});
				
			}
		});
	}

	//Funcion asignada al boton editar
	$('#tablaUsuarios tbody').on('click', '.btnEditar', function() {
		var row = $(this).closest('tr');
		//var cedulaUsuario = tablaUsuarios.row(row).data().cedulaUsuario;
		//var nombreCUsuario = tablaUsuarios.row(row).data().nombreCUsuario;
		//var emailUsuario = tablaUsuarios.row(row).data().emailUsuario;
		//var nickUsuario = tablaUsuarios.row(row).data().nickUsuario;
		$('#txtCedulaUsuarioUpdate').val(function(){
			return tablaUsuarios.row(row).data().cedulaUsuario;
			
		});
		$('#txtNombreCUsuarioUpdate').val(function(){
			return tablaUsuarios.row(row).data().nombreCUsuario;
			
		});
		$('#txtEmailUsuarioUpdate').val(function(){
			return tablaUsuarios.row(row).data().emailUsuario;
			
		});
		$('#txtNickUsuarioUpdate').val(function(){
			return tablaUsuarios.row(row).data().nickUsuario;
			
		});
	});

	//Funcion asignada al boton eliminar
	$('#tablaUsuarios tbody').on('click', '.btnBorrar', function() {
		var row = $(this).closest('tr');
		var cedula = tablaUsuarios.row(row).data().cedulaUsuario;
		eliminarUsuario(cedula);
	});
});

function getHeaders() {
	return {
		'Accept': 'application/json',
		'Content-Type': 'application/json',
		'Authorization': localStorage.token
	};
}

async function eliminarUsuario(cedulaUsuario) {
	if (!confirm("¿Desea eliminar este usuario?")) {
		return;
	}
	const request = await fetch('api/usuarios/eliminar/' + cedulaUsuario, {
		method: 'DELETE',
		headers: getHeaders()
	});
	const response = await request.text();
	if(response.includes('ADMIN')){
		alert('NO SE PUEDE ELIMINAR AL ADMINISTRADOR');
	}else if (response.includes('ELIMINADO')) {
		location.reload();
	} else {
		alert(response);
	}
}

async function actualizarUsuario() {
	let datos = {};
	datos.cedulaUsuario = document.getElementById('txtCedulaUsuarioUpdate').value;
	datos.emailUsuario = document.getElementById('txtEmailUsuarioUpdate').value;
	datos.nickUsuario = document.getElementById('txtNickUsuarioUpdate').value;
	datos.nombreCUsuario = document.getElementById('txtNombreCUsuarioUpdate').value;
	datos.passwordUsuario = document.getElementById('txtPasswordUsuarioUpdate').value;

	let passwordConfirm = document.getElementById('txtPasswordUsuarioRepeatUpdate').value;

	if (passwordConfirm != datos.passwordUsuario) {
		alert('Las contraseñas no coinciden');
		return;
	}

	const request = await fetch('api/usuarios/actualizar', {
		method: 'PUT',
		headers: {
			'Accept': 'application/json',
			'Content-Type': 'application/json',
			'Authorization': localStorage.token
		},
		body: JSON.stringify(datos)
	});
	const response = await request.text();
	if(response.includes('ADMIN')){
		alert('NO SE PUEDE ACTUALIZAR AL ADMINISTRADOR');
	}else if (response.includes('ACTUALIZADO')) {
		alert('La cuenta fue actualizada con exito');
		location.reload();
	} else {
		if (response.includes('usuarios.UK_dqepd9dx5ubsh89wj99uimqw')) {
			alert('EL NICK YA ESTA EN USO');
		} if (response.includes('usuarios.UK_t9qm9g9sbjvhupwktuwdr499w')) {
			alert('LA CEDULA YA SE ENCUENTRA REGISTRADA');
		} if (response.includes('usuarios.UK_bdp41y8e8un0nsxowhgsl783s')) {
			alert('EL CORREO YA SE ENCUENTRA REGISTRADO');
		}
	}
}