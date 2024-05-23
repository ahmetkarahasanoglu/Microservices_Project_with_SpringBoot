package com.ahmet.config;

import com.ahmet.exception.EErrorType;
import com.ahmet.exception.ElasticServiceException;
import com.ahmet.utility.JwtTokenManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

/**
 * ElasticSecurityConfig'de gerek duyulan filter için bu sınıf
 * oluşturuldu. Orada kullanılacak. (for 'addFilterBefore' method there).
 */

public class JwtTokenFilter extends OncePerRequestFilter {

    @Autowired
    JwtTokenManager jwtTokenManager;
    @Autowired
    JwtUserDetails jwtUserDetails;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        final String bearerToken = request.getHeader("Authorization"); // Headers'ın içinde "Authorization" key'i ile kaydedilen value'yu alıyoruz.
//        System.out.println("Bearer token:.....: " + bearerToken); // "Authorization" bilgisi göndererek token bilgisi alabiliyoruz. Postman'da localhost:9100/elastic/user/getall yazıp, Authorization sekmesinde AuthType'ı "Bearer Token" seçerek Token'a "Buraya_Token_Yazılacak" yazdığımızda, ve yazdığımız url'i Postman'da 'Send' butonuyla gönderdiğimizde, Postman'da 'Headers' sekmesinde bunu görebiliyoruz. Ayrıca Intelij Console'da "Bearer token:.....: Bearer Buraya_Token_Yazilacak" şeklinde görüntüleyebiliyoruz.
        /**
         * Bu kısım filtreye takılan tüm isteklerin inceleneceği yerdir.
         * Bu nedenle buraya gelen tüm isteklerde, Header kısmında Bearer
         * Token'ı arıyoruz. Eğer Bearer Token yok ise (null ise) hata
         * fırlatırız. Veya eğer gelen değerin içinde token yok ise hata
         * fırlatırız.
         */
        if(SecurityContextHolder.getContext().getAuthentication()==null) { // authenticate olmamış bir istek ise
            if(bearerToken==null || !bearerToken.startsWith("Bearer ")) { // bearerToken yoksa (null ise) , veya "Bearer" ile başlamıyorsa (gelen şey bearer token'dan farklı bir şey ise)
                throw new ElasticServiceException(EErrorType.INVALID_TOKEN);
            }
            String token = bearerToken.substring(7); // "Bearer " kelimesinden sonraki token değerini alıyoruz.
            Optional<Long> authId = jwtTokenManager.getIdFromToken(token);
            if(authId.isEmpty()) {
                throw new ElasticServiceException(EErrorType.INVALID_TOKEN);
            }
            /**
             * Eğer tüm koşullar doğru ise, buradan itibaren bilgileri kotnrol
             * edilen kişiye ait Spring Security tarafından kullanılacak olan
             * bir kimlik kartı hazırlamamız gerekmektedir.
             */
            UserDetails userDetails = jwtUserDetails.getUserByAuthId(authId.get());
            UsernamePasswordAuthenticationToken authenticationToken
                    = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }
        filterChain.doFilter(request, response); // izin ver geçsin --> filtre edilsin.
    }
}
