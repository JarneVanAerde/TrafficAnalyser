package be.kdg.processor.repositories;

public interface RepositoryCRUD<T> {
    void add(T object);
    void remove(T object);
    T get(int id);
    T update(T object);
}
