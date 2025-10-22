package org.api.Entidades;


import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
public class TipoDocumento extends PanacheEntity {

    @NotBlank(message = "El nombre del tipo de documento es obligatorio")
    @Size(max = 50, message = "El tipo no puede exceder 50 caracteres")
    @Column(length = 50, unique = true, nullable = false)
    public String nombre; // Ejemplo: CEDULA, RUC, PASAPORTE

    @Size(max = 200)
    public String descripcion;
}

