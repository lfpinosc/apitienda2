package org.api.Controladores;



import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;

import org.api.Entidades.Factura;
import org.api.Servicios.FacturaService;

@Path("/facturas")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class FacturaResource {

    @Inject
    FacturaService facturaService;

    // 🟢 Listar todas las facturas
    @GET
    public List<Factura> listar() {
        return facturaService.listar();
    }

    // 🔵 Obtener factura por ID
    @GET
    @Path("/{id}")
    public Response obtener(@PathParam("id") Long id) {
        Factura factura = facturaService.obtener(id);
        if (factura == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Factura no encontrada").build();
        }
        return Response.ok(factura).build();
    }

    // 🟡 Crear nueva factura con detalles
    @POST
    public Response crear(@Valid Factura factura) {
        facturaService.crear(factura);
        return Response.status(Response.Status.CREATED).entity(factura).build();
    }

    // 🟠 Actualizar factura existente
    @PUT
    @Path("/{id}")
    public Response actualizar(@PathParam("id") Long id, @Valid Factura factura) {
        Factura actualizada = facturaService.actualizar(id, factura);
        if (actualizada == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Factura no encontrada").build();
        }
        return Response.ok(actualizada).build();
    }

    // 🔴 Eliminar factura
    @DELETE
    @Path("/{id}")
    public Response eliminar(@PathParam("id") Long id) {
        boolean eliminado = facturaService.eliminar(id);
        if (!eliminado) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Factura no encontrada").build();
        }
        return Response.noContent().build();
    }
}

