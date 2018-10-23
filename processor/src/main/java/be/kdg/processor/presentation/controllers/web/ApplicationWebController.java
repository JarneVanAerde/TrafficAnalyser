package be.kdg.processor.presentation.controllers.web;

import be.kdg.processor.config.QueueConfig;
import org.springframework.amqp.rabbit.listener.RabbitListenerEndpointRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Web controller that is used to control everything
 * that has to do with the application.
 */
@Controller
@RequestMapping("/app")
public class ApplicationWebController {
    private final QueueConfig queueConfig;
    private final RabbitListenerEndpointRegistry rabbitListenerEndpointRegistry;
    private boolean isQueuePaused = false;

    @Autowired
    public ApplicationWebController(QueueConfig queueConfig, RabbitListenerEndpointRegistry rabbitListenerEndpointRegistry) {
        this.queueConfig = queueConfig;
        this.rabbitListenerEndpointRegistry = rabbitListenerEndpointRegistry;
    }

    /**
     * Toggles the queue from paused to running
     * @return the corresponding view based on the queue status.
     */
    @GetMapping("/toggleQueue")
    public ModelAndView showQueueStatus() {
        if (!isQueuePaused) {
            isQueuePaused = true;
            rabbitListenerEndpointRegistry.getListenerContainer(queueConfig.getQueueId()).stop();
            return new ModelAndView("queuePaused");
        }
        else {
            isQueuePaused = false;
            rabbitListenerEndpointRegistry.getListenerContainer(queueConfig.getQueueId()).start();
            return new ModelAndView("queueRunning");
        }
    }

    /**
     * @return current queue status without toggeling.
     */
    @GetMapping("/queueStatus")
    public ModelAndView showCurrentQueueStatus() {
        if (!isQueuePaused) return new ModelAndView("queueRunning");
        else return new ModelAndView("queuePaused");
    }
}
