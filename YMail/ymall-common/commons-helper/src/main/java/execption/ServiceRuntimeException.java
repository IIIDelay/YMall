package execption;

import common.ErrorCodeEnum;
import common.ResultCodeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 自定义全局异常类
 */
@Data
@ApiModel(value = "自定义全局异常类")
@NoArgsConstructor
public class ServiceRuntimeException extends RuntimeException {

    private static final long serialVersionUID = -4966798461568039741L;

    @ApiModelProperty(value = "异常状态码")
    private Integer code;

    /**
     * 异常消息
     */
    private String message;

    /**
     * errorCodeEnum
     */
    private ErrorCodeEnum errorCodeEnum;

    /**
     * 通过状态码和错误消息创建异常对象
     *
     * @param message
     * @param code
     */
    public ServiceRuntimeException(String message, Integer code) {
        super(message);
        this.code = code;
    }

    /**
     * 接收枚举类型对象
     *
     * @param resultCodeEnum
     */
    public ServiceRuntimeException(ResultCodeEnum resultCodeEnum) {
        super(resultCodeEnum.getMessage());
        this.code = resultCodeEnum.getCode();
    }

    /**
     * 接收枚举类型对象
     *
     * @param resultCodeEnum
     */
    public ServiceRuntimeException(ResultCodeEnum resultCodeEnum, String errMsg) {
        super(resultCodeEnum.getMessage());
        this.code = resultCodeEnum.getCode();
        this.message = errMsg;
    }


    /**
     * of
     *
     * @return ServiceRuntimeException
     */
    public static ServiceRuntimeException of() {
        return build(-1, "接口调用异常!", null);
    }

    /**
     * of
     *
     * @return ServiceRuntimeException
     */
    public static ServiceRuntimeException of(String message) {
        return build(-1, message, null);
    }

    /**
     * of
     *
     * @param errorCode errorCode
     * @return ServiceRuntimeException
     */
    public static ServiceRuntimeException of(ErrorCodeEnum errorCode) {
        return build(-1, errorCode.getDescErr(), errorCode);
    }

    private static ServiceRuntimeException build(int code, String msg, ErrorCodeEnum errorCode) {
        ServiceRuntimeException sre = new ServiceRuntimeException();
        sre.setCode(code);
        sre.setErrorCodeEnum(errorCode);
        sre.setMessage(msg);
        return sre;
    }
}
