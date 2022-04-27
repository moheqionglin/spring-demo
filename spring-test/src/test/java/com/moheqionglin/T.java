package com.moheqionglin;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static java.nio.charset.StandardCharsets.UTF_8;

public class T {


    public static String encrypt(String input) throws Exception {
        String gcmAlgo = "AES/GCM/NoPadding";
        String gcmKey =  "mCJ8kSHXTBh3c8!d";
        String gcmIVParam = "rs2M3#xG";

        SecretKey key = new SecretKeySpec(gcmKey.getBytes(), "AES");
        byte[] plaintext = input.getBytes(UTF_8);
        byte[] IV = gcmIVParam.getBytes(UTF_8);

        Cipher cipher = Cipher.getInstance(gcmAlgo);
        SecretKeySpec keySpec = new SecretKeySpec(key.getEncoded(), "AES");
        GCMParameterSpec gcmParameterSpec = new GCMParameterSpec(128, IV);
        cipher.init(1, keySpec, gcmParameterSpec);
        byte[] cipherText = cipher.doFinal(plaintext);

        System.out.println(java.util.Base64.getEncoder().encodeToString(plaintext));
        System.out.println(java.util.Base64.getEncoder().encodeToString((input + "xxxx1").getBytes(UTF_8)));
        return java.util.Base64.getEncoder().encodeToString(cipherText);
    }

    public static void main(String[] args) throws Exception {
        System.out.println(URLEncoder.encode("dbUwqq14rRQLkTTIHwBhLTkfDf2FsNDJxv5oixnfbGKNCI628jpch7yBwnZs9w5P/mQ=", "UTF-8"));
        T.encrypt("周万里");
        System.out.println(new Date(122, 3,14,0,0,0));
        List<Integer> list = new ArrayList<Integer>();
        System.out.println(list.contains(null));
    }

}
