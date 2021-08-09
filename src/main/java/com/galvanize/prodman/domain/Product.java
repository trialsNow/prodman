package com.galvanize.prodman.domain;

import lombok.*;

import java.math.BigDecimal;


@Getter
@Setter
@Builder
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@AllArgsConstructor(access=AccessLevel.PACKAGE)
public class Product {

    private Integer productId;
    private String externalProductId;
    private String name;
    private String description;
    private BigDecimal price;
    private Integer views;
    private Boolean deleted;
    private String version;

    public BigDecimal getConvertedPrice(BigDecimal exchangeRate){
        return this.getPrice().multiply(exchangeRate).setScale(2, BigDecimal.ROUND_HALF_UP);
    }

}
