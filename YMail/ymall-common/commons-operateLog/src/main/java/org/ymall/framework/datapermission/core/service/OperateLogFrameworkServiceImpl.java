/*
 * Copyright (c) 2023. 版权归III_Delay所有
 */

package org.ymall.framework.datapermission.core.service;

import cn.hutool.core.bean.BeanUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;

/**
 * 操作日志 Framework Service 实现类
 *
 * 基于 {@link OperateLogApi} 实现，记录操作日志
 *
 * @author 芋道源码
 */
@RequiredArgsConstructor(onConstructor = @_(@Autowired))
public class OperateLogFrameworkServiceImpl implements OperateLogFrameworkService {

    private final OperateLogApi operateLogApi;

    @Override
    @Async
    public void createOperateLog(OperateLog operateLog) {
        OperateLogCreateReqDTO reqDTO = BeanUtil.copyProperties(operateLog, OperateLogCreateReqDTO.class);
        operateLogApi.createOperateLog(reqDTO);
    }

}
