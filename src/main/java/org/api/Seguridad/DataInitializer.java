package org.api.Seguridad;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.api.Entidades.Cliente;

import java.util.Set;

@ApplicationScoped
public class DataInitializer {

    @Inject
    UserService userService;

    @Inject
    org.api.Servicios.ClienteService clienteService;

    @PostConstruct
    @Transactional
    public void init() {
        // --- 1️⃣ Crear usuario admin si no existe ---
        String adminUsername = "admin";
        User existing = userService.findByUsername(adminUsername);
        if (existing == null) {
            userService.create(adminUsername, "admin123", Set.of("ADMIN", "USER"));
            System.out.println("Usuario ADMIN creado: admin / admin123");
        }

        // --- 2️⃣ Crear cliente de prueba si no existe ---
        String clienteEmail = "cliente@test.com";
        Cliente existingCliente = Cliente.find("email", clienteEmail).firstResult();
        if (existingCliente == null) {
            Cliente c = new Cliente();
            c.nombre = "Cliente Test";
            c.direccion = "Av. Principal 123";
            c.telefono = "+593987654321";
            c.email = clienteEmail;
            c.persist();
            System.out.println("Cliente de prueba creado: " + clienteEmail);
        }
    }
}
