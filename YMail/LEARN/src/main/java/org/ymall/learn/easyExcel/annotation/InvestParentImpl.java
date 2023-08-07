package org.ymall.learn.easyExcel.annotation;

import com.alibaba.fastjson.JSONArray;
import com.cheng.dto.Invest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author cheng-qiang
 * @date 2022年11月09日16:38
 */
@Service
public class InvestParentImpl implements ExcelDynamicSelect{
    @Override
    public String getSource() {
        Invest invest1 = new Invest("A0001", "固定资产投资",1);
        Invest invest2 = new Invest("A0002", "股权投资",2);
        Invest invest3 = new Invest("A0003", "金融投资",3);
        Invest invest4 = new Invest("A0004", "其他投资",4);
        List<Invest> invests = new ArrayList<>();
        invests.add(invest1);
        invests.add(invest2);
        invests.add(invest3);
        invests.add(invest4);
        return JSONArray.toJSONString(invests.toArray());
    }
}
