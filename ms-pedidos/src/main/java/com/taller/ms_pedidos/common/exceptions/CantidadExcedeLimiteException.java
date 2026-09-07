package com.taller.ms_pedidos.common.exceptions;

public class CantidadExcedeLimiteException extends RuntimeException {
    public CantidadExcedeLimiteException(int cantidad, int limite) {
        super("No se pueden pedir más de " + limite + " unidades. Cantidad solicitada: " + cantidad);
    }
}