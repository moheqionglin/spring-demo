package com.moheqionglin.tomcat;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Date;

import sun.security.tools.keytool.CertAndKeyGen;
import sun.security.x509.X500Name;

public class KeyStoreGenerate {
    public static String filePath = getProjectDir() + "/tomcat-base-dir/mohe.keystore";

    private static final int keysize = 1024;
    private static final String commonName = "www.mohe.com";
    private static final String organizationalUnit = "IT";
    private static final String organization = "test";
    private static final String city = "shanghai";
    private static final String state = "shanghai";
    private static final String country = "shanghai";
    private static final long validity = 1096; // 3 years
    public static final String alias = "tomcat";
    private static final char[] keyPassword = "123456".toCharArray();

    public static boolean exists(){
        return new File(filePath).exists();
    }

    public static void generate() throws GeneralSecurityException {
        try {
            
            KeyStore ks = KeyStore.getInstance("pkcs12");
//            char[] password = "123456".toCharArray();
            ks.load(null, null);
            
            CertAndKeyGen keypair = new CertAndKeyGen("RSA", "SHA1WithRSA", null);
            X500Name x500Name = new X500Name(commonName, organizationalUnit, organization, city, state, country);
            keypair.generate(keysize);
            
            PrivateKey privateKey = keypair.getPrivateKey();
            X509Certificate[] chain = new X509Certificate[1];
            chain[0] = keypair.getSelfCertificate(x500Name, new Date(), (long)validity*24*60*60);
            
            // store away the key store
            FileOutputStream fos = new FileOutputStream(filePath);
            ks.setKeyEntry(alias, privateKey, keyPassword, chain);
            ks.store(fos, keyPassword);
            fos.close();
            System.out.println("create Success");
        } catch (KeyStoreException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (CertificateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String getProjectDir(){
        String path = TomcatBootstrap.class.getClassLoader().getResource("./").getPath();
        return path.substring(0, path.lastIndexOf("/target/classes/"));
    }
}