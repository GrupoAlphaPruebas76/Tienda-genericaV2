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
		alert('Las contraseñas no coinciden');
		return;
	}

	const request = await fetch('api/usuarios/guardar', {
		method: 'POST',
		headers: getHeaders(),
		body: JSON.stringify(datos)
	});
	const response = await request.text();
	if (response.includes('AGREGADO')) {
		alert('La cuenta fue creada con exito');
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
