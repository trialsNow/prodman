package com.galvanize.prodman.model;

import java.util.Collections;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class ErrorResponse {

    @JsonProperty(value="errors")
    List<FieldError> errors;

    @JsonProperty
    private boolean success;


    public ErrorResponse(List<FieldError> errors){
        this.success = false;
        this.errors=errors;
    }

    public ErrorResponse(FieldError error){
        this.success = false;
        this.errors= Collections.singletonList(error);
    }


}
