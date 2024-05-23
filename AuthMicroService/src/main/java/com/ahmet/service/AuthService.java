package com.ahmet.service;

import com.ahmet.AuthServiceApplication;
import com.ahmet.dto.request.DoLoginRequestDto;
import com.ahmet.dto.request.RegisterRequestDto;
import com.ahmet.dto.request.UserProfileSaveRequestDto;
import com.ahmet.exception.AuthServiceException;
import com.ahmet.exception.EErrorType;
import com.ahmet.manager.IUserProfileManager;
import com.ahmet.mapper.IAuthMapper;
import com.ahmet.rabbitmq.model.SaveAuthModel;
import com.ahmet.rabbitmq.producer.CreateUserProducer;
import com.ahmet.repository.IAuthRepository;
import com.ahmet.repository.entity.Auth;
import com.ahmet.utility.JwtTokenManager;
import com.ahmet.utility.ServiceManager;
import com.ahmet.utility.TokenManager;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AuthService extends ServiceManager<Auth,Long> {


    private final IAuthRepository repository;
    private final JwtTokenManager tokenManager;
    private final CreateUserProducer createUserProducer;
    private final IUserProfileManager iUserProfileManager;

    public AuthService(IAuthRepository repository, JwtTokenManager tokenManager, IUserProfileManager iUserProfileManager, CreateUserProducer createUserProducer) {
        super(repository);
        this.repository = repository;
        this.tokenManager = tokenManager;
        this.iUserProfileManager = iUserProfileManager;
        this.createUserProducer = createUserProducer;
    }

    public Auth register(RegisterRequestDto dto) {
        if(repository.isUsername(dto.getUsername())) { // Kullanıcı adı daha önceden alınmışsa
            throw new AuthServiceException(EErrorType.REGISTER_ERROR_USERNAME);
        }
        Auth auth = IAuthMapper.INSTANCE.toAuth(dto);
        /**
         * Repo -> repository.save(auth) - bana kaydettiği entity'yi döner.
         * Servis -> save(auth) - bana kaydettiği entity'yi döner.
         * direkt -> auth, bir şekilde kaydedilen entity'nin
         *           bilgileri işlenir ve bunu döner.
         */
//        return repository.save(auth);  // bunu tercih etmiycez burda.
        save(auth);  // bunu kullancaz. save -> ServiceManager'dan geliyo. Biz, ServiceManager'ın içindeki bu 'save' metoduna başka işlemler de ekliyoz; tüm Service dosyalarımızın 'save' işleminde kullanacağı (createat, updateat, state)
        // iUserProfileManager.save(IAuthMapper.INSTANCE.fromAuth(auth));
        createUserProducer.convertAndSend(SaveAuthModel.builder()
                        .authid(auth.getId())
                        .email(auth.getEmail())
                        .username(auth.getUsername())

                .build());
        return auth;
    }

    public Optional<Auth> findOptionalByUsernameAndPassword(String username, String password) {
        return repository.findOptionalByUsernameAndPassword(username, password);
    }

    /**
     * Kullanıcıyı doğrulayacak ve kullanıcının sistem içinde hareket
     * edebilmesi için ona özel bir kimlik verecek.
     * Token -> JWT token
     * @param dto
     * @return
     */
    public String doLogin(DoLoginRequestDto dto) {
        Optional<Auth> auth = repository.findOptionalByUsernameAndPassword(dto.getUsername(), dto.getPassword());
        if(auth.isEmpty()) {
            throw new AuthServiceException(EErrorType.LOGIN_ERROR_USERNAME_PASSWORD);
        } else {
            return tokenManager.createToken(auth.get().getId()).get();
        }
    }

    public List<Auth> findAll(String token) {
        Optional<Long> id = tokenManager.getByIdFromToken(token);
        if(id.isEmpty())
            throw new AuthServiceException(EErrorType.INVALID_TOKEN);
        if(findById(id.get()).isEmpty()) // Gelen id bizim sistemimizde yok ise (birisi kırıp sırayla deneme yapıyorsa diye önlem alıyoruz.)
            throw new AuthServiceException(EErrorType.INVALID_TOKEN);
        return findAll();
    }
}
