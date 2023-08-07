package org.ymall.learn.easyExcel.annotation;

import com.alibaba.fastjson.JSONArray;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author cheng-qiang
 * @date 2022年11月09日16:39
 */
@Service
public class InvestChildImpl implements ExcelDynamicSelect{
    @Override
    public String getSource() {
        Map<String, List<String>> map = new HashMap<>();
        List<String> gd = new ArrayList<>();
        gd.add("基本建设");
        gd.add("技改");
        gd.add("重大技术开发投入");
        gd.add("其他");
        List<String> gq = new ArrayList<>();
        gq.add("追加投资");
        gq.add("出资参股");
        gq.add("并购");
        gq.add("新设企业");
        map.put("固定资产投资",gd);
        map.put("股权投资",gq);
        return JSONArray.toJSONString(map);
    }
}
