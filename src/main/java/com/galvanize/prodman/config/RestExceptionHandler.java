package com.galvanize.prodman.config;

import com.galvanize.prodman.exception.CustomResponseStatusException;
import com.galvanize.prodman.model.ErrorResponse;
import com.galvanize.prodman.model.FieldError;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;

import static com.galvanize.prodman.constants.Constants.DEFAULT_REASON;
import static com.galvanize.prodman.constants.Constants.UNEXPECTED_CODE;


@RestControllerAdvice(annotations = RestController.class)
public class RestExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(RestExceptionHandler.class);

    @ExceptionHandler(CustomResponseStatusException.class)
    public ResponseEntity<ErrorResponse> handleNotFound(final CustomResponseStatusException exception) {
        FieldError error = new FieldError(exception.getInternalCode(), exception.getReason());
        ErrorResponse errorResponse = new ErrorResponse(error);
        return new ResponseEntity<>(errorResponse, exception.getStatus());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValid(
            final MethodArgumentNotValidException exception) {
        final BindingResult bindingResult = exception.getBindingResult();
        final List<FieldError> fieldErrors = bindingResult.getFieldErrors()
                .stream()
                .map(error -> new FieldError(error.getCode(),
                        String.format(DEFAULT_REASON, error.getField())))
                .collect(Collectors.toList());
        final ErrorResponse errorResponse = new ErrorResponse(fieldErrors);
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Throwable.class)
    public ResponseEntity<ErrorResponse> handleThrowable(final Throwable exception) {
        exception.printStackTrace();
        FieldError error = new FieldError(UNEXPECTED_CODE,exception.getMessage());
        final ErrorResponse errorResponse = new ErrorResponse(error);
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
