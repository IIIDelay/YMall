/*
 * Copyright (c) 2023. 版权归III_Delay所有
 */

package org.ymall.stream.redis;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class Message {

    private String body;

}
