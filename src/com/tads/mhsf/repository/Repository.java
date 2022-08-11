package com.tads.mhsf.repository;

import java.util.List;
import java.util.Optional;

public interface Repository<T, ID> {
    void create(T t);
    Optional<T> findById(ID id);
    void update(T t);
    boolean deleteById(ID id);
    List<T> findAll();
}
