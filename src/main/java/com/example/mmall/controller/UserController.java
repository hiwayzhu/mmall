package com.example.mmall.controller;

import com.example.mmall.common.Const;
import com.example.mmall.common.ServerResponce;
import com.example.mmall.dao.UserMapper;
import com.example.mmall.pojo.User;
import com.example.mmall.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/usr/")
public class UserController {

    @Autowired
    private IUserService iUserService;
    /**
     * 用户登录
     * @param username
     * @param password
     * @param session
     * @return
     */
    @RequestMapping(value = "login.do",method=RequestMethod.POST)
    @ResponseBody
    public ServerResponce<User> login(String username, String password, HttpSession session){
        ServerResponce<User> responce = iUserService.login(username,password);
        if(responce.isSuccess()){
            session.setAttribute(Const.CURRENT_USER,responce.getData());
        }
        return responce;
    }

    @RequestMapping(value = "logout.do",method=RequestMethod.GET)
    @ResponseBody
    public ServerResponce<String> logout(HttpSession session){
        session.removeAttribute(Const.CURRENT_USER);
        return ServerResponce.createBySuccess();
    }

    @RequestMapping(value = "register.do",method=RequestMethod.GET)
    @ResponseBody
    public ServerResponce<String> register(User user){
        return iUserService.register(user);
    }
}
