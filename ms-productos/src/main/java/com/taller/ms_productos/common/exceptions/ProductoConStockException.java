package com.taller.ms_productos.common.exceptions;

public class ProductoConStockException extends RuntimeException {
    public ProductoConStockException(String mensaje) {
        super(mensaje);
    }
}