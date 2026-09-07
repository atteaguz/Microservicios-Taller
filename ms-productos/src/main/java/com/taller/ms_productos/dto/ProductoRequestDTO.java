package com.taller.ms_productos.dto;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductoRequestDTO {

    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;

    @PositiveOrZero(message = "El precio debe ser mayor o igual a cero")
    @DecimalMax(value = "10000000", message = "El precio no puede superar 10,000,000")
    private BigDecimal precio;

    @Min(value = 1, message = "El stock no puede ser negativo ni cero")
    private Integer stock;
}