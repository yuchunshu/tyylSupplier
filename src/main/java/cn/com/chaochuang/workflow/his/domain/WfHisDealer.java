package cn.com.chaochuang.workflow.his.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import cn.com.chaochuang.common.data.domain.StringUuidEntity;
import cn.com.chaochuang.common.user.domain.SysUser;
import cn.com.chaochuang.workflow.def.domain.WfNode;

/**
 * 环节历史选择
 * 
 * @author hzr 2016-12-29
 *
 */
@Entity
@Table(name = "wf_his_dealer")
public class WfHisDealer extends StringUuidEntity {

    /**  */
    private static final long serialVersionUID = -7905467996027450640L;

    /** 环节定义 */
    private String            nodeId;
    @ManyToOne
    @JoinColumn(name = "nodeId", insertable = false, updatable = false)
    private WfNode            node;

    /** 处理人 */
    private Long              dealerId;
    @ManyToOne
    @JoinColumn(name = "dealerId", insertable = false, updatable = false)
    private SysUser           dealer;

    /** 选择人 */
    private Long              ownerId;
    @ManyToOne
    @JoinColumn(name = "ownerId", insertable = false, updatable = false)
    private SysUser           owner;

    /** 使用时间 */
    private Date              useTime;

    public String getNodeId() {
        return nodeId;
    }

    public void setNodeId(String nodeId) {
        this.nodeId = nodeId;
    }

    public WfNode getNode() {
        return node;
    }

    public void setNode(WfNode node) {
        this.node = node;
    }

    public Long getDealerId() {
        return dealerId;
    }

    public void setDealerId(Long dealerId) {
        this.dealerId = dealerId;
    }

    public SysUser getDealer() {
        return dealer;
    }

    public void setDealer(SysUser dealer) {
        this.dealer = dealer;
    }

    public Long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }

    public SysUser getOwner() {
        return owner;
    }

    public void setOwner(SysUser owner) {
        this.owner = owner;
    }

    public Date getUseTime() {
        return useTime;
    }

    public void setUseTime(Date useTime) {
        this.useTime = useTime;
    }

}
