package org.ymall.learn.easyExcel.dto;

import lombok.Data;

/**
 * @author cheng-qiang
 * @date 2022年11月12日19:03
 */
@Data
public class Invest {

    public Invest(){}

    public Invest(String id, String investName, Integer sort){
        this.id = id;
        this.investName = investName;
        this.sort = sort;
    }

    private String id;

    private String investName;

    private Integer sort;
}
