package com.galvanize.prodman.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SuccessResponse {

    @JsonProperty
    private final String success= "true";

    @JsonProperty
    private final String status ="200";


}
