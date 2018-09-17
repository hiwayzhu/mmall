package com.example.mmall.service;

import com.example.mmall.common.ServerResponse;

public interface ICategoryService {

    ServerResponse addCategory(String categoryName, Integer parentId);

    }
