package com.java.challenge.java_challenge.util;

import com.java.challenge.java_challenge.error.ErrorMessage;
import com.java.challenge.java_challenge.error.RepositoryException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.security.Key;
import java.time.LocalDate;
import java.util.*;
import java.util.function.Function;

@Component
public class JwtTokenUtil implements Serializable {

    Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    public String getUsernameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }



    public String getEmailFromToken(String token) throws RepositoryException {

        //try {
            return getClaimFromToken(token, Claims::getSubject);
            /*

        }catch(Exception exception){
            List<ErrorMessage> errorMessageList = new ArrayList<>();
            ErrorMessage notAuthorized = new ErrorMessage();
            notAuthorized.setTimeStamp(LocalDate.now());
            notAuthorized.setCodigo(401);
            notAuthorized.setDetail("You are not authorized");
            errorMessageList.add(notAuthorized);
            RepositoryException errorResponseTO = new RepositoryException();
            errorResponseTO.setErrorList(errorMessageList);
            throw errorResponseTO;
        }

             */
    }


    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
    }

    public String generateToken(String email) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("email", email);
        return Jwts
                .builder()
                .setClaims(claims)
                .setSubject(email)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .signWith(key)
                .compact();
    }

}