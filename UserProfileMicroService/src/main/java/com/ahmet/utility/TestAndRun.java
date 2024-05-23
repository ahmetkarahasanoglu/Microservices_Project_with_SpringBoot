package com.ahmet.utility;

import com.ahmet.manager.IElasticServiceManager;
import com.ahmet.repository.entity.UserProfile;
import com.ahmet.service.UserProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;

@Component // Uygulama ayağa kalkarken bu sınıftan nesne yaratır.
@RequiredArgsConstructor
public class TestAndRun {

    private final UserProfileService userProfileService;
    private final IElasticServiceManager elasticServiceManager;

    @PostConstruct // Bu sınıftan nesne oluşturulduğunda bu blok çalışacak (Uygulama ayağı kalktığında bu blok çalışacak. - Yukarıdaki alanlardan (@RequiredArgsConstructor ile) nesne oluşturulduktan hemen sonra bu metot çalışacak))
    public void init() {
        /**
         * Bu kısım kullanılacak ise, (zorunlu durumlar için) işiniz
         * bitince bu kodu (bi alt satırdaki 'run' metodunu) yorum
         * satırına almak doğru olacaktır.
         * Çalışması sistemi etkilemeyecek durumlarda ise thread içinde
         * çalıştırmak doğru olacaktır (yani bu 'run' bloğu arka
         * planda çalışsın, işi bitince de dursun; ve sistemimin
         * ayağa kalkmasını geciktirmesin istiyorsak Thread kullanmalıyız).
         */
//        new Thread(()->{
//            run();
//        });
    }

    public void run() {
        List<UserProfile> list = userProfileService.findAll();
        list.forEach(x-> {
            elasticServiceManager.addUser(x); // (UserMicro'nun veri tabanındaki tüm verilerin ElasticMicro'ya gönderilmesi için)
        });
    }

}
