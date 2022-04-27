package com.moheqionglin.aces;


import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class AES对称Test {

        private static final String secretKey = "mXhCJTc8!d8kSBH3";
        private static final String ivParam = "rMx3Gs2#";
        public static final int GCM_IV_LENGTH = 12;
        public static final int GCM_TAG_LENGTH = 16;

        public static String gcmEncrypt(String plainText) throws Exception {
            SecretKeySpec secretKeySpec = new SecretKeySpec(secretKey.getBytes(), "AES");
            byte[] enc = encrypt(getUTF8Bytes(plainText), secretKeySpec, ivParam.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(enc);
        }

        public static String gcmDecrypt(String cipherText) throws Exception {
            return decrypt(Base64.getDecoder().decode(cipherText));
        }

        public static byte[] encrypt(byte[] plaintext, SecretKey key, byte[] IV) throws Exception {
            Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
            SecretKeySpec keySpec = new SecretKeySpec(key.getEncoded(), "AES");
            GCMParameterSpec gcmParameterSpec = new GCMParameterSpec(128, IV);
            cipher.init(1, keySpec, gcmParameterSpec);
            byte[] cipherText = cipher.doFinal(plaintext);
            return cipherText;
        }

        public static String decrypt(byte[] cipherText) throws Exception {
            Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
            SecretKeySpec keySpec = new SecretKeySpec(getUTF8Bytes(secretKey), "AES");
            GCMParameterSpec gcmParameterSpec = new GCMParameterSpec(128, getUTF8Bytes(ivParam));
            cipher.init(2, keySpec, gcmParameterSpec);
            byte[] decryptedText = cipher.doFinal(cipherText);
            return new String(decryptedText);
        }

        private static byte[] getUTF8Bytes(String input) {
            return input.getBytes(StandardCharsets.UTF_8);
        }

    public static void main(String[] args) throws Exception {
        System.out.println(AES对称Test.gcmEncrypt("{\"username\":\"万里\",\"age\":10}"));
        System.out.println(AES对称Test.gcmDecrypt(AES对称Test.gcmEncrypt("{\"username\":\"万里\",\"age\":10}")));

    }

}


