package org.ymall.learn.easyExcel.dto;

import com.alibaba.excel.annotation.ExcelProperty;


import lombok.Data;
import org.ymall.learn.easyExcel.annotation.ImportExcel;
import org.ymall.learn.easyExcel.annotation.NameCodeConverter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * @author cheng-qiang
 * @date 2022年11月17日11:58
 */
@Data
public class UserImport {
    @ExcelProperty(index = 0,value = "编号")
    @NotNull(message = "编号不能为空！")
    private Long id;
    @ExcelProperty(index = 1,value = "姓名")
    @NotEmpty(message = "姓名不能为空！")
    private String name;
    @ExcelProperty(index = 2,value = "年龄")
    private Integer age;
    @ExcelProperty(index = 3,value = "性别")
    private String sex;
    @ExcelProperty(index = 4,value = "出生日期")
    private Date birthday;
    @ExcelProperty(index = 5,value = "居住城市",converter = NameCodeConverter.class)
    @ImportExcel(classPath = "com.cheng.dto.City")
    private String city;
    @ExcelProperty(index = 7,value = "投资分类")
    private String investChild;
    @ExcelProperty(index = 6,value = "投资类别")
    private String investParent;
}
