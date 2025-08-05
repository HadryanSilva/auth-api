package br.com.hadryan.api.exception;

public record DefaultErrorMessage(String message, int httpStatus) {
}
