package com.taller.ms_usuarios.validator;

//Reglas de negocio para la entidad Usuario

import com.taller.ms_usuarios.common.exceptions.*;
import com.taller.ms_usuarios.dto.UsuarioResponseDTO;
import com.taller.ms_usuarios.model.Usuario;
import com.taller.ms_usuarios.repository.UsuarioRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
@AllArgsConstructor
public class UsuarioValidator {

    private final UsuarioRepository usuarioRepository;

    //validar que el email sea unico al crear un usuario
    public void checkEmailUniqueCreate(String email){

        if(usuarioRepository.existsByEmail(email)){
            throw new EmailDuplicatedException(email);
        }
    }

    //validar que el email sea unico al actualizar un usuario
    public void checkEmailUniqueUpdate(Usuario usuarioActual, String email){

        boolean emailChanged = !usuarioActual.getEmail().equalsIgnoreCase(email);

        if(emailChanged && usuarioRepository.existsByEmail(email)){
            throw new EmailDuplicatedException(email);
        }
    }



}
