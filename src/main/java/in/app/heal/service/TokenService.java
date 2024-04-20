package in.app.heal.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;
import org.springframework.stereotype.Service;
import io.github.cdimascio.dotenv.Dotenv;

import java.net.http.HttpHeaders;
import java.security.Key;
import javax.crypto.spec.SecretKeySpec;

@Service
public class TokenService {
    Dotenv dotenv = Dotenv.load();
    String secret = dotenv.get("SECRET_KEY");
    public String generateToken(String email) {
        return Jwts.builder()
            .signWith(SignatureAlgorithm.HS256, secret)
            .claim("email", email)
            .setIssuedAt(Date.from(Instant.now()))
            .setExpiration(Date.from(
                Instant.now().plus(30l, ChronoUnit.DAYS)))
            .compact();
    }
    public String getEmailFromToken(String token) {
        Key hmackey = new SecretKeySpec(Base64.getDecoder().decode(secret),
            SignatureAlgorithm.HS256.getJcaName());
        Claims jwt = Jwts.parserBuilder()
          .setSigningKey(hmackey)
          .build()
          .parseClaimsJws(token)
          .getBody();
        return (String) jwt.get("email");
    }
    public String getToken(String auth) {
        String token = "";
        if (!auth.isEmpty()) {
            token = auth.split(" ")[1];
        }
        return token;
    }
}
