package cn.com.chaochuang.workflow.his.bean;

import java.util.Date;

/**
 * @author yuandl 2016-11-24
 *
 */
public class HisDealerEditBean {
	
    private String id;
    /** 环节定义 */
    private String nodeId;
    /** 处理人 */
    private Long   dealerId;

    /** 处理人s */
    private String dealerIds;

    /** 选择人 */
    private Long   ownerId;

    /** 使用时间 */
    private Date   useTime;

   
    public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNodeId() {
        return nodeId;
    }

    public void setNodeId(String nodeId) {
        this.nodeId = nodeId;
    }

    public String getDealerIds() {
        return dealerIds;
    }

    public void setDealerIds(String dealerIds) {
        this.dealerIds = dealerIds;
    }

    public Long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }

    public Date getUseTime() {
        return useTime;
    }

    public void setUseTime(Date useTime) {
        this.useTime = useTime;
    }

    public Long getDealerId() {
        return dealerId;
    }

    public void setDealerId(Long dealerId) {
        this.dealerId = dealerId;
    }

}
