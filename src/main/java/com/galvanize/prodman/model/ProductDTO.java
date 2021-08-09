package com.galvanize.prodman.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonView;
import com.galvanize.prodman.annotation.BigDecimalScale;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import javax.swing.text.View;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;


@Getter
@Setter
public class ProductDTO {

    @JsonView({Views.GetResponse.class})
    @NotNull
    @Size(min =3,max = 50)
    private String name;

    @JsonView({Views.GetResponse.class})
    @Size(min=3,max = 255)
    private String description;

    @JsonView({Views.GetResponse.class})
    @Size(min=3,max = 255)
    private String externalProductId;

    @NotNull
    @BigDecimalScale(scale = 2)
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @JsonView({Views.GetResponse.class})
    private BigDecimal price;

    private String version;



    @JsonView({Views.GetResponse.class})
    private Integer viewCount;

}
