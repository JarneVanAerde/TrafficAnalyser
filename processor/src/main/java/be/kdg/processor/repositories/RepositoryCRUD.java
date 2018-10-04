package be.kdg.processor.repositories;

public interface RepositoryCRUD<T> {
    void create(T object);
    void delete(T object);
    T read(int id);
    T update(T object);
}
