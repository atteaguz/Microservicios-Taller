package com.taller.ms_productos.common.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // 409 Conflict - Nombre duplicado
    @ExceptionHandler(NombreDuplicadoException.class)
    public ResponseEntity<ApiError> handleNombreDuplicadoException(NombreDuplicadoException ex) {
        ApiError error = new ApiError(
                HttpStatus.CONFLICT.value(),
                "Conflict",
                ex.getMessage(),
                null
        );
        return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
    }

    // 409 Conflict - Stock insuficiente
    @ExceptionHandler(StockInsuficienteException.class)
    public ResponseEntity<ApiError> handleStockInsuficienteException(StockInsuficienteException ex) {
        ApiError error = new ApiError(
                HttpStatus.CONFLICT.value(),
                "Conflict",
                ex.getMessage(),
                null
        );
        return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
    }

    // 409 Conflict - Producto con stock
    @ExceptionHandler(ProductoConStockException.class)
    public ResponseEntity<ApiError> handleProductoConStockException(ProductoConStockException ex) {
        ApiError error = new ApiError(
                HttpStatus.CONFLICT.value(),
                "Conflict",
                ex.getMessage(),
                null
        );
        return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
    }

    // 404 Not Found - Producto no encontrado
    @ExceptionHandler(ProductoNotFoundException.class)
    public ResponseEntity<ApiError> handleProductoNotFoundException(ProductoNotFoundException ex) {
        ApiError error = new ApiError(
                HttpStatus.NOT_FOUND.value(),
                "Not Found",
                ex.getMessage(),
                null
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    // 400 Bad Request - Validaciones de Bean Validation
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> handleValidation(MethodArgumentNotValidException ex) {
        List<String> listError = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(fieldError -> fieldError.getField() + ": " + fieldError.getDefaultMessage())
                .toList();

        ApiError error = new ApiError(
                HttpStatus.BAD_REQUEST.value(),
                "Bad Request",
                "Uno o mas campos no son validos",
                listError
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    // 500 Internal Server Error - Errores inesperados
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleError(Exception ex) {
        ApiError error = new ApiError(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Ocurrio un error inesperado",
                ex.getMessage(),
                null
        );
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }
}
