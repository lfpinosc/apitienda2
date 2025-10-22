package org.api.Entidades;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.util.List;

@Entity
public class Cliente extends PanacheEntity {

    @NotBlank(message = "El nombre es obligatorio")
    @Size(min = 2, max = 100, message = "El nombre debe tener entre 2 y 100 caracteres")
    @Column(length = 100, nullable = false)
    public String nombre;

    @NotBlank(message = "La dirección es obligatoria")
    @Size(max = 150, message = "La dirección no puede exceder 150 caracteres")
    @Column(length = 150, nullable = false)
    public String direccion;

    @NotBlank(message = "El teléfono es obligatorio")
    @Pattern(regexp = "^[0-9+\\-() ]{7,20}$", message = "El teléfono no tiene un formato válido")
    @Column(length = 20, nullable = false)
    public String telefono;

    @NotBlank(message = "El email es obligatorio")
    @Email(message = "Debe ser un correo electrónico válido")
    @Size(max = 120, message = "El correo no puede exceder 120 caracteres")
    @Column(length = 120, unique = true, nullable = false)
    public String email;

    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL, orphanRemoval = true)
    public List<DocumentoPersona> documentos;
}
