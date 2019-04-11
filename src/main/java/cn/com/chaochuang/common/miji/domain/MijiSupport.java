/*
 * FileName:    MijiSupport.java
 * Description:
 * Company:     
 * Copyright:    (c) 2014
 * History:     2014年12月8日 (guig) 1.0 Create
 */

package cn.com.chaochuang.common.miji.domain;

import cn.com.chaochuang.common.miji.reference.MijiType;

/**
 * @author guig
 *
 */
public interface MijiSupport {

    public static final String MIJI_FIELD_NAME = "miji";

    public MijiType getMiji();

    public void setMiji(MijiType miji);

}
