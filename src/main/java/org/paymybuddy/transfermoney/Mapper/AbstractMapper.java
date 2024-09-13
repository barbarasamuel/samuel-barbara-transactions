package org.paymybuddy.transfermoney.Mapper;

import org.springframework.data.domain.Page;

import java.util.List;

public interface AbstractMapper <E,D>{
    E convertToEntity(D dto);
    D convertToDTO(E entity);
    List<D> convertListToDTO(List<E> entity);
    //Iterable<E> convertIterableToDTO(List<D> entity);
    //Page<D> converttoDTO(Page<E> entity);
}
