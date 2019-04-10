/*
 * FileName:    MijiStringPersistEntity.java
 * Description:
 * Company:     南宁超创信息工程有限公司
 * Copyright:   ChaoChuang (c) 2016
 * History:     2016年4月18日 (HM) 1.0 Create
 */

package cn.com.chaochuang.common.miji.domain;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

import cn.com.chaochuang.common.data.domain.ValidAble;

/**
 * @author HM
 *
 */
@MappedSuperclass
public class MijiUUIdPersistEntity extends MijiUUIdEntity implements ValidAble {

    private static final long serialVersionUID = -3610109483553799659L;

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
