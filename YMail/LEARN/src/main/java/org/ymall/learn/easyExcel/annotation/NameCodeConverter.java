package org.ymall.learn.easyExcel.annotation;

import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.converters.ReadConverterContext;
import com.alibaba.fastjson.JSON;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.MapType;
import org.ymall.learn.easyExcel.config.RedisService;
import org.ymall.learn.easyExcel.utils.EasyExcelUtils;

import java.lang.reflect.Field;
import java.util.Map;

/**
 * @author cheng-qiang
 * @date 2023年01月04日15:45
 */
public class NameCodeConverter implements Converter<String> {

    private RedisService redisService;

    public NameCodeConverter(){
        this.redisService = SpringContextUtil.getBean(RedisService.class);
    }

    ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String convertToJavaData(ReadConverterContext<?> context) throws Exception {
        MapType mapType = objectMapper.getTypeFactory().constructMapType(Map.class, String.class, Map.class);
        Object map = redisService.get(EasyExcelUtils.DICTIONARY_TABLE);
        String mapInfo = JSON.toJSON(map).toString();
        Map<String, Map<String, String>> dictionaryTableDataMap = objectMapper.readValue(mapInfo, mapType);
        String name = context.getReadCellData().getStringValue();
        Field field = context.getContentProperty().getField();
        ImportExcel annotation = field.getAnnotation(ImportExcel.class);
        if (annotation!=null){
            String classPathKey = annotation.classPath();
            Map<String, String> tableMap = dictionaryTableDataMap.get(classPathKey);
            if (tableMap!=null){
                return tableMap.get(name);
            }
        }
        return null;
    }
}
