/*
 * Copyright (c) 2023. 版权归III_Delay所有
 */

package org.iiidev.ymall.result;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;
import java.util.Optional;

@Data
@NoArgsConstructor
@ApiModel(value = "全局统一返回结果")
public class ServiceResponse<OUT> {

    @ApiModelProperty(value = "返回码")
    private Integer code;

    @ApiModelProperty(value = "返回消息")
    private String message;

    @ApiModelProperty(value = "返回数据")
    private OUT data;

    protected static <OUT> ServiceResponse<OUT> build(OUT data) {
        ServiceResponse<OUT> ServiceResponse = new ServiceResponse<>();
        Optional.ofNullable(data).ifPresent(ServiceResponse::setData);
        return ServiceResponse;
    }

    public static <OUT> ServiceResponse<OUT> build(OUT body, RespCodeEnum respCodeEnum) {
        ServiceResponse<OUT> ServiceResponse = build(body);
        ServiceResponse.setCode(respCodeEnum.code);
        ServiceResponse.setMessage(respCodeEnum.message);
        return ServiceResponse;
    }

    public static<OUT> ServiceResponse<OUT> ok(){
        return ServiceResponse.ok(null);
    }

    public static<OUT> ServiceResponse<OUT> ok(OUT data){
        return build(data, RespCodeEnum.SUCCESS);
    }

    public static<OUT> ServiceResponse<OUT> fail(){
        return ServiceResponse.fail(null);
    }

    public static<OUT> ServiceResponse<OUT> fail(OUT data){
        return build(data, RespCodeEnum.FAIL);
    }

    public ServiceResponse<OUT> message(String msg){
        this.setMessage(msg);
        return this;
    }

    public ServiceResponse<OUT> code(Integer code){
        this.setCode(code);
        return this;
    }

    public boolean isOk() {
        if (Objects.equals(this.getCode(), RespCodeEnum.SUCCESS)) {
            return true;
        }
        return false;
    }
}
