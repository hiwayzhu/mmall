package com.example.mmall.controller.backend;

import com.example.mmall.common.Const;
import com.example.mmall.common.ServerResponse;
import com.example.mmall.pojo.User;
import com.example.mmall.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/manager/user")
public class UserManagerController {

    @Autowired
    private IUserService iUserService;

    @RequestMapping(value = "login.do",method = RequestMethod.POST)
    public ServerResponse<User> login(String username,String password,HttpSession session){
        ServerResponse<User> response = iUserService.login(username,password);
        if(response.isSuccess()){
            User user = response.getData();
            if(user.getRole() == Const.Role.ROLE_ADMIN){
                session.setAttribute(Const.CURRENT_USER,user);
                return ServerResponse.createBySuccess(user);
            }else{
                return ServerResponse.createByErrorMsg("不是管理员，无法登陆");
            }
        }
        return response;
    }
}
