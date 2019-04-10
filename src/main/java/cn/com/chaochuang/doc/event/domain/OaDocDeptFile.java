/*
 * FileName:    OaDocDeptFile.java
 * Description:
 * Company:     南宁超创信息工程有限公司
 * Copyright:   ChaoChuang (c) 2017
 * History:     2017年9月14日 (yuchunshu) 1.0 Create
 */

package cn.com.chaochuang.doc.event.domain;


import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import cn.com.chaochuang.common.data.domain.StringUuidEntity;


/** 
 * @ClassName: OaDocDeptFile 
 * @Description: 本部门公文关联
 * @author: yuchunshu
 * @date: 2017年9月14日 上午9:57:40  
 */
@Entity
@AttributeOverrides({ @AttributeOverride(name = "id", column = @Column(name = "file_id")) })
@Table(name = "oa_doc_dept_file")
public class OaDocDeptFile extends StringUuidEntity {

    private static final long  serialVersionUID      = 1153312316351132725L;

    /** 环节实例ID */
    private String             nodeInstId;
    /** 共享部门ID */
    private Long               deptId;
    
	public String getNodeInstId() {
		return nodeInstId;
	}
	public void setNodeInstId(String nodeInstId) {
		this.nodeInstId = nodeInstId;
	}
	public Long getDeptId() {
		return deptId;
	}
	public void setDeptId(Long deptId) {
		this.deptId = deptId;
	}

}
