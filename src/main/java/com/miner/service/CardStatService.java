package com.miner.service;

import com.miner.entity.CardStatEntity;

import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author hushangjie
 * @email 979783618@qq.com
 * @date 2017-10-11 18:36:19
 */
public interface CardStatService {
	
	CardStatEntity queryObject(Integer cardId);

	CardStatEntity queryByCode(String code);

	List<CardStatEntity> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(CardStatEntity cardStat);

	void saveBatch(List<CardStatEntity> cardStatEntities);
	
	void update(CardStatEntity cardStat);
	
	void delete(Integer cardId);
	
	void deleteBatch(Integer[] cardIds);
}
