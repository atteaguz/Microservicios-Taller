package com.taller.ms_pedidos.client;

import com.taller.ms_pedidos.dto.UsuarioDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
@RequiredArgsConstructor
public class UsuarioClient {

    private final RestClient restClient;

    @Value("${services.usuarios.url}")
    private String usuariosUrl;

    public UsuarioDTO obtenerUsuario(Long id) {
        return restClient.get()
                .uri(usuariosUrl + "/api/usuarios/{id}", id)
                .retrieve()
                .body(UsuarioDTO.class);
    }
}