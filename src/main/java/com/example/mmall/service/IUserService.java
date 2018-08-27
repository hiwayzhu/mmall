package com.example.mmall.service;

import com.example.mmall.common.ServerResponce;
import com.example.mmall.pojo.User;

public interface IUserService {

    ServerResponce<User> login(String username, String password);
     ServerResponce<String> register(User user);
     ServerResponce<String> checkValid(String str,String type);
}
