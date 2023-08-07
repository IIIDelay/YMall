package org.ymall.learn.easyExcel.validations;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

/**
 * @author 程强
 * @date 2023/2/10
 * @Description 
 * @Copyright (c) 2023 by 程强, by 新疆兵团勘测设计院（集团）有限公司 All Rights Reserved.
 */
public class TestValidations {

    public static void main(String[] args) throws IOException, InvalidFormatException {
        String filePath = "E:\\coding\\Stick Technology\\cheng-qiang-code-samples\\EasyExcel\\src\\main\\resources\\templates\\用户信息_数据导入.xlsx";
        // 获取工作薄
        Workbook workbook = WorkbookFactory.create(new FileInputStream(filePath));
        // 获取所有工作表
        List<Sheet> sheets = new ArrayList<>(workbook.getNumberOfSheets());
        Iterator<Sheet> sheetIterator = workbook.sheetIterator();
        while (sheetIterator.hasNext()){
            Sheet sheet = sheetIterator.next();
            sheets.add(sheet);
        }
        // 获取隐藏的工作表
        List<Sheet> hiddenSheets = new ArrayList<>();
        for (int i = 0; i < sheets.size(); i++) {
            String sheetName = sheets.get(i).getSheetName();
            if ("btks_invest".equals(sheetName)){
                continue;
            }
            boolean sheetHidden = workbook.isSheetHidden(i);
            if (sheetHidden){
                hiddenSheets.add(sheets.get(i));
            }
        }
        // 获取隐藏工作表（字典表）行数据  k:表名称 M:字典数据
        Map<String,Map<String,String>> hiddenMap = new HashMap<>();
        for (Sheet hiddenSheet : hiddenSheets) {
            // k:名称  v:编号
            Map<String,String> resultMap = new HashMap<>();
            String sheetName = hiddenSheet.getSheetName();
            Iterator<Row> rowIterator = hiddenSheet.rowIterator();
            while (rowIterator.hasNext()){
                Row row = rowIterator.next();
                Iterator<Cell> cellIterator = row.cellIterator();
                StringBuilder builder = new StringBuilder();
                while(cellIterator.hasNext()){
                    Cell cell = cellIterator.next();
                    builder.append(cell.getStringCellValue()).append("_");
                }
                String[] split = builder.toString().split("_");
                resultMap.put(split[1],split[0]);
            }
            hiddenMap.put(sheetName,resultMap);
        }
        System.out.println(hiddenMap);
    }
}
