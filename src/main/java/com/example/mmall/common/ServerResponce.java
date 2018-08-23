package com.example.mmall.common;

import java.io.Serializable;

public class ServerResponce<T>  implements Serializable {

    private int status;
    private String msg;
    private T data;

    private ServerResponce(int status){
        this.status = status;
    }
    private ServerResponce(int status,T data){
        this.status = status;
        this.data = data;
    }
    private ServerResponce(int status,String msg,T data){
        this.status = status;
        this.msg = msg;
        this.data = data;
    }
    private ServerResponce(int status,String msg){
        this.status = status;
        this.msg = msg;
    }

    public boolean isSuccess(){
        return this.status ==ResponseCode.SUCCESS.getCode();
    }

    public int getStatus(){
        return status;
    }

    public T getData(){
        return data;
    }
    public String getMsg(){
        return msg;
    }

    public static <T> ServerResponce<T> createBySuccess(){
        return new ServerResponce<T>(ResponseCode.SUCCESS.getCode());
    }

    public static <T> ServerResponce<T> createBySuccessMsg(String msg){
        return new ServerResponce<T>(ResponseCode.SUCCESS.getCode(),msg);
    }

    public static <T> ServerResponce<T> createBySuccess(T data){
        return new ServerResponce<T>(ResponseCode.SUCCESS.getCode(),data);
    }

    public static <T> ServerResponce<T> createBySuccessMsg(String msg,T data){
        return new ServerResponce<T>(ResponseCode.SUCCESS.getCode(),msg,data);
    }

    public static <T> ServerResponce<T> createByError(){
        return new ServerResponce<T>(ResponseCode.ERROR.getCode(),ResponseCode.ERROR.getDesc());
    }
    public static <T> ServerResponce<T> createByErrorMsg(String errorMsg){
        return new ServerResponce<T>(ResponseCode.ERROR.getCode(),errorMsg);
    }

}
