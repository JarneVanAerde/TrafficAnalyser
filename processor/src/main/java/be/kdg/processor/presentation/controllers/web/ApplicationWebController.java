package be.kdg.processor.presentation.controllers.web;

import be.kdg.processor.config.QueueConfig;
import org.springframework.amqp.rabbit.listener.RabbitListenerEndpointRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

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

    @GetMapping("/queueStatus")
    public ModelAndView showCurrentQueueStatus() {
        if (!isQueuePaused) return new ModelAndView("queueRunning");
        else return new ModelAndView("queuePaused");
    }
}
