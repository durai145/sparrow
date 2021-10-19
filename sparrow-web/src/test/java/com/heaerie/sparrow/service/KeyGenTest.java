package com.heaerie.sparrow.service;

import org.junit.Test;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.management.openmbean.KeyAlreadyExistsException;
import java.io.*;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableEntryException;
import java.security.cert.CertificateException;
import java.util.Enumeration;

public class KeyGenTest {
    public final static String KEY_STORE_FILE="/opt/heaerie/pillar/conf/pillar.jks";

    public void CreateKey(String key,String keyStorePass) throws KeyStoreException, CertificateException, NoSuchAlgorithmException, IOException, UnrecoverableEntryException {

        // check key store already exists
        KeyStore ks = KeyStore.getInstance("JCEKS");
        FileInputStream fis = new FileInputStream(new File(KEY_STORE_FILE));
        ks.load(fis, keyStorePass.toCharArray());
        KeyStore.ProtectionParameter protParam = new KeyStore.PasswordProtection(keyStorePass.toCharArray());
        KeyStore.Entry skEntry=  ks.getEntry(key, protParam);
        if (skEntry != null) {
            throw new KeyAlreadyExistsException( key + " is already exists");
        }
        KeyGenerator keyGen = KeyGenerator.getInstance("AES");
        keyGen.init(256);
        SecretKey aesKey = keyGen.generateKey();
        skEntry = new KeyStore.SecretKeyEntry(aesKey);
        ks.setEntry(key,skEntry,protParam);
        FileOutputStream fos = new FileOutputStream(new File(KEY_STORE_FILE));
        ks.store(fos, keyStorePass.toCharArray());
        fos.close();

    }

    public  void  ListKey(String storeKeyPass) throws IOException, KeyStoreException, CertificateException, NoSuchAlgorithmException {
      FileInputStream fis= new FileInputStream(new File(KEY_STORE_FILE));
      KeyStore ks =  KeyStore.getInstance("JCEKS");
      ks.load(fis, storeKeyPass.toCharArray());
       Enumeration<String> a =ks.aliases() ;
        String alias = null;
       while((alias= a.nextElement()) != null)  {
            System.out.println(alias + "\n");
       }

    }

    @Test
    public void createTest() throws CertificateException, UnrecoverableEntryException, NoSuchAlgorithmException, KeyStoreException, IOException {
        KeyGenTest a = new KeyGenTest();
       // a.CreateKey("001", "india");

    }
 }
