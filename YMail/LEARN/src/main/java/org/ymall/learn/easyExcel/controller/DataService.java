package org.ymall.learn.easyExcel.controller;


import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.ymall.learn.easyExcel.dto.UserImport;

import java.util.Collection;
import java.util.Map;

/**
 *
 * @author cheng-qiang
 * @date 2022年11月17日11:41
 */
@Service
@RequiredArgsConstructor(onConstructor = @_(@Autowired))
public class DataService {

    public void saveBatch(Collection<UserImport> userExtras) {
        for (UserImport userExtra : userExtras) {
            System.out.println(userExtra);
        }
    }
}
