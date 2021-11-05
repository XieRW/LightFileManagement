package com.xrw.springCloudAlibaba.utils;
/**
* @author chenwenyan
* @createDate 创建时间：2020年2月8日 下午9:25:33
*/

import org.apache.tomcat.util.codec.binary.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;


public class AESUtil {
 
    //key为16位
    private static String KEY = "_aes_secret_key_";
 
    private static String IV = "_aes_secret_iv__";
    
//    private static String KEY = "XSDSWSDSSDSAWDSW";
//    
//    private static String IV = "AIEWNIXUOHZGNAUG";
 
 
    /**
     * 加密方法
     *
     * @param data 要加密的数据
     * @param key  加密key
     * @param iv   加密iv
     * @return 加密的结果
     * @throws Exception
     */
    public static String encrypt(String data, String key, String iv) throws Exception {
        try {
 
            Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");//"算法/模式/补码方式"NoPadding PkcsPadding
            int blockSize = cipher.getBlockSize();
 
            byte[] dataBytes = data.getBytes();
            int plaintextLength = dataBytes.length;
            if (plaintextLength % blockSize != 0) {
                plaintextLength = plaintextLength + (blockSize - (plaintextLength % blockSize));
            }
 
            byte[] plaintext = new byte[plaintextLength];
            System.arraycopy(dataBytes, 0, plaintext, 0, dataBytes.length);
 
            SecretKeySpec keyspec = new SecretKeySpec(key.getBytes(), "AES");
            IvParameterSpec ivspec = new IvParameterSpec(iv.getBytes());
 
            cipher.init(Cipher.ENCRYPT_MODE, keyspec, ivspec);
            byte[] encrypted = cipher.doFinal(plaintext);
 
            return new Base64().encodeToString(encrypted);
 
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
 
    /**
     * 解密方法
     *
     * @param data 要解密的数据
     * @param key  解密key
     * @param iv   解密iv
     * @return 解密的结果
     * @throws Exception
     */
    public static String desEncrypt(String data, String key, String iv) throws Exception {
        try {
            byte[] encrypted1 = new Base64().decode(data);
 
            Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
            SecretKeySpec keyspec = new SecretKeySpec(key.getBytes(), "AES");
            IvParameterSpec ivspec = new IvParameterSpec(iv.getBytes());
 
            cipher.init(Cipher.DECRYPT_MODE, keyspec, ivspec);
 
            byte[] original = cipher.doFinal(encrypted1);
            String originalString = new String(original,"utf-8");
            return originalString;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
 
    /**
     * 使用默认的key和iv加密
     *
     * @param data
     * @return
     * @throws Exception
     */
    public static String encrypt(String data) throws Exception {
        return encrypt(data, KEY, IV);
    }
 
    /**
     * 使用默认的key和iv解密
     *
     * @param data
     * @return
     * @throws Exception
     */
    public static String desEncrypt(String data) throws Exception {
        return desEncrypt(data, KEY, IV);
    }
 
 
    /**
     * 测试
     */
    public static void main(String args[]) throws Exception {
 
        String test = "123456c";
//        String test = new String(test1.getBytes(), "UTF-8");
        String data = null;
        String key = KEY;
        String iv =IV;
        // /g2wzfqvMOeazgtsUVbq1kmJawROa6mcRAzwG1/GeJ4=
        data = encrypt(test, key, iv);
        System.out.println("数据：" + test);
        System.out.println("加密：" + data);
        String jiemi = desEncrypt("IGHWM9nAHRLV26VYpeIiuA==", key, iv).trim();
        
        System.out.println("解密：" + jiemi);
 
 
    }
}