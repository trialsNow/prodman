package com.galvanize.prodman.service;

//Out of container test
import com.galvanize.prodman.domain.Product;
import com.galvanize.prodman.exception.CustomResponseStatusException;
import com.galvanize.prodman.model.FxResponse;
import com.galvanize.prodman.model.ProductDTO;
import com.galvanize.prodman.model.converter.ProductConverter;
import com.galvanize.prodman.repository.ProductRepository;
import mockit.Expectations;
import mockit.Injectable;
import mockit.Tested;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ProductServiceTest {

    private static final String externalId="TST-001";

    @Injectable
    FxService fxService;

    @Injectable
    ProductRepository productRepository;

    @Tested
    ProductConverter productConverter;

    @Tested
    ProductService sut;

    @Test
    void testGetValidProductById(){
        withNoCurrencyExpectations(externalId,1);
        ProductDTO response = sut.getProductById(externalId,null);
        assertEquals(BigDecimal.TEN,response.getPrice());
    }

    @Test
    void testGetValidProductByIdAndCurrency(){
        withServiceExpectations("CAD",externalId,1);
        ProductDTO response = sut.getProduct(externalId,"CAD");
        assertEquals(BigDecimal.TEN.multiply(BigDecimal.valueOf(1.25).setScale(2, BigDecimal.ROUND_HALF_UP))
                ,response.getPrice());
    }

    @Test
     void testInvalidExternalId() {
        assertThrows(CustomResponseStatusException.class, () -> {
            sut.getProductById("12344",null);
        });
    }

    private void withNoCurrencyExpectations(final String externalId, int update){
        new Expectations(){
            {
                productRepository.incrementViewCount(externalId);
                result= update;

                productRepository.getProductDetails(externalId);
                result= Product.builder().name("Test").description("Mocked data").
                        price(BigDecimal.TEN).externalProductId(externalId).deleted(false).build();
            }

        };
    }



    private void withServiceExpectations(final String currency,final String externalId,int update){
        new Expectations(){
            {
                productRepository.incrementViewCount(externalId);
                result= update;

                productRepository.getProductDetails(externalId);
                result= Product.builder().name("Test").description("Mocked data").
                        price(BigDecimal.TEN).externalProductId(externalId).deleted(false).build();

                fxService.getQuotes();
                FxResponse fxResponse = new FxResponse();
                Map<String, BigDecimal> quotes = new HashMap<>();
                quotes.put("USDCAD",BigDecimal.valueOf(1.25));
                fxResponse.setQuotes(quotes);
                result= fxResponse;
            }

        };
    }

}
