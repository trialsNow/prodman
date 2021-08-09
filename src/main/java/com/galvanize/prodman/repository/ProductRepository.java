package com.galvanize.prodman.repository;

import com.galvanize.prodman.domain.Product;

import java.util.Optional;

public interface ProductRepository {

    Optional<Product> getProductDetails(String externalProductId);

    int incrementViewCount(String externalProductId);
}
