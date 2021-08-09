package com.galvanize.prodman.service;

import com.galvanize.prodman.domain.Product;
import com.galvanize.prodman.exception.CustomResponseStatusException;
import com.galvanize.prodman.model.FxResponse;
import com.galvanize.prodman.model.ProductDTO;
import com.galvanize.prodman.model.converter.ProductConverter;
import com.galvanize.prodman.repository.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.net.URISyntaxException;
import java.util.Optional;

import static com.galvanize.prodman.constants.Constants.*;


@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final FxService fxService;
    private final ProductConverter productConverter;

    private static final Logger logger = LoggerFactory.getLogger(ProductService.class);

    @Autowired
    public ProductService(ProductRepository productRepository, FxService fxService,
                          ProductConverter converter) {
        this.productRepository = productRepository;
        this.fxService = fxService;
        this.productConverter = converter;
    }

    public void create(final ProductDTO productDTO) {
        //TODO Placeholder;
    }

    public void delete(final Integer id) {
        //TODO Placeholder
    }

    @Transactional
    public ProductDTO getProductById(String externalId, String currency) {
        return (productConverter.toModel(getProductAsync(externalId, currency)));
    }

    private Product getProduct(String externalId) {
        int result = updateViewCount(externalId);
        if (result == 0) {
            throw new CustomResponseStatusException(HttpStatus.NOT_FOUND, PRODUCT_NOT_FOUND_CODE, String.format(PRODUCT_NOT_EXIST, externalId));
        }
        Optional<Product> productDetailsOpt = productRepository.getProductDetails(externalId);
        if (!productDetailsOpt.isPresent()) {
            throw new CustomResponseStatusException(HttpStatus.NOT_FOUND, PRODUCT_NOT_FOUND_CODE, String.format(PRODUCT_NOT_EXIST, externalId));
        }
        return productDetailsOpt.get();
    }

    public int updateViewCount(String externalId) {
        return productRepository.incrementViewCount(externalId);
    }

    public ProductDTO getProduct(String externalId, String currency) {
        Product product = getProduct(externalId);
        if (StringUtils.hasText(currency)) {
            if (SUPPORTED_CURRENCIES.contains(currency.toUpperCase())) {
                FxResponse response = fxService.getQuotes();
                if (response != null && response.getQuotes() != null) {
                    BigDecimal exchangeRate = response.getQuotes().get(USD + currency.toUpperCase());
                    product.setPrice(product.getConvertedPrice(exchangeRate));
                } else {
                    throw new CustomResponseStatusException(HttpStatus.BAD_REQUEST, FX_CURRENCY_ERROR_CODE,
                            FAILED_FX_SERVICE_REASON);
                }
            } else {
                throw new CustomResponseStatusException(HttpStatus.BAD_REQUEST, NOT_SUPPORTED_CURRENCY_CODE,
                        String.format(NOT_SUPPORTED_CURRENCY, currency));
            }
        }
        return productConverter.toModel(product);
    }

    @Transactional
    public Product getProductAsync(String externalId, String currency) {
        Product product = getProduct(externalId);
        if (StringUtils.hasText(currency)) {
            if (SUPPORTED_CURRENCIES.contains(currency.toUpperCase())) {
                try {
                    BigDecimal exchange = getExchangeRate(currency.toUpperCase());
                    product.setPrice(product.getConvertedPrice(exchange));
                } catch (Exception e) {
                    e.printStackTrace();
                    throw new CustomResponseStatusException(HttpStatus.BAD_REQUEST, FX_CURRENCY_ERROR_CODE,
                            FAILED_FX_SERVICE_REASON);
                }
            } else {
                throw new CustomResponseStatusException(HttpStatus.BAD_REQUEST, NOT_SUPPORTED_CURRENCY_CODE,
                        String.format(NOT_SUPPORTED_CURRENCY, currency));
            }
        }
        return product;
    }

    @Transactional
    public Mono<ProductDTO> getProductAsyncWeb(String externalId, String currency) {
        logger.info("Started get calls..");
        Product product = getProduct(externalId);
        if (StringUtils.hasText(currency)) {
            if (SUPPORTED_CURRENCIES.contains(currency.toUpperCase())) {
                try {
                    Mono<FxResponse> response = fxService.getQuotesAsync();
                    /*response.subscribe(res->{
                        logger.info("Subscribed..");
                    });*/
                    return response.doOnNext(res -> {
                        logger.info("Step: doOnNext");
                        if (res == null || res.getQuotes() == null) {
                            logger.error("Quotes not available");
                            throw new CustomResponseStatusException(HttpStatus.BAD_REQUEST, FX_CURRENCY_ERROR_CODE,FAILED_FX_SERVICE_REASON);
                        }
                    }).map(res -> {
                        logger.info("Step: map");
                        BigDecimal exchangeRate = res.getQuotes().get(USD + currency.toUpperCase());
                        product.setPrice(product.getConvertedPrice(exchangeRate));
                        return productConverter.toModel(product);
                    });

                } catch (Exception e) {
                    e.printStackTrace();
                    throw new CustomResponseStatusException(HttpStatus.BAD_REQUEST, FX_CURRENCY_ERROR_CODE,
                            FAILED_FX_SERVICE_REASON);
                }
            } else {
                logger.error("Currency not supported");
                throw new CustomResponseStatusException(HttpStatus.BAD_REQUEST, NOT_SUPPORTED_CURRENCY_CODE,
                        String.format(NOT_SUPPORTED_CURRENCY, currency));
            }
        }
        return Mono.just(productConverter.toModel(product));
    }


    private BigDecimal getExchangeRate(String currency) throws URISyntaxException {
        BigDecimal rate=null;
        Mono<FxResponse> response = fxService.getQuotesAsync();
        FxResponse res = response.block();
        if (res != null && res.getQuotes() != null) {
            rate = res.getQuotes().get(USD + currency.toUpperCase());
        }
        if (rate != null) {
            return rate;
        } else {
            throw new CustomResponseStatusException(HttpStatus.BAD_REQUEST, FX_CURRENCY_ERROR_CODE,
                    FAILED_FX_SERVICE_REASON);
        }

    }


}
