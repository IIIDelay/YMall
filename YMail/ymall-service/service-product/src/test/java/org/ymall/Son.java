/*
 * Copyright (c) 2023. 版权归III_Delay所有
 */

package org.ymall;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
public class Son extends Parent{
    private String age;

    public Son GET() {
        super.setPname("pp");
        this.setPname("zz");
        return this;
    }
}
