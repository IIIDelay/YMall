/*
 * Copyright (c) 2023. 版权归III_Delay所有
 */

package org.ymall.ware.enums;

/**
 * @param
 * @return
 */
public enum TaskStatus {
     PAID("已付款"),
     DEDUCTED("已减库存"),
     OUT_OF_STOCK("已付款，库存超卖"),
     DELEVERED("已出库"),
     SPLIT("已拆分");

    private  String comment;

    TaskStatus(String comment) {
        this.comment=comment;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
