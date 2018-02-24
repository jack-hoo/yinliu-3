package com.miner.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import com.miner.dao.UserDao;
import com.miner.entity.UserEntity;
import com.miner.service.UserService;



@Service("userService")
public class UserServiceImpl implements UserService {
	@Autowired
	private UserDao userDao;

    @Override
    public UserEntity queryByUserName(String username) {
        return userDao.queryByUserName(username);
    }

    @Override
	public UserEntity queryObject(Integer userId){
		return userDao.queryObject(userId);
	}
	
	@Override
	public List<UserEntity> queryList(Map<String, Object> map){
		return userDao.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return userDao.queryTotal(map);
	}
	
	@Override
	public void save(UserEntity user){
		userDao.save(user);
	}
	
	@Override
	public void update(UserEntity user){
		userDao.update(user);
	}
	
	@Override
	public void delete(Integer userId){
		userDao.delete(userId);
	}
	
	@Override
	public void deleteBatch(Integer[] userIds){
		userDao.deleteBatch(userIds);
	}
	
}
