package com.example.mmall.service.impl;

import com.example.mmall.common.ServerResponse;
import com.example.mmall.dao.CategoryMapper;
import com.example.mmall.pojo.Category;
import com.example.mmall.service.ICategoryService;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.mysql.fabric.Server;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Collection;
import java.util.List;
import java.util.Set;

@Service("iCategoryService")
public class CategoryserviceImpl implements ICategoryService {

    private Logger logger = LoggerFactory.getLogger(CategoryserviceImpl.class);

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

    public ServerResponse updateCategoryName(Integer categoryId,String categoryName){
        if(categoryId == null||StringUtils.isBlank(categoryName)){
            return ServerResponse.createByErrorMsg("更新品类参数错误");
        }
        Category category = new Category();
        category.setId(categoryId);
        category.setName(categoryName);

        int rowCount = categoryMapper.updateByPrimaryKeySelective(category);
        if(rowCount>0){
            return ServerResponse.createBySuccessMsg("更新品类名字成功");
        }
        return ServerResponse.createByErrorMsg("更新品类名字失败");
    }

    public ServerResponse<List<Category>> getChildrenParallelCategory(Integer categoryId){
        List<Category> list = categoryMapper.selectCategoryChildrenByParentId(categoryId);
        if(CollectionUtils.isEmpty(list)){
            logger.info("未找到当前分类的子分类");
        }
        return ServerResponse.createBySuccess(list);
    }

    public ServerResponse selectCategoryAndChildrenById(Integer categoryId){
        Set<Category> categorySet = Sets.newHashSet();
        findChildrenCategory(categorySet,categoryId);
        List<Integer> categoryIdList = Lists.newArrayList();
        if(categoryId!==null){
            for(Category categoryItem:categorySet){
                categoryIdList.add(categoryItem.getId());
            }
        }
        return  ServerResponse.createBySuccess(categoryIdList);
    }

    //递归方法，算出子节点
    private Set<Category> findChildrenCategory(Set<Category> categorySet,Integer categoryId){
        Category category = categoryMapper.selectByPrimaryKey(categoryId);
        if(category !=null){
            categorySet.add(category);
        }

        List<Category> list = categoryMapper.selectCategoryChildrenByParentId(categoryId);
        for(Category categoryItem:list){
            findChildrenCategory(categorySet,categoryItem.getId());
        }

        return categorySet;
    }

}
