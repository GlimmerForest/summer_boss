package com.william.boss.utils;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.Security;

/**
 * AES加解密，默认256位
 * @author john
 *
 */
public class AesUtil {
    public static Logger LOGGER = LoggerFactory.getLogger(AesUtil.class);

    public static final String IV = "1234567890abcdef";
   
    public static final String KEY = "boss567890abcdef";

    public static final Charset ENCODING = StandardCharsets.UTF_8;
 
    static {
        if (Security.getProvider(BouncyCastleProvider.PROVIDER_NAME) == null) {
            Security.addProvider(new BouncyCastleProvider());
        }
    } 
    
    public static String encrypt(String content) {

    	return encrypt(content, KEY, IV, ENCODING);
    }  
    
    public static String decrypt(String content) {

        return decrypt(content, KEY, IV, ENCODING);

    }

    /**
     * 数据加密-编码
     * @param content 原内容
     * @param key 加密key
     * @param iv 偏移量
     * @param encoding 编码
     * @return 加密编码后的字符串
     */
    public static String encrypt(String content, String key, String iv, Charset encoding) {
        key = StringUtils.isBlank(key) ? KEY : key;
        iv = StringUtils.isBlank(iv) ? IV : iv;
        encoding = ObjectUtils.isEmpty(encoding) ? ENCODING : encoding;
        byte[] aesEncrypt = encode(content, key, iv, encoding);

        return Base64.encodeBase64String(aesEncrypt);
    }

    /**
     * 数据解码-解密
     * @param content 加密内容
     * @param key 加密key
     * @param iv 偏移量
     * @param encoding 编码
     * @return 解密后字符串
     */
    public static String decrypt(String content, String key, String iv, Charset encoding) {
        key = StringUtils.isBlank(key) ? KEY : key;
        iv = StringUtils.isBlank(iv) ? IV : iv;
        encoding = ObjectUtils.isEmpty(encoding) ? ENCODING : encoding;

        byte[] base64DecodeStr = Base64.decodeBase64(content);
        byte[] aesDecode = decode(base64DecodeStr, key, iv, encoding);

        if (aesDecode == null) {
            return null;
        }
        return new String(aesDecode, encoding);
    }

    /**
     * 数据加密
     * @param content 原内容
     * @param key 加密key
     * @param iv 偏移量
     * @param encoding 编码
     * @return 加密后数据
     */
    public static byte[] encode(String content, String key, String iv, Charset encoding ) {
        try {
            SecretKeySpec sKey = new SecretKeySpec(key.getBytes(), "AES");
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding", "BC");
            IvParameterSpec ivSpec = new IvParameterSpec(iv.getBytes());
            cipher.init(Cipher.ENCRYPT_MODE, sKey, ivSpec);
            return cipher.doFinal(content.getBytes(encoding));
        } catch (Exception ex) {
            LOGGER.error("数据加密:", ex);
        }
        return null;
    }

    /**
     * 数据解密
     * @param content 加密内容通过base64解码后的内容
     * @param key 加密key
     * @param iv 偏移量
     * @param encoding 编码
     * @return 解密后数据
     */
    public static byte[] decode(byte[] content, String key, String iv, Charset encoding) {
        try {
            SecretKeySpec sKey = new SecretKeySpec(key.getBytes(), "AES");
            IvParameterSpec ivSpec = new IvParameterSpec(iv.getBytes(encoding));
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding", "BC");
            cipher.init(Cipher.DECRYPT_MODE, sKey, ivSpec);
            return cipher.doFinal(content);
        } catch (Exception ex) {
            LOGGER.error("数据解密失败:", ex);
        }
        return null;
    }

    public static void main(String[] args) {
        System.out.println(encrypt("123"));
        System.out.println(decrypt(encrypt("123")));
    }
}
 

