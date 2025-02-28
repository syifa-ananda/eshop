package id.ac.ui.cs.advprog.eshop.repository;

import java.util.Iterator;

public interface BaseRepository<T, I> {
    T create(T entity);
    Iterator<T> findAll();
    T findById(I id);
    T update(I id, T entity);
    void delete(I id);
}
