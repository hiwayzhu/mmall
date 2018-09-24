package com.example.mmall.service;

import com.example.mmall.common.ServerResponse;
import com.example.mmall.pojo.Product;
import com.example.mmall.vo.ProductDetailVo;
import com.github.pagehelper.PageInfo;

public interface IProductService {

     ServerResponse saveOrUpdateProduct(Product product);
     ServerResponse<String> setSaleStatus(Integer productId,Integer status);
     ServerResponse<ProductDetailVo> managerProductDetail(Integer productId);
    ServerResponse getProductList(int pageNum,int pageSize);
    ServerResponse<PageInfo> searchProduct(String productName, Integer productId, int pageNum, int pageSize);


    }

