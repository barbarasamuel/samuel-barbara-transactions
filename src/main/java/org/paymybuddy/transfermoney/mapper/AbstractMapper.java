package org.paymybuddy.transfermoney.mapper;


import java.util.List;

public interface AbstractMapper <E,D>{
    E convertToEntity(D dto);
    D convertToDTO(E entity);
    List<D> convertListToDTO(List<E> entity);
}
