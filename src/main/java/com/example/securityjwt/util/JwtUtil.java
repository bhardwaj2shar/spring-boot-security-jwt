package com.example.securityjwt.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtUtil {
    private static final String SECRET_KEY = "youraverybstrongcsecretdkeyfthatgishatileastj32kbytesllong"; // Replace with your secret key!

    public String extractUsername(String token) {
        return extractClaims(token, Claims:: getSubject);
    }
    public Date extractExpiration(String token) {
        return extractClaims(token, Claims:: getExpiration);
    }
    private <T> T extractClaims(String token, Function<Claims, T> clazz) {
         Claims claims = Jwts.parserBuilder().setSigningKey(SECRET_KEY).build().parseClaimsJws(token).getBody();
        return clazz.apply(claims);
    }

    public String generateToken(UserDetails userDetails) {
        Map<String,Object> claims = new HashMap<>();
        return Jwts.builder().setClaims(claims).setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(
                new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24 * 7)
        ).signWith(SignatureAlgorithm.HS256, SECRET_KEY).compact();
    }
    public boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    private boolean isTokenExpired(String token) {
        final Date expiration = extractExpiration(token);
        return expiration.before(new Date());
    }

}
