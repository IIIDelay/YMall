/*
 * Copyright (c) 2023. 版权归III_Delay所有
 */

package org.ymall.commons.helper;

import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.ProjectionOperation;

/**
 * MongoUtils
 *
 * @Author IIIDelay
 * @Date 2023/9/24 9:27
 **/
public class MongoUtils {
    public static ProjectionOperation buildProjectOp(String... feilds) {
        ProjectionOperation projectionOperation = Aggregation.project(feilds);
        return projectionOperation;
    }

}
