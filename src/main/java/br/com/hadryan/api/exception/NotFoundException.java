package br.com.hadryan.api.exception;

import org.springframework.http.HttpStatus;

public class NotFoundException extends RuntimeException {

    private String message;

    private HttpStatus httpStatus;

    public NotFoundException(String message) {
        this.message = message;
        this.httpStatus = HttpStatus.NOT_FOUND;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }
}
