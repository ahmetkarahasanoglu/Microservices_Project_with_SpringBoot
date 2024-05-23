package com.ahmet.config;

import com.ahmet.repository.entity.UserProfile;
import com.ahmet.service.UserProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class JwtUserDetails implements UserDetailsService {

    @Autowired
    UserProfileService userProfileService;

    @Override // Biz bu metodu kullanmıycaz, lazım değil (username ile çağırmıyoruz, id ile çağırıyoruz User nesnesini)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return null;
    }

    public UserDetails getUserByAuthId(Long authid) {
        Optional<UserProfile> userProfile = userProfileService.findByAuthId(authid);
        if(userProfile.isEmpty()) {
            return null;
        }
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("Admin")); // Bu ve aşağıdaki yetkiye sahip kişiler giriş yapabilir. Not: ElastikMikro / UserProfileController'da @PreAuthorize ile hangi yetkiye sahip kişilerin o url'i kullanabileceğini belirtiyoruz.
        authorities.add(new SimpleGrantedAuthority("OzelMusteri"));
        authorities.add(new SimpleGrantedAuthority("VIP"));
        return User.builder() // (User'ın import edildiği yer: springframework.security.core.userdetails)
                .username(userProfile.get().getUsername())
                .password("")
                .accountLocked(false)
                .accountExpired(false)
                .authorities(authorities)
                .build();
    }

}
