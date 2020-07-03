package com.AISubtitles.Server.service.impl;


import com.AISubtitles.Server.dao.ChunkDao;
import com.AISubtitles.Server.domain.Chunk;
import com.AISubtitles.Server.service.ChunkService;
import com.AISubtitles.Server.utils.Md5Utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

@Service
public class ChunkServiceImpl implements ChunkService {

    @Autowired
    private ChunkDao chunkDao;

    @Override
    public void saveChunk(Chunk chunk) {
        chunkDao.save(chunk);
    }


    //检查文件块是否存在
    //没看明白。。。。。。
    @Override
    public boolean checkChunk(String identifier, Integer chunkNumber) {
        Specification<Chunk> specification = (Specification<Chunk>) (root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            predicates.add(criteriaBuilder.equal(root.get("identifier"), identifier));
            predicates.add(criteriaBuilder.equal(root.get("chunkNumber"), chunkNumber));

            return criteriaQuery.where(predicates.toArray(new Predicate[predicates.size()])).getRestriction();
        };

        return chunkDao.findOne(specification).orElse(null) == null;
    }

    @Override
    public List<String> getAllVideosUploading(String userId) {
        return chunkDao.findByVideoNameUserIdLike(userId);
    }

    @Override
    public void cancel(String identifier) {
        chunkDao.deleteByIdentifier(identifier);
    }


}
