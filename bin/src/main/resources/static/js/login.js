$(document).ready(function() {
	//On ready
});

async function iniciarSesion() {
	let datos = {};
	datos.nickUsuario = document.getElementById('txtLoginNickName').value;
	datos.passwordUsuario = document.getElementById('txtLoginPassword').value;

	const request = await fetch('api/login', {
		method: 'POST',
		headers: {
			'Accept': 'application/json',
			'Content-Type': 'application/json'
		},
		body: JSON.stringify(datos)
	});
	const response = await request.text();
	//alert(response);
	if (response.includes('FAIL')) {
		alert('Las credenciales son incorrectas, intente nuevamente');
	} else {
		let loginData = JSON.parse(response);
		localStorage.token = loginData.token;
		localStorage.nickUsuario = datos.nickUsuario;
		$(location).attr('href', 'home.html');
	}
}
