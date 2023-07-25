/*
 * Copyright (c) 2023. 版权归III_Delay所有
 */

package org.ymall.commons.helper;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import cn.afterturn.easypoi.excel.entity.result.ExcelImportResult;
import cn.hutool.core.util.StrUtil;
import common.ResultCodeEnum;
import execption.ServiceRuntimeException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.entity.ContentType;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.multipart.MultipartFile;
import org.ymall.commons.pojo.EasyPoiEntityBase;
import utils.OptionWrapper;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.List;

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

    /**
     * importDataDefault : easyPOI默认导入
     *
     * @param inClass      inClass
     * @param file         file
     * @param importFields importFields
     * @return List<IN>
     */
    public <IN> List<IN> importDataDefault(Class<IN> inClass, MultipartFile file, String[] importFields) {
        try {
            ImportParams importParams = new ImportParams();
            // 标题
            importParams.setTitleRows(1);
            // 表头
            importParams.setHeadRows(1);
            // 从哪个sheet开始读
            importParams.setStartSheetIndex(1);
            // 从第1个读到第4个sheet
            importParams.setSheetNum(1);
            // 校验字段 是否合法的Excel
            importParams.setImportFields(importFields);
            // 开启校验 => JSR 303校验注解
            importParams.setNeedVerify(true);
            return ExcelImportUtil.importExcel(file.getInputStream(), inClass, importParams);
        } catch (Exception e) {
            throw new ServiceRuntimeException(ResultCodeEnum.FAIL, "easyPoi导入Excel失败");
        }
    }

    /**
     * importDataMoreDefault : 导出校验的信息
     *
     * @param inClass      inClass
     * @param file         file
     * @param importFields importFields
     * @return ExcelImportResult<IN>
     */
    public <IN> ExcelImportResult<IN> importDataMoreDefault(Class<IN> inClass, MultipartFile file, String[] importFields) {
        try {
            ImportParams importParams = new ImportParams();
            // 标题
            importParams.setTitleRows(1);
            // 表头
            importParams.setHeadRows(1);
            // 从哪个sheet开始读
            importParams.setStartSheetIndex(1);
            // 从第1个读到第4个sheet
            importParams.setSheetNum(1);
            // 校验字段 是否合法的Excel
            importParams.setImportFields(importFields);
            // 开启校验 => JSR 303校验注解
            importParams.setNeedVerify(true);
            return ExcelImportUtil.importExcelMore(file.getInputStream(), inClass, importParams);
        } catch (Exception e) {
            throw new ServiceRuntimeException(ResultCodeEnum.FAIL, "easyPoi导入Excel失败");
        }
    }

    /**
     * validImportData : 单行校验异常直接报错
     *
     * @param excelImportResult excelImportResult
     */
    public <IN extends EasyPoiEntityBase>void validImportData(ExcelImportResult<IN> excelImportResult) {
        if (excelImportResult == null) {
            return;
        }
        List<IN> failList = excelImportResult.getFailList();
        if (CollectionUtils.isNotEmpty(failList)) {
            for (IN in : failList) {
                String errMsg = StringUtils.join("第 ", in.getRowNum(), "行导入Excel校验异常: ", in.getErrorMsg());
                throw ServiceRuntimeException.of(errMsg);
            }
        }
    }

    /**
     * validImportDataToFile : 验证JSR303 等异常, 导出txt文件
     *
     * @param excelImportResult excelImportResult
     * @param response          response
     * @param fileName          fileName
     */
    public <IN extends EasyPoiEntityBase> void validImportDataToFile(ExcelImportResult<IN> excelImportResult, HttpServletResponse response, String fileName) {
        if (excelImportResult == null) {
            return;
        }
        excelImportResult.getFailList();

        OptionWrapper.ofNotNone(excelImportResult.getFailList()).ifPresent(failList -> {
            StringBuffer sbf = new StringBuffer();
            failList.forEach(in -> {
                String errMsgRow = StringUtils.join("第 ", in.getRowNum(), "行导入Excel校验异常: ", in.getErrorMsg(), ". ");
                sbf.append(errMsgRow).append("\n");
            });

            response.setCharacterEncoding(StandardCharsets.UTF_8.toString());
            // "text/plain"
            response.setContentType(ContentType.TEXT_PLAIN.getMimeType());
            //设置文件的名称和格式
            response.addHeader("Content-Disposition", "attachment;filename=" + fileName + ".txt");

            InputStream inputStream = IOUtils.toInputStream(sbf.toString());
            IOUtils.copy(inputStream, response.getOutputStream());
        });
    }
}
