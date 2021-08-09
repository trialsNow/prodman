package com.galvanize.prodman.repository;

    public class QueryConstants {

        public static final String GET_PRODUCT_DETAILS_BY_EXT_ID ="SELECT NAME,DESCRIPTION,PRICE,VIEWS,EXTERNAL_PRODUCT_ID FROM PRODUCT WHERE " +
            "EXTERNAL_PRODUCT_ID=:externalProductId AND DELETED=0";

        public static final String INCREMENT_VIEW_COUNT ="UPDATE PRODUCT SET VIEWS = (SELECT MAX(VIEWS)+1 FROM PRODUCT WHERE " +
                "EXTERNAL_PRODUCT_ID=:externalProductId AND DELETED=0) WHERE " +
                "EXTERNAL_PRODUCT_ID=:externalProductId";
}
