

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
