package com.taller.ms_productos.controller;

import com.taller.ms_productos.dto.ProductoRequestDTO;
import com.taller.ms_productos.dto.ProductoResponseDTO;
import com.taller.ms_productos.service.ProductoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/productos")
@RequiredArgsConstructor
public class ProductoController {

    private final ProductoService productoService;

    @GetMapping("/listar")
    public ResponseEntity<List<ProductoResponseDTO>> listar() {
        return ResponseEntity.ok(productoService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductoResponseDTO> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(productoService.obtenerPorId(id));
    }

    @PostMapping("/crear")
    public ResponseEntity<ProductoResponseDTO> crear(@Valid @RequestBody ProductoRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(productoService.crearProducto(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductoResponseDTO> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody ProductoRequestDTO dto) {
        return ResponseEntity.ok(productoService.actualizarProducto(id, dto));
    }

    @PatchMapping("/{id}/descontar-stock")
    public ResponseEntity<ProductoResponseDTO> descontarStock(
            @PathVariable Long id,
            @RequestParam("cantidad") int cantidad) {
        return ResponseEntity.ok(productoService.descontarStock(id, cantidad));
    }

    @PatchMapping("/{id}/reponer-stock")
    public ResponseEntity<ProductoResponseDTO> reponerStock(
            @PathVariable Long id,
            @RequestParam("cantidad") int cantidad) {
        return ResponseEntity.ok(productoService.reponerStock(id, cantidad));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        productoService.eliminarProducto(id);
        return ResponseEntity.noContent().build();
    }
}
