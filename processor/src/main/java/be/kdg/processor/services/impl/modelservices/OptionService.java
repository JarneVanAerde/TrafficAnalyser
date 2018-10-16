package be.kdg.processor.services.impl.modelservices;

import be.kdg.processor.models.options.Option;
import be.kdg.processor.persistence.OptionsRepository;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class OptionService {
    private final OptionsRepository optionsRepository;
    @Getter
    private Option options;

    @Autowired
    public OptionService(OptionsRepository optionsRepository) {
        this.optionsRepository = optionsRepository;
        this.options = new Option();
        optionsRepository.save(this.options);
    }

    private void updateOptions(Option options) {
        this.options = optionsRepository.save(options);
    }

    public void setEmissionFactor(int factor) {
        this.options.setEmissionFactor(factor);
        updateOptions(this.options);
    }

    public void setSpeedFactor(int factor) {
        this.options.setSpeedFactor(factor);
        updateOptions(this.options);
    }
}
