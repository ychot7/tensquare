package com.tensquare.spit.dao;

import com.tensquare.spit.pojo.Spit;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface SpitDao extends MongoRepository<Spit, String> {

    /**
     * 根据上级Id查询吐槽列表(分页)
     * @param parentId
     * @param pageable
     * @return
     */
    public Page<Spit> findByParentid(String parentId, Pageable pageable);
}
