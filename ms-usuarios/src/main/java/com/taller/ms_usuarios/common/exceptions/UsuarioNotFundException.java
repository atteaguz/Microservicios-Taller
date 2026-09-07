package com.taller.ms_usuarios.common.exceptions;

public class UsuarioNotFundException extends RuntimeException {

    public UsuarioNotFundException(Long id) {
        super("Usuario no encontrado con id: " + id);
    }
}
