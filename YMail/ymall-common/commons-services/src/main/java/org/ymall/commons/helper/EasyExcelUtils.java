/*
 * Copyright (c) 2023. 版权归III_Delay所有
 */

package org.ymall.commons.helper;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.Assert;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.read.metadata.ReadSheet;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.alibaba.excel.write.builder.ExcelWriterSheetBuilder;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.alibaba.excel.write.metadata.holder.WriteSheetHolder;
import com.alibaba.excel.write.metadata.holder.WriteWorkbookHolder;
import com.alibaba.excel.write.metadata.style.WriteCellStyle;
import com.alibaba.excel.write.metadata.style.WriteFont;
import com.alibaba.excel.write.style.HorizontalCellStyleStrategy;
import com.alibaba.excel.write.style.column.LongestMatchColumnWidthStyleStrategy;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataValidation;
import org.apache.poi.ss.usermodel.DataValidationConstraint;
import org.apache.poi.ss.usermodel.DataValidationHelper;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Name;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.springframework.web.multipart.MultipartFile;
import org.ymall.commons.easyExcel.annotation.ExcelSelected;
import org.ymall.commons.easyExcel.listener.AnalysisEventCustomListener;
import org.ymall.commons.easyExcel.resolve.ExcelSelectedResolve;
import org.ymall.commons.easyExcel.resolve.SelectedSheetWriteHandler;

import javax.servlet.http.HttpServletResponse;
import java.beans.PropertyDescriptor;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Excel 工具类
 */
@Slf4j
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
            ExcelWriterSheetBuilder sheet = EasyExcel.write(os, cls).sheet(0);
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

    /**
     * 字典表key
     **/
    public static final String DICTIONARY_TABLE = "DICTIONARY:TABLE";
    /**
     * 默认起始行
     **/
    public static final Integer HEAD_ROW_NUMBER = 1;
    private static final String DEFAULT_SHEET_NAME = "sheet1";
    public static final String PREFIX = "easyExcel_sendList_";
    public static final String SEND_LIST_ERROR = "_error";
    public static final Long EXPIRE_TIME = 60 * 10L;

    /**
     * 设置表格样式（头是头的样式、内容是内容的样式）
     *
     * @param fillForegroundColorHead    头部背景色 IndexedColors.PINK.getIndex()
     * @param fontHeightInPointsHead     头部字体大小
     * @param fillForegroundColorContent 内容背景色 IndexedColors.LEMON_CHIFFON.getIndex()
     * @param fontHeightInPointsContent  内容字体大小
     * @param borderStyle                边框样式 BorderStyle.DASHED
     * @return com.alibaba.excel.write.style.HorizontalCellStyleStrategy
     * @date 2022/8/16 10:23
     */
    public static HorizontalCellStyleStrategy createTableStyle(Short fillForegroundColorHead,
                                                               int fontHeightInPointsHead,
                                                               Short fillForegroundColorContent,
                                                               int fontHeightInPointsContent,
                                                               BorderStyle borderStyle) {
        WriteCellStyle headWriteCellStyle = new WriteCellStyle();
        headWriteCellStyle.setFillForegroundColor(fillForegroundColorHead);
        WriteFont headWriteFont = new WriteFont();
        headWriteFont.setFontHeightInPoints((short) fontHeightInPointsHead);
        headWriteCellStyle.setWriteFont(headWriteFont);
        WriteCellStyle contentWriteCellStyle = new WriteCellStyle();
        contentWriteCellStyle.setFillPatternType(FillPatternType.SOLID_FOREGROUND);
        contentWriteCellStyle.setFillForegroundColor(fillForegroundColorContent);
        WriteFont contentWriteFont = new WriteFont();
        contentWriteFont.setFontHeightInPoints((short) fontHeightInPointsContent);
        contentWriteCellStyle.setWriteFont(contentWriteFont);
        contentWriteCellStyle.setBorderBottom(borderStyle);
        contentWriteCellStyle.setBorderLeft(borderStyle);
        contentWriteCellStyle.setBorderRight(borderStyle);
        contentWriteCellStyle.setBorderTop(borderStyle);
        return new HorizontalCellStyleStrategy(headWriteCellStyle, contentWriteCellStyle);
    }

    /**
     * 导出单个sheet表格
     *
     * @param response                   HttpServletResponse
     * @param list                       数据列表
     * @param fileName                   文件名称
     * @param sheetName                  sheet名称
     * @param clazz                      数据类型
     * @param fillForegroundColorHead    头部背景色 IndexedColors.PINK.getIndex()
     * @param fontHeightInPointsHead     头部字体大小
     * @param fillForegroundColorContent 内容背景色 IndexedColors.LEMON_CHIFFON.getIndex()
     * @param fontHeightInPointsContent  内容字体大小
     * @param borderStyle                边框样式 BorderStyle.DASHED
     * @date 2022/8/16 10:32
     */
    public static void writeSingleExcel(HttpServletResponse response,
                                        List<?> list,
                                        String fileName,
                                        String sheetName,
                                        Class<?> clazz,
                                        Short fillForegroundColorHead,
                                        int fontHeightInPointsHead,
                                        Short fillForegroundColorContent,
                                        int fontHeightInPointsContent,
                                        BorderStyle borderStyle) {
        try {
            response.setCharacterEncoding("utf8");
            response.setContentType("application/vnd.ms-excel;charset=utf-8");
            response.setHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(fileName + ".xlsx", "UTF-8"));
            response.setHeader("Cache-Control", "no-store");
            response.addHeader("Cache-Control", "max-age=0");
            EasyExcel.write(response.getOutputStream(), clazz)
                .sheet(sheetName).registerWriteHandler(createTableStyle(
                    fillForegroundColorHead,
                    fontHeightInPointsHead,
                    fillForegroundColorContent,
                    fontHeightInPointsContent,
                    borderStyle
                ))
                .doWrite(list);
        } catch (Exception exception) {
            log.error("导出单个sheet表格：", exception);
        }
    }

    /**
     * 导出多个sheet表格
     *
     * @param response                   HttpServletResponse
     * @param listMap                    key 是 sheet名称,value 是 数据列表
     * @param fileName                   文件名称
     * @param classMap                   key是listMap中的key,value是对应的类型
     * @param fillForegroundColorHead    头部背景色 IndexedColors.PINK.getIndex()
     * @param fontHeightInPointsHead     头部字体大小
     * @param fillForegroundColorContent 内容背景色 IndexedColors.LEMON_CHIFFON.getIndex()
     * @param fontHeightInPointsContent  内容字体大小
     * @param borderStyle                边框样式 BorderStyle.DASHED
     * @date 2022/8/16 10:37
     */
    public static void writeMultiExcel(HttpServletResponse response,
                                       Map<String, List<?>> listMap,
                                       String fileName,
                                       Map<String, Class<?>> classMap,
                                       Short fillForegroundColorHead,
                                       int fontHeightInPointsHead,
                                       Short fillForegroundColorContent,
                                       int fontHeightInPointsContent,
                                       BorderStyle borderStyle) {
        try {
            response.setCharacterEncoding("utf8");
            response.setContentType("application/vnd.ms-excel;charset=utf-8");
            response.setHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(fileName + ".xlsx", "UTF-8"));
            response.setHeader("Cache-Control", "no-store");
            response.addHeader("Cache-Control", "max-age=0");
            ExcelWriter writeMultiExcel = EasyExcel.write(response.getOutputStream()).build();
            Set<String> keySet = listMap.keySet();
            int count = 0;
            for (String key : keySet) {
                List<?> list = listMap.get(key);
                WriteSheet writeSheet = EasyExcel.writerSheet(count, key).head(classMap.get(key)).registerWriteHandler(createTableStyle(
                    fillForegroundColorHead,
                    fontHeightInPointsHead,
                    fillForegroundColorContent,
                    fontHeightInPointsContent,
                    borderStyle
                )).build();
                writeMultiExcel.write(list, writeSheet);
                count++;
            }
            writeMultiExcel.finish();
        } catch (Exception ignored) {
        }
    }

    /**
     * 导出Excel文件
     *
     * @param headColumnMap 有序列头部
     * @param dataList      数据体
     * @param response      HttpServletResponse
     * @param fileName      文件名称
     * @date 2022/8/16 15:07
     */
    public static void exportExcelFile(Map<String, String> headColumnMap, List<Map<String, Object>> dataList, HttpServletResponse response, String fileName) {
        try {
            List<List<String>> excelHead = new ArrayList<>();
            if (MapUtils.isNotEmpty(headColumnMap)) {
                headColumnMap.forEach((key, value) -> excelHead.add(org.apache.commons.compress.utils.Lists.newArrayList(Arrays.stream(value.split(",")).iterator())));
            }
            List<List<Object>> excelRows = new ArrayList<>();
            if (MapUtils.isNotEmpty(headColumnMap) && CollectionUtils.isNotEmpty(dataList)) {
                for (Map<String, Object> dataMap : dataList) {
                    List<Object> rows = new ArrayList<>();
                    headColumnMap.forEach((key, value) -> {
                        if (dataMap.containsKey(key)) {
                            Object data = dataMap.get(key);
                            rows.add(data);
                        }
                    });
                    excelRows.add(rows);
                }
            }
            createExcelFile(excelHead, excelRows, response, fileName);
        } catch (Exception ignored) {
        }
    }

    /**
     * 创建Excel文件
     *
     * @param excelHead 表头标题
     * @param excelRows 表行数据
     * @param response  HttpServletResponse
     * @param fileName  文件名称
     * @date 2022/8/16 15:07
     */
    private static void createExcelFile(List<List<String>> excelHead, List<List<Object>> excelRows, HttpServletResponse response, String fileName) {
        try {
            if (CollectionUtils.isNotEmpty(excelHead)) {
                response.setCharacterEncoding("utf8");
                response.setContentType("application/vnd.ms-excel;charset=utf-8");
                response.setHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(fileName + ".xlsx", "UTF-8"));
                response.setHeader("Cache-Control", "no-store");
                response.addHeader("Cache-Control", "max-age=0");
                EasyExcel.write(response.getOutputStream()).registerWriteHandler(new LongestMatchColumnWidthStyleStrategy())
                    .head(excelHead)
                    .sheet(DEFAULT_SHEET_NAME)
                    .doWrite(excelRows);
            }
        } catch (Exception ignored) {
        }
    }

    /**
     * 读取Excel数据--监听器
     *
     * @param multipartFile MultipartFile
     * @return java.util.ArrayList<com.cheng.listener.AnalysisEventCustomListener>
     * @date 2022/8/16 12:00
     */
    public static List<AnalysisEventCustomListener> readEasyExcel(MultipartFile multipartFile) {
        try {
            InputStream inputStream = multipartFile.getInputStream();
            ExcelReader excelReader = EasyExcel.read(inputStream).build();
            List<ReadSheet> sheetList = excelReader.excelExecutor().sheetList();
            ArrayList<AnalysisEventCustomListener> listenerList = new ArrayList<>();
            for (int i = 0; i < sheetList.size(); i++) {
                AnalysisEventCustomListener customListener = new AnalysisEventCustomListener();
                ReadSheet readSheet = EasyExcel.readSheet(i).registerReadListener(customListener).build();
                excelReader.read(readSheet);
                listenerList.add(customListener);
            }
            excelReader.finish();
            return listenerList;
        } catch (Exception ignored) {
        }
        return new ArrayList<>();
    }

    /**
     * 读取Excel数据--数据映射
     *
     * @param multipartFile MultipartFile
     * @param clazz         映射模型
     * @return java.util.List<?>
     * @date 2022/8/16 15:19
     */
    public static List<?> readExcelModel(MultipartFile multipartFile, Class<?> clazz) {
        try {
            return EasyExcel.read(multipartFile.getInputStream()).head(clazz).sheet().doReadSync();
        } catch (Exception ignored) {
        }
        return new ArrayList<>();
    }

    /**
     * 导出单个sheet动态数据选项模板
     *
     * @param head      表格头信息
     * @param sheetNo   sheet下标
     * @param sheetName sheen名称
     * @param fileName  文件名称
     * @param response  HttpServletResponse
     * @date 2022/11/16 15:49
     */
    public static <T> void writeSelectedSheet(Class<T> head,
                                              Integer sheetNo,
                                              String sheetName,
                                              String fileName,
                                              HttpServletResponse response) throws IOException {
        response.setContentType("application/vnd.ms-excel");
        response.setHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(fileName + ".xlsx", "UTF-8"));
        response.setHeader("Cache-Control", "no-cache");
        response.setHeader("Pragma", "no-cache");
        response.setDateHeader("Expires", -1);
        response.setCharacterEncoding("UTF-8");
        ExcelWriter excelWriter = EasyExcel.write(response.getOutputStream()).build();
        Map<Integer, ExcelSelectedResolve> selectedMap = resolveSelectedAnnotation(head);
        WriteSheet writeSheet = EasyExcel.writerSheet(sheetNo, sheetName)
            .head(head)
            .registerWriteHandler(new SelectedSheetWriteHandler(selectedMap))
            .build();
        excelWriter.write(new ArrayList<>(), writeSheet);
        excelWriter.finish();
    }

    /**
     * 导出多个sheet动态数据选项模板
     *
     * @param classMap  头信息
     * @param sheetName sheen名称
     * @param fileName  文件名称
     * @param response  HttpServletResponse
     * @date 2022/11/16 15:49
     */
    public static void writeSelectedSheet(Map<String, Class<?>> classMap,
                                          List<String> sheetName,
                                          String fileName,
                                          HttpServletResponse response) throws IOException {
        response.setContentType("application/vnd.ms-excel");
        response.setHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(fileName + ".xlsx", "UTF-8"));
        response.setHeader("Cache-Control", "no-cache");
        response.setHeader("Pragma", "no-cache");
        response.setDateHeader("Expires", -1);
        response.setCharacterEncoding("UTF-8");
        ExcelWriter excelWriter = EasyExcel.write(response.getOutputStream()).build();
        int count = 0;
        for (String key : sheetName) {
            Map<Integer, ExcelSelectedResolve> selectedMap = resolveSelectedAnnotation(classMap.get(key));
            WriteSheet writeSheet = EasyExcel.writerSheet(count, key)
                .head(classMap.get(key))
                .registerWriteHandler(new SelectedSheetWriteHandler(selectedMap))
                .build();
            excelWriter.write(new ArrayList<>(), writeSheet);
            count++;
        }
        excelWriter.finish();
    }

    /**
     * 解析表头类中的下拉注解
     *
     * @param head 表头类
     * @param <T>  泛型
     * @return Map<下拉框列索引, 下拉框内容> map
     */
    private static <T> Map<Integer, ExcelSelectedResolve> resolveSelectedAnnotation(Class<T> head) {
        Map<Integer, ExcelSelectedResolve> selectedMap = new HashMap<>();
        Field[] fields = head.getDeclaredFields();
        for (int i = 0; i < fields.length; i++) {
            Field field = fields[i];
            ExcelSelected selected = field.getAnnotation(ExcelSelected.class);
            ExcelProperty property = field.getAnnotation(ExcelProperty.class);
            if (selected != null) {
                ExcelSelectedResolve excelSelectedResolve = new ExcelSelectedResolve();
                final Object jsonObject = excelSelectedResolve.resolveSelectedSource(selected);
                if (jsonObject != null) {
                    if (property != null) {
                        excelSelectedResolve.setParent(selected.parent());
                        excelSelectedResolve.setSelf(property.value()[0]);
                        excelSelectedResolve.setSource(jsonObject);
                        excelSelectedResolve.setFirstRow(selected.firstRow());
                        excelSelectedResolve.setLastRow(selected.lastRow());
                        excelSelectedResolve.setClassPath(selected.classPath());
                        int index = property.index();
                        if (index >= 0) {
                            selectedMap.put(index, excelSelectedResolve);
                        } else {
                            index = i;
                            selectedMap.put(index, excelSelectedResolve);
                        }
                        excelSelectedResolve.setRowIndex(index);
                    }
                }
            }
        }
        if (CollUtil.isNotEmpty(selectedMap)) {
            final Map<String, Integer> indexMap = selectedMap.values().stream().collect(Collectors.toMap(ExcelSelectedResolve::getSelf, ExcelSelectedResolve::getRowIndex));
            selectedMap.forEach((k, v) -> {
                if (indexMap.containsKey(v.getParent())) {
                    v.setParentColumnIndex(indexMap.get(v.getParent()));
                }
            });
        }
        return selectedMap;
    }


    /**
     * 二级下拉框字典表映射-（编号,名称,其他）
     *
     * @param writeWorkbookHolder writeWorkbookHolder
     * @param writeSheetHolder    writeSheetHolder
     * @param options             options
     * @param parentColumnIndex   parentColumnIndex
     * @param childColumnIndex    childColumnIndex
     * @param fromRow             fromRow
     * @param endRow              endRow
     */
    public static void addCascadeValidationToSheet(WriteWorkbookHolder writeWorkbookHolder,
                                                   WriteSheetHolder writeSheetHolder,
                                                   Map<String, List<String>> options,
                                                   int parentColumnIndex,
                                                   int childColumnIndex,
                                                   int fromRow,
                                                   int endRow
    ) {
        final Workbook workbook = writeWorkbookHolder.getWorkbook();
        final Sheet sheet = writeSheetHolder.getSheet();
        DataValidationHelper helper = sheet.getDataValidationHelper();
        String hiddenSheetName = "btks_invest";
        Sheet hiddenSheet = workbook.getSheet(hiddenSheetName);
        if (null == hiddenSheet) {
            hiddenSheet = workbook.createSheet(hiddenSheetName);
        }
        int rowIndex = 0;
        for (Map.Entry<String, List<String>> entry : options.entrySet()) {
            String parent = formatName(entry.getKey());
            List<String> children = entry.getValue();
            if (CollUtil.isEmpty(children)) {
                continue;
            }
            int columnIndex = 0;
            Row row = hiddenSheet.createRow(rowIndex++);
            Cell cell;
            for (String child : children) {
                cell = row.createCell(columnIndex++);
                cell.setCellValue(child);
            }
            char lastChildrenColumn = (char) ((int) 'A' + (children.size() == 0 ? 1 : children.size()) - 1);
            String format = String.format(hiddenSheetName + "!$A$%s:$%s$%s", rowIndex, lastChildrenColumn, rowIndex);
            createName(workbook, parent, format);
            final DataValidationConstraint formulaListConstraint = helper.createFormulaListConstraint("INDIRECT($" + (char) ((int) 'A' + parentColumnIndex) + "2)");
            CellRangeAddressList regions = new CellRangeAddressList(fromRow, endRow, childColumnIndex, childColumnIndex);
            final DataValidation validation = helper.createValidation(formulaListConstraint, regions);
            validation.setErrorStyle(DataValidation.ErrorStyle.STOP);
            validation.setShowErrorBox(true);
            validation.setSuppressDropDownArrow(true);
            validation.createErrorBox("友情提示", "请输入下拉选项中的内容");
            sheet.addValidationData(validation);
        }
        workbook.setSheetHidden(workbook.getSheetIndex(hiddenSheetName), true);
    }

    /**
     * 静态下拉框-纯文本
     *
     * @param sheet  Sheet
     * @param helper DataValidationHelper
     * @param k      Integer
     * @param v      ExcelSelectedResolve
     * @date 2022/11/12 17:19
     */
    public static void selectStillness(Sheet sheet, DataValidationHelper helper, Integer k, ExcelSelectedResolve v) {
        CellRangeAddressList rangeList = new CellRangeAddressList(v.getFirstRow(), v.getLastRow(), k, k);
        DataValidationConstraint constraint;
        JSONArray jsonArray = (JSONArray) v.getSource();
        Object[] objects = jsonArray.toArray();
        StringBuilder temp = new StringBuilder();
        for (Object object : objects) {
            temp.append((String) object).append(",");
        }
        String[] arr = temp.toString().split(",");
        constraint = helper.createExplicitListConstraint(arr);
        DataValidation validation = helper.createValidation(constraint, rangeList);
        validation.setErrorStyle(DataValidation.ErrorStyle.STOP);
        validation.setShowErrorBox(true);
        validation.setSuppressDropDownArrow(true);
        validation.createErrorBox("友情提示", "请输入下拉选项中的内容");
        sheet.addValidationData(validation);
    }

    /**
     * 一级下拉框字典表关系映射-（编号,名称,其他）
     *
     * @param writeWorkbookHolder WriteWorkbookHolder
     * @param sheet               Sheet
     * @param helper              DataValidationHelper
     * @param k                   Integer
     * @param v                   ExcelSelectedResolve
     * @param classPath           classPath
     * @date 2022/11/12 17:29
     */
    public static void selectDictionary(WriteWorkbookHolder writeWorkbookHolder, Sheet sheet, DataValidationHelper helper, Integer k, ExcelSelectedResolve v, String classPath) {
        JSONArray jsonArray = (JSONArray) v.getSource();
        Object[] objects = jsonArray.toArray();
        Workbook workbook = writeWorkbookHolder.getWorkbook();
        Sheet hiddenSheet = workbook.getSheet(classPath);
        if (null == hiddenSheet) {
            hiddenSheet = workbook.createSheet(classPath);
        }
        Field[] fields = new Field[0];
        Class<?> aClass = null;
        try {
            aClass = Class.forName(classPath);
            fields = aClass.getDeclaredFields();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        int columnIndex = 0;
        for (Object object : objects) {
            Row row = hiddenSheet.createRow(columnIndex++);
            for (int i = 0; i < fields.length; i++) {
                try {
                    PropertyDescriptor pd = new PropertyDescriptor(fields[i].getName(), aClass);
                    String name = pd.getName();
                    JSONObject jsonObject = (JSONObject) object;
                    Object value = jsonObject.get(name);
                    Cell cell;
                    cell = row.createCell(i);
                    if (value instanceof Integer) {
                        cell.setCellValue(Integer.toString((Integer) value));
                    } else {
                        cell.setCellValue(((String) value));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        String format = String.format(classPath + "!$B$1:$B$%s", objects.length);
        final DataValidationConstraint formulaListConstraint = helper.createFormulaListConstraint(format);
        CellRangeAddressList regions = new CellRangeAddressList(v.getFirstRow(), v.getLastRow(), k, k);
        final DataValidation validation = helper.createValidation(formulaListConstraint, regions);
        validation.setErrorStyle(DataValidation.ErrorStyle.STOP);
        validation.setShowErrorBox(true);
        validation.setSuppressDropDownArrow(true);
        validation.createErrorBox("友情提示", "请输入下拉选项中的内容");
        sheet.addValidationData(validation);
        workbook.setSheetHidden(workbook.getSheetIndex(classPath), true);
    }

    /**
     * 创建名称管理器
     *
     * @param workbook workbook
     * @param nameName nameName
     * @param formula  formula
     * @date 2022/11/11 11:00
     */
    public static void createName(Workbook workbook, String nameName, String formula) {
        Name name = workbook.getName(nameName);
        if (null == name) {
            name = workbook.createName();
            name.setNameName(nameName);
            name.setRefersToFormula(formula);
        }
    }


    /**
     * 格式化名称
     *
     * @param name 名称
     * @return java.lang.String
     * @date 2022/11/11 11:27
     */
    static String formatName(String name) {
        name = name.replaceAll(" ", "").replaceAll("-", "_").replaceAll(":", ".");
        if (Character.isDigit(name.charAt(0))) {
            name = "_" + name;
        }
        return name;
    }

    /**
     * 隐藏sheet表
     *
     * @param workbook workbook
     * @param start    start
     * @date 2022/11/11 11:28
     */
    public static void hideTempDataSheet(Workbook workbook, int start) {
        for (int i = start; i < workbook.getNumberOfSheets(); i++) {
            workbook.setSheetHidden(i, true);
        }
    }

    /**
     * 获取Excel单元格的索引
     *
     * @param obj        JavaBean对象
     * @param fieldValue JavaBean字段值
     */
    public static Integer getCellIndex(Object obj, String fieldValue) {
        try {
            Field declaredField = obj.getClass().getDeclaredField(fieldValue);
            ExcelProperty annotation = declaredField.getAnnotation(ExcelProperty.class);
            if (annotation == null) {
                return null;
            }
            return annotation.index();
        } catch (NoSuchFieldException ignored) {
        }
        return null;
    }

    /**
     * 获取字典表数据模型-用于通用编号名称数据转换
     *
     * @param inputStream excel文件流
     * @return 字典表数据模型
     */
    public static Map<String, Map<String, String>> getDictionaryTableDataMap(InputStream inputStream) {
        try {
            // 获取工作薄
            Workbook workbook = WorkbookFactory.create(inputStream);
            // 获取所有工作表
            List<Sheet> sheets = new ArrayList<>(workbook.getNumberOfSheets());
            Iterator<Sheet> sheetIterator = workbook.sheetIterator();
            while (sheetIterator.hasNext()) {
                Sheet sheet = sheetIterator.next();
                sheets.add(sheet);
            }
            // 获取隐藏的工作表
            List<Sheet> hiddenSheets = new ArrayList<>();
            for (int i = 0; i < sheets.size(); i++) {
                String sheetName = sheets.get(i).getSheetName();
                if ("btks_invest".equals(sheetName)) {
                    continue;
                }
                boolean sheetHidden = workbook.isSheetHidden(i);
                if (sheetHidden) {
                    hiddenSheets.add(sheets.get(i));
                }
            }
            // 获取隐藏工作表（字典表）行数据  k:表名称 M:字典数据
            Map<String, Map<String, String>> hiddenMap = new HashMap<>();
            for (Sheet hiddenSheet : hiddenSheets) {
                // k:名称  v:编号
                Map<String, String> resultMap = new HashMap<>();
                String sheetName = hiddenSheet.getSheetName();
                Iterator<Row> rowIterator = hiddenSheet.rowIterator();
                while (rowIterator.hasNext()) {
                    Row row = rowIterator.next();
                    Iterator<Cell> cellIterator = row.cellIterator();
                    StringBuilder builder = new StringBuilder();
                    while (cellIterator.hasNext()) {
                        Cell cell = cellIterator.next();
                        builder.append(cell.getStringCellValue()).append("_");
                    }
                    String[] split = builder.toString().split("_");
                    resultMap.put(split[1], split[0]);
                }
                hiddenMap.put(sheetName, resultMap);
            }
            return hiddenMap;
        } catch (Exception exception) {
            exception.printStackTrace();
        } finally {
            if (null != inputStream) {
                try {
                    inputStream.close();
                } catch (IOException exception) {
                    exception.printStackTrace();
                }
            }
        }
        return new HashMap<>();
    }

    /**
     * getWeakBook : 获取指定行对象
     *
     * @param file       file
     * @param sheetIndex sheetIndex
     * @param rowIndex   rowIndex
     * @return Row
     */
    public Row getWeakBook(MultipartFile file, int sheetIndex, int rowIndex) {
        if (file == null || file.getSize() <= 0) {
            return null;
        }
        try {
            InputStream inputStream = file.getInputStream();
            Workbook wb = WorkbookFactory.create(inputStream);
            Sheet sheetAt = wb.getSheetAt(sheetIndex);
            Row row = sheetAt.getRow(rowIndex);
            return row;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
