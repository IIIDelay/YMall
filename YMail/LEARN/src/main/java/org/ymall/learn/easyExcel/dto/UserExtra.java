package org.ymall.learn.easyExcel.dto;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.alibaba.excel.annotation.write.style.ContentRowHeight;
import com.alibaba.excel.annotation.write.style.HeadRowHeight;
import com.cheng.annotation.CityExcelSelectedImpl;
import com.cheng.annotation.ExcelSelected;
import com.cheng.annotation.InvestChildImpl;
import com.cheng.annotation.InvestParentImpl;
import lombok.Data;

import java.util.Date;

/**
 * @author cheng-qiang
 * @date 2022年11月09日15:36
 */
@Data
@ContentRowHeight(30)
@HeadRowHeight(20)
@ColumnWidth(35)
public class UserExtra {
    @ExcelProperty(index = 0,value = "编号")
    private Long id;
    @ExcelProperty(index = 1,value = "姓名")
    private String name;
    @ExcelProperty(index = 2,value = "年龄")
    private Integer age;
    @ExcelProperty(index = 3,value = "性别")
    @ExcelSelected(source = "[\"男\",\"女\"]")
    private String sex;
    @ExcelProperty(index = 4,value = "出生日期")
    private Date birthday;
    @ExcelProperty(index = 5,value = "居住城市")
    @ExcelSelected(sourceClass = CityExcelSelectedImpl.class,classPath = "com.cheng.dto.City")
    private String city;
    @ExcelSelected(parent = "投资类别",sourceClass = InvestChildImpl.class)
    @ExcelProperty(index = 7,value = "投资分类")
    private String investChild;
    @ExcelSelected(sourceClass = InvestParentImpl.class,classPath = "com.cheng.dto.Invest")
    @ExcelProperty(index = 6,value = "投资类别")
    private String investParent;
    @ExcelIgnore
    private Boolean isDeleted;
}
