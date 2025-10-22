package org.api.Servicios;




import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import java.util.List;

import org.api.Entidades.Cliente;

@ApplicationScoped
public class ClienteService {

    public List<Cliente> listar() {
        return Cliente.listAll();
    }

    public Cliente obtener(Long id) {
        return Cliente.findById(id);
    }

    @Transactional
    public Cliente crear(Cliente cliente) {
        cliente.persist();
        return cliente;
    }

    @Transactional
    public Cliente actualizar(Long id, Cliente nuevo) {
        Cliente existente = Cliente.findById(id);
        if (existente == null) {
            return null;
        }
        existente.nombre = nuevo.nombre;
        existente.direccion = nuevo.direccion;
        existente.telefono = nuevo.telefono;
        existente.email = nuevo.email;
        return existente;
    }

    @Transactional
    public boolean eliminar(Long id) {
        return Cliente.deleteById(id);
    }
}

