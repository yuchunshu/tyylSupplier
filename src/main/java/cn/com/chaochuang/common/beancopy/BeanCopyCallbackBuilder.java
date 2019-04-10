/*
 * FileName:    BeanCopyCallback.java
 * Description:
 * Company:     南宁超创信息工程有限公司
 * Copyright:   ChaoChuang (c) 2015
 * History:     2015年1月28日 (guig) 1.0 Create
 */

package cn.com.chaochuang.common.beancopy;

/**
 * @author guig
 *
 */
public class BeanCopyCallbackBuilder<ST, DT> {

    private final BeanCopyCallback<ST, DT> callback;

    public BeanCopyCallbackBuilder(final BeanCopyCallback<ST, DT> callback) {
        this.callback = callback;
    }

    public void call(Object sourObj, Object destObj) {
        if (null != callback) {
            callback.afterCopy((ST) sourObj, (DT) destObj);
        }
    }

}
