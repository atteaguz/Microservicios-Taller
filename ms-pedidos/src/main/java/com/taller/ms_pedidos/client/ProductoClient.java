package com.taller.ms_pedidos.client;

import com.taller.ms_pedidos.dto.ProductoDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
@RequiredArgsConstructor
public class ProductoClient {

    private final RestClient restClient;

    @Value("${services.productos.url}")
    private String productosUrl;

    public ProductoDTO obtenerProducto(Long id) {
        return restClient.get()
                .uri(productosUrl + "/api/productos/{id}", id)
                .retrieve()
                .body(ProductoDTO.class);
    }

    public void descontarStock(Long productoId, int cantidad) {
        restClient.patch()
                .uri(productosUrl + "/api/productos/{id}/descontar-stock?cantidad={cantidad}",
                        productoId, cantidad)
                .retrieve()
                .toBodilessEntity();
    }

    public void reponerStock(Long productoId, int cantidad) {
        restClient.patch()
                .uri(productosUrl + "/api/productos/{id}/reponer-stock?cantidad={cantidad}",
                        productoId, cantidad)
                .retrieve()
                .toBodilessEntity();
    }
}