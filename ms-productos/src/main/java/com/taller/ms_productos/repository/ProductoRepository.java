package com.taller.ms_productos.repository;

import com.taller.ms_productos.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {

    // Para la regla de negocio: verificar si existe un producto con ese nombre (ignorando mayusculas/minusculas)
    boolean existsByNombreIgnoreCase(String nombre);

    // Para validaciones adicionales
    Optional<Producto> findByNombreIgnoreCase(String nombre);
}
