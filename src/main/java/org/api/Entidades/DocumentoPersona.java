package org.api.Entidades;


import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Entity
@Table(
    uniqueConstraints = {
        @UniqueConstraint(columnNames = {"cliente_id", "tipoDocumento_id"})
    }
)
public class DocumentoPersona extends PanacheEntity {

    @ManyToOne(optional = false)
    public Cliente cliente;

    @ManyToOne(optional = false)
    public TipoDocumento tipoDocumento;

    @NotBlank(message = "El número de documento es obligatorio")
    @Size(max = 30, message = "El número no puede exceder 30 caracteres")
    @Pattern(regexp = "^[A-Za-z0-9\\-]+$", message = "Número de documento no válido")
    @Column(length = 30, nullable = false)
    public String numero;
}

