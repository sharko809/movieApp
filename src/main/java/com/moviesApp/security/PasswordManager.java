package com.moviesApp.security;

import com.moviesApp.PropertiesManager;
import org.apache.commons.codec.binary.Base64;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

/**
 * Created by dsharko on 8/2/2016.
 */
public class PasswordManager {

    private static final Logger LOGGER = LogManager.getLogger();
    private static final Integer PASSWORD_LENGTH;

    static {
        try {
            PASSWORD_LENGTH = Integer.parseInt(PropertiesManager.getProperty("password.minLength"));
        } catch (NumberFormatException e) {
            LOGGER.fatal("Can't parse property value. " + e);
            throw new RuntimeException("Can't parse property value. " + e);
        }
    }

    public static String getSaltedHashPassword(String password) throws IllegalArgumentException {
        // password validity is checked by validators in controllers
        byte[] salt = generateSalt(password);
        return Base64.encodeBase64String(salt) + "$" + hash(password, salt);
    }

    private static String hash(String password, byte[] salt) throws IllegalArgumentException {
        SecretKeyFactory secretKeyFactory;
        try {
            secretKeyFactory = SecretKeyFactory.getInstance(PropertiesManager.getProperty("password.secretKeyFactory"));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            LOGGER.fatal("No specified algorithm found. Error: " + e);
            throw new RuntimeException("No specified algorithm found. Error: " + e);
        }

        PBEKeySpec pbeKeySpec;
        try {
            pbeKeySpec = new PBEKeySpec(password.toCharArray(), salt,
                    Integer.parseInt(PropertiesManager.getProperty("password.hashIterations")),
                    Integer.parseInt(PropertiesManager.getProperty("password.keyLength")));
        } catch (NumberFormatException e) {
            LOGGER.fatal("Can't parse property value. " + e);
            throw new RuntimeException("Can't parse property value. " + e);
        }

        SecretKey secretKey;
        try {
            secretKey = secretKeyFactory.generateSecret(pbeKeySpec);
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
            LOGGER.fatal("Can't find matching key spec. Error: " + e);
            throw new RuntimeException("Can't find matching key spec. Error: " + e);
        }

        return Base64.encodeBase64String(secretKey.getEncoded());
    }

    private static byte[] generateSalt(String password) {
        if (password == null || password.trim().equals("") || password.length() < PASSWORD_LENGTH) {
            LOGGER.error("Wrong password: " + password);
            throw new IllegalArgumentException("Password should not be empty and must have at least " + PASSWORD_LENGTH + " characters.");
        }
        byte[] bytes = String.valueOf(password.length() * 42 + 3).getBytes();
        byte[] bytes_ = password.getBytes();
        byte[] combined = new byte[bytes.length + bytes_.length];
        System.arraycopy(bytes, 0, combined, 0, bytes.length);
        System.arraycopy(bytes_, 0, combined, bytes.length, bytes_.length);
        return combined;
    }


}
