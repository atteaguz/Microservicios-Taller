package com.taller.ms_pedidos.mapper;

import com.taller.ms_pedidos.dto.PedidoRequestDTO;
import com.taller.ms_pedidos.dto.PedidoResponseDTO;
import com.taller.ms_pedidos.dto.ProductoDTO;
import com.taller.ms_pedidos.dto.UsuarioDTO;
import com.taller.ms_pedidos.model.EstadoPedido;
import com.taller.ms_pedidos.model.Pedido;
import org.mapstruct.*;

import java.time.LocalDateTime;

@Mapper(componentModel = "spring", imports = {LocalDateTime.class, EstadoPedido.class})
public interface PedidoMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "total", ignore = true)
    @Mapping(target = "fecha", expression = "java(LocalDateTime.now())")
    @Mapping(target = "estado", expression = "java(EstadoPedido.CONFIRMADO)")
    Pedido toEntity(PedidoRequestDTO dto);

    // Combina 3 fuentes: Pedido, UsuarioDTO y ProductoDTO
    @Mapping(target = "id", source = "pedido.id")
    @Mapping(target = "usuario", source = "usuario")
    @Mapping(target = "producto", source = "producto")
    @Mapping(target = "cantidad", source = "pedido.cantidad")
    @Mapping(target = "total", source = "pedido.total")
    @Mapping(target = "fecha", source = "pedido.fecha")
    @Mapping(target = "estado", source = "pedido.estado")
    PedidoResponseDTO toResponse(Pedido pedido, UsuarioDTO usuario, ProductoDTO producto);
}
