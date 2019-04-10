
package cn.com.chaochuang.doc.expiredate.domain;


import javax.persistence.Entity;
import javax.persistence.Table;

import cn.com.chaochuang.common.data.domain.StringUuidEntity;


/** 
 * @ClassName: OaDocNodeExpireDate 
 * @Description: 环节限办时间设置
 * @author: yuchunshu
 * @date: 2017年10月12日 下午5:04:58  
 */
@Entity
@Table(name = "oa_doc_node_expiredate")
public class OaDocNodeExpireDate extends StringUuidEntity {

    private static final long  serialVersionUID      = 1153312316351132725L;

    /** 公文ID */
    private String             fileId;
    /** 环节ID */
    private String             nodeId;
    /** 环节限办时间 */
    private Long               nodeExpireMinute;
	public String getFileId() {
		return fileId;
	}
	public void setFileId(String fileId) {
		this.fileId = fileId;
	}
	public String getNodeId() {
		return nodeId;
	}
	public void setNodeId(String nodeId) {
		this.nodeId = nodeId;
	}
	public Long getNodeExpireMinute() {
		return nodeExpireMinute;
	}
	public void setNodeExpireMinute(Long nodeExpireMinute) {
		this.nodeExpireMinute = nodeExpireMinute;
	}
    
	
}
