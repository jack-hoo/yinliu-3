package com.miner.service;

import com.miner.entity.UserEntity;

import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author hushangjie
 * @email 979783618@qq.com
 * @date 2017-10-11 21:28:13
 */
public interface UserService {

    UserEntity queryByUserName(String username);
	
	UserEntity queryObject(Integer userId);
	
	List<UserEntity> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(UserEntity user);
	
	void update(UserEntity user);
	
	void delete(Integer userId);
	
	void deleteBatch(Integer[] userIds);
}
