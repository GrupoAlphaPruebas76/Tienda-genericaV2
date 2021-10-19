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

async function registrarUsuario() {
	let datos = {};
	datos.cedulaUsuario = document.getElementById('txtCedulaUsuario').value;
	datos.emailUsuario = document.getElementById('txtEmailUsuario').value;
	datos.nickUsuario = document.getElementById('txtNickUsuario').value;
	datos.nombreCUsuario = document.getElementById('txtNombreCUsuario').value;
	datos.passwordUsuario = document.getElementById('txtPasswordUsuario').value;

	let passwordConfirm = document.getElementById('txtPasswordUsuarioRepeat').value;

	if (passwordConfirm != datos.passwordUsuario) {
		Swal.fire({
			icon: 'error',
			tittle: 'Oops...',
			text: 'Las contraseñas no coinciden'
	});
		return;
	}

	const request = await fetch('api/usuarios/guardar', {
		method: 'POST',
		headers: getHeaders(),
		body: JSON.stringify(datos)
	});
	const response = await request.text();
	if (response.includes('AGREGADO')) {
		Swal.fire({
			icon: 'success',
			tittle:'La cuenta fue creada con exito',
			showConfirmButton: false,
		});
		location.reload();
	} else {
		if (response.includes('usuarios.UK_dqepd9dx5ubsh89wj99uimqw')) {
			Swal.fire({
				icon: 'error',
				tittle: 'Oops...',
				text:'El nombre de usuario ya esta en uso'});
		} if (response.includes('usuarios.UK_t9qm9g9sbjvhupwktuwdr499w')) {
			Swal.fire({
				icon: 'error',
				tittle: 'Oops...',
				text:'La cedula ya se encuentra registrada'});
		} if (response.includes('usuarios.UK_bdp41y8e8un0nsxowhgsl783s')) {
			Swal.fire({
				icon: 'error',
				tittle: 'Oops...',
				text:'El correo ya se encuentra registrado',
			});
		}
	}
}
