package com.taller.ms_productos.mapper;

import com.taller.ms_productos.dto.ProductoRequestDTO;
import com.taller.ms_productos.dto.ProductoResponseDTO;
import com.taller.ms_productos.model.Producto;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductoMapper {

    @Mapping(target = "id", ignore = true)
    Producto toEntity(ProductoRequestDTO dto);

    @Mapping(source = "stockBajo", target = "stockBajo")
    ProductoResponseDTO toResponse(Producto producto);

    List<ProductoResponseDTO> toResponseList(List<Producto> productos);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntity(@MappingTarget Producto producto, ProductoRequestDTO dto);
}
