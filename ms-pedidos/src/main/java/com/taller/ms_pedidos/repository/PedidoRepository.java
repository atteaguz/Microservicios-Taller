package com.taller.ms_pedidos.repository;

import com.taller.ms_pedidos.model.EstadoPedido;
import com.taller.ms_pedidos.model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long> {

    // Contar pedidos por usuario y estado
    long countByUsuarioIdAndEstado(Long usuarioId, EstadoPedido estado);
}
