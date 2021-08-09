package com.galvanize.prodman.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class FieldError {

    @JsonProperty
    private String code;

    @JsonProperty
    private String reason;

    public FieldError(String code, String reason){
        this.code=code;
        this.reason=reason;
    }

}
