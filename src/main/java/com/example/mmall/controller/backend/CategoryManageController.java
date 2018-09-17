package com.example.mmall.controller.backend;

import com.example.mmall.common.Const;
import com.example.mmall.common.ResponseCode;
import com.example.mmall.common.ServerResponse;
import com.example.mmall.pojo.User;
import com.example.mmall.service.ICategoryService;
import com.example.mmall.service.IUserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("manager/category")
public class CategoryManageController {

    @Resource
    private IUserService iUserService;
    @Resource
    private ICategoryService iCategoryService;

    @RequestMapping("addCategory.do")
    @ResponseBody
    public ServerResponse addCategory(HttpSession session, String categoryName, @RequestParam(value="parentId",defaultValue = "0") int parentId){
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"用户未登录，请登录");
        }
        //校验一下是否是管理员
        if(iUserService.checkAdminRole(user).isSuccess()){
            //是管理员
            return iCategoryService.addCategory(categoryName,parentId);
        }else{
            return ServerResponse.createByErrorMsg("无权限操作，需要管理员权限！");
        }
    }


}