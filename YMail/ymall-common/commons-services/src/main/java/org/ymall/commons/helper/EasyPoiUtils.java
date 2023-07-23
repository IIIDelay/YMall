package org.ymall.commons.helper;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Workbook;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.nio.charset.StandardCharsets;
import java.util.Collection;

/**
 * EasyPoiUtils
 *
 * @Author IIIDelay
 * @Date 2023/7/23 18:08
 **/
@Slf4j
public class EasyPoiUtils {
    /**
     * exportData: 普通导出excel
     *
     * @param response  response
     * @param list      list
     * @param clazz     clazz
     * @param fileName  fileName
     * @param sheetName sheetName
     * @param title     title
     */
    public static <OUT> void exportData(HttpServletResponse response, Collection<OUT> list,
                                        Class clazz, String fileName, String sheetName, String title) {
        try {
            response.setHeader("content-Type", "application/vnd.ms-excel");
            fileName = new String(fileName.getBytes(), StandardCharsets.UTF_8.toString());
            // 设置下载名称
            response.setHeader("Content-Disposition", "attachment;filename=" + fileName + ".xlsx");
            // 字节流输出
            ServletOutputStream out = response.getOutputStream();
            // 设置excel参数
            ExportParams params = new ExportParams();
            // 设置sheet名
            params.setSheetName(sheetName);
            // 设置标题
            params.setTitle(title);
            // 导入excel
            Workbook workbook = ExcelExportUtil.exportExcel(params, clazz, list);
            // 写入
            workbook.write(out);
        } catch (Exception e) {
            log.error(StrUtil.format("导出excel: {}失败", fileName), e);
        }
    }
}
