package com.afrianpasa.rabbittest.rabbitmq.consumer;

import com.afrianpasa.rabbittest.domain.Book;
import com.afrianpasa.rabbittest.domain.request.BookIdRequest;
import com.afrianpasa.rabbittest.repository.BookRepository;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Slf4j
@Component
public class BookConsumer {

    @Autowired
    private BookRepository bookRepository;

    @RabbitListener(queues = "${app.queue.name}")
    public void execute(String req) {
        Gson gson = new Gson();
        BookIdRequest bookIdRequest = gson.fromJson(req, BookIdRequest.class);
        log.info(" [x] [Rabbit Process] Received for process for book id : {}", bookIdRequest.getId());
        try {
            Optional<Book> book = bookRepository.findById(bookIdRequest.getId());
            //TODO change code below to your requirement
            if (book.isPresent())
                log.info("response : {}", book.get());
            else
                log.warn("response : Book not found");
        } catch (Exception e) {
            log.info(" [x] [Rabbit Process] process failed with error {} '" + e.getMessage());
        }
    }
}
