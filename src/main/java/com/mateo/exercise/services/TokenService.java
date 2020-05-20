package com.mateo.exercise.services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.mateo.exercise.data.models.UserModel;
import com.mateo.exercise.data.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class TokenService {


    @Value("${app.auth.key}")
    private String SECRET_KEY;

    @Autowired
    private UserRepository userRepository;


    private final long VALID_FOR = 86400000;

    public final String TOKEN_TYPE = "JWT";
    public final String TOKEN_ISSUER = "secure-api";
    public final String TOKEN_AUDIENCE = "secure-app";


    public String issueToken(String username) {
        UserModel user = userRepository.findUserByUsername(username);

        List<String> scopes = new ArrayList<>();
        scopes.add("SCOPE_READ");
        if(user.getRole() == 2) {
            scopes.add("SCOPE_MODIFY");
        }

        String token = JWT.create()
                .withSubject(username)
                .withIssuer(TOKEN_ISSUER)
                .withAudience(TOKEN_AUDIENCE)
                .withClaim("scopes", scopes)
                .withExpiresAt(new Date(System.currentTimeMillis() + VALID_FOR))
                .sign(Algorithm.HMAC512(SECRET_KEY));

        return token;
    }

    public UsernamePasswordAuthenticationToken validateToken(String token) {
        DecodedJWT decryptedToken = null;
        try {
             decryptedToken = JWT.require(Algorithm.HMAC512(SECRET_KEY))
                    .build()
                    .verify(token);
        } catch (Exception e) {
            return null;
        }

        String subject = decryptedToken.getSubject();

        List<SimpleGrantedAuthority> authorities = new ArrayList<SimpleGrantedAuthority>();

        Claim scopes = decryptedToken.getClaims().get("scopes");

        String[] grants = scopes.asArray(String.class);

        for(String grant: grants) {
            authorities.add(new SimpleGrantedAuthority(grant));
        }


        return new UsernamePasswordAuthenticationToken(subject, null, authorities);
    }
}




