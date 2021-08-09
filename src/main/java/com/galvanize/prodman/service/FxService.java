package com.galvanize.prodman.service;

import com.galvanize.prodman.exception.RestTemplateErrorHandler;
import com.galvanize.prodman.model.FxResponse;
import com.galvanize.prodman.util.WebClientUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.net.URISyntaxException;

@Service
public class FxService {

    private static final String SUPPORTED_CURRENCIES = "USD,CAD,EUR,GBP";

    @Value("${fx.api.url}")
    private String fxApiUrl;

    @Value("${fx.api.key}")
    private String fxApiKey;

    private final RestTemplate restTemplate;

    public FxService(final RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.errorHandler(new RestTemplateErrorHandler()).build();
    }

    public FxResponse getQuotes() {
        String endPoint = String.format("%s?access_key=%s&currencies=%s&format=1", fxApiUrl, fxApiKey, SUPPORTED_CURRENCIES);
        return restTemplate.getForObject(endPoint, FxResponse.class);
    }

    public Mono<FxResponse> getQuotesAsync() throws URISyntaxException {
        String endPoint = String.format("%s?access_key=%s&currencies=%s&format=1", fxApiUrl, fxApiKey, SUPPORTED_CURRENCIES);
        WebClientUtil util = new WebClientUtil("FxService");
        return util.executeGet(new URI(endPoint),FxResponse.class);
    }
}