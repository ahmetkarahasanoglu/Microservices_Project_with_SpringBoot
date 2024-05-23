package com.ahmet.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true) // @PreAuthorize'ı kullanabilmek için yaptık (ElasticMikro / UserProfileController'da)
public class ElasticSecurityConfig {



    @Bean
    public JwtTokenFilter getTokenFilter() {
        return new JwtTokenFilter();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        /**
         * _csrf: login sayfasında kullanılabilen bir güvenliktir. Server
         * tarafından gönderilen csrf kodu (uuid), login html sayfasında
         * bulunur. Şifre girildiğinde bu csrf koduyla beraber server'a
         * gider. Bu, saniyede binlerce kez deneme yapan şifre kırma
         * saldırılarına karşı önlemdir. Spring'in login sayfası bunu
         * kullanıyor.
         * '--> Bunu kapatmak için disable komutu kullanıyoruz. RestApi'de
         * kullanmıyoruz csrf'i.
         */
        httpSecurity.csrf().disable();
        httpSecurity.authorizeRequests()
                .antMatchers("/myloginpage.html", "/v1/**").permitAll() // permit (only) these urls without authentication. ||| "/v1/**": permits all urls that are under "/v1".
                .anyRequest().authenticated(); // Require authentication for all incoming HTTP requests (except for the above specified ones)! - When localhost:9100 is entered on browser, it will show: 403 Forbidden.
//        httpSecurity.formLogin(); // redirects to Spring's login form (if the user is not authenticated to see the page).
//        httpSecurity.formLogin().loginPage("/myloginpage.html"); // redirects to custom-created login form (if the user is not authenticated to see the page).

        /**
         * AuthServis üzerinden kendisini doğrulayan bir kişinin isteklerinin
         * nasıl işleyeceğini çözmemiz gerekiyor. Kişinin elinde olan token
         * bilgisi okunarak bu kişiye yetkileri dahilinde geçerli olan token
         * üzerinden oturum izni verilecek, böylece kişi her seferinde kendini
         * doğrulamak zorunda kalmayacak. Bunun için, öncelikle filter
         * işleminin öncesine bir işlem adımı koyarak  kişiyi doğruluyoruz.
         */
        httpSecurity.addFilterBefore(getTokenFilter(), UsernamePasswordAuthenticationFilter.class);
        return httpSecurity.build();
    }

}
