package com.miner.controller;

import com.miner.common.exception.MinerException;
import com.miner.common.utils.R;
import com.miner.dto.CartConsumeDTO;
import com.miner.entity.CardStatEntity;
import com.miner.service.CardStatService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("/laxin")
public class LaxinController {
    protected static Logger logger = LoggerFactory.getLogger(CardStatController.class);
    @Autowired
    private CardStatService cardStatService;
    //拉新使用卡密
    @PostMapping("")
    public R consumeCard(@RequestBody @Validated CartConsumeDTO cartConsumeDTO){
        //查询卡号
        CardStatEntity cardStatEntity = cardStatService.queryByCode(cartConsumeDTO.getCode());
        if (cardStatEntity == null){
            throw new MinerException("卡号不存在");
        }
        if (cardStatEntity.getUsedTime() != null){
            throw new MinerException("该卡已被使用过");
        }
        if (cardStatEntity.getUsedForType() != 1) {
            throw new MinerException("业务类型错误");
        }
        //使用卡密,修改状态
        cardStatEntity.setUsed(true)
                .setUsedForType(1)
                .setUsedTime(new Date())
                .setLink(cartConsumeDTO.getUrl());
        cardStatService.update(cardStatEntity);
        return R.ok();
    }

}
