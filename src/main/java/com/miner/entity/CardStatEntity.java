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
 * @date 2017-10-11 18:36:19
 */
@Data
@Accessors(chain = true)
public class CardStatEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//
	private Integer cardId;
	//导入的卡号
	private String cardNumber;
	//使用的链接
	private String link;
	//卡导入时间
	private Date createTime;
	//卡使用时间
	private Date usedTime;
	private int usedForType;
    private boolean used;
    private String taskId;
    private boolean taskState;
}
