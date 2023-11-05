package com.api.codigoqr.Model.Errors;

import com.google.zxing.WriterException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.io.IOException;


@ControllerAdvice
@RestController
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(WriterException.class)
    public final ResponseEntity<CustomErrorResponse> handleWriterException(WriterException ex) {
        CustomErrorResponse errorResponse = new CustomErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Error al generar el código QR", ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(IOException.class)
    public final ResponseEntity<CustomErrorResponse> handleIOException(IOException ex) {
        CustomErrorResponse errorResponse = new CustomErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Error de E/S al generar el código QR", ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(InvalidDateRangeException.class)
    public final ResponseEntity<CustomErrorResponse> handleInvalidDateRangeException(InvalidDateRangeException ex) {
        CustomErrorResponse errorResponse = new CustomErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Rango de fechas inválido", ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ICalDataNullException.class)
    public final ResponseEntity<CustomErrorResponse> handleICalDataNullException(ICalDataNullException ex) {
        CustomErrorResponse errorResponse = new CustomErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Error: iCalData is null", ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<CustomErrorResponse> handleGeneralException(Exception ex) {
        CustomErrorResponse errorResponse = new CustomErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Error general al generar el código QR", ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}

