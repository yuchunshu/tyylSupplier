
package cn.com.chaochuang.supplier.bean;

import java.util.List;

import org.dozer.Mapping;

public class SupGroupTreeBean {

    private Long                         id;

    @Mapping("groupName")
    private String                       text;

    private String                       state;

    private String                       iconCls;

    private List<SupGroupTreeBean>     	 children;

    /**隶属分组全称，即绝对目录，如aaa/bbb/ccc */
    private String                       fullGroupName;

    /**分组编码，如001001001，为方便向下级包含搜索*/
    private String                       groupCode;


    public SupGroupTreeBean() {

    }

    /**
     * @param id
     * @param text
     * @param state
     */
    public SupGroupTreeBean(Long id, String text, String state) {
        super();
        this.id = id;
        this.text = text;
        this.state = state;
    }

    /**
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return the text
     */
    public String getText() {
        return text;
    }

    /**
     * @param text the text to set
     */
    public void setText(String text) {
        this.text = text;
    }

    /**
     * @return the state
     */
    public String getState() {
        return state;
    }

    /**
     * @param state the state to set
     */
    public void setState(String state) {
        this.state = state;
    }

    /**
     * @return the iconCls
     */
    public String getIconCls() {
        return iconCls;
    }

    /**
     * @param iconCls the iconCls to set
     */
    public void setIconCls(String iconCls) {
        this.iconCls = iconCls;
    }

    /**
     * @return the children
     */
    public List<SupGroupTreeBean> getChildren() {
        return children;
    }

    /**
     * @param children the children to set
     */
    public void setChildren(List<SupGroupTreeBean> children) {
        this.children = children;
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


}
