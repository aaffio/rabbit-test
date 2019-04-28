package com.afrianpasa.rabbittest.rabbitmq;

import com.afrianpasa.rabbittest.RabbitTestApplication;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class Runner implements CommandLineRunner {

    private final RabbitTemplate rabbitTemplate;
    private final Receiver receiver;

    public Runner(Receiver receiver, RabbitTemplate rabbitTemplate) {
        this.receiver = receiver;
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("Sending message...");
        rabbitTemplate.convertAndSend(RabbitTestApplication.topicExchangeName, "rabbit.test.route", "Hello from RabbitMQ!");
        receiver.getLatch().await(50000, TimeUnit.MILLISECONDS);
    }

}