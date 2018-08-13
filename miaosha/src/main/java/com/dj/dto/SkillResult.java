package com.dj.dto;

import java.util.List;

/**
 * 所有ajax的请求的返回类型，封装json结果
 * @param <T>
 */
public class SkillResult<T> {

    //判断Ajax请求是否成功
    private boolean success;

    //存放的数据
    private T data;

    //存放的List数据
    private List<T> listData;

    //错误的信息
    private String error;

    public SkillResult(boolean success, T data) {
        this.success = success;
        this.data = data;
    }

    public SkillResult(boolean success, String error) {
        this.success = success;
        this.error = error;
    }

    public SkillResult(boolean success, List<T> listData) {
        this.success = success;
        this.listData = listData;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public List<T> getListData() {
        return listData;
    }

    public void setListData(List<T> listData) {
        this.listData = listData;
    }
}
