

/*
 * Copyright (c) 2023. 版权归III_Delay所有
 */

package org.ymall.commons.cms.common.lang;

import java.util.Map;

/**

 **/
public abstract class AbstractEntry<K, V> implements Map.Entry<K, V> {
    @Override
    public V setValue(V value) {
        return value;
    }
}
