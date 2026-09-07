package com.taller.ms_pedidos.common.exceptions;

public class PedidoNotFoundException extends RuntimeException {
    public PedidoNotFoundException(Long id) {
        super("Pedido no encontrado con id: " + id);
    }
}