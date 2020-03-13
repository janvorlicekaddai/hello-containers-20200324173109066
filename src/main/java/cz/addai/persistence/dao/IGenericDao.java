package cz.addai.persistence.dao;

import java.io.Serializable;
import java.util.List;

public interface IGenericDao<K extends Serializable, T extends Serializable> {

    T findOne(final K id);

    List<T> findAll();

    void create(final T entity);

    T update(final T entity);

    void delete(final T entity);

    void deleteById(final K entityId);
}