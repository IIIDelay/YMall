/*
 * Copyright (c) 2023. 版权归III_Delay所有
 */

package org.valid.test.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.valid.test.validator.DemoDataValid;

import java.util.Date;

@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@DemoDataValid
public class DemoData {

    private Integer integer;

    private String string;

    private Date date;
}
