/*
 * Copyright (c) 2023. 版权归III_Delay所有
 */

package utils;

import org.iiidev.ymall.common.UnitConvertType;
import dto.MyYearSumReportDTO;
import org.iiidev.ymall.utils.UnitConvertUtil;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * UnitConvertUtilTest
 *
 * @Author IIIDelay
 * @Date 2023/6/10 12:45
 **/
public class UnitConvertUtilTest {

    @Test
    public void unitMapConvertTest() {
        // 获取模拟数据
        List<MyYearSumReportDTO> list = getMySumReportList();

        Map<String, UnitConvertType> map = new HashMap<>();
        map.put("payTotalAmount", UnitConvertType.B);
        map.put("jcAmountPercentage", UnitConvertType.PERCENTAGE);
        map.put("jcCountPermillage", UnitConvertType.PERMIL);
        map.put("length", UnitConvertType.R);
        map.put("width", UnitConvertType.R);
        UnitConvertUtil.unitMapConvert(list, map);
        System.out.println("通过map标识的自动转换玩法：" + list.toString());
    }

    private static List<MyYearSumReportDTO> getMySumReportList() {
        MyYearSumReportDTO mySumReportDTO = new MyYearSumReportDTO();
        mySumReportDTO.setPayTotalAmount(new BigDecimal(1100000));
        mySumReportDTO.setJcAmountPercentage(BigDecimal.valueOf(0.695));
        mySumReportDTO.setJcCountPermillage(BigDecimal.valueOf(0.7894));
        mySumReportDTO.setLength(BigDecimal.valueOf(1300.65112));
        mySumReportDTO.setWidth(BigDecimal.valueOf(6522.12344));

        MyYearSumReportDTO mySumReportDTO1 = new MyYearSumReportDTO();
        mySumReportDTO1.setPayTotalAmount(new BigDecimal(2390000));
        mySumReportDTO1.setJcAmountPercentage(BigDecimal.valueOf(0.885));
        mySumReportDTO1.setJcCountPermillage(BigDecimal.valueOf(0.2394));
        mySumReportDTO1.setLength(BigDecimal.valueOf(1700.64003));
        mySumReportDTO1.setWidth(BigDecimal.valueOf(7522.12344));

        List<MyYearSumReportDTO> list = new ArrayList<>();

        list.add(mySumReportDTO);
        list.add(mySumReportDTO1);
        return list;
    }

    @Test
    public void unitAnnotateConvertTest() {
        List<MyYearSumReportDTO> yearsList = getMyYearSumReportList();
        UnitConvertUtil.unitAnnotateConvert(yearsList);
        System.out.println("通过注解标识的自动转换玩法：" + yearsList.toString());
    }

    private static List<MyYearSumReportDTO> getMyYearSumReportList() {
        MyYearSumReportDTO mySumReportDTO = new MyYearSumReportDTO();
        mySumReportDTO.setPayTotalAmount(new BigDecimal(1100000));
        mySumReportDTO.setJcAmountPercentage(BigDecimal.valueOf(0.695));
        mySumReportDTO.setJcCountPermillage(BigDecimal.valueOf(0.7894));
        mySumReportDTO.setLength(BigDecimal.valueOf(1300.65112));
        mySumReportDTO.setWidth(BigDecimal.valueOf(6522.12344));
        MyYearSumReportDTO mySumReportDTO1 = new MyYearSumReportDTO();
        mySumReportDTO1.setPayTotalAmount(new BigDecimal(2390000));
        mySumReportDTO1.setJcAmountPercentage(BigDecimal.valueOf(0.885));
        mySumReportDTO1.setJcCountPermillage(BigDecimal.valueOf(0.2394));
        mySumReportDTO1.setLength(BigDecimal.valueOf(1700.64003));
        mySumReportDTO1.setWidth(BigDecimal.valueOf(7522.12344));

        List<MyYearSumReportDTO> list = new ArrayList<>();
        list.add(mySumReportDTO);
        list.add(mySumReportDTO1);
        return list;
    }
}