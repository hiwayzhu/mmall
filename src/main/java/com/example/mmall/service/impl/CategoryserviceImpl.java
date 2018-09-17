package com.example.mmall.service.impl;

import com.example.mmall.common.ServerResponse;
import com.example.mmall.dao.CategoryMapper;
import com.example.mmall.pojo.Category;
import com.example.mmall.service.ICategoryService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("iCategoryService")
public class CategoryserviceImpl implements ICategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    public ServerResponse addCategory(String categoryName,Integer parentId){
        if(parentId == null||StringUtils.isBlank(categoryName)){
            return ServerResponse.createByErrorMsg("添加品类参数错误");
        }

        Category category = new Category();
        category.setName(categoryName);
        category.setParentId(parentId);
        category.setStatus(true);//这个分类是可用的

        int resultCount = categoryMapper.insert(category);
        if(resultCount>0){
            return ServerResponse.createBySuccessMsg("添加品类成功");
        }

        return ServerResponse.createByErrorMsg("添加品类失败");
    }
}
