package org.api.Controladores;


import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;

import org.api.Entidades.Producto;
import org.api.Servicios.ProductoService;

@Path("/productos")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ProductoResource {

    @Inject
    ProductoService productoService;

    // 🟢 Listar todos los productos
    @GET
    public List<Producto> listar() {
        return productoService.listar();
    }

    // 🔵 Obtener producto por ID
    @GET
    @Path("/{id}")
    public Response obtener(@PathParam("id") Long id) {
        Producto producto = productoService.obtener(id);
        if (producto == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Producto no encontrado").build();
        }
        return Response.ok(producto).build();
    }

    // 🟡 Crear nuevo producto
    @POST
    public Response crear(@Valid Producto producto) {
        productoService.crear(producto);
        return Response.status(Response.Status.CREATED).entity(producto).build();
    }

    // 🟠 Actualizar producto existente
    @PUT
    @Path("/{id}")
    public Response actualizar(@PathParam("id") Long id, @Valid Producto producto) {
        Producto actualizado = productoService.actualizar(id, producto);
        if (actualizado == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Producto no encontrado").build();
        }
        return Response.ok(actualizado).build();
    }

    // 🔴 Eliminar producto
    @DELETE
    @Path("/{id}")
    public Response eliminar(@PathParam("id") Long id) {
        boolean eliminado = productoService.eliminar(id);
        if (!eliminado) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Producto no encontrado").build();
        }
        return Response.noContent().build();
    }
}
