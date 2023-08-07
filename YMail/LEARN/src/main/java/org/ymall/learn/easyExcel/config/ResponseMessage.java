package org.ymall.learn.easyExcel.config;

/**
 * @author cheng-qiang
 * @date 2021年10月14日16:51
 */
public enum ResponseMessage {

    /**查询成功 200**/
    SELECT_SUCCEEDED("200","查询成功"),

    /**请选择待操作记录 500**/
    CHOOSE_RECORD("500","请选择待操作记录"),

    /**添加成功 200**/
    INSERT_SUCCEEDED("200", "添加成功"),

    /**添加失败 500**/
    INSERT_ERROR("500", "添加失败"),

    /**编辑成功 200**/
    UPDATE_SUCCEEDED("200", "编辑成功"),

    /**编辑失败 500**/
    UPDATE_ERROR("500", "编辑失败"),

    /**删除成功 200**/
    DELETE_SUCCEEDED("200", "删除成功"),

    /**删除失败 500**/
    DELETE_ERROR("500", "删除失败"),

    /**下载成功 200**/
    DOWNLOAD_SUCCEEDED("200","下载成功"),

    /**下载失败 500**/
    DOWNLOAD_ERROR("500","下载失败");

    private final String code;

    private final String msg;

    ResponseMessage(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String code(){
        return this.code;
    }

    public String msg(){
        return this.msg;
    }
}
