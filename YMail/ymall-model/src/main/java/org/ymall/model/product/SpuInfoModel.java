package org.ymall.model.product;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.handler.inter.IExcelDataModel;
import cn.afterturn.easypoi.handler.inter.IExcelModel;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * SpuInfoModel
 *
 * @Author IIIDelay
 * @Date 2023/6/2 22:29
 **/
@Data
public class SpuInfoModel implements IExcelModel, IExcelDataModel {

    // 商品名称
    @Excel(name = "商品名称")
    @NotBlank(message = "商品名称不能为空")
    private String spuName;

    // 商品描述(后台简述）
    @Excel(name = "商品描述(后台简述）不能为空")
    @NotBlank(message = "商品描述(后台简述）")
    private String description;

    // 创建时间
    @Excel(name = "创建时间")
    @NotNull(message = "创建时间不能为Null")
    private LocalDateTime createTime;

    @Excel(name = "修改时间")
    private LocalDateTime updateTime;

    private String errorMsg;

    private Integer rowNum;


    @Override
    public String getErrorMsg() {
        return errorMsg;
    }

    @Override
    public void setErrorMsg(String s) {
        this.errorMsg = s;
    }

    @Override
    public Integer getRowNum() {
        return rowNum;
    }

    @Override
    public void setRowNum(Integer integer) {
        this.rowNum = integer;
    }
}

