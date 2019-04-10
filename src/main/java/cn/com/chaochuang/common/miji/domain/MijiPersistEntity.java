/*
 * FileName:    MijiPersistEntity.java
 * Description:
 * Company:     南宁超创信息工程有限公司
 * Copyright:   ChaoChuang (c) 2014
 * History:     2014年7月11日 (LaoZhiYong) 1.0 Create
 */

package cn.com.chaochuang.common.miji.domain;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

import cn.com.chaochuang.common.data.domain.ValidAble;

/**
 * @author LaoZhiYong
 *
 */
@MappedSuperclass
public class MijiPersistEntity extends MijiLongIdEntity implements ValidAble {

    private static final long serialVersionUID = -6630062563357862382L;

    @Column(name = "_valid")
    protected Integer         valid            = VALID;

    /**
     * @return the _valid
     */
    public Integer getValid() {
        return valid;
    }

    /**
     * @param _valid
     *            the _valid to set
     */
    public void setValid(Integer valid) {
        this.valid = valid;
    }

}
