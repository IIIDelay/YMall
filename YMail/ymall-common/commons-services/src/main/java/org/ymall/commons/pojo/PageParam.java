/*
 * Copyright (c) 2023. 版权归III_Delay所有
 */

package org.ymall.commons.pojo;

import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class PageParam implements Serializable {
    private static final long serialVersionUID = 8734678919092202117L;

    private static final Integer PAGE_NO = 1;
    private static final Integer PAGE_SIZE = 10;

    @NotNull(message = "页码不能为空")
    @Min(value = 1, message = "页码最小值为 1")
    private Integer pageNo = PAGE_NO;
    
    @NotNull(message = "每页条数不能为空")
    @Min(value = 1, message = "每页条数最小值为 1")
    @Max(value = 100, message = "每页条数最大值为 100")
    private Integer pageSize = PAGE_SIZE;

}