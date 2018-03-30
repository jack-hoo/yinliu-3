package com.miner.controller;

import java.io.*;
import java.util.*;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.miner.common.exception.MinerException;
import com.miner.dto.CartConsumeDTO;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.PoolingClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.SyncBasicHttpParams;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.miner.entity.CardStatEntity;
import com.miner.service.CardStatService;
import com.miner.common.utils.PageUtils;
import com.miner.common.utils.Query;
import com.miner.common.utils.R;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.servlet.http.HttpServletRequest;


/**
 * @author hushangjie
 * @email 979783618@qq.com
 * @date 2017-10-11 18:36:19
 */
@RestController
@RequestMapping("card")
public class CardStatController {

    protected static Logger logger = LoggerFactory.getLogger(CardStatController.class);
    @Autowired
    private CardStatService cardStatService;

    //导入卡密

    @PostMapping("/import_common")
    public R importCard(@RequestParam("file") MultipartFile file, HttpServletRequest request) {
        if (request.getSession().getAttribute("username") == null) {
            return R.error();
        }
        List<CardStatEntity> cardStatEntities = new ArrayList<>();
        try {
            InputStream inputStream = file.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String card = "";
            while ((card = bufferedReader.readLine()) != null) {
                CardStatEntity cardStatEntity = new CardStatEntity();
                cardStatEntity.setCardNumber(card);
                cardStatEntity.setUsedForType(0);
                cardStatEntities.add(cardStatEntity);
            }
            bufferedReader.close();
            inputStreamReader.close();
            inputStream.close();
            //保存到数据库
            cardStatService.saveBatch(cardStatEntities);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return R.ok().put("size", cardStatEntities.size());
    }

    @PostMapping("/import_laxin")
    public R importCardForLaxin(@RequestParam("file") MultipartFile file, HttpServletRequest request) {
        if (request.getSession().getAttribute("username") == null) {
            return R.error();
        }
        List<CardStatEntity> cardStatEntities = new ArrayList<>();
        try {
            InputStream inputStream = file.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String card = "";
            while ((card = bufferedReader.readLine()) != null) {
                CardStatEntity cardStatEntity = new CardStatEntity();
                cardStatEntity.setCardNumber(card);
                cardStatEntity.setUsedForType(1);
                cardStatEntities.add(cardStatEntity);
            }
            bufferedReader.close();
            inputStreamReader.close();
            inputStream.close();
            //保存到数据库
            cardStatService.saveBatch(cardStatEntities);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return R.ok().put("size", cardStatEntities.size());
    }

    //查询任务状态
    @GetMapping("/query_state")
    public R query(@RequestParam(value = "code", required = true) String code) {
        CardStatEntity cardStatEntity = cardStatService.queryByCode(code);
        if (cardStatEntity != null && cardStatEntity.getUsedForType() == 1) {
            throw new MinerException("请关注您的淘宝拉新状态");
        }
        if (cardStatEntity == null) {
            throw new MinerException("暂无数据");
        }

        String taskId = cardStatEntity.getTaskId();
        HttpClient client = new DefaultHttpClient(new PoolingClientConnectionManager(), new SyncBasicHttpParams());
        String url = "http://service.liuliangbao.cn/api/v2/task.pc.exec";
        HttpPost post = new HttpPost(url);
        List<NameValuePair> list = new ArrayList();
        list.add(new BasicNameValuePair("apiKey", "e9f98c627748d4f1daac07a7bb360f40"));
        list.add(new BasicNameValuePair("taskId", taskId));
        try {
            UrlEncodedFormEntity entity = new UrlEncodedFormEntity(list, "UTF-8");
            post.setEntity(entity);

            HttpResponse response = client.execute(post);

            String result = EntityUtils.toString(response.getEntity(), "UTF-8");
            JSONObject all = JSON.parseObject(result);
            JSONObject resu = JSON.parseObject(all.getString("result"));
            Integer status = resu.getInteger("status");
            String state = "";
            if (status == 0) {
                state = "停止中";
            }
            if (status == 1) {
                state = "优化中";
            }
            if (status == 3) {
                state = "暂停中";
            }
            if (status == 4) {
                state = "定时中";
            }
            return R.ok().put("timeStart", cardStatEntity.getUsedTime()).put("result", resu).put("url", cardStatEntity.getLink()).put("state", state);
        } catch (Exception e) {
            logger.error("查询任务失败");
            throw new MinerException("查询任务失败");

        }
        /* return R.ok();*/
    }

    /**
     * 添加卡密
     *
     * @param cardStat
     * @return
     */
    @PostMapping("")
    public R save(@RequestBody CardStatEntity cardStat,HttpServletRequest request) {
        if (request.getSession().getAttribute("username") == null) {
            return R.error();
        }
        cardStatService.save(cardStat);

        return R.ok();
    }

    //查看所有卡密

    /**
     * 查询所有卡券,可以按照(时间段，已使用，未使用)
     */
    @GetMapping("")
    public R list(@RequestParam Map<String, Object> params,HttpServletRequest request) {
        if (request.getSession().getAttribute("username") == null) {
            return R.error();
        }
        //查询列表数据
        Query query = new Query(params);
        if (params.get("page") != null && params.get("limit") != null) {
            List<CardStatEntity> cardStatList = cardStatService.queryList(query);
            int total = cardStatService.queryTotal(query);

            PageUtils pageUtil = new PageUtils(cardStatList, total, query.getLimit(), query.getPage());

            return R.ok().put("page", pageUtil);
        } else {
            List<CardStatEntity> cardStatList = cardStatService.queryList(query);
            return R.ok().put("list", cardStatList);
        }

    }

    //使用卡密
    @PostMapping("consume")
    public R consumeCard(@RequestBody @Validated CartConsumeDTO cartConsumeDTO) {

        //22点后无法下单
        Calendar calendar = Calendar.getInstance();
        if (calendar.getTime().getHours() > 22) {
            throw new MinerException("系统休息中，明天再来吧！");
        }
        //查询卡号
        CardStatEntity cardStatEntity = cardStatService.queryByCode(cartConsumeDTO.getCode());
        if (cardStatEntity == null) {
            throw new MinerException("卡号不存在");
        }
        if (cardStatEntity.getUsedTime() != null) {
            throw new MinerException("该卡已被使用过");
        }
        if (cardStatEntity.getUsedForType() != 0) {
            throw new MinerException("业务类型错误");
        }
        String taskId = "";
        //调用远程接口，自动处理引流订单
        HttpClient client = new DefaultHttpClient(new PoolingClientConnectionManager(), new SyncBasicHttpParams());
        String url = "http://service.liuliangbao.cn/api/v2/task.pc.create";
        HttpPost post = new HttpPost(url);
        List<NameValuePair> list = new ArrayList();
        list.add(new BasicNameValuePair("apiKey", "e9f98c627748d4f1daac07a7bb360f40"));
        list.add(new BasicNameValuePair("plan", "200"));
        list.add(new BasicNameValuePair("url", cartConsumeDTO.getUrl()));
        list.add(new BasicNameValuePair("fastCurve", "true"));
        try {
            UrlEncodedFormEntity entity = new UrlEncodedFormEntity(list, "UTF-8");
            post.setEntity(entity);

            HttpResponse response = client.execute(post);

            String result = EntityUtils.toString(response.getEntity(), "UTF-8");
            JSONObject all = JSON.parseObject(result);
            JSONObject resu = JSON.parseObject(all.getString("result"));
            taskId = resu.getString("taskId");
            logger.info("任务创建成功");
        } catch (Exception e) {
            logger.error("任务创建失败");
            e.printStackTrace();
            throw new MinerException("任务开始失败");

        }
        //开启任务

        HttpClient client2 = new DefaultHttpClient(new PoolingClientConnectionManager(), new SyncBasicHttpParams());
        String url2 = "http://service.liuliangbao.cn/api/v2/service.start";
        HttpPost post2 = new HttpPost(url2);
        List<NameValuePair> list2 = new ArrayList();
        list2.add(new BasicNameValuePair("apiKey", "e9f98c627748d4f1daac07a7bb360f40"));
        list2.add(new BasicNameValuePair("taskId", taskId));
        list2.add(new BasicNameValuePair("taskType", "1"));
        try {
            UrlEncodedFormEntity entity = new UrlEncodedFormEntity(list2, "UTF-8");
            post2.setEntity(entity);

            HttpResponse response = client.execute(post2);

            String result = EntityUtils.toString(response.getEntity(), "UTF-8");
            JSONObject all = JSON.parseObject(result);
            JSONObject resu = JSON.parseObject(all.getString("status"));
            if (resu.getInteger("code") != 100) {
                throw new MinerException("任务开始失败");
            }
            cardStatEntity.setLink(cartConsumeDTO.getUrl());
            cardStatEntity.setUsedTime(new Date());
            cardStatEntity.setUsed(true);
            cardStatEntity.setUsedForType(0);
            cardStatEntity.setTaskState(true);
            cardStatEntity.setTaskId(taskId);
            cardStatService.update(cardStatEntity);
            logger.info("任务开启成功");
        } catch (Exception e) {
            logger.error("任务开启失败");
            e.printStackTrace();
        }
        return R.ok();
    }


    /**
     * 信息
     */
    @GetMapping("/{cardId}")
    public R info(@PathVariable("cardId") Integer cardId,HttpServletRequest request) {
        if (request.getSession().getAttribute("username") == null) {
            return R.error();
        }
        CardStatEntity cardStat = cardStatService.queryObject(cardId);

        return R.ok().put("cardStat", cardStat);
    }


    /**
     * 修改
     */
    @PutMapping("")
    public R update(@RequestBody List<CardStatEntity> cardStats,HttpServletRequest request) {
        if (request.getSession().getAttribute("username") == null) {
            return R.error();
        }
        for (CardStatEntity cardStatEntity : cardStats) {
            cardStatEntity.setTaskState(true);
            cardStatService.update(cardStatEntity);
        }

        return R.ok();
    }

    /**
     * 删除
     */
    @DeleteMapping("")
    public R delete(@RequestBody Integer[] cardIds,HttpServletRequest request) {
        if (request.getSession().getAttribute("username") == null) {
            return R.error();
        }
        cardStatService.deleteBatch(cardIds);

        return R.ok();
    }

}
