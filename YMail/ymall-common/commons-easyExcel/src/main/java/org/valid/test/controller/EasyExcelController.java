/*
 * Copyright (c) 2023. 版权归III_Delay所有
 */

package org.valid.test.controller;

import org.springframework.stereotype.Indexed;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.valid.test.model.DemoData;
import org.ymall.easy.annotation.ExcelParam;
import org.ymall.easy.annotation.ExcelResponse;
import org.ymall.easy.validator.ReadRows;
import org.ymall.easy.validator.errors.ExcelValidErrors;
import org.ymall.easy.validator.errors.ExcelValidObjectError;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/easy/excel")
public class EasyExcelController {

    @PostMapping("/list/obj")
    public List<DemoData> listObj(@ExcelParam @Validated List<DemoData> list, ExcelValidErrors errors) {
        if (errors.hasErrors()) {
            String messages = errors.getAllErrors().stream()
                .map(ExcelValidObjectError::getMessage)
                .collect(Collectors.joining(" | "));
            throw new RuntimeException("发现异常:" + messages);
        }
        return list;
    }

    @PostMapping("/list/rows")
    public ReadRows<DemoData> readRows(@ExcelParam(value = "file") @Validated ReadRows<DemoData> readRows) {
        return readRows;
    }

    @ExcelResponse
    @GetMapping("/list/download")
    public List<DemoData> downloadList() {
        return Arrays.asList(new DemoData(1, "hello", new Date()), new DemoData(2, "excel", new Date()));
    }

}
