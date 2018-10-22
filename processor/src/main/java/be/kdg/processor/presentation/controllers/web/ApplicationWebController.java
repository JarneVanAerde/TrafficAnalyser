package be.kdg.processor.presentation.controllers.web;

import org.springframework.amqp.rabbit.listener.RabbitListenerEndpointRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/app")
public class ApplicationWebController {
    private final RabbitListenerEndpointRegistry rabbitListenerEndpointRegistry;
    private boolean isQueuePaused = false;

    @Autowired
    public ApplicationWebController(RabbitListenerEndpointRegistry rabbitListenerEndpointRegistry) {
        this.rabbitListenerEndpointRegistry = rabbitListenerEndpointRegistry;
    }

    @GetMapping("/toggleQueue")
    public ModelAndView showQueueStatus() {
        if (!isQueuePaused) {
            isQueuePaused = true;
            rabbitListenerEndpointRegistry.getListenerContainer("cameraMessageQueue").stop();
            return new ModelAndView("queuePaused");
        }
        else {
            isQueuePaused = false;
            rabbitListenerEndpointRegistry.getListenerContainer("cameraMessageQueue").start();
            return new ModelAndView("queueRunning");
        }
    }
}
