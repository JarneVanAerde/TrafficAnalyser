package be.kdg.simulator.services.impl.utils;

import be.kdg.simulator.configs.FrequencyConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * Decides the frequency for normal -and peak traffic conditions
 */
@Service
public class FrequencyDecider {
    private final FrequencyConfig frequencyConfig;
    private LocalTime timeMorningBegin;
    private LocalTime timeMorningEnd;
    private LocalTime timeEveningBegin;
    private LocalTime timeEveningEnd;

    @Autowired
    public FrequencyDecider(FrequencyConfig frequencyConfig) {
        this.frequencyConfig = frequencyConfig;
        initialzeTimePeriods();
    }

    /**
     * Initializes the timestamp ranges based on the information
     * out of the config file
     */
    private void initialzeTimePeriods() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");

        String[] morningHours = frequencyConfig.getMorningHours().split("-");
        timeMorningBegin = LocalTime.parse(morningHours[0], formatter);
        timeMorningEnd = LocalTime.parse(morningHours[1], formatter);

        String[] eveningHours = frequencyConfig.getEveningHours().split("-");
        timeEveningBegin = LocalTime.parse(eveningHours[0], formatter);
        timeEveningEnd = LocalTime.parse(eveningHours[1], formatter);
    }

    /**
     * Calculates whether a normal or peak frequency needs to be returned.
     * @return corresponding frequency.
     */
    public long getFrequency() {
        LocalTime now = LocalTime.now();

        if ((now.isAfter(timeMorningBegin) && now.isBefore(timeMorningEnd)
                || (now.isAfter(timeEveningBegin) && now.isBefore(timeEveningEnd)))) return frequencyConfig.getPeakRate();
        else return frequencyConfig.getNormalRate();
    }
}
