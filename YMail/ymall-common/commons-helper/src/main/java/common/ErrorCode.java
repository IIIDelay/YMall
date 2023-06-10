package common;

import lombok.AllArgsConstructor;

/**
 * ErrorCodeEnum
 *
 * @Author IIIDelay
 * @Date 2023/6/10 16:02
 **/
@AllArgsConstructor
public enum ErrorCode {
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
