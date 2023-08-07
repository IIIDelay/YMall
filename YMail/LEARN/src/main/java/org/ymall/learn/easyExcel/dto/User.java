package org.ymall.learn.easyExcel.dto;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.alibaba.excel.annotation.write.style.ContentRowHeight;
import com.alibaba.excel.annotation.write.style.HeadRowHeight;
import lombok.Data;

import java.io.Serializable;

/**
 * 用户信息
 * @author cheng-qiang
 * @date 2022年08月16日11:03
 */
@Data
@ContentRowHeight(30)
@HeadRowHeight(20)
@ColumnWidth(35)
public class User implements Serializable {
    @ColumnWidth(50)
    @ExcelProperty("真实姓名")
    private String realName;
    @ColumnWidth(50)
    @ExcelProperty("住宅地址")
    private String address;
}
