package com.example.mmall.service.impl;

import com.example.mmall.common.Const;
import com.example.mmall.common.ServerResponse;
import com.example.mmall.dao.CartMapper;
import com.example.mmall.pojo.Cart;
import com.example.mmall.service.ICartService;
import com.example.mmall.vo.CartProductVo;
import com.example.mmall.vo.CartVo;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service("iCartService")
public class CartServiceImpl extends ICartService {

    @Autowired
    private CartMapper cartMapper;

    public ServerResponse add(Integer userId,Integer productId,Integer count){
        Cart cart = cartMapper.selectCartByUserIdProductId(userId,productId);
        if(cart == null){
            Cart cartItem = new Cart();
            cartItem.setQuantity(count);
            cartItem.setChecked(Const.Cart.CHECKED);
            cartItem.setProductId(productId);
            cartItem.setUserId(userId);
        }else{
            count = cart.getQuantity() +count;
            cart.setQuantity(count);
            cartMapper.updateByPrimaryKeySelective(cart);
        }

        return null;
    }

   private CartVo getCartVoLimit(Integer userId){
        CartVo cartVo = new CartVo();
       List<Cart> cartList = cartMapper.selectCartByUserId(userId);
       List<CartProductVo> cartProductVoList = Lists.newArrayList();

       BigDecimal cartTotalPrice = new BigDecimal("0");
   }
}
