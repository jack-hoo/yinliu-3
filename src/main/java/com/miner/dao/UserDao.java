package com.miner.dao;

import com.miner.entity.UserEntity;
import com.miner.dao.BaseDao;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
/**
 * 
 * 
 * @author hushangjie
 * @email 979783618@qq.com
 * @date 2017-10-11 21:28:13
 */
@Mapper
@Repository
public interface UserDao extends BaseDao<UserEntity> {
    UserEntity queryByUserName(String username);
}
