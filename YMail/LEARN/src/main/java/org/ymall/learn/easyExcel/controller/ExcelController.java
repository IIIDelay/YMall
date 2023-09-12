package org.ymall.learn.easyExcel.controller;

import cn.hutool.core.util.IdUtil;
import com.alibaba.excel.EasyExcel;
import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.MapType;
import io.swagger.annotations.ApiOperation;
import org.apache.poi.ss.usermodel.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.ymall.learn.easyExcel.annotation.CommentWriteHandler;
import org.ymall.learn.easyExcel.annotation.ExcelError;
import org.ymall.learn.easyExcel.annotation.ExcelReader;
import org.ymall.learn.easyExcel.config.BtJsonResult;
import org.ymall.learn.easyExcel.config.RedisService;
import org.ymall.learn.easyExcel.dto.User;
import org.ymall.learn.easyExcel.dto.UserExtra;
import org.ymall.learn.easyExcel.dto.UserImport;
import org.ymall.learn.easyExcel.listener.AnalysisEventCustomListener;
import org.ymall.learn.easyExcel.listener.JdbcEventListener;
import org.ymall.learn.easyExcel.utils.EasyExcelUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * Excel控制器
 * @author cheng-qiang
 * @date 2022年08月16日11:00
 */
@RestController
@RequestMapping(value = "/Excel")
public class ExcelController {

    @Resource
    private ExcelReader excelReader;

    @Resource
    private  DataService dataService;

    @Resource
    private RedisService redisService;
    ObjectMapper objectMapper = new ObjectMapper();

    @GetMapping("/export/SingleExcel")
    private void exportSingleExcel(HttpServletResponse response) {
        List<User> userList = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            User user = new User();
            user.setRealName("坚持是一种技术"+i);
            user.setAddress("火星HX"+i);
            userList.add(user);
        }
        EasyExcelUtils.writeSingleExcel(response,
                userList,
                "人员名单",
                "人员信息",
                User.class,
                IndexedColors.BLUE.getIndex(),
                20,
                IndexedColors.GREEN.getIndex(),
                20,
                BorderStyle.DASHED
                );
    }

    @GetMapping(value = "/export/UserExtra",name = "http://localhost:8080/Excel/export/UserExtra")
    private void exportUserExtra(HttpServletResponse response) throws IOException {
        List<String> sheetName = new ArrayList<>();
        sheetName.add("员工信息");
        // sheetName.add("员工信息2");
        Map<String,Class<?>> classMap = new HashMap<>();
        classMap.put("员工信息", UserExtra.class);
        // classMap.put("员工信息2",UserExtra.class);
        EasyExcelUtils.writeSelectedSheet(classMap,sheetName,"用户信息_数据导入",response);
    }

    @GetMapping("/export/MultiExcel")
    private void exportMultiExcel(HttpServletResponse response) {
        Map<String,List<?>> listMap = new HashMap<>();
        List<User> userList = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            User user = new User();
            user.setRealName("言少钱"+i);
            user.setAddress("太白金星HX"+i);
            userList.add(user);
        }
        listMap.put("人员信息",userList);
        Map<String,Class<?>> classMap = new HashMap<>();
        classMap.put("人员信息",User.class);
        EasyExcelUtils.writeMultiExcel(response,
                listMap,
                "人员名单",
                classMap,
                IndexedColors.BLUE.getIndex(),
                20,
                IndexedColors.GREEN.getIndex(),
                20,
                BorderStyle.DASHED
        );
    }

    @GetMapping("/export/ExcelFile")
    private void exportExcelFile(HttpServletResponse response) {
        LinkedHashMap<String, String> headColumnMap = new LinkedHashMap<>();
        headColumnMap.put("className","班级");
        headColumnMap.put("name","学生信息,姓名");
        headColumnMap.put("sex","学生信息,性别");
        List<Map<String, Object>> dataList = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            Map<String, Object> dataMap = new HashMap<>();
            dataMap.put("className", "一年级");
            dataMap.put("name", "张三" + i);
            dataMap.put("sex", "男");
            dataList.add(dataMap);
        }
        EasyExcelUtils.exportExcelFile(headColumnMap, dataList,response,"学生信息");
    }

    @PostMapping("/import/Excel")
    private void importExcel(@RequestParam(value = "multipartFile") MultipartFile multipartFile) {
        List<AnalysisEventCustomListener> analysisEventCustomListeners = EasyExcelUtils.readEasyExcel(multipartFile);
        for (AnalysisEventCustomListener analysisEventCustomListener : analysisEventCustomListeners) {
            analysisEventCustomListener.getDataList().forEach(item-> System.out.println(item.get(0)+"   "+item.get(1)));
        }
    }

    @PostMapping("/import/ExcelWith")
    private void importExcelWith(@RequestParam(value = "multipartFile") MultipartFile multipartFile) {
        List<?> list = EasyExcelUtils.readExcelModel(multipartFile, User.class);
        List<User> userList = new ArrayList<>();
        for (Object item : list) {
            userList.add((User)item);
        }
        userList.forEach(System.out::println);
    }

    @PostMapping(value = "/excel/import",name = "http://localhost:8080/Excel/excel/import")
    @ApiOperation(value = "数据导入")
    public BtJsonResult importManufacturerInfo(@RequestPart MultipartFile file) throws IOException {
        InputStream inputStream = file.getInputStream();
        Map<String, Map<String, String>> dictionaryTableDataMap = EasyExcelUtils.getDictionaryTableDataMap(inputStream);
        redisService.setEx(EasyExcelUtils.DICTIONARY_TABLE,dictionaryTableDataMap,EasyExcelUtils.EXPIRE_TIME);
        ExcelReader.Meta<UserImport> excelDataMeta = new ExcelReader.Meta<>();
        excelDataMeta.setExcelStream(file.getInputStream());
        excelDataMeta.setDomain(UserImport.class);
        excelDataMeta.setHeadRowNumber(EasyExcelUtils.HEAD_ROW_NUMBER);
        excelDataMeta.setConsumer(dataService::saveBatch);
        JdbcEventListener<UserImport> eventListener = this.excelReader.read(excelDataMeta);
        if (eventListener.getExcelErrorMap().size()>0){
            List<UserImport> listExcels = eventListener.getListExcels();
            String uuid = IdUtil.getSnowflakeNextIdStr();
            String key = EasyExcelUtils.PREFIX  + uuid;
            redisService.setEx(key,listExcels,EasyExcelUtils.EXPIRE_TIME);
            redisService.setEx(key+EasyExcelUtils.SEND_LIST_ERROR,eventListener.getExcelErrorMap(),EasyExcelUtils.EXPIRE_TIME);
            return BtJsonResult.build(BtJsonResult.ERROR,"导入失败！",uuid);
        }
        return BtJsonResult.build(BtJsonResult.SUCCESS,"导入成功！");
    }

    @GetMapping(value = "downloadErrorExcel",name = "http://localhost:8080/Excel/downloadErrorExcel?uuid=")
    @ApiOperation(value = "下载批注错误excel")
    public void downloadErrorExcel(HttpServletResponse  response,String uuid) throws IOException {
        String fileName = "Excel数据导入问题反馈.xlsx";
        response.setContentType("application/ms-excel;charset=UTF-8");
        response.setHeader("Content-Disposition", "attachment;filename=" + new String(fileName.getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1));
        CommentWriteHandler commentWriteHandler = new CommentWriteHandler();
        String key = EasyExcelUtils.PREFIX  + uuid;
        Object object = redisService.get(key);
        String info = JSON.toJSON(object).toString();
        JavaType javaType = objectMapper.getTypeFactory().constructParametricType(ArrayList.class, UserImport.class);
        List<UserImport> userImports = objectMapper.readValue(info, javaType);
        MapType mapType = objectMapper.getTypeFactory().constructMapType(Map.class, Integer.class,ArrayList.class);
        Object map = redisService.get(key + EasyExcelUtils.SEND_LIST_ERROR);
        String mapInfo = JSON.toJSON(map).toString();
        Map<Integer, List<ExcelError>> errorMap = objectMapper.readValue(mapInfo, mapType);
        commentWriteHandler.setExcelErrorMap(errorMap);
        EasyExcel.write(response.getOutputStream(), UserImport.class)
                .inMemory(Boolean.TRUE)
                .sheet("sheet1")
                //注册批注拦截器
                .registerWriteHandler(commentWriteHandler)
                .doWrite(userImports);
    }

}
