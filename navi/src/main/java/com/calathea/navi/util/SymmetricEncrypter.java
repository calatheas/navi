package com.calathea.navi.util;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.GeneralSecurityException;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Base64;

public class SymmetricEncrypter implements Encrypter {
    private String charsetName = "UTF-8";
    private String algorithm = "AES";
    private String transformation = "AES/ECB/PKCS5Padding";
    private final SecretKeySpec secretKey;
    private final Cipher cipher;
    private final Base64.Encoder base64Encoder = Base64.getEncoder();
    private final Base64.Decoder base64Decoder = Base64.getDecoder();

    public SymmetricEncrypter(String mySecretKey) {
        secretKey = new SecretKeySpec(Encrypter.hexStringToByteArray(mySecretKey), algorithm);

        try {
            cipher = Cipher.getInstance(transformation);
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to create encrypter");
        }
    }

    @Override
    public String encrypt(String normalString) {
        try {
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            return base64Encoder.encodeToString(cipher.doFinal(normalString.getBytes(charsetName)));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String decrypt(String encrypted) {
        try {
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            return new String(cipher.doFinal(base64Decoder.decode(encrypted)), charsetName);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    private static byte[] generateKey(int n) throws NoSuchAlgorithmException {
        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        keyGenerator.init(n);
        SecretKey key = keyGenerator.generateKey();
        return key.getEncoded();
    }

    public static void generateAndPrintKey() {
        try {
            // 1. Generate key byte array
            byte[] secretKey = generateKey(128);
            String filepath = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")) + ".key";

            // 2. Save key to file (byte -> hex string(utf-8))
            FileOutputStream fos = new FileOutputStream(filepath);
            fos.write(Encrypter.byteArrayToHexString(secretKey).getBytes(StandardCharsets.UTF_8));
            fos.close();

            // 3. Load and print file (hex string(utf-8) -> byte)
            String hexString = Files.readAllLines(Paths.get(filepath), StandardCharsets.UTF_8).get(0);

            System.out.println("Generated "+filepath);
            System.out.println("hexString : "+hexString);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }
}
