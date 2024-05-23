package com.ahmet.utility;

import org.springframework.stereotype.Component;

/**
 * Dandik bi kod. Deneme amaçlı yaptık.
 */
@Component
public class TokenManager {

    public String createToken(Long id) {
        String token = "esatistoken:" + id;
        return token;
    }

    public Long getIdByToken(String token) {
        int index = token.indexOf(":");
        Long id = Long.parseLong(token.substring(index+1));
        return id;
    }

}
