package com.taller.ms_productos.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductoResponseDTO {
    private Long id;
    private String nombre;
    private BigDecimal precio;
    private Integer stock;
    private Boolean stockBajo; // Campo explicito en el contrato de salida
}