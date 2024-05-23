package com.ahmet.repository;

import com.ahmet.repository.entity.Auth;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IAuthRepository extends JpaRepository<Auth,Long> {

    /**
     * Bu kullanıcı adı daha önce kullanılmış mı; birisi 'signup'
     * yaparken "bu kullanıcı adı daha önce başkası tarafından
     * kullanılmış mı" onu kontrol ediyoruz.
     */
    @Query(value = "select COUNT(a)>0 from Auth a where a.username=?1")
    boolean isUsername(String username);

    /**
     * Kullanıcı adı ve şifresi doğru mu; verilen kaydın olup olmadığı
     * kontrol ediliyor.
     * @param username
     * @param password
     * @return
     */
    Optional<Auth> findOptionalByUsernameAndPassword(String username, String password);

}
