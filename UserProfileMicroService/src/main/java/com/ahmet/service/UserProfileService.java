package com.ahmet.service;

import com.ahmet.dto.request.UserProfileSaveRequestDto;
import com.ahmet.manager.IElasticServiceManager;
import com.ahmet.mapper.IUserProfileMapper;
import com.ahmet.rabbitmq.model.SaveAuthModel;
import com.ahmet.repository.IUserProfileRepository;
import com.ahmet.repository.entity.UserProfile;
import com.ahmet.utility.ServiceManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class UserProfileService extends ServiceManager<UserProfile,Long> {

    private final IUserProfileRepository repository;
    private final IElasticServiceManager elasticServiceManager;

    public UserProfileService(IUserProfileRepository repository, IElasticServiceManager elasticServiceManager) {
        super(repository);
        this.repository = repository;
        this.elasticServiceManager = elasticServiceManager;
    }

    public Boolean saveDto(UserProfileSaveRequestDto dto) {
        save(IUserProfileMapper.INSTANCE.toUserProfile(dto));
        return true;
    }

    public void saveRabbit(SaveAuthModel model) {
        UserProfile profile = IUserProfileMapper.INSTANCE.toUserProfile(model);
        save(profile);
//        elasticServiceManager.addUser(profile);
    }

    /**
     * Bu, uzun süren bir işlemi simüle etmek için Thread kullanılarak
     * bekletilmiş bir metottur.
     * - Bu metot, örneğin "Kağıt" kelimesi için her seferinde aynı sonucu
     *   mu üretir? Evet. O yüzden her seferinde veri tabanına
     *   gidip gelmesine gerek yok; Cache'e alırız.
     */
    @Cacheable(value = "getUpperName")
    public String getUpper(String name) {
        try{
            Thread.sleep(3000L);
        }catch(Exception e) {

        }
        return name.toUpperCase();
    }

    @CacheEvict(value = "getUpperName", allEntries = true) // 'getUpper' metodunun tüm cache kayıtlarını temizler.
    public void clearCache() {
        System.out.println("Tüm kayıtları temizledik");
    }

}
