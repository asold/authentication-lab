package com.authentication.printer.server.helpers;

import java.util.Date;

import javax.crypto.SecretKey;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

public class SessionManagementImpl implements ISessionManagement {

    // will need to change this to env. variable most likely
    private static final SecretKey SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS256); // generates a secure key one the class is initialized


    private static final long TOKEN_VALIDITY = 30 * 60 * 1000;

    @Override
    public Token generateSessionToken(String username) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + TOKEN_VALIDITY);

        String token = Jwts.builder()
                .setSubject(username)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();

        return new Token(token);
    }

    // will need to validate validit and maybe the username?
    @Override
    public boolean validateSessionToken(Token token) {
        try {
            Jws<Claims> claims = Jwts.parser()
                    .setSigningKey(SECRET_KEY)
                    .parseClaimsJws(token.getToken());

            Date expirationDate = claims.getBody().getExpiration();
            return expirationDate.after(new Date());
        } catch (Exception e) {
            return false;
        }
    }
}
