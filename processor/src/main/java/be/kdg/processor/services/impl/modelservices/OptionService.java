package be.kdg.processor.services.impl.modelservices;

import be.kdg.processor.models.options.Option;
import be.kdg.processor.models.options.OptionKey;
import be.kdg.processor.persistence.OptionRepository;
import be.kdg.processor.services.exceptions.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

/**
 * This service is used for option CRUD.
 */
@Service
@Transactional
public class OptionService {
    private final OptionRepository optionsRepository;

    @Autowired
    public OptionService(OptionRepository optionsRepository) {
        this.optionsRepository = optionsRepository;
        addDefaultOptions();
    }

    /**
     * Adds the default options of the application
     */
    private void addDefaultOptions() {
        saveOption(new Option(OptionKey.SPEED_FAC.toString(), 2));
        saveOption(new Option(OptionKey.EMISSION_FAC.toString(), 100));
    }

    /**
     * @param option the option to save
     * @return the saved option with an id.
     */
    public Option saveOption(Option option) {
        return optionsRepository.save(option);
    }

    /**
     * @param key the option key
     * @return the option value that corresponds with the key
     * @throws ServiceException wrapper-exception
     */
    public double getOptionValue(OptionKey key) throws ServiceException {
        return optionsRepository.findById(key.toString())
                .orElseThrow(() -> new ServiceException(getClass().getSimpleName() + ": value for key + " + key + " was not found in the database"))
                .getValue();
    }

    /**
     * @param key the option key
     * @return the corresponding option
     * @throws ServiceException wrapper-exception
     */
    public Option getOption(OptionKey key) throws ServiceException {
        return optionsRepository.findById(key.toString())
                .orElseThrow(() -> new ServiceException(getClass().getSimpleName() + ": Option with key " + key + " was not found in the database"));
    }

    /**
     * @return all the options
     */
    public List<Option> getOptions() {
        return Collections.unmodifiableList(optionsRepository.findAll());
    }
}
