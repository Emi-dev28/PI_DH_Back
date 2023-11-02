package com.PI_back.pi_back.security;

import com.PI_back.pi_back.security.auth_Interfaces.JwtService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
@AllArgsConstructor
@NoArgsConstructor
public class JwtServiceImplement implements JwtService {
    // Valores que los tengo que pasar desde el application.yml
    @Value("${application.security.jwt.secret-key}")
    private String secretKey;
    @Value("${application.security.jwt.expiration}")
    private long expiration;
    @Value("${application.security.jwt.refresh.expiration}")
    private long refreshExpiration;



    // sirve para extraer el username del token.
    @Override
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }


    @Override
    public String generateToken(UserDetails userDetails, Map<String, Object> extraClaims) {
        return generateToken(extraClaims, userDetails);

    }

    @Override
    public String generateRefreshToken(UserDetails userDetails) {
        return buildToken(new HashMap<>(), userDetails, refreshExpiration);
    }

    @Override
    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    private boolean isTokenExpired(String token) {
       return  extractExpiration(token)
               .before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private String buildToken(
            Map<String, Object> extraClaims,
            UserDetails userDetails,
            long refreshExpiration
    ){
        return Jwts.builder()
                .setClaims(extraClaims) // le setteo el cuerpo del token
                .setSubject(userDetails.getUsername()) // le setteo las propiedades
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + (expiration * 60 * 1000)))
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    private SecretKey getSigningKey() {
        SecretKey secret = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey));
        return Keys.hmacShaKeyFor(secret.getEncoded());
    }
    private <T> T extractClaim(
            String token,
            Function<Claims, T> claimsResolver
            ){
        final Claims claims = extractAllClaim(token);
        return claimsResolver.apply(claims);
    }
    //
    private Claims extractAllClaim(String token) {
    return Jwts.parserBuilder()
            .setSigningKey(getSigningKey())
            .build()
            .parseClaimsJws(token)
            .getBody();
    }

    // metodo para generar token;
    private String generateToken(
            Map<String, Object> extraClaims,
            UserDetails userDetails
    ){
      return Jwts
              .builder()
              .setClaims(extraClaims)
              .setSubject(userDetails.getUsername())
              .setIssuedAt(new Date(System.currentTimeMillis()))
              .setExpiration(new Date(System.currentTimeMillis()+ 1000  * 60 ))
              .signWith(getSigningKey())
              .compact();
    }
}
