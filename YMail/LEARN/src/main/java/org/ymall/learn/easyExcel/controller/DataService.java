package org.ymall.learn.easyExcel.controller;

import com.cheng.dto.UserImport;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Map;

/**
 * @author cheng-qiang
 * @date 2022年11月17日11:41
 */
@Service
public class DataService {

    public void saveBatch(Collection<UserImport> userExtras) {
        for (UserImport userExtra : userExtras) {
            System.out.println(userExtra);
        }
    }
}
