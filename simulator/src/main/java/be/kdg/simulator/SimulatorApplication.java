package be.kdg.simulator;

import be.kdg.simulator.receivers.Receiver;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.amqp.core.Queue;

//cyclic dependency
// => gebruik bij de ene constructor
// => gebruik field injection bij de andere
@SpringBootApplication
public class SimulatorApplication {
    static final String topicExcahgneName = "spring-boot-exchange";
    static final String queueName = "spring-boot";

    /**
     * @return A new rabbitmq-queue with a chosen queue-name
     */
    @Bean
    Queue queue() {
        return new Queue(queueName, false);
    }

    /**
     * @return Simple container collecting information to describe a topic exchange
     */
    @Bean
    TopicExchange exchange() {
        return new TopicExchange(topicExcahgneName);
    }

    /**
     * @param queue Your newly made queue
     * @param exchange Your newly made Topic exchange
     * @return A simple binding container that describes the binding
     */
    @Bean
    Binding binding(Queue queue, TopicExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with("foo.bar.#");
    }

    /**
     * @param connectionFactory connection for rabbitmq server
     * @param listenerAdapter listener that delegates the handling of messages to target listener
     * @return A new listener container
     */
    @Bean
    SimpleMessageListenerContainer container(ConnectionFactory connectionFactory, MessageListenerAdapter listenerAdapter) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setQueueNames(queueName);
        container.setMessageListener(listenerAdapter);
        return container;
    }

    /**
     * @param receiver delegation class used to push messages from listener to receiver
     * @return a listadapter that will listen for messages and map them to the right method of the receiver
     */
    @Bean
    MessageListenerAdapter listenerAdapter(Receiver receiver) {
        return new MessageListenerAdapter(receiver, "receivemessage");
    }

    public static void main(String[] args) {
        SpringApplication.run(SimulatorApplication.class, args);
    }
}
