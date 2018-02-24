package com.miner.service.impl;

import com.miner.service.CardStatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import com.miner.dao.CardStatDao;
import com.miner.entity.CardStatEntity;




@Service("cardStatService")
public class CardStatServiceImpl implements CardStatService {
	@Autowired
	private CardStatDao cardStatDao;
	
	@Override
	public CardStatEntity queryObject(Integer cardId){
		return cardStatDao.queryObject(cardId);
	}

    @Override
    public CardStatEntity queryByCode(String code) {
        return cardStatDao.queryByCardNumber(code);
    }

    @Override
	public List<CardStatEntity> queryList(Map<String, Object> map){
		return cardStatDao.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return cardStatDao.queryTotal(map);
	}
	
	@Override
	public void save(CardStatEntity cardStat){
		cardStatDao.save(cardStat);
	}

    @Override
    public void saveBatch(List<CardStatEntity> cardStatEntities) {
        cardStatDao.saveBatch(cardStatEntities);
    }

    @Override
	public void update(CardStatEntity cardStat){
		cardStatDao.update(cardStat);
	}
	
	@Override
	public void delete(Integer cardId){
		cardStatDao.delete(cardId);
	}
	
	@Override
	public void deleteBatch(Integer[] cardIds){
		cardStatDao.deleteBatch(cardIds);
	}
	
}
