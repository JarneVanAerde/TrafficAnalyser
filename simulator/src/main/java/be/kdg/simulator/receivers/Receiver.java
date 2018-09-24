package be.kdg.simulator.receivers;

import org.springframework.stereotype.Component;

import java.util.concurrent.CountDownLatch;

@Component
public class Receiver {
    private CountDownLatch latch = new CountDownLatch(1);

    /**
     * Receives the message that was sent by the rabbittemplate
     * @param message message that was composed in rabbittemplate
     */
    public void receiveMessage(String message) {
        System.out.println("Received <" + message + ">");
        latch.countDown();
    }

    /**
     * @return latch is used for multithreading
     */
    public CountDownLatch getLatch() {
        return latch;
    }
}