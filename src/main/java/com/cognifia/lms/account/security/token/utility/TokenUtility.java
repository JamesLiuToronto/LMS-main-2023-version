package com.cognifia.lms.account.security.token.utility;

import java.time.Instant;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.extern.slf4j.Slf4j;

import com.cognifia.lms.account.security.token.dto.TokenDTO;

@Slf4j
public class TokenUtility extends AbstractUtility {
    private static final List<String> keys = Arrays.asList("type", "keyName", "keyValue");

    public TokenUtility(String secret, int duration) {
        super(secret, duration, keys);
    }

    public TokenDTO getTokenDTOFromToken(String token) {
        Map<String, Object> claims = getMapFromToken(token);
        boolean expired = ((Date) claims.get(expirationKey)).toInstant().isBefore(Instant.now());
        return TokenDTO.builder().type((String) claims.get("type")).keyName((String) claims.get("keyName"))
                       .keyValue((String) claims.get("keyValue")).isExpired(expired).build();
    }

    public String createJwtSignedHMAC(TokenDTO token) {
        Map<String, Object> items = new HashMap<>();
        items.put("type", token.getType());
        items.put("keyName", token.getKeyName());
        items.put("keyValue", token.getKeyValue());

        return createJwtSignedHMAC("tokenDTO", items);
    }
}
