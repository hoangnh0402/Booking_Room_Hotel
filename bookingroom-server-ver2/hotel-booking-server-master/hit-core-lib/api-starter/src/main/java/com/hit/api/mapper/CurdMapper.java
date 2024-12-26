package com.hit.api.mapper;

import com.hit.common.core.domain.model.UserPrincipal;
import org.mapstruct.Context;
import org.mapstruct.IterableMapping;
import org.mapstruct.Named;

import java.util.List;

public interface CurdMapper<Rq, Rs, E> {

    @Named("toResponse")
    Rs toResponse(E e, @Context UserPrincipal userPrincipal);

    @IterableMapping(qualifiedByName = "toResponse")
    List<Rs> toResponses(List<E> list, @Context UserPrincipal userPrincipal);

    @Named("toEntity")
    E toEntity(Rq rq, @Context UserPrincipal userPrincipal);

    @IterableMapping(qualifiedByName = "toEntities")
    List<E> toEntities(List<Rq> list, @Context UserPrincipal userPrincipal);

}
