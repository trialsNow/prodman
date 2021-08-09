package com.galvanize.prodman.util;

import com.galvanize.prodman.exception.CustomResponseStatusException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.net.URI;

public class WebClientUtil {
    private WebClient webClient;
    private static final Logger logger = LoggerFactory.getLogger(WebClientUtil.class);

    public WebClientUtil(String registrationId) {
        webClient = WebClient.builder().defaultHeaders(httpHeaders -> {
            httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        }).build();
    }

    public <T> Mono<T> executeGet(URI url,
                                                  Class<T> responseType) throws CustomResponseStatusException {
        try {
            return webClient.get().
                    uri(url).
                    retrieve().bodyToMono(responseType);
        } catch (Exception e) {
            e.printStackTrace();
            throw new CustomResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

}
