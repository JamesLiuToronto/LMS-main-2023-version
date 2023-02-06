package com.cognifia.lms.account.security.token.utility;

import javax.crypto.spec.SecretKeySpec;
import java.io.Serializable;
import java.security.Key;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public abstract class AbstractUtility implements Serializable {
    private final String secret;
    private final int duration;
    private final List<String> keys;
    protected final String expirationKey = "expiration";

    protected AbstractUtility(String secret, int duration, List<String> keys) {
        this.secret = secret;
        this.duration = duration;
        this.keys = keys;
    }

    protected String createJwtSignedHMAC(String subject, Map<String, Object> items) {
        Key hmacKey = new SecretKeySpec(Base64.getDecoder().decode(secret), SignatureAlgorithm.HS256.getJcaName());
        Instant now = Instant.now();
        JwtBuilder builder = Jwts.builder();
        items.forEach(builder::claim);
        Date expiration = Date.from(now.plus(duration, ChronoUnit.MINUTES));
        return builder.setSubject(subject).setId(UUID.randomUUID().toString()).setIssuedAt(Date.from(now))
                      .setExpiration(expiration).signWith(hmacKey, SignatureAlgorithm.HS256).compact();
    }

    protected Map<String, Object> getMapFromToken(String token) {
        Claims claims = parseJwt(token).getBody();
        Map<String, Object> result = new HashMap<>();
        result.put(expirationKey, claims.getExpiration());
        keys.forEach(k -> result.put(k, claims.get(k)));
        return result;
    }

    private Jws<Claims> parseJwt(String jwtString) {
        Key key = new SecretKeySpec(Base64.getDecoder().decode(secret), SignatureAlgorithm.HS256.getJcaName());
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(jwtString);
    }

    protected String generateNewSecret(String base) {
        String secret = Base64.getEncoder().encodeToString(base.getBytes());
        byte[] bts = Base64.getDecoder().decode(secret);
        Key hmacKey = new SecretKeySpec(bts, SignatureAlgorithm.HS256.getJcaName());

        Instant now = Instant.now();
        return Jwts.builder().setSubject("APP-SECRET").setId(UUID.randomUUID().toString()).setIssuedAt(Date.from(now))
                   .setExpiration(Date.from(now.plus(60l, ChronoUnit.MINUTES))).signWith(hmacKey).compact();
    }
}
