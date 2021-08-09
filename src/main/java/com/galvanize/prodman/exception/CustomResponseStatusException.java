package com.galvanize.prodman.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

@Getter
@Setter
public class CustomResponseStatusException extends ResponseStatusException {

    private String internalCode;

    public CustomResponseStatusException(HttpStatus status, String reason) {
        super(status, reason);
    }

    public CustomResponseStatusException(HttpStatus status, String internalCode, String reason) {
        super(status, reason);
        this.internalCode = internalCode;
    }
}
