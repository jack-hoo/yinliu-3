package com.miner.controller;

import com.miner.common.exception.MinerException;
import com.miner.common.utils.R;
import com.miner.entity.UserEntity;
import com.miner.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by hushangjie on 2017/10/11.
 */
@Controller
@RequestMapping("")
public class Main {
    @Autowired
    private UserService userService;
    @RequestMapping("/")
    public String index(){
        return "main";
    }
    @RequestMapping("/main")
    public String main(){
        return "main";
    }
    @RequestMapping("/query")
    public String query(){
        return "query";
    }
    @RequestMapping("/admin")
    public String admin(HttpServletRequest request){
        if (request.getSession().getAttribute("username") == null){
            return "redirect:login";
        }
        return "admin";
    }
    @GetMapping("/login")
    public String loginView(HttpServletRequest request){
        if (request.getSession().getAttribute("username") != null){
            return "redirect:admin";
        }
        return "login";
    }
    @PostMapping("/login")
    @ResponseBody
    public R login(@RequestBody UserEntity userEntity, HttpServletRequest request){
        UserEntity user = userService.queryByUserName(userEntity.getUsername());
        if (user == null){
            throw new MinerException("用户不存在");
        }
        if (!user.getPassword().equals(userEntity.getPassword())){
            throw new MinerException("密码错误");
        }
        request.getSession().setAttribute("username",userEntity.getUsername());
        return R.ok();
    }
}
