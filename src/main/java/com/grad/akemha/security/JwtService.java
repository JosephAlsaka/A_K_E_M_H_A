package com.grad.akemha.security;

import com.grad.akemha.entity.User;
import com.grad.akemha.repository.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.security.Key;
import java.security.PublicKey;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {
    private static final String SECRET_KEY = "97e74ee81f5206429721abf0cd87b2450299e2ba3be8feca9d85d3c2c18842e7";
//    @Autowired
//    private UserRepository userRepository;

    public String getIdentifier(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(getSignInKey()) // TODO: check the deprecated
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }

//        public String getId(String token){ //when we will use this method? when we receive the token from the client when client makes a request to the server and through this token we get the user name
//        String claims = Jwts.parser()
//                .setSigningKey(getSignInKey())
//                .build()
//                .parseClaimsJws(token)
//                .getBody().getId();
//        return claims;
//    }

    public String extractUserId(HttpHeaders httpHeaders) { //when we will use this method? when we receive the token from the client when client makes a request to the server and through this token we get the user name
        // Assuming your token is in the "Authorization" header
        String token = httpHeaders.getFirst("Authorization");
        String jwt = token.replace("Bearer ", "");
        var id = extractAllClaims(jwt).get("id");
        return String.valueOf(id);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public String generateToken(User user) {
        Map<String, Object> claims = Map.of(
                "id", user.getId(),
                "role", user.getRole()
        );
        return buildToken(claims, user);
    }

    public String buildToken(
            Map<String, Object> extraClaims,
            UserDetails userDetails
    ) {
        return Jwts
                .builder()
                .claims(extraClaims)
                .subject(userDetails.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 31556952000L)) // 1 year
                .signWith(getSignInKey(), Jwts.SIG.HS256)
                .compact();
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = getIdentifier(token);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private Claims extractAllClaims(String token) {
        return Jwts
                .parser()
                .verifyWith(getSignInKey()) //TODO: is this should be public key or private?
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    private SecretKey getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
