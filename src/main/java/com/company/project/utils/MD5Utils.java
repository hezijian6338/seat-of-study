package com.company.project.utils;

import java.math.BigInteger;
import java.security.MessageDigest;

public class MD5Utils {

    /**
     * @param str 需要加密的字符串
     * @return StringToMD5_digest加密后的字符串
     */
    public static String StringToMD5_digest(String str) {
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            md5.update(str.getBytes());
            return new BigInteger(1, md5.digest()).toString(16);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 按固定字符加密
     *
     * @param str 需要加密的字符串
     * @return StringToMD5_hex加密后的字符串
     */
    public static String StringToMD5_hex(String str) {
        char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M',
                'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};
        try {
            byte[] btInput = str.getBytes();
            // 获得MD5摘要算法的 MessageDigest 对象
            MessageDigest mdInst = MessageDigest.getInstance("MD5");
            // 使用指定的字节更新摘要
            mdInst.update(btInput);
            // 获得密文
            byte[] md = mdInst.digest();
            // 把密文转换成十六进制的字符串形式
            int j = md.length;
            char s[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                s[k++] = hexDigits[byte0 >>> 4 & 0xf];
                s[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(s);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
