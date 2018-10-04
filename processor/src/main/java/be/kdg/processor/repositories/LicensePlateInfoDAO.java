package be.kdg.processor.repositories;

import be.kdg.processor.models.licensePlateInfos.LicensePlateInfo;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class LicensePlateInfoDAO implements RepositoryCRUD<LicensePlateInfo> {
    private final List<LicensePlateInfo> licensePlateInfos;

    public LicensePlateInfoDAO() {
        this.licensePlateInfos = new ArrayList<>();
    }

    @Override
    public void add(LicensePlateInfo licensePlateInfo) {
        licensePlateInfos.add(licensePlateInfo);
    }

    @Override
    public void remove(LicensePlateInfo licensePlateInfo) {
        licensePlateInfos.remove(licensePlateInfo);
    }

    @Override
    public LicensePlateInfo get(int id) {
        return licensePlateInfos.get(id);
    }

    @Override
    public LicensePlateInfo update(LicensePlateInfo licensePlateInfo) {
        return null;
    }
}
