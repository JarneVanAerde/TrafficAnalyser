package be.kdg.processor.persistence;

import be.kdg.processor.models.fines.Fine;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class FineDAO implements RepositoryCRUD<Fine> {
    private final List<Fine> fines;

    public FineDAO() {
        this.fines = new ArrayList<>();
    }

    @Override
    public void create(Fine fine) {
        fines.add(fine);
    }

    @Override
    public void delete(Fine fine) {
        fines.remove(fine);
    }

    @Override
    public Fine read(int id) {
        return fines.get(id);
    }

    @Override
    public Fine update(Fine fine) {
        return null;
    }
}