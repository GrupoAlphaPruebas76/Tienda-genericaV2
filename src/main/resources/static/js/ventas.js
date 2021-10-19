$(document).ready(function () {

    var tablaVentas = $('#tablaVentas').DataTable({
        "language": { "url": "//cdn.datatables.net/plug-ins/1.10.15/i18n/Spanish.json" },
        "columns": [
            { "data": "codigoVenta" },
            { "data": "cedulaCliente" },
            { "data": "cedulaUsuario" },
            { "data": "ivaVenta" },
            { "data": "valorVenta" },
            { "data": "totalVenta" },
            {
                defaultContent: '<button class="btnEditar btn btn-success" data-toggle="modal" data-target="#update_user_modal">Editar</button>&nbsp;&nbsp;<button class="btnBorrar btn btn-danger" type="button">Borrar</button>'
            }
        ],
    })

    GetVentasList();

    // Obtener lista de clientes
    function GetVentasList() {
        return $.ajax({
            url: '/api/ventas/listar',
            type: 'get',
            dataType: "script",
            data: { authorization: localStorage.token },
            success: function (response) {
                const ventas = JSON.parse(response);
                //console.log(usuarios); <-aqui hago un console para ver lo que devuelve
                ventas.forEach(venta => {
                    tablaVentas.row.add({
                        "codigoVenta": venta.codigoVenta,
                        "cedulaCliente": venta.cedulaCliente,
                        "cedulaUsuario": venta.cedulaUsuario,
                        "ivaVenta": venta.ivaVenta,
                        "valorVenta": venta.valorVenta,
                        "totalVenta": venta.totalVenta,
                    }).draw();
                });
            }
        });
    }

    //Funcion asignada al boton editar
    $('#tablaVentas tbody').on('click', '.btnEditar', function () {
        var row = $(this).closest('tr');
        //var cedulaUsuario = tablaUsuarios.row(row).data().cedulaUsuario;
        //var nombreCUsuario = tablaUsuarios.row(row).data().nombreCUsuario;
        //var emailUsuario = tablaUsuarios.row(row).data().emailUsuario;
        //var nickUsuario = tablaUsuarios.row(row).data().nickUsuario;
        $('#txtCodigoVentaUpdate').val(function () {
            return tablaVentas.row(row).data().codigoVenta;

        });
        $('#txtCedulaClienteUpdate').val(function () {
            return tablaVentas.row(row).data().cedulaCliente;

        });
        $('#txtCedulaUsuarioUpdate').val(function () {
            return tablaVentas.row(row).data().cedulaUsuario;

        });
        $('#txtIVAVentaUpdate').val(function () {
            return tablaVentas.row(row).data().ivaVenta;
        });
        $('#txtValorVentaUpdate').val(function () {
            return tablaVentas.row(row).data().valorVenta;
        });       
        $('#txtTotalVentaUpdate').val(function () {
            return tablaVentas.row(row).data().totalVenta;
        });
    });

    //Funcion asignada al boton eliminar
    $('#tablaVentas tbody').on('click', '.btnBorrar', function () {
        var row = $(this).closest('tr');
        var codigo = tablaVentas.row(row).data().codigoVenta;
        eliminarVenta(codigo);
    });
});

function getHeaders() {
    return {
        'Accept': 'application/json',
        'Content-Type': 'application/json',
        'Authorization': localStorage.token
    };
}

async function eliminarVenta(codigo) {
    if (!confirm("¿Desea eliminar este venta?")) {
        return;
    }
    const request = await fetch('api/ventas/eliminar/' + codigo, {
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

async function actualizarVenta() {
    let datos = {};
    datos.codigoVenta = document.getElementById('txtCodigoVentaUpdate').value;
    datos.cedulaCliente = document.getElementById('txtCedulaClienteUpdate').value;
    datos.cedulaUsuario = document.getElementById('txtCedulaUsuarioUpdate').value;
    datos.ivaVenta = document.getElementById('txtIVAVentaUpdate').value;
    datos.valorVenta = document.getElementById('txtValorVenta').value;
    datos.totalVenta = document.getElementById('txtTotalVentaUpdate').value;

    const request = await fetch('api/ventas/actualizar', {
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