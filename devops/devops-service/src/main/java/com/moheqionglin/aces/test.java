package com.moheqionglin.aces;


import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;

public class test {

    private static final String ALGORITHM = "AES/CBC/PKCS5Padding";

    //public static final String TRANSFORMATION = "RSA/None/NoPadding";

    /**加密方式，标准jdk的*/
    //public static final String TRANSFORMATION = "RSA/None/PKCS1Padding";

    public static void test1() throws Exception {

        String bodyString = "{\"source\":\"aa\",\"bb\":\"ddaa\",\"type\":1}"; //body消息体
        String key = randomString();
        String iv = randomString();

        String rsaPublicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCKLccgx2RYbanSZxT95uxvrf7DuP32Pv0wLqv+VkXExldim05//7hiTFcOo1hSKD2UGog/nc/g9NA+yoY8xTw/5tT2gauKRG5/ZaTWbc4vkdTCs9zsrRYYdbw/pDoC4gBbf+8XCP8YI8FHRtJ68+f6xmGKI1c57CZtOvnAFFxRmQIDAQAB" ;
        String ciphertext = Base64.encodeBase64String(encryptByPublicKey((key + iv).getBytes(),
                Base64.decodeBase64(rsaPublicKey)));

        String encryptBody = encrypt(bodyString, key.getBytes(), iv.getBytes());
        System.out.println(ciphertext);
        System.out.println("===");
        System.out.println(encryptBody);
    }


    private static String randomString() {
        String result = "";
        char[] chars = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
        int length = 16;
        for (int i = length; i > 0; --i) {
            result += chars[(int) Math.floor(Math.random() * chars.length)];
        }
        return result;
    }


    /**
     * 数据加密
     *
     * @param srcData
     * @param key
     * @param iv
     * @return
     */
    public static String encrypt(String srcData, byte[] key, byte[] iv) {
        SecretKeySpec keySpec = new SecretKeySpec(key, "AES");
        Cipher cipher;
        String encodeBase64String = null;
        try {
            cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, keySpec, new IvParameterSpec(iv));
            byte[] encData = cipher.doFinal(srcData.getBytes());
            encodeBase64String = Base64.encodeBase64String(encData);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return encodeBase64String;
    }


    /**
     * 数据解密
     *
     * @param encDataStr
     * @param key
     * @param iv
     * @return
     */
    public static String decrypt(String encDataStr, byte[] key, byte[] iv) {
        byte[] encData = Base64.decodeBase64(encDataStr);
        SecretKeySpec keySpec = new SecretKeySpec(key, "AES");
        Cipher cipher;
        byte[] decbbdt = null;
        try {
            cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, keySpec, new IvParameterSpec(iv));
            decbbdt = cipher.doFinal(encData);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new String(decbbdt);
    }

    /**
     * 使用公钥加密
     */
    public static byte[] encryptByPublicKey(byte[] data, byte[] publicKey) throws Exception {
        // 得到公钥对象
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(publicKey);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PublicKey pubKey = keyFactory.generatePublic(keySpec);
        // 加密数据
        Cipher cp = Cipher.getInstance("RSA");
        cp.init(Cipher.ENCRYPT_MODE, pubKey);
        return cp.doFinal(data);
    }

    public static void main(String[] args) {
        try {
            test1();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
