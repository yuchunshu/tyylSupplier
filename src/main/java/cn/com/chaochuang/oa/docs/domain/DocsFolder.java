/**
 * 
 */
package cn.com.chaochuang.oa.docs.domain;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import cn.com.chaochuang.common.data.domain.LongIdEntity;

/**
 * @author hzr 2016年7月13日
 *
 */
@Entity
@AttributeOverrides({ @AttributeOverride(name = "id", column = @Column(name = "folder_id")) })
@Table(name = "oa_docs_folder")
public class DocsFolder extends LongIdEntity {

    private static final long serialVersionUID = -1575509047683831878L;

    /**目录名称 */
    private String folderName;
    
    /**目录全称，即绝对目录，如aaa/bbb/ccc */
    private String fullFloderName;
    
    /**目录编码，如001001001，为方便向下级包含搜索*/
    private String folderCode;
    
    /**父目录ID*/
    private Long   parentId;

    public String getFolderName() {
        return folderName;
    }

    public void setFolderName(String folderName) {
        this.folderName = folderName;
    }

    public String getFullFloderName() {
        return fullFloderName;
    }

    public void setFullFloderName(String fullFloderName) {
        this.fullFloderName = fullFloderName;
    }

    public String getFolderCode() {
        return folderCode;
    }

    public void setFolderCode(String folderCode) {
        this.folderCode = folderCode;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }
    
    
}
