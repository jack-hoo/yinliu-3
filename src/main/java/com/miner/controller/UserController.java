package com.miner.controller;

import java.util.List;
import java.util.Map;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.miner.entity.UserEntity;
import com.miner.service.UserService;
import com.miner.common.utils.PageUtils;
import com.miner.common.utils.Query;
import com.miner.common.utils.R;




/**
 * 
 * 
 * @author hushangjie
 * @email 979783618@qq.com
 * @date 2017-10-11 21:28:13
 */
@RestController
@RequestMapping("user")
public class UserController {
	@Autowired
	private UserService userService;
	
	/**
	 * 列表
	 */
	@GetMapping("")
	public R list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);
		if (params.get("page") != null && params.get("limit")!= null){
			List<UserEntity> userList = userService.queryList(query);
			int total = userService.queryTotal(query);
			
			PageUtils pageUtil = new PageUtils(userList, total, query.getLimit(), query.getPage());
			
			return R.ok().put("page", pageUtil);
		}else{
			List<UserEntity> userList = userService.queryList(query);
			return R.ok().put("list", userList);
		}
		
	}
	
	
	/**
	 * 信息
	 */
	@GetMapping("/{userId}")
	public R info(@PathVariable("userId") Integer userId){
		UserEntity user = userService.queryObject(userId);
		
		return R.ok().put("user", user);
	}
	
	/**
	 * 保存
	 */
	@PostMapping("")
	public R save(@RequestBody UserEntity user){
		userService.save(user);
		
		return R.ok();
	}
	
	/**
	 * 修改
	 */
	@PutMapping("")
	public R update(@RequestBody UserEntity user){
		userService.update(user);
		
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@DeleteMapping("")
	public R delete(@RequestBody Integer[] userIds){
		userService.deleteBatch(userIds);
		
		return R.ok();
	}
	
}
