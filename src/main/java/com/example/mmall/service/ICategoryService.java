package com.example.mmall.service;

import com.example.mmall.common.ServerResponse;
import com.example.mmall.pojo.Category;

import java.util.List;
import java.util.Set;

public interface ICategoryService {

    ServerResponse addCategory(String categoryName, Integer parentId);
    ServerResponse updateCategoryName(Integer categoryId,String categoryName);
    ServerResponse<List<Category>> getChildrenParallelCategory(Integer categoryId);
    ServerResponse<List<Integer>>selectCategoryAndChildrenById(Integer categoryId);



    }
