package com.galvanize.prodman.repository.impl;

import com.galvanize.prodman.domain.Product;
import com.galvanize.prodman.repository.BaseDataAccessor;
import com.galvanize.prodman.repository.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static com.galvanize.prodman.repository.QueryConstants.GET_PRODUCT_DETAILS_BY_EXT_ID;
import static com.galvanize.prodman.repository.QueryConstants.INCREMENT_VIEW_COUNT;

@Repository
public class ProductRepositoryImpl extends BaseDataAccessor implements ProductRepository {
    private static final Logger logger = LoggerFactory.getLogger(ProductRepositoryImpl.class);

    @Override
    public Optional<Product> getProductDetails(String externalProductId) {
        Optional<Product> productDetails;
        try {
            productDetails =
                    queryObject(GET_PRODUCT_DETAILS_BY_EXT_ID, new Object[]{externalProductId}, Product.class);
        } catch (EmptyResultDataAccessException e) {
            logger.warn("No Data Found for Product Id:{}",externalProductId);
            return Optional.empty();
        }
        return productDetails;
    }

    @Override
    public int incrementViewCount(String externalProductId){
        SqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue("externalProductId", externalProductId);
        return namedParameterJdbcTemplate.update(INCREMENT_VIEW_COUNT, namedParameters);
    }

    private <T> Optional<T> queryObject(String sql, Object[] params, Class<T> convertClass) {

        T t = jdbcTemplate.queryForObject(sql,
                new BeanPropertyRowMapper<>(convertClass),
                params
        );
        return Optional.ofNullable(t);

    }
}
