package com.afrianpasa.rabbittest.rabbitmq.publisher;

import com.afrianpasa.rabbittest.RabbitTestApplication;
import com.afrianpasa.rabbittest.domain.request.BookIdRequest;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class BookPublisher {
    @Autowired
    private AmqpTemplate amqpTemplate;

    public void getBookById(BookIdRequest req) {
        Gson gson = new Gson();
        log.info(gson.toJson(req));
        try {
            amqpTemplate.convertAndSend(RabbitTestApplication.topicExchangeName, RabbitTestApplication.routingKey,
                    gson.toJson(req));
            log.info(" [x] Sent for process for book id :'" + req.getId() + "'");
        } catch (Exception e) {
            log.error("{} :: {}",e.getMessage(), e.getCause());
        }
    }
}
