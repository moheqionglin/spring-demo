package com.moheqionglin.aces;

import org.apache.commons.codec.binary.Base64;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.math.BigDecimal;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.X509EncodedKeySpec;

public class TTest {
    private static final String ALGORITHM = "AES/CBC/PKCS5Padding";
    public static final String KEY_ALGORITHM = "AES";
    public static final String CBC_CIPHER_ALGORITHM = "AES/CBC/PKCS5Padding";


    public static final String securityStr = "CFF7T+YjvkclDpzi1tj0TiWzbux1bkMKhDMaTLFBvbMGpF5FicHl5ojHSsW8FtYJDoN0k/JElzFAR96u40V55KnJqfbkpmJFAk6F5QRkHiCXOjHI+XwolQQcp2ct7PfwGhAOHa2Z2LTc25eT2yF2q1aPjqmDmhwxUxa25z31H7U3I3qNru5A779DNS33rbhxrLQEkZVjmEG3MUs3XfAVNxSlptT8MHadUVGbIgCdowsySQTJP5cPGEBqQOFznv+OMT1TwzuurYx5mpYeWjQP8q9ZLsZmJMocvFTUZIxe5GZGH4QCYJOJbv53gAJMVqH9ZG4wjAGrdVuxU5tM3ygiIftPJP0EMszun5k/KQA1NAKKVrVZHEiTl8FwIXauqBLShQ4hKG/4kxnzEw5YhVBGKG6aAyW5YnR5DeSE3rxmBBGMSi/DosE928R7NLZ2MuzE3pE8tMClxxVWhQh0UR0wZKQlBcCx0hN+XFuUA1SvHFhYmFHWuoFec1frU79gD3/R0BWN6DwHieEdjrP8yfhLYvGt/+xEAMPbemZDIdnamw/eDOJn39thAGQWL23EDeBX";

    public static void test1() throws Exception {

        String bodyString = "[{\"source\":\"aa\",\"bb\":\"ddaa\",\"type\":1}]"; //body消息体
        String key = randomString();
        String iv = randomString();

        String rsaPublicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCKLccgx2RYbanSZxT95uxvrf7DuP32Pv0wLqv+VkXExldim05//7hiTFcOo1hSKD2UGog/nc/g9NA+yoY8xTw/5tT2gauKRG5/ZaTWbc4vkdTCs9zsrRYYdbw/pDoC4gBbf+8XCP8YI8FHRtJ68+f6xmGKI1c57CZtOvnAFFxRmQIDAQAB";
        String ciphertext = Base64.encodeBase64String(encryptByPublicKey((key + iv).getBytes(),
                Base64.decodeBase64(rsaPublicKey)));

        // body to be sent
        String encoded = Base64.encodeBase64String(AesCbcEncode(bodyString.getBytes(), key.getBytes(), iv.getBytes()));

        System.out.println("ciphertext = " + ciphertext);
        System.out.println("body = " + encoded);

        System.out.println("orgin = " + bodyString);
        System.out.println("decode by aes = " + new String(AesCbcDecode(Base64.decodeBase64(encoded), key.getBytes(), iv.getBytes())));

        //ueu6vA8xfo7tzKQZ, iv: 2kOxAc2qi7wYLP2q
        System.out.println("key： " + key + ", iv: " + iv);


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
     * 使用公钥加密
     */
    public static byte[] encryptByPublicKey(byte[] data, byte[] publicKey) throws Exception {
        // 得到公钥对象
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(publicKey);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        RSAPublicKey pubKey = (RSAPublicKey) keyFactory.generatePublic(keySpec);
        // 加密数据
        Cipher cp = Cipher.getInstance("RSA");
        cp.init(Cipher.ENCRYPT_MODE, pubKey);
        return cp.doFinal(data);
    }

    public static byte[] AesCbcDecode(byte[] decodedText, byte[] keyByte, byte[] ivByte) {
        SecretKey key = restoreSecretKey(keyByte);

        IvParameterSpec ivParameterSpec = new IvParameterSpec(ivByte);
        try {
            Cipher cipher = Cipher.getInstance(CBC_CIPHER_ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, key, ivParameterSpec);
            return cipher.doFinal(decodedText);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException
                | InvalidKeyException | InvalidAlgorithmParameterException
                | IllegalBlockSizeException | BadPaddingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    public static byte[] AesCbcEncode(byte[] plainText, byte[] keyByte, byte[] ivByte) throws Exception {
        // TODO:仅做测试用 线上须去掉这个log
        SecretKey key = restoreSecretKey(keyByte);
        try {
            IvParameterSpec ivParameterSpec = new IvParameterSpec(ivByte);

            Cipher cipher = Cipher.getInstance(CBC_CIPHER_ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, key, ivParameterSpec);
            return cipher.doFinal(plainText);

        } catch (NoSuchAlgorithmException | NoSuchPaddingException
                | InvalidKeyException | InvalidAlgorithmParameterException
                | IllegalBlockSizeException | BadPaddingException e) {
            throw e;
        }
    }

    public static SecretKey restoreSecretKey(byte[] secretBytes) {
        SecretKey secretKey = new SecretKeySpec(secretBytes, KEY_ALGORITHM);
        return secretKey;
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
    public static void main(String[] args) {
        try {
//            test1();
//            , iv: 2kOxAc2qi7wYLP2q

            String decrypt = decrypt(securityStr, "ueu6vA8xfo7tzKQZ".getBytes(), "2kOxAc2qi7wYLP2q".getBytes());
            System.out.println(decrypt);

            BigDecimal bigDecimal = BigDecimal.valueOf(2.10);
            System.out.println(bigDecimal.scale());
            System.out.println();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

