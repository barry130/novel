package com.canace.novel.core.common.resp;

import com.canace.novel.core.common.constant.ErrorCodeEnum;
import lombok.Getter;

import java.util.Objects;

/**
 * @author canace
 * @version 1.0
 * @description Http Rest 响应工具及数据格式封装
 * @date 2023/11/25 16:17
 */

@Getter
public class RestResp<T> {

    //响应码
    private final String code;

    //响应信息
    private final String message;

    //响应数据
    private T data;

    /**
     * 业务处理成功，无数据返回
     */
    private RestResp(){
        this.code = ErrorCodeEnum.OK.getCode();
        this.message = ErrorCodeEnum.OK.getMessage();
    }

    /**
     * 业务处理失败，返回code和message
     * @param errorCode 错误
     */
    private RestResp(ErrorCodeEnum errorCode){
        this.code = errorCode.getCode();
        this.message = errorCode.getMessage();
    }

    /**
     * 业务处理成功，有数据返回
     * @param data 返回数据
     */
    private RestResp(T data){
        this.code = ErrorCodeEnum.OK.getCode();
        this.message = ErrorCodeEnum.OK.getMessage();
        this.data = data;
    }

    /**
     * 业务处理成功,无数据返回
     */
    public static RestResp<Void> ok() {
        return new RestResp<>();
    }

    /**
     * 业务处理成功，有数据返回
     */
    public static <T> RestResp<T> ok(T data) {
        return new RestResp<>(data);
    }

    /**
     * 业务处理失败
     */
    public static RestResp<Void> fail(ErrorCodeEnum errorCode) {
        return new RestResp<>(errorCode);
    }

    /**
     * 业务处理失败(数据获取失败)
     */
    public static <T> RestResp<T> fail() {
        return new RestResp<>(ErrorCodeEnum.SYSTEM_DATA_ERROR);
    }

    /**
     * 系统错误(通用)
     */
    public static <T> RestResp<T> error() {
        return new RestResp<>(ErrorCodeEnum.SYSTEM_ERROR);
    }

    /**
     * 判断是否成功
     */
    public boolean isOk() {
        return Objects.equals(this.code, ErrorCodeEnum.OK.getCode());
    }
}
