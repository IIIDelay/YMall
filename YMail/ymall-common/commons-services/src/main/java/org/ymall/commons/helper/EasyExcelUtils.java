/*
 * Copyright (c) 2023. 版权归III_Delay所有
 */

package org.ymall.commons.helper;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.Assert;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.alibaba.excel.write.builder.ExcelWriterSheetBuilder;
import com.alibaba.excel.write.metadata.style.WriteCellStyle;
import com.alibaba.excel.write.metadata.style.WriteFont;
import com.alibaba.excel.write.style.HorizontalCellStyleStrategy;
import com.google.common.collect.Lists;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Excel 工具类
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
    public static <T> void downloadExcel(HttpServletResponse response, Class cls, String fileName,
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
    public static <T> void downloadExcel(HttpServletResponse response, Class cls, String fileName,
                                         List<T> data) {
        downloadExcel(response, cls, fileName, data, null);
    }

    /**
     * importDefault
     *
     * @param file      file
     * @param headClazz headClazz
     * @return List<OUT>
     */
    public static <OUT> List<OUT> importDefault(MultipartFile file, Class<OUT> headClazz) {
        try {
            if (file == null || file.getSize() <= 0) {
                return Collections.emptyList();
            }
            checkType(file.getName());
            return EasyExcel.read(file.getInputStream(), headClazz, null)
                .autoCloseStream(false)
                .doReadAllSync().stream()
                .filter(in -> in.getClass().isAssignableFrom(headClazz))
                .map(in -> (OUT) in)
                .collect(Collectors.toList());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
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

    private static void checkType(String fileName) {
        List<String> excelTypes = Lists.newArrayList(ExcelTypeEnum.XLSX.getValue(), ExcelTypeEnum.XLS.getValue());
        boolean contains = excelTypes.contains(FilenameUtils.getExtension(fileName));
        Assert.isFalse(contains, () -> new RuntimeException("导入文件类型非excel类型"));
    }

    /**
     * 设置表格内容居中显示策略
     */
    public static HorizontalCellStyleStrategy buildStyleStrategy() {
        // 这里需要设置不关闭流
        WriteCellStyle headWriteCellStyle = new WriteCellStyle();
        // 设置背景颜色
        headWriteCellStyle.setFillForegroundColor(IndexedColors.SKY_BLUE.getIndex());
        // 设置头字体
        WriteFont headWriteFont = new WriteFont();
        headWriteFont.setFontHeightInPoints((short) 15);
        headWriteFont.setBold(true);
        headWriteCellStyle.setWriteFont(headWriteFont);
        // 设置头居中
        headWriteCellStyle.setHorizontalAlignment(HorizontalAlignment.CENTER);

        // 内容策略
        WriteCellStyle contentWriteCellStyle = new WriteCellStyle();
        WriteFont headWriteFont2 = new WriteFont();
        headWriteFont2.setFontHeightInPoints((short) 13);
        // 设置 水平居中
        contentWriteCellStyle.setHorizontalAlignment(HorizontalAlignment.CENTER);
        // 垂直居中
        contentWriteCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        contentWriteCellStyle.setBorderLeft(BorderStyle.THIN);
        contentWriteCellStyle.setBorderRight(BorderStyle.THIN);
        contentWriteCellStyle.setBorderTop(BorderStyle.THIN);
        contentWriteCellStyle.setBorderBottom(BorderStyle.THIN);
        return new HorizontalCellStyleStrategy(headWriteCellStyle, contentWriteCellStyle);
    }
}
