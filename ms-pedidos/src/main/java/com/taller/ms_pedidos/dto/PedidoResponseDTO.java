package com.taller.ms_pedidos.dto;

import com.taller.ms_pedidos.model.EstadoPedido;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PedidoResponseDTO {
    private Long id;
    private UsuarioDTO usuario;
    private ProductoDTO producto;
    private Integer cantidad;
    private BigDecimal total;
    private LocalDateTime fecha;
    private EstadoPedido estado;
}