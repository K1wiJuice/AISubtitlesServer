package com.AISubtitles.Server.service;

import org.apache.commons.codec.binary.Base64;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

public class Imagebase64Service {

    public String getImageBinary(String way) {
        File f = new File(way);
        try {
            BufferedImage bi = ImageIO.read(f);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(bi, "jpg", baos);
            byte[] bytes = baos.toByteArray();

            return Base64.encodeBase64String(bytes).trim();
        } 
        catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void base64StringToImage(String base64String,String newway) {
        try {
            byte[] bytes1 = Base64.decodeBase64(base64String);
            ByteArrayInputStream bais = new ByteArrayInputStream(bytes1);
            BufferedImage bi1 = ImageIO.read(bais);
            File f1 = new File(newway);
            ImageIO.write(bi1, "jpg", f1);
        } 
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}