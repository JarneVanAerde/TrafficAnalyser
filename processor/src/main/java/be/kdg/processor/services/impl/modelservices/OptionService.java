package be.kdg.processor.services.impl.modelservices;

import be.kdg.processor.models.options.Option;
import be.kdg.processor.models.options.OptionKey;
import be.kdg.processor.persistence.OptionRepository;
import be.kdg.processor.services.exceptions.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class OptionService {
    private final OptionRepository optionsRepository;

    @Autowired
    public OptionService(OptionRepository optionsRepository) {
        this.optionsRepository = optionsRepository;
        addDefaultOptions();
    }

    private void addDefaultOptions() {
        saveOption(new Option(OptionKey.SPEED_FAC, 2));
        saveOption(new Option(OptionKey.EMISSION_FAC, 100));
    }

    private void saveOption(Option option) {
        optionsRepository.save(option);
    }

    public double getOptionValue(OptionKey key) throws ServiceException {
        return optionsRepository.findById(key)
                .orElseThrow(() -> new ServiceException(getClass().getSimpleName() + ": value for key + " + key + " was not found in the database"))
                .getValue();
    }
}
