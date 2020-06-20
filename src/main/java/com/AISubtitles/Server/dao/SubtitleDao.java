package com.AISubtitles.Server.dao;

import com.AISubtitles.Server.domain.Subtitle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

/**
 * @ Author     ：lzl
 * @ Date       ：Created in 19:13 2020/6/19
 * @ Description：对字幕的一些数据处理操作
 * @ Modified By：
 * @Version: 1.0$
 */
public interface SubtitleDao extends JpaRepository<Subtitle,Integer> {


}
