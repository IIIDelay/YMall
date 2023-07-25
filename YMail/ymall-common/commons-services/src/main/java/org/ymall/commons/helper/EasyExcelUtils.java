/*
 * Copyright (c) 2023. 版权归III_Delay所有
 */

package org.ymall.commons.helper;

import cn.hutool.core.collection.CollUtil;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.alibaba.excel.write.builder.ExcelWriterSheetBuilder;
import org.apache.commons.collections4.CollectionUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * Excel 工具类
 *
 */
public class EasyExcelUtils {
    /**
     * 下载excel
     *
     * @param response 响应
     * @param cls      cls
     * @param fileName 文件名称
     * @param data     数据
     * @throws IOException ioexception
     */
    public static <T>void downloadExcel(HttpServletResponse response, Class cls, String fileName,
                                        List<T> data, List<String> includeColumns) {
        // 如果传入的data数据是空的话就让data成为一个空集合 变成下载导入模板
        if (CollUtil.isEmpty(data)) {
            data = new ArrayList<>();
        }
        try (OutputStream os = response.getOutputStream()) {
            fileName = new String(fileName.getBytes(), StandardCharsets.UTF_8.toString());
            setResponseHeader(response, fileName + ExcelTypeEnum.XLSX.getValue());
            ExcelWriterSheetBuilder sheet = EasyExcel.write(os, cls).sheet("sheet 0");
            if (CollectionUtils.isNotEmpty(includeColumns)) {
                sheet.includeColumnFieldNames(includeColumns);
            }
            sheet.doWrite(data);
        } catch (IOException e) {
            throw new RuntimeException("下载导入模板异常");
        }
    }

    /**
     * downloadExcel
     *
     * @param response response
     * @param cls      cls
     * @param fileName fileName
     * @param data     data
     */
    public static <T>void downloadExcel(HttpServletResponse response, Class cls, String fileName,
                                        List<T> data) {
        downloadExcel(response, cls, fileName, data, null);
    }

    /**
     * 设置响应头
     *
     * @param response 响应
     * @param fileName 文件名称
     */
    public static void setResponseHeader(HttpServletResponse response, String fileName) throws UnsupportedEncodingException {
        response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
        response.setHeader("Connection", "close");
        response.setHeader("Content-Type", "application/octet-stream");
    }

}
