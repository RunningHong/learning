package com.hong.springboot.util;

/**
 * 通用web返回类
 */
public class WebResponse<T>{

    private int status;
    private String message;
    private T data;

    public WebResponse() {
    }

    public WebResponse(int status, String message, T data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public static WebResponse success() {
        return new WebResponse(200, "", null);
    }

    public static <T> WebResponse<T> success(T data) {
        return new WebResponse(200, "", data);
    }

    public static <T> WebResponse<T> success(T data, String message) {
        return new WebResponse(200, message, data);
    }

    public static <T> WebResponse<T> success(int status, T data, String message) {
        return new WebResponse(status, message, data);
    }

    public static WebResponse fail() {
        return new WebResponse(500, "", null);
    }

    public static <T> WebResponse<T> fail(String message) {
        return new WebResponse(500, message, null);
    }

    public static <T> WebResponse<T> fail(int status, String message) {
        return new WebResponse(status, message, null);
    }
}