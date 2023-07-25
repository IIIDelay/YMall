/*
 * Copyright (c) 2023. 版权归III_Delay所有
 */

package org.ymall.model.enums;

public enum PaymentType {
    ALIPAY("支付宝"),
    WEIXIN("微信" );

    private String comment ;


    PaymentType(String comment ){
        this.comment=comment;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

}
