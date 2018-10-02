package be.kdg.processor.services;

import java.io.IOException;

public interface DetectionService<E> {
    void detectFine(E message) throws IOException;
}
