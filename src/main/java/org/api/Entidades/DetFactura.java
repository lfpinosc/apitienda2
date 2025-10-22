package org.api.Entidades;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

@Entity
public class DetFactura extends PanacheEntity {

    @ManyToOne(optional = false)
    @JoinColumn(name = "factura_id")
    public Factura factura;

    @NotNull(message = "El producto es obligatorio")
    @ManyToOne(optional = false)
    @JoinColumn(name = "producto_id")
    public Producto producto;

    @Min(value = 1, message = "La cantidad debe ser al menos 1")
    @Column(nullable = false)
    public int cantidad;

    @Column(nullable = false)
    public Double subtotal = 0.0;
}
