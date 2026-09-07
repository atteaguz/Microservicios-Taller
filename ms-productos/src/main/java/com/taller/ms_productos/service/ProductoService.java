package com.taller.ms_productos.service;

import com.taller.ms_productos.common.exceptions.ProductoNotFoundException;
import com.taller.ms_productos.dto.ProductoRequestDTO;
import com.taller.ms_productos.dto.ProductoResponseDTO;
import com.taller.ms_productos.mapper.ProductoMapper;
import com.taller.ms_productos.model.Producto;
import com.taller.ms_productos.repository.ProductoRepository;
import com.taller.ms_productos.validator.ProductoValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductoService {

    private final ProductoRepository productoRepository;
    private final ProductoMapper productoMapper;
    private final ProductoValidator productoValidator;

    // Listar todos los productos
    public List<ProductoResponseDTO> listarTodos() {
        List<Producto> productos = productoRepository.findAll();
        return productoMapper.toResponseList(productos);
    }

    // Obtener producto por ID
    public ProductoResponseDTO obtenerPorId(Long id) {
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new ProductoNotFoundException(id));
        return productoMapper.toResponse(producto);
    }

    // Crear producto
    @Transactional
    public ProductoResponseDTO crearProducto(ProductoRequestDTO dto) {
        // Validar nombre único
        productoValidator.checkNombreUnico(dto.getNombre());

        // Mapear DTO a Entity
        Producto producto = productoMapper.toEntity(dto);

        // Guardar en la base de datos
        producto = productoRepository.save(producto);

        // Mapear a Response
        return productoMapper.toResponse(producto);
    }

    // Actualizar producto
    @Transactional
    public ProductoResponseDTO actualizarProducto(Long id, ProductoRequestDTO dto) {

        // Reglas de negocio
        // Verificar que existe
        Producto productoActual = productoRepository.findById(id)
                .orElseThrow(() -> new ProductoNotFoundException(id));

        // Validar nombre unico (si cambió)
        productoValidator.checkNombreUnicoUpdate(productoActual, dto.getNombre());

        // Actualizar usando el mapper (solo campos no nulos)
        productoMapper.updateEntity(productoActual, dto);

        // Guardar en la base de datos
        productoActual = productoRepository.save(productoActual);

        // Mapear a Response
        return productoMapper.toResponse(productoActual);
    }

    // Descontar stock
    @Transactional
    public ProductoResponseDTO descontarStock(Long id, int cantidad) {
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new ProductoNotFoundException(id));

        // Validar stock suficiente
        productoValidator.checkStockSuficiente(producto, cantidad);

        // Descontar stock
        producto.setStock(producto.getStock() - cantidad);
        producto = productoRepository.save(producto);

        return productoMapper.toResponse(producto);
    }

    // Reponer stock
    @Transactional
    public ProductoResponseDTO reponerStock(Long id, int cantidad) {
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new ProductoNotFoundException(id));

        // Reponer stock (sin restricciones)
        producto.setStock(producto.getStock() + cantidad);
        producto = productoRepository.save(producto);

        return productoMapper.toResponse(producto);
    }

    // Eliminar producto
    @Transactional
    public void eliminarProducto(Long id) {
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new ProductoNotFoundException(id));

        // Validar que no tenga stock
        productoValidator.checkProductoSinStock(producto);

        // Eliminar
        productoRepository.delete(producto);
    }
}
