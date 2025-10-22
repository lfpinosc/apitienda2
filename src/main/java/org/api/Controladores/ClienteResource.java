package org.api.Controladores;


import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;

import org.api.Entidades.Cliente;
import org.api.Servicios.ClienteService;

@Path("/clientes")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ClienteResource {

    @Inject
    ClienteService clienteService;

    // 🟢 Listar todos
    @GET
    public List<Cliente> listar() {
        return clienteService.listar();
    }

    // 🔵 Obtener por ID
    @GET
    @Path("/{id}")
    public Response obtener(@PathParam("id") Long id) {
        Cliente cliente = clienteService.obtener(id);
        if (cliente == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Cliente no encontrado").build();
        }
        return Response.ok(cliente).build();
    }

    // 🟡 Crear nuevo
    @POST
    public Response crear(@Valid Cliente cliente) {
        clienteService.crear(cliente);
        return Response.status(Response.Status.CREATED).entity(cliente).build();
    }

    // 🟠 Actualizar existente
    @PUT
    @Path("/{id}")
    public Response actualizar(@PathParam("id") Long id, @Valid Cliente cliente) {
        Cliente actualizado = clienteService.actualizar(id, cliente);
        if (actualizado == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Cliente no encontrado").build();
        }
        return Response.ok(actualizado).build();
    }

    // 🔴 Eliminar
    @DELETE
    @Path("/{id}")
    public Response eliminar(@PathParam("id") Long id) {
        boolean eliminado = clienteService.eliminar(id);
        if (!eliminado) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Cliente no encontrado").build();
        }
        return Response.noContent().build();
    }
}