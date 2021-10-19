$(document).ready(function() {

	cargarTablar();
	$('#tablaUsuarios').DataTable();

});

async function cargarTablar() {
	const request = await fetch('api/usuarios/listar', {
		method: 'GET',
		headers: getHeaders()
	});

	const usuarios = await request.json();

	let filasTablaUsuarios = '';

	for (let usuario of usuarios) {
		let botonEliminar = '<a href="#" onclick = "eliminarUsuario('+usuario.idUsuario+')" class="btn btn-danger btn-circle btn-sm"><i class="fas fa-trash"></i></a>';
		let filaTablaUsuario =
			'<tr><td>' + usuario.idUsuario +
			'</td><td>' + usuario.cedulaUsuario +
			'</td><td>' + usuario.nombreCUsuario +
			'</td><td>' + usuario.emailUsuario +
			'</td><td>' + usuario.nickUsuario +
			'</td><td>' + botonEliminar + '</td></tr>';
		filasTablaUsuarios += filaTablaUsuario;
	}

	document.querySelector('#tablaUsuarios tbody').outerHTML = filasTablaUsuarios;

}
function getHeaders (){
	return {
			'Accept': 'application/json',
			'Content-Type': 'application/json',
			'Authorization': localStorage.token
		};
}

async function eliminarUsuario(id){
	if(!confirm("¿Desea eliminar este usuario?")){
		return;
	}
	const request = await fetch('api/usuarios/eliminar/' + id, {
		method: 'DELETE',
		headers: getHeaders()
	});
	location.reload();
}
