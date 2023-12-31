/*
 * Copyright (c) 2023. 版权归III_Delay所有
 */

package org.ymall.ware.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @param
 * @return
 */
@Data
public class WareOrderTask {

    @TableId(type = IdType.AUTO)
    private String id ;

    @TableField
    private String orderId;

    @TableField
    private String consignee;

    @TableField
    private String consigneeTel;

    @TableField
    private String deliveryAddress;

    @TableField
    private String orderComment;

    @TableField
    private String paymentWay;

    @TableField
    private String taskStatus;

    @TableField
    private String orderBody;

    @TableField
    private String trackingNo;

    @TableField
    private Date createTime;

    @TableField
    private String wareId;

    @TableField
    private String taskComment;

    @TableField(exist = false)
    private List<WareOrderTaskDetail> details;

}
