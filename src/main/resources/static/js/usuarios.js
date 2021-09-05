// Call the dataTables jQuery plugin
$(document).ready(function() {
  cargarUsuarios();
  $('#usuariosTable').DataTable();
});

async function cargarUsuarios() {
    const request = await fetch('api/usuarios', {
    method: 'GET',
    headers: {
        'Acept' : 'application/json',
        'Content-Type' : 'application/json'
    }
    });

    const usuarios = await request.json();

//    console.log(usuarios);
    let listadoHtml = ""

    for (let usuario of usuarios) {
        let botonElminar = '<a href="#" onclick="eliminarUsuario('+ usuario.id +')" class="btn btn-danger btn-circle btn-sm"><i class="fas fa-trash"></i></a>'
        let usuarioHtml = '<tr><td>' + usuario.nombre + ' ' + usuario.apellido + '</td>'
                        + '<td>' + usuario.email + '</td>'
                        + '<td>' + usuario.telefono + '</td>'
                        + '<td>' + botonElminar + '</td>'
                        + '</tr>'

        listadoHtml += usuarioHtml
    }

    document.querySelector('#usuariosTable tbody').outerHTML = listadoHtml
}

async function eliminarUsuario(id) {
    if (confirm("¿Desea eliminar el usuario " + id + "?")) {
        const request = await fetch('api/usuarios/' + id, {
            method: 'DELETE',
            headers: {
                'Acept' : 'application/json',
                'Content-Type' : 'application/json'
            }
        });
        $('#usuariosTable').DataTable().ajax.reload();
    }
    else
        return;
}