package com.AISubtitles.Server.service;

import com.AISubtitles.Server.domain.Result;

public interface GenerateCoverPageService {

    Result generateCoverPage(Integer videoId, String time);

}
