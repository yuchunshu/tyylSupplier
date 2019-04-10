package cn.com.chaochuang.workflow.def.bean;

import java.util.List;

/**
 * 
 * @author LJX
 *
 */
public class NodeDeptSelectTreeBean{
	private Long                     		id;

    private String                   		text;

    private String                   		state;

    private String                   		iconCls;

    private List<NodeDeptSelectTreeBean> 	children;
    
	private String 							dutyIds;
	

	public String getDutyIds() {
		return dutyIds;
	}

	public void setDutyIds(String dutyIds) {
		this.dutyIds = dutyIds;
	}

	public NodeDeptSelectTreeBean() {
		super();
	}

	public NodeDeptSelectTreeBean(Long id, String text,String dutyIds) {
		super();
        this.id = id;
        this.text = text;
        this.dutyIds = dutyIds;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getIconCls() {
		return iconCls;
	}

	public void setIconCls(String iconCls) {
		this.iconCls = iconCls;
	}

	public List<NodeDeptSelectTreeBean> getChildren() {
		return children;
	}

	public void setChildren(List<NodeDeptSelectTreeBean> children) {
		this.children = children;
	}
	
	
}
