package com.AISubtitles.Server.service;

import com.tencentcloudapi.common.Credential;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.common.profile.ClientProfile;
import com.tencentcloudapi.common.profile.HttpProfile;
import com.tencentcloudapi.fmu.v20191213.FmuClient;
import com.tencentcloudapi.fmu.v20191213.models.BeautifyPicRequest;
import com.tencentcloudapi.fmu.v20191213.models.BeautifyPicResponse;
import org.springframework.stereotype.Service;

public class BeautifyPicService {

	public String beautify_api(String SecretID,String SecretKey,String base, int white, int smooth, int facelift, int eye) {
        try {
            Credential cred = new Credential(SecretId, SecretKey);

            HttpProfile httpProfile = new HttpProfile();
            httpProfile.setEndpoint("fmu.ap-beijing.tencentcloudapi.com");

            ClientProfile clientProfile = new ClientProfile();
            clientProfile.setHttpProfile(httpProfile);

            FmuClient client = new FmuClient(cred, "ap-beijing", clientProfile);

            String pre = "{\"Image\":\"%s\",\"Whitening\":%s,\"Smoothing\":%s,\"FaceLifting\":%s,\"EyeEnlarging\":%s}";
            String params =  String.format(pre, base, white, smooth, facelift, eye);
            BeautifyPicRequest req = BeautifyPicRequest.fromJsonString(params, BeautifyPicRequest.class);

            BeautifyPicResponse resp = client.BeautifyPic(req);
            String res = BeautifyPicRequest.toJsonString(resp);
            String resu = res.split(":")[1].split(",")[0];
            String result = resu.substring(1,resu.length()-1);
            return result;
        }
        catch (TencentCloudSDKException e) {
                System.out.println(e.toString());
        }
		return base;

    }
}
