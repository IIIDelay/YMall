/*
 * Copyright (c) 2024. 版权归III_Delay所有
 */

package org.ymall.commons.helper;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.metadata.data.ReadCellData;
import com.alibaba.excel.read.listener.ReadListener;

import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

/**
 * ImportReadListener
 *
 * @Author IIIDelay
 * @Date 2024/1/24 20:44
 **/
public class ImportReadListener<IN> implements ReadListener<IN> {
    private BiConsumer<IN, AnalysisContext> invokeConsumer;
    private Consumer<AnalysisContext> doAfterAllConsumer;
    private Consumer<Map<Integer, ReadCellData<?>>> invokeHeadConsumer;

    @Override
    public void invoke(IN in, AnalysisContext analysisContext) {
        if (invokeConsumer != null) {
            invokeConsumer.accept(in, analysisContext);
        }
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        if (invokeConsumer != null) {

            doAfterAllConsumer.accept(analysisContext);
        }
    }

    @Override
    public void invokeHead(Map<Integer, ReadCellData<?>> headMap, AnalysisContext context) {
        if (invokeConsumer != null) {
            invokeHeadConsumer.accept(headMap);
        }
    }
}