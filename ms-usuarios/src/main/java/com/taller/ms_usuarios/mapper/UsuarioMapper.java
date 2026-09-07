package com.taller.ms_usuarios.mapper;


import com.taller.ms_usuarios.dto.UsuarioRequestDTO;
import com.taller.ms_usuarios.dto.UsuarioResponseDTO;
import com.taller.ms_usuarios.model.Usuario;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UsuarioMapper {

    //De requestDTO a entidad
    @Mapping(target = "id", ignore = true) // Ignorar el campo id al mapear de DTO a entidad
    Usuario toEntity(UsuarioRequestDTO dto);

    //De entidad a DTO
    UsuarioResponseDTO toResponse(Usuario entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE) // Ignorar valores nulos al actualizar
    void updateEntity(@MappingTarget Usuario usuario, UsuarioRequestDTO dto);

    List<UsuarioResponseDTO> toResponseList(List<Usuario> usuarios);
}
