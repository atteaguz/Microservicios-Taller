package com.taller.ms_pedidos.common.exceptions;

public class LimitePedidosActivosException extends RuntimeException {
    public LimitePedidosActivosException(int limite, long pedidosActuales) {
        super("El usuario ya tiene " + pedidosActuales + " pedidos activos. Límite máximo: " + limite);
    }
}
