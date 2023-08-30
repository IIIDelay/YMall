

/*
 * Copyright (c) 2023. 版权归III_Delay所有
 */

package org.ymall.commons.cms.common.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;
import org.springframework.security.core.parameters.P;
import org.ymall.commons.cms.common.lang.ITree;

import java.util.List;
import java.util.Objects;

/**

 **/
@Setter
@Getter
public abstract class TreeNode<T> implements ITree<T>, Comparable<TreeNode<T>> {
    private T id;
    private String name;
    private T parentId;
    private List<TreeNode<T>> childList;
}
