package com.taller.ms_pedidos.common.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.RestClientResponseException;

import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // 404 - Pedido no encontrado
    @ExceptionHandler(PedidoNotFoundException.class)
    public ResponseEntity<ApiError> handlePedidoNotFoundException(PedidoNotFoundException ex) {
        ApiError error = new ApiError(
                HttpStatus.NOT_FOUND.value(),
                "Not Found",
                ex.getMessage(),
                null
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    // 400 - Cantidad excede limite
    @ExceptionHandler(CantidadExcedeLimiteException.class)
    public ResponseEntity<ApiError> handleCantidadExcedeLimiteException(CantidadExcedeLimiteException ex) {
        ApiError error = new ApiError(
                HttpStatus.BAD_REQUEST.value(),
                "Bad Request",
                ex.getMessage(),
                null
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    // 409 - Limite de pedidos activos
    @ExceptionHandler(LimitePedidosActivosException.class)
    public ResponseEntity<ApiError> handleLimitePedidosActivosException(LimitePedidosActivosException ex) {
        ApiError error = new ApiError(
                HttpStatus.CONFLICT.value(),
                "Conflict",
                ex.getMessage(),
                null
        );
        return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
    }

    // 409 - Pedido ya cancelado
    @ExceptionHandler(PedidoYaCanceladoException.class)
    public ResponseEntity<ApiError> handlePedidoYaCanceladoException(PedidoYaCanceladoException ex) {
        ApiError error = new ApiError(
                HttpStatus.CONFLICT.value(),
                "Conflict",
                ex.getMessage(),
                null
        );
        return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
    }

    // Propagación de errores de otros microservicios
    @ExceptionHandler(RestClientResponseException.class)
    public ResponseEntity<ApiError> handleErrorDeOtroMicroservicio(RestClientResponseException ex) {
        HttpStatus status = HttpStatus.valueOf(ex.getStatusCode().value());
        ApiError error = new ApiError(
                status.value(),
                status.getReasonPhrase(),
                ex.getMessage(),
                null
        );
        return ResponseEntity.status(status).body(error);
    }

    // 400 - Validaciones de Bean Validation
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
                "Uno o más campos no son validos",
                listError
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    // 500 - Errores inesperados
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
