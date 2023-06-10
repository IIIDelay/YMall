package org.ymall.model.product;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * BusLog
 */
@TableName("bus_log")
@Data
public class BusLog extends Model<BusLog> implements Serializable {
    private static final long serialVersionUID = -6716044045477179304L;
    
    /**
     * 自增id
     */
    private long id;

    /**
     * 业务名称
     */
    private String busName;

    /**
     * busDescrip 业务操作描述
     */
    private String busDescrip;

    /**
     * operPerson 操作人
     */
    private String operPerson;

    private Date operTime;

    private String ipFrom;

    private String paramFile;
}
