package org.api.Servicios;


import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import java.util.List;

import org.api.Entidades.Cliente;
import org.api.Entidades.DetFactura;
import org.api.Entidades.Factura;
import org.api.Entidades.Producto;

@ApplicationScoped
public class FacturaService {

    public List<Factura> listar() {
        return Factura.listAll();
    }

    public Factura obtener(Long id) {
        return Factura.findById(id);
    }

    @Transactional
    public Factura crear(Factura factura) {

        // 🔹 1. Verificar y cargar el cliente existente
        if (factura.cliente == null || factura.cliente.id == null) {
            throw new IllegalArgumentException("La factura debe tener un cliente con su ID");
        }

        Cliente clienteDB = Cliente.findById(factura.cliente.id);
        if (clienteDB == null) {
            throw new IllegalArgumentException("El cliente con ID " + factura.cliente.id + " no existe");
        }
        factura.cliente = clienteDB;

        // 🔹 2. Calcular totales y cargar productos correctamente
        calcularTotales(factura);

        // 🔹 3. Persistir la factura (ya con cliente y productos “managed”)
        factura.persist();
        return factura;
    }

    @Transactional
    public Factura actualizar(Long id, Factura nuevaFactura) {
        Factura existente = Factura.findById(id);
        if (existente == null) {
            return null;
        }

        existente.cliente = nuevaFactura.cliente;
        existente.fecha = nuevaFactura.fecha;
        existente.detalles.clear();

        // Reasignar detalles y recalcular
        for (DetFactura det : nuevaFactura.detalles) {
            det.factura = existente;
            existente.detalles.add(det);
        }

        calcularTotales(existente);
        return existente;
    }

    @Transactional
    public boolean eliminar(Long id) {
        return Factura.deleteById(id);
    }

    private void calcularTotales(Factura factura) {
        double total = 0.0;
        for (DetFactura det : factura.detalles) {
            if (det.producto == null || det.producto.id == null) {
                throw new IllegalArgumentException("El detalle debe incluir un producto con su ID");
            }
            // 🔹 Cargar el producto desde la base de datos
            Producto productoDB = Producto.findById(det.producto.id);
            if (productoDB == null) {
                throw new IllegalArgumentException("El producto con ID " + det.producto.id + " no existe");
            }
            // 🔹 Asignar el producto completo al detalle
            det.producto = productoDB;
            det.factura = factura;
            if (det.cantidad <= 0) {
                throw new IllegalArgumentException("La cantidad debe ser mayor a 0");
            }
            // 🔹 Calcular el subtotal correctamente
            det.subtotal = productoDB.precio.doubleValue() * det.cantidad;
            total += det.subtotal;
        }
        factura.total = total;
    }
}

