/*
 * Copyright (c) 2023. 版权归III_Delay所有
 */

package org.iiidev.ymall.result;

import org.iiidev.ymall.common.ResultCodeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 全局统一返回结果类
 */
@Data
@ApiModel(value = "全局统一返回结果")
public class Result<T> {

    @ApiModelProperty(value = "返回码")
    private Integer code;

    @ApiModelProperty(value = "返回消息")
    private String message;

    @ApiModelProperty(value = "返回数据")
    private T data;

    public Result() {
    }

    // 返回数据
    protected static <T> Result<T> build(T data) {
        Result<T> result = new Result<>();
        if (data != null) {
            result.setData(data);
        }
        return result;
    }

    /**
     * build
     *
     * @param body           body
     * @param resultCodeEnum resultCodeEnum
     * @return Result<T>
     */
    public static <T> Result<T> build(T body, ResultCodeEnum resultCodeEnum) {
        Result<T> result = build(body);
        result.setCode(resultCodeEnum.getCode());
        result.setMessage(resultCodeEnum.getMessage());
        return result;
    }

    /**
     * ok
     *
     * @return Result<T>
     */
    public static <T> Result<T> ok() {
        return Result.ok(null);
    }

    /**
     * ok 操作成功
     *
     * @param data data
     * @return Result<T>
     */
    public static <T> Result<T> ok(T data) {
        return build(data, ResultCodeEnum.SUCCESS);
    }

    /**
     * fail
     *
     * @return Result<T>
     */
    public static <T> Result<T> fail() {
        return Result.fail(null);
    }

    /**
     * fail 操作失败
     *
     * @param data data
     * @return Result<T>
     */
    public static <T> Result<T> fail(T data) {
        Result<T> result = build(data);
        return build(data, ResultCodeEnum.FAIL);
    }

    /**
     * message
     *
     * @param msg msg
     * @return Result<T>
     */
    public Result<T> message(String msg) {
        this.setMessage(msg);
        return this;
    }

    public Result<T> code(Integer code) {
        this.setCode(code);
        return this;
    }

    public boolean isOk() {
        if (this.getCode().intValue() == ResultCodeEnum.SUCCESS.getCode().intValue()) {
            return true;
        }
        return false;
    }
}
