package com.galvanize.prodman.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.ResponseErrorHandler;

import java.io.IOException;

import static com.galvanize.prodman.constants.Constants.FAILED_FX_SERVICE_REASON;
import static com.galvanize.prodman.constants.Constants.FX_CURRENCY_ERROR_CODE;
import static org.springframework.http.HttpStatus.Series.CLIENT_ERROR;
import static org.springframework.http.HttpStatus.Series.SERVER_ERROR;

public class RestTemplateErrorHandler implements ResponseErrorHandler {
    @Override
    public boolean hasError(ClientHttpResponse httpResponse)
            throws IOException {

        return (
                httpResponse.getStatusCode().series() == CLIENT_ERROR
                        || httpResponse.getStatusCode().series() == SERVER_ERROR);
    }

    @Override
    public void handleError(ClientHttpResponse httpResponse)
            throws IOException {
        throw new CustomResponseStatusException(HttpStatus.BAD_REQUEST, FX_CURRENCY_ERROR_CODE,
                FAILED_FX_SERVICE_REASON);
    }
}
