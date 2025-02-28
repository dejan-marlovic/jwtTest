package org.example;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.Calendar;
import java.util.Date;

public class Main {
    public static void main(String[] args) {

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.SECOND, 60);
        Date futureDate = calendar.getTime();


        String BASE64_SECRET = getBase64Secret();
        System.out.println("Base64 Secret (paste this into jwt.io): " + BASE64_SECRET);


        System.out.println("Hello, JWT!");

        //server creates  a token
        //token can not be recreated from client but it can get info in different parts: header, payload, sign
        String token = Jwts.builder()
                .subject("Dejan")
                .issuedAt(new Date())
                .expiration(futureDate)
                .signWith(SignatureAlgorithm.HS256, BASE64_SECRET)
                .compact();

        System.out.println("Token: " + token);

        Claims claims = null;
        try {
            //builder pattern we dont have to care that the object type has changed, after build

            claims = Jwts.parser()
                    .setSigningKey(BASE64_SECRET)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();

        } catch (Exception e) {
            System.out.println("Invalid token!");
        }


        if (claims != null) {
            System.out.println("Decoded Subject: " + claims.getSubject());
            System.out.println("Token Expiration: " + claims.getExpiration());

        }


    }

    public static String getBase64Secret() {
        SecureRandom random = new SecureRandom();
        byte[] key = new byte[32]; // 32 bytes = 256 bits
        random.nextBytes(key);
        return Base64.getEncoder().encodeToString(key); // Return Base64-encoded key
    }
}