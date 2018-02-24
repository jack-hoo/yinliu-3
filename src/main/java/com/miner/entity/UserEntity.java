package com.miner.entity;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;
import lombok.experimental.Accessors;



/**
 * 
 * 
 * @author hushangjie
 * @email 979783618@qq.com
 * @date 2017-10-11 21:28:13
 */
@Data
@Accessors(chain = true)
public class UserEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//
	private Integer userId;
	//
	private String username;
	//
	private String password;

}
