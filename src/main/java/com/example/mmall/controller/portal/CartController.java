package com.example.mmall.controller.portal;

import com.example.mmall.common.Const;
import com.example.mmall.common.ResponseCode;
import com.example.mmall.common.ServerResponse;
import com.example.mmall.pojo.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/cart")
public class CartController {

    public ServerResponse add(HttpSession session,Integer count,Integer productId){
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),ResponseCode.NEED_LOGIN.getDesc());
        }

    }
}
