package com.AISubtitles.Server.utils;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;


public class Md5Utils {

    private static final String ALGORITHM = "MD5";

    private static byte[] md5(String s) {
        MessageDigest algorithm;
        try {
            algorithm = MessageDigest.getInstance(ALGORITHM);
            algorithm.reset();
            algorithm.update(s.getBytes(StandardCharsets.UTF_8));
            byte[] messageDigest = algorithm.digest();
            return messageDigest;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    private static final String toHex(byte hash[]) {
        if (hash == null) return null;

        StringBuffer buf = new StringBuffer(hash.length * 2);
        int i;
        for (i = 0; i < hash.length; i++) {
            if ((hash[i] & 0xff) < 0x10) {
                buf.append("0");
            }
            buf.append(Long.toString(hash[i] & 0xff, 16));
        }

        return buf.toString();
    }

    public static String hash(String s) {
        try {
            String toHexed = toHex(md5(s));
            if (toHexed != null) {
                return new String(toHexed.getBytes(StandardCharsets.UTF_8),
                                StandardCharsets.UTF_8);
            } else {
                return s;
            }
        } catch (Exception e) {
            return s;
        }
    }
}
