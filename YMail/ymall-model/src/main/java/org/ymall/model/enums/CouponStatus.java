/*
 * Copyright (c) 2023. 版权归III_Delay所有
 */

package org.ymall.model.enums;

import lombok.Getter;

@Getter
public enum CouponStatus {
    NOT_USED("未使用"),
    USE_RUN("使用中" ),
    USED("已使用");
    
    private String comment ;

    CouponStatus(String comment ){
        this.comment=comment;
    }
}