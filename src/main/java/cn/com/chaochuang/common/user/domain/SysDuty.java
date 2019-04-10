package cn.com.chaochuang.common.user.domain;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;

import cn.com.chaochuang.common.data.domain.LongIdEntity;
import cn.com.chaochuang.common.lookup.annotation.LookUp;

/**
 * @author LJX 20161130
 *
 */
@LookUp
@Entity
@AttributeOverrides({ @AttributeOverride(name = "id", column = @Column(name = "duty_id")) })
public class SysDuty extends LongIdEntity {
    private static final long serialVersionUID = -590878609921590484L;
    private String            dutyName;
    private Long              dutyDesc;

    /**
     * @return the dutyName
     */
    public String getDutyName() {
        return dutyName;
    }

    /**
     * @param dutyName
     *            the dutyName to set
     */
    public void setDutyName(String dutyName) {
        this.dutyName = dutyName;
    }

    public Long getDutyDesc() {
        return dutyDesc;
    }

    public void setDutyDesc(Long dutyDesc) {
        this.dutyDesc = dutyDesc;
    }


}
