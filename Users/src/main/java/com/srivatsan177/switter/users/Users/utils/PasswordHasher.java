package com.srivatsan177.switter.users.Users.utils;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class PasswordHasher {
    public static String hash(String password) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-512");
        StringBuilder hexDigest = new StringBuilder();
        for(byte b: md.digest(password.getBytes(StandardCharsets.UTF_8)) ) hexDigest.append(String.format("%02x", b));
        return new String(hexDigest);
    }
}
