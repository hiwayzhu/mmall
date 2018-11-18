package com.example.mmall.controller.portal;

import com.example.mmall.common.Const;
import com.example.mmall.common.ResponseCode;
import com.example.mmall.common.ServerResponse;
import com.example.mmall.pojo.User;
import com.example.mmall.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/usr/")
public class UserSpringSessionController {

    @Autowired
    private IUserService iUserService;
    /**
     * 用户登录
     * @param username
     * @param password
     * @param session
     * @return
     */
    @RequestMapping(value = "login.do",method=RequestMethod.POST)
    @ResponseBody
    public ServerResponse<User> login(String username, String password, HttpSession session){
        ServerResponse<User> responce = iUserService.login(username,password);
        if(responce.isSuccess()){
            session.setAttribute(Const.CURRENT_USER,responce.getData());
        }
        return responce;
    }

    @RequestMapping(value = "logout.do",method=RequestMethod.POST)
    @ResponseBody
    public ServerResponse<String> logout(HttpSession session){
        session.removeAttribute(Const.CURRENT_USER);
        return ServerResponse.createBySuccess();
    }

    @RequestMapping(value = "register.do",method=RequestMethod.POST)
    @ResponseBody
    public ServerResponse<String> register(User user){
        return iUserService.register(user);
    }

    @RequestMapping(value = "check_valid.do",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<String> checkValid(String str,String type){
        return iUserService.checkValid(str,type);
    }

    @RequestMapping(value = "get_user_info.do",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<User> getUserInfo(HttpSession session){
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if(user !=null){
            return ServerResponse.createBySuccess(user);
        }
        return ServerResponse.createByErrorMsg("用户未登录，无法获取当前信息");
    }


    @RequestMapping(value = "forgetGetQuestion.do",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<String> forgetGetQuestion(String username){
        return iUserService.selectQuestion(username);
    }


    @RequestMapping(value = "forgetCheckAnswer.do",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<String> forgetCheckAnswer(String username,String question,String answer){
        return  iUserService.checkAnswer(username,question,answer);
    }

    @RequestMapping(value = "forgetResetPassword.do",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<String> forgetResetPassword(String username,String newPassword,String forgetToken){
        return  iUserService.forgetResetPassword(username,newPassword,forgetToken);
    }

    @RequestMapping(value = "resetPassword.do",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<String> resetPassword(HttpSession session,String passwordOld,String passwordNew){
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if(user ==null){
            return ServerResponse.createByErrorMsg("用户未登录");
        }

        return iUserService.resetPassword(passwordOld,passwordNew,user);
    }

    @RequestMapping(value = "updateInformation.do",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<User> updateInformation(HttpSession session,User user){
         User currentUser = (User) session.getAttribute(Const.CURRENT_USER);
         if(currentUser ==null){
             return ServerResponse.createByErrorMsg("用户未登录");
         }

         user.setId(currentUser.getId());
         user.setUsername(currentUser.getUsername());
         ServerResponse<User> response = iUserService.updateInformation(user);
         if(response.isSuccess()){
             session.setAttribute(Const.CURRENT_USER,response.getData());
         }
         return response;
    }

    @RequestMapping(value = "getInformation.do",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<User> get_information(HttpSession session){
        User currentUser = (User)session.getAttribute(Const.CURRENT_USER);
        if(currentUser == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"未登录,需要强制登录status=10");
        }
        return iUserService.getInformation(currentUser.getId());
    }
}