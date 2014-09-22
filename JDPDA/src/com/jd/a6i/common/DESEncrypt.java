/* 
 * Copyright (c) 2013, S.F. Express Inc. All rights reserved.
 */
package com.jd.a6i.common;

import android.util.Base64;

import java.security.Key;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import static android.util.Base64.CRLF;
import static android.util.Base64.NO_WRAP;
import static android.util.Base64.encode;

/**
 * ������DES����
 * <p/>
 * <pre>
 * HISTORY
 * ****************************************************************************
 *  ID   DATE           PERSON          REASON
 *  1    2013��7��23��      014767          Create
 * ****************************************************************************
 * </pre>
 *
 * @author 014767
 * @since 1.0
 */
public class DESEncrypt {

    public static final String DEFAULT_CHARSET = "UTF-8";
    private Cipher encryptCipher;
    private Cipher decryptCipher;
    
    /**
     * @param strKey ���ܺͽ��ܵ���Կ
     */
    public DESEncrypt(String secretKey) {
        try {
            Key key = getKey(secretKey);
            encryptCipher = Cipher.getInstance("DES");
            encryptCipher.init(Cipher.ENCRYPT_MODE, key);
            decryptCipher = Cipher.getInstance("DES");
            decryptCipher.init(Cipher.DECRYPT_MODE, key);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    /**
     * @param content ��Ҫ���ܵ�����
     * @return
     */
    public String encrypt(String content) {
        try {
            byte[] encrypted = encrypt(content.getBytes(DEFAULT_CHARSET));
            return new String(encode(encrypted, CRLF | NO_WRAP), DEFAULT_CHARSET);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * @param content ��Ҫ���ܵ�����
     * @return
     */
    public String decrypt(String content) {
        try {
            return new String(decrypt(Base64.decode(content, CRLF)), DEFAULT_CHARSET);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
    /**
     * 64λ����
     * @param data
     * @return
     * @throws Exception
     */
    private byte[] decrypt(byte data[]) throws Exception {
        return decryptCipher.doFinal(data);
    }

    /**
     * 64λ����
     * @param data
     * @return
     * @throws Exception
     */
    private byte[] encrypt(byte data[]) throws Exception {
        return encryptCipher.doFinal(data);
    }

    /**
     * ������Կ�����µ�������Կ
     * @param secretKey
     * @return
     * @throws Exception
     */
    private Key getKey(String secretKey) throws Exception {
    	byte[] keyBytes = secretKey.getBytes();
        byte secretBytes[] = new byte[8];
        for (int i = 0; i < keyBytes.length && i < secretBytes.length; i++)
        	secretBytes[i] = keyBytes[i];

        return new SecretKeySpec(secretBytes, "DES");
    }
}
