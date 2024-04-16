package in.app.heal.service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import org.springframework.stereotype.Service;
import io.github.cdimascio.dotenv.Dotenv;

@Service
public class GenerateTokenService {
Dotenv dotenv = Dotenv.load();
    public String generateToken(String email) {
    String secret = dotenv.get("SECRET_KEY");
        return Jwts.builder()
            .signWith(SignatureAlgorithm.HS256, secret)
            .claim("email", email)
            .setIssuedAt(Date.from(Instant.now()))
            .setExpiration(Date.from(
                Instant.now().plus(30l, ChronoUnit.DAYS)))
            .compact();
    }
}
