package org.api.Servicios;




import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import java.util.List;

import org.api.Entidades.Producto;

@ApplicationScoped
public class ProductoService {

    public List<Producto> listar() {
        return Producto.listAll();
    }

    public Producto obtener(Long id) {
        return Producto.findById(id);
    }

    @Transactional
    public Producto crear(Producto producto) {
        producto.persist();
        return producto;
    }

    @Transactional
    public Producto actualizar(Long id, Producto nuevo) {
        Producto existente = Producto.findById(id);
        if (existente == null) {
            return null;
        }
        existente.nombre = nuevo.nombre;
        existente.descripcion = nuevo.descripcion;
        existente.precio = nuevo.precio;
        existente.stock = nuevo.stock;
        return existente;
    }

    @Transactional
    public boolean eliminar(Long id) {
        return Producto.deleteById(id);
    }
}
