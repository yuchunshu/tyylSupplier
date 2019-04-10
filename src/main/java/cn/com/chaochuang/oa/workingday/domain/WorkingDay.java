package cn.com.chaochuang.oa.workingday.domain;

import java.util.Date;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.Table;

import cn.com.chaochuang.common.data.domain.LongIdEntity;
import cn.com.chaochuang.common.dictionary.IDictionary;
import cn.com.chaochuang.common.util.Tools;
import cn.com.chaochuang.doc.event.reference.DateFlag;
import cn.com.chaochuang.doc.event.reference.DateFlagConverter;

/**
 * @author huangwq
 *
 */
@Entity
@Table(name = "oa_working_day")
@AttributeOverrides({ @AttributeOverride(name = "id", column = @Column(name = "id")) })
public class WorkingDay extends LongIdEntity implements IDictionary {
    /**  */
    private static final long serialVersionUID = 6493622043059181833L;
    private Date              dayDate;
    /** 工作日标志:0 工作日 1 非工作日 */
    @Convert(converter = DateFlagConverter.class)
    private DateFlag          dateFlag;

    public DateFlag getDateFlag() {
        return dateFlag;
    }

    public void setDateFlag(DateFlag dateFlag) {
        this.dateFlag = dateFlag;
    }

    /**
     * @return the dayDate
     */
    public Date getDayDate() {
        return dayDate;
    }

    /**
     * @param dayDate
     *            the dayDate to set
     */
    public void setDayDate(Date dayDate) {
        this.dayDate = dayDate;
    }

    @Override
    public String getKey() {
        return Tools.DATE_FORMAT4.format(dayDate);
    }

    @Override
    public String getValue() {
        return dateFlag.getKey();
    }

    @Override
    public Object getObject() {
        return this;
    }

}
