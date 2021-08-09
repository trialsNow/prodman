package com.galvanize.prodman.model.converter;

import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public abstract class AbstractConverter<T, C> {

    Function<T, C> fromDto;
    Function<C, T> fromDomain;

    public void setFromDto(Function<T, C> fromDto) {
        this.fromDto = fromDto;
    }

    public  void setFromDomain(Function<C, T> fromDomain) {
        this.fromDomain = fromDomain;
    }

    public  C toDomain(final T model) {
        return fromDto.apply(model);
    }

    public  T toModel(final C c) {
        return fromDomain.apply(c);
    }
}
