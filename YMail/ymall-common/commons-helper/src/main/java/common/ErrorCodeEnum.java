/*
 * Copyright (c) 2023. 版权归III_Delay所有
 */

package common;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * ErrorCodeEnum
 *
 * @Author IIIDelay
 * @Date 2023/6/10 16:02
 **/
@AllArgsConstructor
@Getter
public enum ErrorCodeEnum {
    Common_Param_Error(-1, "EP_000001", "参数校验异常", "Failed to valid parameter.");

    /**
     * statusCode
     * -1: 异常, 0: 无变化, 1: 正常
     */
    private int statusCode;

    /**
     * StandErrCode: 指定异常码
     */
    private String StandErrCode;

    /**
     * descErr: 中文一场描述
     */
    private String descErr;

    /**
     * ensDescErr: 国际化异常描述
     */
    private String ensDescErr;
}
