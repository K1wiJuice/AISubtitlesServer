package com.AISubtitles.Server.dao;

import com.AISubtitles.Server.domain.Chunk;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ChunkDao extends JpaRepository<Chunk, Integer>, JpaSpecificationExecutor<Chunk> {


    //原生sql是为了只获取一个字段
    //接受的参数是userId，但是表中没有这个字段，需要模糊查询
    String sql = "select distinct video_name_user_id from chunk where video_name_user_id LIKE ?";
    @Query(value=sql, nativeQuery=true)
    List<String> findByVideoNameUserIdLike(String userId);

    void deleteByVideoNameUserId(String videoNameUserId);

}
