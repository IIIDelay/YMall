package org.ymall.learn.easyExcel.config;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author cheng-qiang
 * @date 2021年10月15日11:05
 */
public class BtJsonResult {

    public static final  String ERROR = "500";

    public static final  String SUCCESS = "200";

    public static final  String NO_FOUND = "404";

    // 定义jackson对象
    private static final ObjectMapper MAPPER = new ObjectMapper();

    // 响应业务状态
    private String status;

    // 响应消息
    private String msg;

    // 响应中的数据
    private Object data;

    @JsonIgnore
    private String ok;	// 不使用

    public static BtJsonResult build(ResponseMessage resultCode, Object data) {
        return new BtJsonResult(resultCode.code(), resultCode.msg(), data);
    }

    public static BtJsonResult build(String status, String msg, Object data) {
        return new BtJsonResult(status, msg, data);
    }

    public static BtJsonResult build(String status, String msg) {
        return new BtJsonResult(status, msg, null);
    }

    public static BtJsonResult build(String status, String msg, Object data, String ok) {
        return new BtJsonResult(status, msg, data, ok);
    }

    public static BtJsonResult ok(Object data) {
        return new BtJsonResult(data);
    }

    public static BtJsonResult ok() {
        return new BtJsonResult(null);
    }

    public static BtJsonResult errorMsg(String msg) {
        return new BtJsonResult("500", msg, null);
    }

    public static BtJsonResult errorMap(Object data) {
        return new BtJsonResult("501", "error", data);
    }

    public static BtJsonResult errorTokenMsg(String msg) {
        return new BtJsonResult("502", msg, null);
    }

    public static BtJsonResult errorException(String msg) {
        return new BtJsonResult("555", msg, null);
    }

    public static BtJsonResult errorUserQQ(String msg) {
        return new BtJsonResult("556", msg, null);
    }

    public BtJsonResult() {

    }

    public BtJsonResult(String status, String msg, Object data) {
        this.status = status;
        this.msg = msg;
        this.data = data;
    }

    public BtJsonResult(String status, String msg, Object data, String ok) {
        this.status = status;
        this.msg = msg;
        this.data = data;
        this.ok = ok;
    }

    public BtJsonResult(Object data) {
        this.status = "200";
        this.msg = "OK";
        this.data = data;
    }

    public Boolean isOK() {
        return this.status == "200";
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getOk() {
        return ok;
    }

    public void setOk(String ok) {
        this.ok = ok;
    }

    @Override
    public String toString() {
        return "BtJsonResult{" +
                "status='" + status + '\'' +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                ", ok='" + ok + '\'' +
                '}';
    }
}
