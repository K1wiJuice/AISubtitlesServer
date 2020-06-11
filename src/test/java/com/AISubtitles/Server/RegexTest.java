package com.AISubtitles.Server;


import com.AISubtitles.Server.utils.PhoneNumberFormatUtils;
import org.junit.jupiter.api.Test;

public class RegexTest {


    @Test
    public void phoneNumberTest() {
        System.out.println(PhoneNumberFormatUtils.isPhoneNumber("15665835082"));
        System.out.println(PhoneNumberFormatUtils.isPhoneNumber("1566583508"));
        System.out.println(PhoneNumberFormatUtils.isPhoneNumber("156658350824"));
        System.out.println(PhoneNumberFormatUtils.isPhoneNumber("1566583508l"));
        System.out.println(PhoneNumberFormatUtils.isPhoneNumber("1566@35082"));
    }
}
