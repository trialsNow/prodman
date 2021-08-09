package com.galvanize.prodman.model.converter;

import com.galvanize.prodman.domain.Product;
import com.galvanize.prodman.model.ProductDTO;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class ProductConverter extends AbstractConverter<ProductDTO, Product> {
    public ProductConverter() {
        super();
        this.setFromDto(toDomain);
        this.setFromDomain(toDto);
    }

    private Function<ProductDTO, Product> toDomain =
            productDTO -> Product.builder().name(productDTO.getName()).
                    description(productDTO.getDescription()).price(productDTO.getPrice()).
                    version(productDTO.getVersion()).views(productDTO.getViewCount()).externalProductId(productDTO.getExternalProductId()).build();
    private Function<Product, ProductDTO> toDto =
            productDomain -> {
                ProductDTO product = new ProductDTO();
                product.setName(productDomain.getName());
                product.setDescription(productDomain.getDescription());
                product.setPrice(productDomain.getPrice());
                product.setVersion(productDomain.getVersion());
                product.setViewCount(productDomain.getViews());
                product.setExternalProductId(productDomain.getExternalProductId());
                return product;
            };
}
