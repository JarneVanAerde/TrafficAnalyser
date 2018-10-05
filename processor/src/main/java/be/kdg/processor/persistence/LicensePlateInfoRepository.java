package be.kdg.processor.persistence;

import be.kdg.processor.models.licensePlates.LicensePlateInfo;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class LicensePlateInfoRepository implements RepositoryCRUD<LicensePlateInfo> {
    private final List<LicensePlateInfo> licensePlateInfos;

    public LicensePlateInfoRepository() {
        this.licensePlateInfos = new ArrayList<>();
    }

    @Override
    public LicensePlateInfo create(LicensePlateInfo licensePlateInfo) {
        licensePlateInfos.add(licensePlateInfo);
        return licensePlateInfo;
    }

    @Override
    public void delete(LicensePlateInfo licensePlateInfo) {
        licensePlateInfos.remove(licensePlateInfo);
    }

    @Override
    public LicensePlateInfo read(int id) {
        return licensePlateInfos.get(id);
    }

    @Override
    public LicensePlateInfo update(LicensePlateInfo licensePlateInfo) {
        return null;
    }
}
