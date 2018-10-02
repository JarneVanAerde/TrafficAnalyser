package be.kdg.processor.services;

public interface DetectionService<E> {
    void detectFine(E message);
}
