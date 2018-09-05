package com.example.mmall.service.impl;

import com.example.mmall.common.Const;
import com.example.mmall.common.ServerResponce;
import com.example.mmall.common.TokenCache;
import com.example.mmall.dao.UserMapper;
import com.example.mmall.pojo.User;
import com.example.mmall.service.IUserService;
import com.example.mmall.util.MD5Util;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

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
        ServerResponce validResponse = this.checkValid(user.getUsername(),Const.USERNAME);
        if(!validResponse.isSuccess()){
            return validResponse;
        }
        validResponse = this.checkValid(user.getEmail(),Const.EMAIL);
        if(!validResponse.isSuccess()){
            return validResponse;
        }

        user.setRole(Const.Role.ROLE_CUSTOMER);
        //MD5加密 todo

        user.setPassword(MD5Util.MD5EncodeUtf8(user.getPassword()));
        int resultCount = userMapper.insert(user);
        if(resultCount == 0){
            return ServerResponce.createByErrorMsg("注册失败");
        }
        return ServerResponce.createBySuccessMsg("注册成功");
    }

    public ServerResponce<String> checkValid(String str,String type){
        if(StringUtils.isNotBlank(type)){
            if(Const.USERNAME.equals(type)){
                int resultCount = userMapper.checkUsername(str);
                if(resultCount>0){
                    return ServerResponce.createByErrorMsg("用户名已存在");
                }
            }
            if(Const.EMAIL.equals(type)){
                int resultCount = userMapper.checkEmail(str);
                if(resultCount>0){
                    return ServerResponce.createByErrorMsg("email已存在");
                }
            }
        }else{
            return ServerResponce.createByErrorMsg("参数错误");
        }
        return ServerResponce.createBySuccessMsg("校验成功");
    }

    public ServerResponce selectQuestion(String username){
        ServerResponce validResponse = this.checkValid(username,Const.USERNAME);
        if(validResponse.isSuccess()){
            return ServerResponce.createByErrorMsg("用户不存在");
        }
        String question = userMapper.selectQuestionByUsername(username);
        if(StringUtils.isNotBlank(question)){
            return ServerResponce.createBySuccess(question);
        }

        return ServerResponce.createByErrorMsg("找回密码的问题是空的");
    }

    public ServerResponce<String> checkAnswer(String username,String question,String answer){
        int resultCount = userMapper.checkAnswer(username,question,answer);
        if(resultCount>0){
            String forgetToken = UUID.randomUUID().toString();
            TokenCache.setKey("token_"+username,forgetToken);
            return  ServerResponce.createBySuccess(forgetToken);
        }
        return ServerResponce.createByErrorMsg("问题答案错误！");
    }
}
