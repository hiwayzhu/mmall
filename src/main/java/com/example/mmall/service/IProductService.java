package com.example.mmall.service;

import com.example.mmall.common.ServerResponse;
import com.example.mmall.pojo.Product;

public interface IProductService {

     ServerResponse saveOrUpdateProduct(Product product);
     ServerResponse<String> setSaleStatus(Integer productId,Integer status);

    }

