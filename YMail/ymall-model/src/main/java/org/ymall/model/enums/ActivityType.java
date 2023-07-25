/*
 * Copyright (c) 2023. 版权归III_Delay所有
 */

package org.ymall.model.enums;

import lombok.Getter;

@Getter
public enum ActivityType {
    FULL_REDUCTION("满减"),
    FULL_DISCOUNT("满量打折");

    private String comment;

    ActivityType(String comment) {
        this.comment = comment;
    }

    public static String getNameByType(String type) {
        ActivityType arrObj[] = ActivityType.values();
        for (ActivityType obj : arrObj) {
            if (obj.name().equals(type)) {
                return obj.getComment();
            }
        }
        return "";
    }
}