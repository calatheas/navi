package com.calathea.navi.util;

import javax.crypto.Cipher;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Base64;

public class AsymmetricEncrypter implements Encrypter {
    private String charsetName = "UTF-8";
    private String algorithm = "RSA";
    private String transformation = "RSA/ECB/PKCS1PADDING";
    private String provider = "SunJCE";
    private final PublicKey publicKey;
    private final PrivateKey privateKey;
    private final Cipher cipher;
    private final Base64.Encoder base64Encoder = Base64.getEncoder();
    private final Base64.Decoder base64Decoder = Base64.getDecoder();

    public AsymmetricEncrypter(String publicKeyHexString, String privateKeyHexString) {
        try {
            cipher = Cipher.getInstance(transformation, provider);
            KeyFactory keyFactory = KeyFactory.getInstance(algorithm);
            publicKey = keyFactory.generatePublic(new X509EncodedKeySpec(Encrypter.hexStringToByteArray(publicKeyHexString)));
            privateKey = keyFactory.generatePrivate(new PKCS8EncodedKeySpec(Encrypter.hexStringToByteArray(privateKeyHexString)));
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to create encrypter");
        }
    }

    @Override
    public String encrypt(String normalString) {
        try {
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            return base64Encoder.encodeToString(cipher.doFinal(normalString.getBytes(charsetName)));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String decrypt(String encrypted) {
        try {
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            return new String(cipher.doFinal(base64Decoder.decode(encrypted)), charsetName);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static KeyPair _generateKeyPair() throws NoSuchAlgorithmException {
        KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
        generator.initialize(2048);
        return generator.generateKeyPair();
    }

    public static void generateKeyPair() {
        try {
            KeyPair kp = _generateKeyPair();
            String now = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));

            FileOutputStream fos = new FileOutputStream(now+"-public.key");
            fos.write(Encrypter.byteArrayToHexString(kp.getPublic().getEncoded()).getBytes(StandardCharsets.UTF_8));

            FileOutputStream fos2 = new FileOutputStream(now+"-private.key");
            fos2.write(Encrypter.byteArrayToHexString(kp.getPrivate().getEncoded()).getBytes(StandardCharsets.UTF_8));

            fos.close();
            fos2.close();
        } catch (NoSuchAlgorithmException | IOException e) {
            e.printStackTrace();
        }
    }
}
