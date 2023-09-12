package org.ymall.learn.easyExcel.annotation;

import com.alibaba.fastjson.JSONArray;
import org.springframework.stereotype.Service;
import org.ymall.learn.easyExcel.dto.City;

import java.util.ArrayList;
import java.util.List;

/**
 * @author cheng-qiang
 * @date 2022年11月09日15:38
 */
@Service
public class CityExcelSelectedImpl implements ExcelDynamicSelect{

    @Override
    public String getSource() {
        City city1 = new City("A0001", "成都");
        City city2 = new City("A0002", "上海");
        City city3 = new City("A0003", "杭州");
        City city4 = new City("A0004", "深圳");
        City city5 = new City("A0005", "香港");
        List<City> cities = new ArrayList<>();
        cities.add(city1);
        cities.add(city2);
        cities.add(city3);
        cities.add(city4);
        cities.add(city5);
        return JSONArray.toJSONString(cities.toArray());
    }
}
