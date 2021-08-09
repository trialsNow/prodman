package com.galvanize.prodman.constants;

import java.util.Arrays;
import java.util.List;

public class Constants {
    public static final String PRODUCT_NOT_EXIST = "Cannot find product: %s";
    public static final String NOT_SUPPORTED_CURRENCY = "Currency not supported: %s";
    //Use static data
    public static final String NOT_SUPPORTED_CURRENCY_CODE= "PROD-E001";
    public static final String FX_CURRENCY_ERROR_CODE= "FX-E001";
    public static final String FAILED_FX_SERVICE_REASON= "Cannot convert to requested currency. Please use request without currency param";
    public static final String PRODUCT_NOT_FOUND_CODE= "PROD-E002";
    public static final List<String> SUPPORTED_CURRENCIES = Arrays.asList("USD","CAD","EUR","GBP");
    public static final String USD = "USD";
    public static final String DEFAULT_REASON = "Data Validation Failed: %s";
    public static final String UNEXPECTED_CODE="PROD-E500";
}
