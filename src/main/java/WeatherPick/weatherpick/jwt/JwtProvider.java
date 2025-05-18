package WeatherPick.weatherpick.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;


@Component
public class JwtProvider {
    private String secretKey = "Skrr5Skrr";

    public String create(String email){
        Date expiredDate = Date.from(Instant.now().plus(1, ChronoUnit.HOURS));
        String jwt = Jwts.builder()
                .signWith(SignatureAlgorithm.ES256, secretKey)
                .setSubject(email).setIssuedAt(new Date()).setExpiration(expiredDate)
                .compact();
        return jwt;
    }

    public String validate(String jwt){
        Claims claims = null;
        try {
            claims = Jwts.parser().setSigningKey(secretKey)
                    .parseClaimsJwt(jwt).getBody();
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
        return claims.getSubject();
    }
}
