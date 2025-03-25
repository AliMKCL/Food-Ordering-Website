package com.example.demo.service.hashing;

import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Service
public class SHA256HashService {

    public String hashText(String text) throws NoSuchAlgorithmException {

        MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
        byte[] encoded = messageDigest.digest(text.getBytes(StandardCharsets.UTF_8));

        StringBuilder builder = new StringBuilder(2 * encoded.length);
        for (int i = 0; i < encoded.length; i++) {
            String hex = Integer.toHexString(0xff & encoded[i]);
            if(hex.length() == 1) {
                builder.append('0');
            }
            builder.append(hex);
        }
        return builder.toString();
    }



}
