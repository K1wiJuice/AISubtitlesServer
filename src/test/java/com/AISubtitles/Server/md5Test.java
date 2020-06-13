package com.AISubtitles.Server;


import com.AISubtitles.Server.utils.Md5Utils;
import org.junit.jupiter.api.Test;

public class md5Test {

    @Test
    public void md5Test() {
        System.out.println(Md5Utils.md5("byc"));
    }
}
