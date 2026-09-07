package com.taller.ms_productos.common.exceptions;

public class NombreDuplicadoException extends RuntimeException {
    public NombreDuplicadoException(String nombre) {
        super("Ya existe un producto con el nombre: " + nombre);
    }
}
