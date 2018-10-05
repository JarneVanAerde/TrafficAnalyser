package be.kdg.processor.persistence;

public interface RepositoryCRUD<T> {
    T create(T object);
    void delete(T object);
    T read(int id);
    T update(T object);
}
