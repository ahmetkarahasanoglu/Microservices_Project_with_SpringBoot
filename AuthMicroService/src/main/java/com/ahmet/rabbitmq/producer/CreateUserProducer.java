package com.ahmet.rabbitmq.producer;

import com.ahmet.rabbitmq.model.SaveAuthModel;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateUserProducer {
    /**
     * Belli bir bilginin Rabbitmq üzerinden iletilmesi işlemi
     * (Mikroservisler arası veri gönderimi - kuyruklu yapı (bek-
     * leme kabiliyetli)
     */
    private final RabbitTemplate rabbitTemplate;

    public void convertAndSend(SaveAuthModel model) { // Rabbitmq'da kullanılan iletme ve alma mesajlarının tamamı void'dir, herhangi bir şey dönmezler (kuyruk özelliği var [bekleme kabiliyetli].
        rabbitTemplate.convertAndSend("direct-exchange-auth",  // Mesajı base64'e çevirerek (serileştirerek) gönderiyor.
                "save-auth-key", model);
    }

}
