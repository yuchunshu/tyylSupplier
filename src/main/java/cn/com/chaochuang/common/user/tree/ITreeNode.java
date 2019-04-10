/*
 * FileName:    ITreeNode.java
 * Description:
 * Company:     南宁超创信息工程有限公司
 * Copyright:   ChaoChuang (c) 2014
 * History:     2014年7月18日 (LaoZhiYong) 1.0 Create
 */

package cn.com.chaochuang.common.user.tree;

import java.util.List;

/**
 * @author LaoZhiYong
 *
 */
public interface ITreeNode {
    /**
     * 取得结点的键.
     *
     * @return 结点的键值
     */
    String getNodeKey();

    /**
     * 取得结点名.
     *
     * @return 结点名
     */
    String getNodeName();

    /**
     * 是否叶子结点.
     *
     * @return 叶子结点返回true，否则返回false
     */
    boolean isLeaf();

    /**
     * 取得子结点列表.
     *
     * @return 子结点组成的List
     */
    List getChildren();
}
