package com.taller.ms_usuarios.common.exceptions;

public class EmailDuplicatedException extends RuntimeException {

    public EmailDuplicatedException(String email) {
        super("El email " + email + " ya existe");
    }

}
