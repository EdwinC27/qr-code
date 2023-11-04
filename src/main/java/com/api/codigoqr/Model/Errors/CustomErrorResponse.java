package com.api.codigoqr.Model.Errors;

public class CustomErrorResponse {
    private int statusCode; // Agregar el código de estado
    private String message;
    private String details;

    public CustomErrorResponse(int statusCode, String message, String details) {
        this.statusCode = statusCode;
        this.message = message;
        this.details = details;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public String getMessage() {
        return message;
    }

    public String getDetails() {
        return details;
    }
}

