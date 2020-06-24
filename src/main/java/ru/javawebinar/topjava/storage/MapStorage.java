package ru.javawebinar.topjava.storage;


import java.util.Collection;

public interface MapStorage<T> {
    T save(T object);

    void delete(long id);

    T get(long id);

    Collection<T> getAll();
}
