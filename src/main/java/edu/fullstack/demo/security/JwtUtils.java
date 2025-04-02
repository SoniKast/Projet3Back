package edu.fullstack.demo.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class JwtUtils {

    public String generateJwt(MyUserDetails userDetails) {

        return Jwts.builder()
                .signWith(SignatureAlgorithm.HS256,"azerty123")
                .setSubject(userDetails.getUsername())
                .addClaims(Map.of("role", userDetails.getUtilisateur().getRole()))
                .addClaims(Map.of("id", userDetails.getUtilisateur().getId()))
                .compact();
    }

    public String getSubject(String jwt) {

        return Jwts.parser()
                .setSigningKey("azerty123")
                .parseClaimsJws(jwt)
                .getBody()
                .getSubject();

    }

}
