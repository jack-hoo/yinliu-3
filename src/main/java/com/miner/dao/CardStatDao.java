package com.miner.dao;

import com.miner.entity.CardStatEntity;
import com.miner.dao.BaseDao;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
/**
 * 
 * 
 * @author hushangjie
 * @email 979783618@qq.com
 * @date 2017-10-11 18:36:19
 */
@Mapper
@Repository
public interface CardStatDao extends BaseDao<CardStatEntity> {
    CardStatEntity queryByCardNumber(String code);
}
