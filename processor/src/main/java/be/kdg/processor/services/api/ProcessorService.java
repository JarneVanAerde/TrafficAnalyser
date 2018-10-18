package be.kdg.processor.services.api;

public interface ProcessorService<T> {
    void processMessage(T message);
}
