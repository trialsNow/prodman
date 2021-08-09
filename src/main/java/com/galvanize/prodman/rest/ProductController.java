package com.galvanize.prodman.rest;

import com.fasterxml.jackson.annotation.JsonView;
import com.galvanize.prodman.model.ProductDTO;
import com.galvanize.prodman.model.SuccessView;
import com.galvanize.prodman.model.Views;
import com.galvanize.prodman.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import javax.validation.constraints.NotEmpty;


@RestController
@RequestMapping(value = "/api/", produces = MediaType.APPLICATION_JSON_VALUE)
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(final ProductService productService) {
        this.productService = productService;
    }

    @PatchMapping(value = "/products/{productId}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    @JsonView(Views.GetResponse.class)
    public Mono<ProductDTO> getProductDetailsByExternalId(@PathVariable @NotEmpty String productId,
                                                                       @RequestParam(name = "currency", required = false)
                                                                         String currency) {
        return productService.getProductAsyncWeb(productId, currency);
    }
}
