package be.kdg.processor.services.api;

import be.kdg.processor.services.exceptions.ServiceException;

public interface DetectionService<T> {
    void detectFine(T message) throws ServiceException;
}
