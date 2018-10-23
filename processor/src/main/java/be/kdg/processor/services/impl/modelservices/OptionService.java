package be.kdg.processor.services.impl.modelservices;

import be.kdg.processor.config.RetryConfig;
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
     * Adds the default options of the application.
     */
    private void addDefaultOptions() {
        saveOption(new Option(OptionKey.SPEED_FAC.toString(), 2));
        saveOption(new Option(OptionKey.EMISSION_FAC.toString(), 100));
        saveOption(new Option(OptionKey.TIME_FRAME_EMISSION.toString(), 24));
        saveOption(new Option(OptionKey.TIME_FRAME_SPEED_MESSAGE.toString(), 15));
        saveOption(new Option(OptionKey.RETRY_DELAY.toString(), 2500));
        saveOption(new Option(OptionKey.RETRY_ATTEMPTS.toString(), 2));
    }

    /**
     * if the option has something to do with the retryTemplate,
     * then that template will be updated.
     *
     * @param option the option to save.
     */
    public void saveOption(Option option) {
        if (option.getKey().equalsIgnoreCase(OptionKey.RETRY_DELAY.toString()))
            RetryConfig.setDelay((long) option.getValue());
        if (option.getKey().equalsIgnoreCase(OptionKey.RETRY_ATTEMPTS.toString()))
            RetryConfig.setMaxAttemps((int) option.getValue());

        optionsRepository.save(option);
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
     * @return all the options
     */
    public List<Option> getOptions() {
        return Collections.unmodifiableList(optionsRepository.findAll());
    }
}
