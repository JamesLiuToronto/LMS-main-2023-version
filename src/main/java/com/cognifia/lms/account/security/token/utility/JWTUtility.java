package com.cognifia.lms.account.security.token.utility;

import java.time.Instant;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cognifia.lms.account.security.token.TokenExpiredException;
import com.cognifia.lms.account.security.token.AuthorizeException;

import lombok.extern.slf4j.Slf4j;

import com.cognifia.lms.account.security.token.dto.UserDTO;

@Slf4j
public class JWTUtility extends AbstractUtility {
    private static final List<String> keys = Arrays.asList("firstName", "lastName", "emailAddress", "userID", "role",
                                                           "method");

   public JWTUtility(String secret, int duration) {
        super(secret, duration, keys);
    }

    public UserDTO validatetoken(String token) throws AuthorizeException {
        UserDTO user = null ;
        try {
            user = getUserDTOFromToken(token);
        } catch(Exception e) {
            throw new AuthorizeException("authorize.token.invalid");
        }

        if (user.isExpired()) {
            throw new TokenExpiredException("authorize.token.expired");
        }
        return user;
    }

    public UserDTO getUserDTOFromToken(String token) {
        Map<String, Object> claims = getMapFromToken(token);
        boolean expired = ((Date) claims.get(expirationKey)).toInstant().isBefore(Instant.now());
        return UserDTO.builder()
                         //.firstName((String) claims.get("firstName")).lastName((String) claims.get("lastName"))
                      .emailAddress((String) claims.get("emailAddress"))
                        //.userID((Integer) claims.get("userID"))
                        //.roleList((String) claims.get("role")).methodList((String) claims.get("method"))
                      .isExpired(expired).build();
    }

    public String createJwtSignedHMAC(UserDTO user) {
        Map<String, Object> items = new HashMap<>();
//        items.put("firstName", user.getFirstName());
//        items.put("lastName", user.getLastName());
        items.put("emailAddress", user.getEmailAddress());
//        items.put("userID", user.getUserID());
//        items.put("role", user.getRoleList());
//        items.put("method", user.getMethodList());

        return createJwtSignedHMAC("userDTO", items);
    }
}
