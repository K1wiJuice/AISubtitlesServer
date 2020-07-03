package com.AISubtitles.Server.utils.tencentai;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.tencentcloudapi.common.Credential;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.common.profile.ClientProfile;
import com.tencentcloudapi.common.profile.HttpProfile;
import com.tencentcloudapi.facefusion.v20181201.FacefusionClient;
import com.tencentcloudapi.facefusion.v20181201.models.DescribeMaterialListRequest;
import com.tencentcloudapi.facefusion.v20181201.models.DescribeMaterialListResponse;
import com.tencentcloudapi.facefusion.v20181201.models.FaceFusionRequest;
import com.tencentcloudapi.facefusion.v20181201.models.FaceFusionResponse;

import java.io.*;
import java.util.Base64;

//import sun.nio.cs.StreamEncoder;

public class FaceFusion {

    /**
     * 将图片转换成base64编码
     *
     * @param imgPath 需要被编码的图片路径
     * @return 图片的base64编码
     * @author PY
     */
    public static String imgToBase64(String imgPath) {
        InputStream in = null;
        byte[] data = null;
        String encode = null;
        Base64.Encoder encoder = Base64.getEncoder();
        try {
            in = new FileInputStream(imgPath);
            data = new byte[in.available()];
            in.read(data);
            encode = encoder.encodeToString(data);
        } catch (IOException e) {
            System.out.println(e.toString());
        } finally {
            try {
                in.close();
            } catch (IOException e) {
                System.out.println(e.toString());
            }
        }
        return encode;
    }

    /**
     * 将图片的base64编码生成jpg文件
     *
     * @param imgData 图片的base64编码
     * @param imgPath 输出图片的路径
     * @return 解码是否成功
     * @author PY
     */
    public static boolean base64ToImg(String imgData, String imgPath) {
        if (imgData == null)
            return false;
        Base64.Decoder decoder = Base64.getDecoder();
        OutputStream out = null;
        try {
            out = new FileOutputStream(imgPath);
            byte[] b = decoder.decode(imgData);
            for (int i = 0; i < b.length; i++) {
                if (b[i] < 0) {
                    b[i] += 256;
                }
            }
            out.write(b);
        } catch (FileNotFoundException e) {
            System.out.println(e.toString());
        } catch (IOException e) {
            System.out.println(e.toString());
        } finally {
            try {
                out.flush();
                out.close();
            } catch (IOException e) {
                System.out.println(e.toString());
            }
        }
        return true;
    }

    /**
     * 获取人脸融合模板列表素材信息。这一部分主要用于传给前端，告诉他们我们可进行换脸的素材有哪些。
     *
     * @return 一个JSONArray，其中的每个JSONObject有两个键值对，一个是MaterialId，表示模板id，为String类型，另一个是Url，表示模板图片的网络地址
     */
    public static JSONArray getDescribeMaterialList() {
        JSONArray res = new JSONArray();
        try {

            Credential cred = new Credential(TencentaiInfo.secretId, TencentaiInfo.secretKey);

            HttpProfile httpProfile = new HttpProfile();
            httpProfile.setEndpoint("facefusion.tencentcloudapi.com");

            ClientProfile clientProfile = new ClientProfile();
            clientProfile.setHttpProfile(httpProfile);

            FacefusionClient client = new FacefusionClient(cred, "ap-beijing", clientProfile);

            String params = "{\"ActivityId\":303269}";
            DescribeMaterialListRequest req = DescribeMaterialListRequest.fromJsonString(params, DescribeMaterialListRequest.class);

            DescribeMaterialListResponse resp = client.DescribeMaterialList(req);

            JSONObject jsonObject = JSONObject.parseObject(DescribeMaterialListRequest.toJsonString(resp));
            JSONArray MaterialInfos = jsonObject.getJSONArray("MaterialInfos");

            for (int i = 0; i < MaterialInfos.size(); i++) {
                JSONObject temp = new JSONObject();
                temp.put("MaterialId", MaterialInfos.getJSONObject(i).get("MaterialId"));
                temp.put("Url", MaterialInfos.getJSONObject(i).get("Url"));
                res.add(temp);
            }
        } catch (TencentCloudSDKException e) {
            System.out.println(e.toString());
        }
        return res;
    }

    /**
     * 人脸融合
     *
     * @param imgPath    用户的人脸图片
     * @param outputPath 融合后的人脸图片
     * @param MaterialId 人脸融合模板id
     * @author PY
     */
    public static void facefusion(String imgPath, String outputPath, String MaterialId) {

        try {

            Credential cred = new Credential(TencentaiInfo.secretId, TencentaiInfo.secretKey);

            HttpProfile httpProfile = new HttpProfile();
            httpProfile.setEndpoint("facefusion.tencentcloudapi.com");

            ClientProfile clientProfile = new ClientProfile();
            clientProfile.setHttpProfile(httpProfile);
            FacefusionClient client = new FacefusionClient(cred, "ap-beijing", clientProfile);

            String format = "{\"ProjectId\":\"303269\",\"ModelId\":\"%s\",\"Image\":\"%s\",\"RspImgType\":\"base64\"}";
            String params = String.format(format, MaterialId, imgToBase64(imgPath));
            FaceFusionRequest req = FaceFusionRequest.fromJsonString(params, FaceFusionRequest.class);

            FaceFusionResponse resp = client.FaceFusion(req);

            JSONObject jsonObject = JSONObject.parseObject(FaceFusionRequest.toJsonString(resp));
            base64ToImg(jsonObject.get("Image").toString(), outputPath);
        } catch (TencentCloudSDKException e) {
            System.out.println(e.toString());
        }

    }

}
