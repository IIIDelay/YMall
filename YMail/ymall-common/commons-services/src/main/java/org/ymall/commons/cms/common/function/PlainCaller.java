/*
 *
 * Copyright (c) 2020-2023, 赛泰先生 (http://www.altitude.xin).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package org.ymall.commons.cms.common.function;

/**
 * 无输入无输出回调方法
 * <p>
 * 参考{@link xin.altitude.cms.common.util.BooleanUtils#ifTrue(boolean, PlainCaller)}的使用
 *

 **/
@FunctionalInterface
public interface PlainCaller {
    /**
     * 执行回调
     */
    void handle();
}
