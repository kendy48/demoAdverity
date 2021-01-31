package com.adverity.demo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

public class ControllerException {

    @ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "From date can't be higher than to date")
    public static class InvalidDateException extends Exception { }

    @ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "Page size must be between 1 and 100")
    public static class InvalidPageSizeException extends Exception { }
}
