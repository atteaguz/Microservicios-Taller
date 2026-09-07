package com.taller.ms_pedidos.service;

import com.taller.ms_pedidos.client.ProductoClient;
import com.taller.ms_pedidos.client.UsuarioClient;
import com.taller.ms_pedidos.common.exceptions.PedidoNotFoundException;
import com.taller.ms_pedidos.dto.PedidoRequestDTO;
import com.taller.ms_pedidos.dto.PedidoResponseDTO;
import com.taller.ms_pedidos.dto.ProductoDTO;
import com.taller.ms_pedidos.dto.UsuarioDTO;
import com.taller.ms_pedidos.mapper.PedidoMapper;
import com.taller.ms_pedidos.model.EstadoPedido;
import com.taller.ms_pedidos.model.Pedido;
import com.taller.ms_pedidos.repository.PedidoRepository;
import com.taller.ms_pedidos.validator.PedidoValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PedidoService {

    private final PedidoRepository pedidoRepository;
    private final PedidoMapper pedidoMapper;
    private final PedidoValidator pedidoValidator;
    private final UsuarioClient usuarioClient;
    private final ProductoClient productoClient;

    @Transactional
    public PedidoResponseDTO crearPedido(PedidoRequestDTO request) {
        // 1.Validar cantidad máxima (regla interna)
        pedidoValidator.validarCantidadMaxima(request.getCantidad());

        // 2.Validar limite de pedidos activos (regla interna)
        pedidoValidator.validarLimitePedidosActivos(request.getUsuarioId());

        // 3.Obtener usuario (si no existe, ms-usuarios responde 404)
        UsuarioDTO usuario = usuarioClient.obtenerUsuario(request.getUsuarioId());

        // 4.Obtener producto (si no existe, ms-productos responde 404)
        ProductoDTO producto = productoClient.obtenerProducto(request.getProductoId());

        // 5.Calcular total (precio × cantidad)
        BigDecimal total = producto.getPrecio().multiply(BigDecimal.valueOf(request.getCantidad()));

        // 6.Descontar stock (ms-productos valida stock disponible)
        productoClient.descontarStock(request.getProductoId(), request.getCantidad());

        // 7.Crear y guardar el pedido (CONFIRMADO)
        Pedido pedido = pedidoMapper.toEntity(request);
        pedido.setTotal(total);
        pedido = pedidoRepository.save(pedido);

        // 8.Armar respuesta combinada
        return pedidoMapper.toResponse(pedido, usuario, producto);
    }

    public List<Pedido> listarTodos() {
        return pedidoRepository.findAll();
    }

    public PedidoResponseDTO obtenerPedidoConDetalles(Long id) {
        // 1.Buscar el pedido
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new PedidoNotFoundException(id));

        // 2.Obtener datos enriquecidos
        UsuarioDTO usuario = usuarioClient.obtenerUsuario(pedido.getUsuarioId());
        ProductoDTO producto = productoClient.obtenerProducto(pedido.getProductoId());

        // 3.Armar respuesta combinada
        return pedidoMapper.toResponse(pedido, usuario, producto);
    }

    @Transactional
    public PedidoResponseDTO cancelarPedido(Long id) {
        //1. Buscar el pedido
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new PedidoNotFoundException(id));

        //2. Validar que no este ya CANCELADO
        pedidoValidator.validarPedidoNoCancelado(pedido);

        //3. Reponer stock (la compensacion)
        productoClient.reponerStock(pedido.getProductoId(), pedido.getCantidad());

        //4. Cambiar estado a CANCELADO y guardar
        pedido.setEstado(EstadoPedido.CANCELADO);
        pedido = pedidoRepository.save(pedido);

        //5. Obtener datos enriquecidos para la respuesta
        UsuarioDTO usuario = usuarioClient.obtenerUsuario(pedido.getUsuarioId());
        ProductoDTO producto = productoClient.obtenerProducto(pedido.getProductoId());

        return pedidoMapper.toResponse(pedido, usuario, producto);
    }
}
