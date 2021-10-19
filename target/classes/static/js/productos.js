$(document).ready(function () {

    var tablaProductos = $('#tablaProductos').DataTable({
        "language": { "url": "//cdn.datatables.net/plug-ins/1.10.15/i18n/Spanish.json" },
        "columns": [
            { "data": "codigoProducto" },
            { "data": "nombreProducto" },
            { "data": "precioCompra" },
            { "data": "precioVenta" },
            { "data": "ivaCompra" },
            { "data": "nombreProveedor" },
            { "data": "nitProveedor" },
            {
                defaultContent: '<button class="btnEditar btn btn-success" data-toggle="modal" data-target="#update_user_modal">Editar</button>&nbsp;&nbsp;<button class="btnBorrar btn btn-danger" type="button">Borrar</button>'
            }
        ],
    })

    GetProductosList();

    // Obtener lista de clientes
    function GetProductosList() {
        return $.ajax({
            url: '/api/productos/listar',
            type: 'get',
            dataType: "script",
            data: { authorization: localStorage.token },
            success: function (response) {
                const productos = JSON.parse(response);
                //console.log(usuarios); //<-aqui hago un console para ver lo que devuelve
                productos.forEach(producto => {
                    tablaProductos.row.add({
                        "codigoProducto": producto.codigoProducto,
                        "nombreProducto": producto.nombreProducto,
                        "precioCompra": producto.precioCompra,
                        "precioVenta": producto.precioVenta,
                        "ivaCompra": producto.ivaCompra,
                        "nombreProveedor": producto.proveedor.nombreProveedor,
                        "nitProveedor": producto.proveedor.nitProveedor,
                    }).draw();
                });
            }
        });
    }

    //Funcion asignada al boton editar
    $('#tablaProductos tbody').on('click', '.btnEditar', function () {
        var row = $(this).closest('tr');
        //var cedulaUsuario = tablaUsuarios.row(row).data().cedulaUsuario;
        //var nombreCUsuario = tablaUsuarios.row(row).data().nombreCUsuario;
        //var emailUsuario = tablaUsuarios.row(row).data().emailUsuario;
        //var nickUsuario = tablaUsuarios.row(row).data().nickUsuario;
        $('#txtCodigoProductoUpdate').val(function () {
            return tablaProductos.row(row).data().codigoProducto;

        });
        $('#txtNombreProductoUpdate').val(function () {
            return tablaProductos.row(row).data().nombreProducto;

        });
        $('#txtPrecioCompraUpdate').val(function () {
            return tablaProductos.row(row).data().precioCompra;

        });
        $('#txtPrecioVentaUpdate').val(function () {
            return tablaProductos.row(row).data().precioVenta;
        });
        $('#txtIVAProductoUpdate').val(function () {
            return tablaProductos.row(row).data().ivaCompra;
        });
        $('#txtNitProveedorUpdate').val(function () {
            return tablaProductos.row(row).data().nitProveedor;
        });
        $('#txtNombreProveedorUpdate').val(function () {
            return tablaProductos.row(row).data().nombreProveedor;
        });
    });

    //Funcion asignada al boton eliminar
    $('#tablaProductos tbody').on('click', '.btnBorrar', function () {
        var row = $(this).closest('tr');
        var codigo = tablaProductos.row(row).data().codigoProducto;
        eliminarProducto(codigo);
    });
});

function getHeaders() {
    return {
        'Accept': 'application/json',
        'Content-Type': 'application/json',
        'Authorization': localStorage.token
    };
}

async function eliminarProducto(codigo) {
    if (!confirm("¿Desea eliminar este Producto?")) {
        return;
    }
    const request = await fetch('api/productos/eliminar/' + codigo, {
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

async function actualizarProducto() {
    let datos = {};
    datos.codigoProducto = document.getElementById('txtCodigoProductoUpdate').value;
    datos.nombreProducto = document.getElementById('txtNombreProductoUpdate').value;
    datos.precioCompra = document.getElementById('txtPrecioCompraUpdate').value;
    datos.precioVenta = document.getElementById('txtPrecioVentaUpdate').value;
    datos.ivaCompra = document.getElementById('txtIVAProductoUpdate').value;
    datos.nitProveedor = document.getElementById('txtNitProveedorUpdate').value;

    const request = await fetch('api/productos/actualizar', {
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