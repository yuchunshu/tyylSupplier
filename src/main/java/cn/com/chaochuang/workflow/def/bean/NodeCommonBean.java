/**
 *
 */
package cn.com.chaochuang.workflow.def.bean;

/**
 * @author hzr 2017.2.18
 *
 */
public class NodeCommonBean {

	private String       nodeId;

	private String       nodeName;

	private Long         dealerId;

	private String       dealerName;

	private String       dirType;

	private Boolean      nextOpenSel;

	public String getNodeId() {
		return nodeId;
	}

	public void setNodeId(String nodeId) {
		this.nodeId = nodeId;
	}

	public String getNodeName() {
		return nodeName;
	}

	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}


	public Long getDealerId() {
		return dealerId;
	}

	public void setDealerId(Long dealerId) {
		this.dealerId = dealerId;
	}

	public String getDealerName() {
		return dealerName;
	}

	public void setDealerName(String dealerName) {
		this.dealerName = dealerName;
	}

	public String getDirType() {
		return dirType;
	}

	public void setDirType(String dirType) {
		this.dirType = dirType;
	}

	public Boolean getNextOpenSel() {
		return nextOpenSel;
	}

	public void setNextOpenSel(Boolean nextOpenSel) {
		this.nextOpenSel = nextOpenSel;
	}



}
