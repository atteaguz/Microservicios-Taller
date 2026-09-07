package com.taller.ms_productos.common.exceptions;

public class ProductoNotFoundException extends RuntimeException {
    public ProductoNotFoundException(Long id) {
        super("Producto con id " + id + " no encontrado.");
    }
}
