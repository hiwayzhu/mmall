package com.example.mmall.util;

import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CookieUtil {

    private final static String COOKIE_DOMAIN = ".happymall.com";
    private final static String COOKIE_NAME = "mall_login_token";

    public static String readLoginToken(HttpServletRequest request){
        Cookie[] cks = request.getCookies();
        if(cks != null){
            for(Cookie ck:cks){
                if(StringUtils.equals(ck.getName(),COOKIE_NAME)){
                    return ck.getValue();
                }
            }
        }
        return null;
    }

    public static void writeLoginToken(HttpServletResponse response,String token){
        Cookie ck = new Cookie(COOKIE_NAME,token);
        ck.setDomain(COOKIE_DOMAIN);
        ck.setPath("/");
        ck.setMaxAge(60*60*24*365);//如果是-1，代表永久，单位是秒。默认写在内存，只在当前页面有效。
        response.addCookie(ck);
    }

    public static void delLoginToken (HttpServletRequest request,HttpServletResponse response){
        Cookie[] cks = request.getCookies();
        if(cks != null){
            for(Cookie ck:cks){
                if(StringUtils.equals(ck.getName(),COOKIE_NAME)){
                    ck.setDomain(COOKIE_DOMAIN);
                    ck.setPath("/");
                    ck.setMaxAge(0);//设置为0，则删除Cookie
                    response.addCookie(ck);
                    return;
                }
            }
        }
    }
}
