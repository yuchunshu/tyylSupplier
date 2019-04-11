/*
 * FileName:    MijiStringIdEntity.java
 * Description:
 * Company:     
 * Copyright:    (c) 2016
 * History:     2016年4月18日 (HM) 1.0 Create
 */

package cn.com.chaochuang.common.miji.domain;

import javax.persistence.Convert;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Transient;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.com.chaochuang.common.data.domain.StringUuidEntity;
import cn.com.chaochuang.common.miji.reference.MijiType;
import cn.com.chaochuang.common.miji.reference.MijiTypeConverter;
import cn.com.chaochuang.common.util.MijiHelper;

/**
 * @author HM
 *
 */
@MappedSuperclass
public class MijiUUIdEntity extends StringUuidEntity implements MijiSupport {

    private static final long  serialVersionUID           = -8309788350923356949L;

    public static final String DEFAULT_MIJI_LOWER_DISPLAY = "无权查看";

    @Transient
    protected final Log        logger                     = LogFactory.getLog(getClass());

    /** 密级 */
    @Convert(converter = MijiTypeConverter.class)
    private MijiType           miji;

    public MijiType getMiji() {
        return miji;
    }

    public void setMiji(MijiType miji) {
        this.miji = miji;
    }

    protected void checkAutoMiji() {
        if (null == getMiji()) {
            if (MijiHelper.isAutoMiji(this)) {
                setMiji(MijiHelper.getCurrentDefaultMiji());
            }
        }
    }

    @PrePersist
    public void beforePersist() {
        if (logger.isDebugEnabled()) {
            System.out.println("===================beforePersist=============================");
        }

        checkAutoMiji();
    }

    @PreUpdate
    public void beforeUpdate() {
        if (logger.isDebugEnabled()) {
            System.out.println("!!!!!!!!==============beforeUpdate=============================");
        }

        checkAutoMiji();
    }

    protected boolean userMijiLower() {
        if (MijiHelper.isInViewScope()) {
            return MijiHelper.currentUserMijiLower(getMiji());
        }
        return false;
    }

    protected String mijiLowerDisplayString() {
        return DEFAULT_MIJI_LOWER_DISPLAY;
    }
}
