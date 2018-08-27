package com.example.mmall.service.impl;

import com.example.mmall.common.Const;
import com.example.mmall.common.ServerResponce;
import com.example.mmall.dao.UserMapper;
import com.example.mmall.pojo.User;
import com.example.mmall.service.IUserService;
import com.example.mmall.util.MD5Util;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("iUserService")
public class UserServiceImpl implements IUserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public ServerResponce<User> login(String username, String password) {
        int resultCount  = userMapper.checkUsername(username);
        if(resultCount == 0){
            return ServerResponce.createByErrorMsg("用户名不存在！");
        }

        String md5Password = MD5Util.MD5EncodeUtf8(password);
        User user = userMapper.selectLogin(username,md5Password);
        if(user == null){
            return ServerResponce.createByErrorMsg("密码错误");
        }

        user.setPassword(StringUtils.EMPTY);
        return  ServerResponce.createBySuccess("登陆成功",user);
    }

    public ServerResponce<String> register(User user){
        int resultCount  = userMapper.checkUsername(user.getUsername());
        if(resultCount > 0){
            return ServerResponce.createByErrorMsg("用户名已存在！");
        }

        resultCount = userMapper.checkEmail(user.getEmail());
        if(resultCount > 0){
            return ServerResponce.createByErrorMsg("邮件已存在！");
        }

        user.setRole(Const.Role.ROLE_CUSTOMER);
        //MD5加密 todo

        user.setPassword(MD5Util.MD5EncodeUtf8(user.getPassword()));
        resultCount = userMapper.insert(user);
        if(resultCount == 0){
            return ServerResponce.createByErrorMsg("注册失败");
        }
        return ServerResponce.createBySuccessMsg("注册成功");
    }
}
