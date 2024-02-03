/*
 * Copyright (c) 2024. 版权归III_Delay所有
 */

package org.ymall.commons;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.metadata.data.ReadCellData;
import com.google.common.collect.Lists;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

/**
 * ImportReadListener
 *
 * @Author IIIDelay
 * @Date 2024/1/24 20:44
 **/
public class ImportReadListener<IN> /* implements ReadListener<IN>  */ {
    public static void main(String[] args) {
        ImportReadListener importReadListener = new ImportReadListener();
        importReadListener.setInvokeHeadConsumer(Lists.newArrayList(1.1));

    }


    private BiConsumer<IN, AnalysisContext> invokeConsumer;
    private Consumer<AnalysisContext> doAfterAllConsumer;
    private Consumer<List<ReadCellData<?>>> invokeHeadConsumer;

    // @Override
    public void invoke(IN in, AnalysisContext analysisContext) {
        if (invokeConsumer != null) {
            invokeConsumer.accept(in, analysisContext);
        }
    }

    // @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        if (invokeConsumer != null) {

            doAfterAllConsumer.accept(analysisContext);
        }
    }

    // @Override
    public void invokeHead(Map<Integer, ReadCellData<?>> headMap, AnalysisContext context) {
        if (invokeConsumer != null) {
            List<ReadCellData<?>> readCellData = Lists.newArrayList(headMap.values());
            readCellData.sort(Comparator.comparingInt(ReadCellData::getColumnIndex));
            invokeHeadConsumer.accept(readCellData);
        }
    }

    public void setInvokeConsumer(BiConsumer<IN, AnalysisContext> invokeConsumer) {
        this.invokeConsumer = invokeConsumer;
    }

    public void setDoAfterAllConsumer(Consumer<AnalysisContext> doAfterAllConsumer) {
        this.doAfterAllConsumer = doAfterAllConsumer;
    }

    public void setInvokeHeadConsumer(List<String> list) {
        // this.invokeHeadConsumer = list;
    }
}