package org.ymall.learn.easyExcel.dto;

import lombok.Data;

/**
 * @author cheng-qiang
 * @date 2022年11月11日17:19
 */
@Data
public class City {

    public City(){}

    public City(String id,String cityName){
        this.id = id;
        this.cityName = cityName;
    }

    /**城市编号**/
    private String id;
    /**城市名称**/
    private String cityName;
}
