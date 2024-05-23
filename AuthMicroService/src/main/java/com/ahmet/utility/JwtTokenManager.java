package com.ahmet.utility;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Optional;

@Component
public class JwtTokenManager {
    // APPLICATION.YML'den değer çekiyoruz: (bu değerleri [şifre vs] bu kodun içinde tutmak istemediğimizden 'application.yml'nin içinde tutuyoruz)
    @Value("${authservice.bunu-biz-yazdik.secret-key}")
    String secretKey;
    @Value("${authservice.bunu-biz-yazdik.issuer}")
    String issuer;

    public Optional<String> createToken(Long id) { // 'id:' user's id
        String token = null;
        Long expDate = 1000L*60; // (60 sn geçerli olsun)
        try {
            /**
             * DİKKAT!!!!
             * Claim objelerinin içine önemli ve herkes ile paylaşmayacağınız
             * bilgileri koymazsınız. Email, username, password vs. gibi
             * önemli bilgiler payload içinde olamaz.
             */
            token = JWT.create().withAudience()
                    .withClaim("id", id)
                    .withClaim("howtopage", "AuthMicroService")
                    .withClaim("lastjoin", System.currentTimeMillis())
                    .withClaim("ders", "Java JWT")
                    .withClaim("Hoca", "Muhammet Hoca")
                    .withIssuer(issuer) // jwt'nin sahibi
                    .withIssuedAt(new Date()) // token oluşturulma tarihi
                    .withExpiresAt(new Date(System.currentTimeMillis() + expDate))
                    .sign(Algorithm.HMAC512(secretKey));
            return Optional.of(token);
        }catch(Exception exception) {
            return Optional.empty();
        }
    }

    public Boolean verifyToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC512(secretKey);
            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer(issuer).build();
            DecodedJWT decodedJWT = verifier.verify(token);
            if(decodedJWT == null)
                return false;
        }catch(Exception exception) {
            return false;
        }
        return true;
    }

    public Optional<Long> getByIdFromToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC512(secretKey);
            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer(issuer).build();
            DecodedJWT decodedJWT = verifier.verify(token);
            if(decodedJWT == null)
                return Optional.empty();
            Long id = decodedJWT.getClaim("id").asLong();
//            String howToPage = decodedJWT.getClaim("howtopage").asString(); // bi görmek için yaptık.
//            System.out.println("howtopage....: " + howToPage); // bi görmek için yaptık.
            return Optional.of(id);
        }catch(Exception exception) {
            return Optional.empty();
        }
    }

}
