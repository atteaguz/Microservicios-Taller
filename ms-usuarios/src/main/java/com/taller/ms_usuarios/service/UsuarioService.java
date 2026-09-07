package com.taller.ms_usuarios.service;

import com.taller.ms_usuarios.common.exceptions.UsuarioNotFundException;
import com.taller.ms_usuarios.dto.UsuarioRequestDTO;
import com.taller.ms_usuarios.dto.UsuarioResponseDTO;
import com.taller.ms_usuarios.mapper.UsuarioMapper;
import com.taller.ms_usuarios.model.Usuario;
import com.taller.ms_usuarios.repository.UsuarioRepository;
import com.taller.ms_usuarios.validator.UsuarioValidator;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    //inyeccion de dependencias
    private final UsuarioRepository usuarioRepository;
    private final UsuarioMapper usuarioMapper;
    private final UsuarioValidator usuarioValidator;

    //Listar todos los usuarios
    public List<UsuarioResponseDTO> ListarTodosUsuarios(){
        List<Usuario> lista = usuarioRepository.findAll();
        return usuarioMapper.toResponseList(lista);
    }

    //Obtener usuario por id
    public UsuarioResponseDTO obtenerUsuarioPorId(Long id){
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new UsuarioNotFundException(id));
        return usuarioMapper.toResponse(usuario);
    }

    //Crear usuario
    public UsuarioResponseDTO crearUsuario(UsuarioRequestDTO usuarioDTO){

        /*\[Reglas de negocio para crear usuarios]\*/

        //Validar que el email sea unico al crear un usuario
        usuarioValidator.checkEmailUniqueCreate(usuarioDTO.getEmail());


        //Mapear de DTO a entidad con el mapper
        Usuario usuario = usuarioMapper.toEntity(usuarioDTO);


        //Guardar en la base de datos
        usuario = usuarioRepository.save(usuario);

        //Mapear de entidad a ResponseDTO con el mapper
        UsuarioResponseDTO usuarioResponse = usuarioMapper.toResponse(usuario);

        return usuarioResponse;
    }

    //Actualizar usuario
    public UsuarioResponseDTO actualizarUsuario(Long id, UsuarioRequestDTO usuarioDTO ){


        //validar que exista
        Usuario usuarioActualBD = usuarioRepository.findById(id).orElseThrow(()-> new UsuarioNotFundException(id));

        //regla de negocio
        usuarioValidator.checkEmailUniqueUpdate(usuarioActualBD, usuarioDTO.getEmail());

        //mapeo

        usuarioMapper.updateEntity(usuarioActualBD,usuarioDTO );


        usuarioActualBD = usuarioRepository.save(usuarioActualBD);


        //mappeo a responsedto



        return usuarioMapper.toResponse(usuarioActualBD);


    }

    //Eliminar usuario
    public UsuarioResponseDTO eliminarUsuario(Long id) {

        //validar que exista
        var usuarioActualBD = usuarioRepository.findById(id).orElseThrow(() -> new UsuarioNotFundException(id));
        //Eliminar el usuario
        usuarioRepository.delete(usuarioActualBD);
        //Retornar el usuario eliminado
        return usuarioMapper.toResponse(usuarioActualBD);
    }
}