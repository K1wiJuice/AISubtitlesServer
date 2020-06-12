package com.AISubtitles.Server.utils;


import java.util.regex.Matcher;
import java.util.regex.Pattern;


//判断手机号格式是否正确
public class PhoneNumberFormatUtils {

    public static boolean isPhoneNumber(String phoneNumber) {
        Pattern p = null;
        Matcher m = null;
        boolean b = false;

        String regex = "^[1](([3|5|8][\\d])|([4][4,5,6,7,8,9])|([6][2,5,6,7])|([7][^9])|([9][1,8,9]))[\\d]{8}$";
        
        return Pattern.matches(regex, phoneNumber);
    }
}
