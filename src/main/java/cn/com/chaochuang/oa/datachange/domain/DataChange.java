/*
 * FileName:    OaNotice.java
 * Description:
 * Company:     南宁超创信息工程有限公司
 * Copyright:   ChaoChuang (c) 2016
 * History:     2016年1月25日 (HM) 1.0 Create
 */

package cn.com.chaochuang.oa.datachange.domain;

import java.util.Date;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.Table;

import cn.com.chaochuang.common.data.domain.LongIdEntity;
import cn.com.chaochuang.oa.datachange.reference.DataChangeTable;
import cn.com.chaochuang.oa.datachange.reference.DataChangeTableConverter;
import cn.com.chaochuang.oa.datachange.reference.OperationType;
import cn.com.chaochuang.oa.datachange.reference.OperationTypeConverter;

/**
 * @author huagnwq
 *
 */
@Entity
@AttributeOverrides({ @AttributeOverride(name = "id", column = @Column(name = "sys_data_change_id")) })
@Table(name = "sys_data_change")
// 对应主键
public class DataChange extends LongIdEntity {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    // 数据修改描述
    private String            changeScript;
    // 数据变动时间
    private Date              changeDate;
    // 数据变动表名
    @Convert(converter = DataChangeTableConverter.class)
    private DataChangeTable   changeTableName;
    // 数据变更类型 insert update delete
    @Convert(converter = OperationTypeConverter.class)
    private OperationType     operationType;

    /**
     * @return the changeScript
     */
    public String getChangeScript() {
        return changeScript;
    }

    /**
     * @param changeScript
     *            the changeScript to set
     */
    public void setChangeScript(String changeScript) {
        this.changeScript = changeScript;
    }

    /**
     * @return the changeDate
     */
    public Date getChangeDate() {
        return changeDate;
    }

    /**
     * @param changeDate
     *            the changeDate to set
     */
    public void setChangeDate(Date changeDate) {
        this.changeDate = changeDate;
    }

    /**
     * @return the changeTableName
     */
    public DataChangeTable getChangeTableName() {
        return changeTableName;
    }

    /**
     * @param changeTableName
     *            the changeTableName to set
     */
    public void setChangeTableName(DataChangeTable changeTableName) {
        this.changeTableName = changeTableName;
    }

    /**
     * @return the operationType
     */
    public OperationType getOperationType() {
        return operationType;
    }

    /**
     * @param operationType
     *            the operationType to set
     */
    public void setOperationType(OperationType operationType) {
        this.operationType = operationType;
    }

}
