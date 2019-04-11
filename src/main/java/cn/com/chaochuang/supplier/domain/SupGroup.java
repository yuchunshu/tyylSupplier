package cn.com.chaochuang.supplier.domain;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import cn.com.chaochuang.common.data.domain.LongIdEntity;

@Entity
@AttributeOverrides({ @AttributeOverride(name = "id", column = @Column(name = "group_id")) })
@Table(name = "sup_group")
public class SupGroup extends LongIdEntity {

    private static final long serialVersionUID = -1575509047683831878L;

    /**目录名称 */
    private String groupName;
    
    /**目录全称，即绝对目录，如aaa/bbb/ccc */
    private String fullGroupName;
    
    /**目录编码，如001001001，为方便向下级包含搜索*/
    private String groupCode;
    
    /**父目录ID*/
    private Long   parentId;

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getFullGroupName() {
		return fullGroupName;
	}

	public void setFullGroupName(String fullGroupName) {
		this.fullGroupName = fullGroupName;
	}

	public String getGroupCode() {
		return groupCode;
	}

	public void setGroupCode(String groupCode) {
		this.groupCode = groupCode;
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

    
    
}
