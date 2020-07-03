package com.AISubtitles.Server.utils.tencentai;

import org.springframework.beans.factory.annotation.Value;

public class TencentaiInfo {

    @Value("${tencent-Id}")
	public static String secretId;

	@Value("${tencent-Key}")
	public static String secretKey;

}
