package cn.com.chaochuang.doc.remotedocswap.domain;

import java.io.Serializable;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import cn.com.chaochuang.common.data.domain.LongIdEntity;


/** 
 * @ClassName: RemoteDocSwapConfig 
 * @Description: 限时短信配置
 * @author: chunshu
 * @date: 2017年8月31日 下午4:37:31  
 */
@Entity
@AttributeOverrides({ @AttributeOverride(name = "id", column = @Column(name = "config_id")) })
@Table(name = "remote_doc_swap_config")
public class RemoteDocSwapConfig extends LongIdEntity {

    //------角色代号
    //超级管理员
    public static final String SWAP_ROLE_ADMIN="admin";
    //机要室签收
    public static final String SWAP_ROLE_JY="swap_signer_jy";
    //办公室签收
    public static final String SWAP_ROLE_BGS="swap_signer_bgs";

    //------替换的内容
    public static final String REPLACE_TITLE="#title";
    public static final String REPLACE_TIME="#limitTime";
    public static final String REPLACE_UNIT="#orgName";

    //收到公文需要发送短信给签收人
    public static final String CONFIG_TYPE_MSG="sendMsg";
    //发送查询--角色对应的查询权限
    public static final String CONFIG_TYPE_SEND_QUERY="sendQuery";
    public static final String CONFIG_TYPE_SIGN_QUERY="signByDept";

    //签收时短信接收人user_id,多个使用,分隔
    public static final String RECEIVER_IDS="receiverIds";
    //签收时短信发送人user_id
    public static final String SEND_MSG_SENDER="msgSender";
    //签收时发送短信的模板
    public static final String SEND_MSG_TMP="msgTemplate";
    //待办发送短信模板
    public static final String FORDO_SEND_MSG="fordoSendMsg";
    //待办发送短信时，设置哪个环节不发短信
    public static final String IGNORE_ORDO_SEND_MSG="ignoreFordoSendMsg";
    //催办发送短信模板
    public static final String NOTICE_SEND_MSG="noticeSendMsg";
    //超时催办发送短信模板
    public static final String OVERTIME_SEND_MSG="overtimeSendMsg";
    //催签收人反馈公文
    public static final String NOTICE_SIGNER_MSG="signerSendMsg";
    //错误信息发送人员
    public static final String ERROR_DATA_RECIEVER="errorDataReceiver";
    public static final String SIGN_NAME_DATA="signNameData";
    
    //限时公文工作日获取失败时发送人员
    public static final String WORK_DAY_ERROR_RECIEVER="workDayErrorReceiver";

    /** 配置名称 */
    private String 			   configName;
    /** 配置类型 */
    private String 			   configType;
    /** 配置内容 */
    private String 			   configValue;
    
    /** 配置说明 */
    private String 			   configComment;

    public String getConfigName() {
        return configName;
    }

    public void setConfigName(String configName) {
        this.configName = configName;
    }

    public String getConfigValue() {
        return configValue;
    }

    public void setConfigValue(String configValue) {
        this.configValue = configValue;
    }

    public String getConfigComment() {
        return configComment;
    }

    public void setConfigComment(String configComment) {
        this.configComment = configComment;
    }

    public String getConfigType() {
        return configType;
    }

    public void setConfigType(String configType) {
        this.configType = configType;
    }

    public String[] getConfigValueList(){
        if(configValue==null){
            return null;
        }
        if(configValue.contains(",")) {
            return configValue.split(",");
        }
        return new String[]{configValue};
    }
}
