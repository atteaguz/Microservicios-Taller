package com.taller.ms_pedidos.validator;

import com.taller.ms_pedidos.common.exceptions.CantidadExcedeLimiteException;
import com.taller.ms_pedidos.common.exceptions.LimitePedidosActivosException;
import com.taller.ms_pedidos.common.exceptions.PedidoYaCanceladoException;
import com.taller.ms_pedidos.model.EstadoPedido;
import com.taller.ms_pedidos.model.Pedido;
import com.taller.ms_pedidos.repository.PedidoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PedidoValidator {

    private static final int MAX_CANTIDAD = 20;
    private static final int MAX_PEDIDOS_ACTIVOS = 5;

    private final PedidoRepository pedidoRepository;

    // Regla 1: Un pedido no puede pedir mas de 20 unidades de un mismo producto
    public void validarCantidadMaxima(int cantidad) {
        if (cantidad > MAX_CANTIDAD) {
            throw new CantidadExcedeLimiteException(cantidad, MAX_CANTIDAD);
        }
    }

    //Regla 2: Un usuario no puede tener mas de 5 pedidos en estado CONFIRMADO
    public void validarLimitePedidosActivos(Long usuarioId) {
        long pedidosActivos = pedidoRepository.countByUsuarioIdAndEstado(usuarioId, EstadoPedido.CONFIRMADO);
        if (pedidosActivos >= MAX_PEDIDOS_ACTIVOS) {
            throw new LimitePedidosActivosException(MAX_PEDIDOS_ACTIVOS, pedidosActivos);
        }
    }

    //Regla 3: No se puede cancelar un pedido que ya esta CANCELADO
    public void validarPedidoNoCancelado(Pedido pedido) {
        if (pedido.getEstado() == EstadoPedido.CANCELADO) {
            throw new PedidoYaCanceladoException(pedido.getId());
        }
    }
}
