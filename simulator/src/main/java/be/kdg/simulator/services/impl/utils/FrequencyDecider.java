package be.kdg.simulator.services.impl.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class FrequencyDecider {
    @Value("${scheduler.morningHours}")
    private String morningHours;
    @Value("${scheduler.eveningHours}")
    private String eveningHours;
    @Value("${scheduler.normalrate}")
    private long normalRate;
    @Value("scheduler.peakrate")
    private long peakRate;

    private LocalDateTime timeMoringBegin;
    private LocalDateTime timeMoringEnd;
    private LocalDateTime timeEveningBegin;
    private LocalDateTime timeEvenigEnd;

    public FrequencyDecider() {
    }

    private void initialzeTimePeriods() {

    }
}
