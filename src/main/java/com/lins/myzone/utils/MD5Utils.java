package com.lins.myzoom.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;

/**
 * @ClassName MD5Utils
 * @Description TODO
 * @Author lin
 * @Date 2021/2/2 15:18
 * @Version 1.0
 **/
public class MD5Utils {
    public static String code(String string) {
        MessageDigest messageDigest= null;
        try {
            messageDigest = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        messageDigest.update(string.getBytes());
        byte[] bytes=messageDigest.digest();
        int i;
        StringBuffer stringBuffer=new StringBuffer("");
        for (int offset=0;offset<bytes.length;offset++){
            i=bytes[offset];
            if(i<0)
                i+=256;
            if(i<16)
                stringBuffer.append(0);
            stringBuffer.append(Integer.toHexString(i));
        }
        //32位加密
        return stringBuffer.toString();
        //16位加密
//        return stringBuffer.toString().substring(8,24);
    }

    public static void main(String[] args){
        System.out.println(code("yinhuilin123"));
    }
}
