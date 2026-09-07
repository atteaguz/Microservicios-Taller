package com.taller.ms_productos.common.exceptions;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class ApiError {
    private LocalDateTime timestamp;
    private int status;
    private String error;
    private String mensaje;
    private List<String> detalles;

    public ApiError(int status, String error, String mensaje, List<String> detalles) {
        this.timestamp = LocalDateTime.now();
        this.status = status;
        this.error = error;
        this.mensaje = mensaje;
        this.detalles = detalles;
    }
}
