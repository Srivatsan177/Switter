package com.srivatsan177.switter.users.Users.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {

    private static final String SECRET_KEY = "Ue9Upzn1hq1yAnwbh2KYujm9KfgdJgtSq8TkfLULVnMfFjpBTmCYFQKUATTKu5ajzNpSJiS3uFHcFNHrv7FZ2U03H4A1VYp9AkdrRSLU7kz0Qjy35UbjpM3RxUY7JbzABCFCxmQDKvzEazaGmBuJQMR4kaKpmg4Vz8VN106jxdhCnr5k3dURiePVUVXnrU1KSUwHKJUuBnUWPGwy2mNrPfaZdiMZMj3NZ28ijA88PZMheNvU8ypPfmJY7Di1JB6r";

    public String extractUsername(String jwt) {
        return extractClaim(jwt, Claims::getSubject);
    }

    public <T> T extractClaim(String jwt, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(jwt);
        return claimsResolver.apply(claims);
    }

    public String generateJwt(
            UserDetails userDetails
    ) {
        return generateJwt(new HashMap<>(), userDetails);
    }

    public String generateJwt(
        Map<String, Object> extraClaims,
        UserDetails userDetails
    ) {
        return Jwts
                .builder()
                .addClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 24))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public boolean isJwtValue(String jwt, UserDetails userDetails) {
        return (extractUsername(jwt).equals(userDetails.getUsername())) && !isJwtExpired(jwt);
    }

    public boolean isJwtExpired(String jwt) {
        return extractClaim(jwt, Claims::getExpiration).before(new Date());
    }

    public Claims extractAllClaims(String jwt) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(jwt)
                .getBody();
    }

    private Key getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
