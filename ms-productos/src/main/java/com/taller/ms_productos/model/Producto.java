package com.taller.ms_productos.model;

import jakarta.persistence.*;
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
@Entity
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El nombre es obligatorio")
    @Column(nullable = false, unique = true)
    private String nombre;

    @PositiveOrZero(message = "El precio debe ser mayor o igual a cero")
    @DecimalMax(value = "10000000", message = "El precio no puede superar 10,000,000")
    @Column(nullable = false, precision = 18, scale = 2)
    private BigDecimal precio;

    @Min(value = 1, message = "El stock no puede ser negativo ni cero")
    @Column(nullable = false)
    private Integer stock;

    /*
     * Metodo calculado: indica si el stock este bajo (menor a 5)
     * JavaBeans: is + nombrePropiedad
     */
    public boolean isStockBajo() {
        return stock != null && stock < 5;
    }
}
