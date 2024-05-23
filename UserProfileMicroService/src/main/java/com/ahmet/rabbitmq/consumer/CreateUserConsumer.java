package com.ahmet.rabbitmq.consumer;

import com.ahmet.rabbitmq.model.SaveAuthModel;
import com.ahmet.repository.entity.UserProfile;
import com.ahmet.service.UserProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

/**
 * Rabbitmq ile kuyruk özellikli gönderme (bekleme özellikli)
 *
 * GÖNDERİCİ: AuthMicroService / rabbitmq / producer / CreateUserProducer
 * ALICI    : burası (CreateUserConsumer)
 */

@Service
@RequiredArgsConstructor
public class CreateUserConsumer {

    private final UserProfileService userProfileService;

    @RabbitListener(queues = "queue-auth") // "queue-auth": RabbitConfig sınıfındaki "queue-auth" u yazdık. -> "queue-auth", AuthMikro'dan gönderilen queue'dur. |||  Bu metot kuyruğu sürekli dinleyecek, gelen bir şey var mı diye.
    public void createUserFromHandleQueue(SaveAuthModel model) { // SaveAuthModel model : Biz, AuthMicroService/rabbitmq/producer/CreateUserProducer sınıfında bu SaveAuthModel model'i göndermiştik buraya. Ordan serialize edilip burda deserialize
        System.out.println("Gelen Data....: " + model.getUsername());
        userProfileService.saveRabbit(model); // gelen veriyi kaydediyoruz.
    }

}
