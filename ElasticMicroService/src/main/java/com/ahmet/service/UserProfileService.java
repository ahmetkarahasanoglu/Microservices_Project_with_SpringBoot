package com.ahmet.service;

import com.ahmet.dto.request.AddUserRequestDto;
import com.ahmet.dto.request.BaseRequestDto;
import com.ahmet.mapper.IUserProfileMapper;
import com.ahmet.repository.IUserProfileRepository;
import com.ahmet.repository.entity.UserProfile;
import com.ahmet.utility.ServiceManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserProfileService extends ServiceManager<UserProfile,String> {

    private final IUserProfileRepository repository;

    public UserProfileService(IUserProfileRepository repository) {
        super(repository);
        this.repository = repository;
    }


    public Optional<UserProfile> findByAuthId(Long authid) {
        return repository.findOptionalByAuthid(authid);
    }

    public void saveDto(AddUserRequestDto dto) {
        if(!repository.existsByUserprofileid(dto.getId())) { // 'isExist' false ise (veritabanında kayıtlı değilse); yani userid daha önceden kayıt edilMEMİŞ ise kayıt işlemi yapsın.
            save(IUserProfileMapper.INSTANCE.toUserProfile(dto));
        }
    }

    /**
     * ----- PAGINATION ------
     * Sayfalama (pagination) işlemlerinde belli bilgiler olmadan
     * işlem yapmak mümkün değildir:
     * - Liste gelmeli
     * - Toplam kayıt miktarı
     * - Hangi sayfada olduğum
     * - Kaç sayfa var
     * - Sonraki sayfa var mı?
     * - Son sayfada mıyım?
     */
    public Page<UserProfile> findAll(BaseRequestDto dto) {
        /**
         * Sıralama ve sayfalama için bize nesneler ve ayarlamalar
         * gerekli.
         */
        Pageable pageable = null;
        Sort sort = null;
        /**
         * Eğer kişi sıralama istediği alanı yazmamış ise yapmak istemiyordur.
         */
        if(dto.getSortParameter()!=null) { // Kullanıcı bir sıralama alanı seçmişse
            String direction = dto.getDirection()==null ? "ASC" : dto.getDirection();
            sort = Sort.by(Sort.Direction.fromString(direction), dto.getSortParameter());
        }
        /**
         * 1. durum -> sıralama yapmak ister ve sayfalama yapmak ister
         * 2. durum -> sıralama istemiyor ve sayfalama yapmak istiyor.
         * 3. durum -> sıralama istemiyor ve sayfalama isteğinde bulunmuyor.
         */
        Integer pageSize = dto.getPageSize()==null ? 10 :
                dto.getPageSize()<1 ? 10 : dto.getPageSize();
        if(sort!=null && dto.getCurrentPage()!=null) { // 1. durum
            pageable = PageRequest.of(dto.getCurrentPage(), pageSize, sort);
        }else if(sort==null && dto.getCurrentPage()!=null) { // 2. durum
            pageable = PageRequest.of(dto.getCurrentPage(), pageSize);
        }else {
            pageable = PageRequest.of(0, pageSize); // 3. durum ||| currentPage:0  pageSize:10(default)
        }
        return repository.findAll(pageable);
    }

}
