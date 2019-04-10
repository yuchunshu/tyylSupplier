/*
 * FileName:    RemoteUnitIdentifier.java
 * Description:
 * Company:     南宁超创信息工程有限公司
 * Copyright:   ChaoChuang (c) 2016
 * History:     2016年7月20日 (yuandl) 1.0 Create
 */

package cn.com.chaochuang.doc.remotedocswap.domain;

import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.Table;

import cn.com.chaochuang.common.data.domain.LongIdEntity;
import cn.com.chaochuang.common.lookup.annotation.LookUp;
import cn.com.chaochuang.doc.remotedocswap.reference.RemoteUnitFlag;
import cn.com.chaochuang.doc.remotedocswap.reference.RemoteUnitFlagConverter;

/**
 * @author yuandl
 *
 */
@Entity
@LookUp
@Table(name = "remote_unit_identifier")
public class RemoteUnitIdentifier extends LongIdEntity {

    private static final long serialVersionUID = -4234885212314233899L;
    /** 单位名称 */
    private String            unitName;

    /** 单位标识 */
    private String            unitIdentifier;

    /** 标识标志 */
    @Convert(converter = RemoteUnitFlagConverter.class)
    private RemoteUnitFlag    identifierFlag;
 
    /**单位对应的签收部门代号*/
    private String 			  roleName;
    
    private String 			  identifierName;
    
    /**使用频率*/
    private Integer 		  frequency;
    private String 			  valid;
    private Integer 		  orderNum;
    
    /**
     * @return the unitName
     */
    public String getUnitName() {
        return unitName;
    }

    /**
     * @param unitName
     *            the unitName to set
     */
    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    /**
     * @return the unitIdentifier
     */
    public String getUnitIdentifier() {
        return unitIdentifier;
    }

    /**
     * @param unitIdentifier
     *            the unitIdentifier to set
     */
    public void setUnitIdentifier(String unitIdentifier) {
        this.unitIdentifier = unitIdentifier;
    }

    /**
     * @return the identifierFlag
     */
    public RemoteUnitFlag getIdentifierFlag() {
        return identifierFlag;
    }

    /**
     * @param identifierFlag
     *            the identifierFlag to set
     */
    public void setIdentifierFlag(RemoteUnitFlag identifierFlag) {
        this.identifierFlag = identifierFlag;
    }

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getIdentifierName() {
		return identifierName;
	}

	public void setIdentifierName(String identifierName) {
		this.identifierName = identifierName;
	}

	public Integer getFrequency() {
		return frequency;
	}

	public void setFrequency(Integer frequency) {
		this.frequency = frequency;
	}

	public String getValid() {
		return valid;
	}

	public void setValid(String valid) {
		this.valid = valid;
	}

	public Integer getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(Integer orderNum) {
		this.orderNum = orderNum;
	}

}
