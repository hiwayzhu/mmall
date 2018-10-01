package com.example.mmall.service;

import com.example.mmall.common.ServerResponse;
import com.example.mmall.vo.CartVo;

public interface ICartService {

    ServerResponse add(Integer userId, Integer productId, Integer count);
    ServerResponse update(Integer userId, Integer productId, Integer count);
    ServerResponse<CartVo> delectProduct(Integer userId, String productIds);
    ServerResponse<CartVo> list(Integer userId);
    ServerResponse<CartVo> selectOrUnSelect (Integer userId,Integer productId,Integer checked);
    ServerResponse<Integer> getCartProductCount(Integer userId);


    }
