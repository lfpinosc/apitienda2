package org.api.Seguridad;



import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import java.util.Set;

@Entity
@Table(name = "users")
public class User extends PanacheEntity {

    @NotBlank
    @Column(nullable = false, unique = true)
    public String username;

    @NotBlank
    @Column(nullable = false)
    public String passwordHash; // almacena hash (BCrypt)

    // Lista simple de roles (ROLE_ADMIN, ROLE_USER, etc.)
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "role")
    public Set<String> roles;
}

