package org.ymall.model.common;

import cn.afterturn.easypoi.handler.inter.IExcelDataModel;
import cn.afterturn.easypoi.handler.inter.IExcelModel;

/**
 * EasyPoiEntityBase
 *
 * @Author IIIDelay
 * @Date 2023/7/23 17:35
 **/
public class EasyPoiEntityBase implements IExcelDataModel, IExcelModel {
    private Integer rowNum;

    private String errorMsg;

    @Override
    public Integer getRowNum() {
        return rowNum;
    }

    @Override
    public void setRowNum(Integer rowNum) {
        this.rowNum = rowNum;
    }

    @Override
    public String getErrorMsg() {
        return errorMsg;
    }

    @Override
    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }
}
