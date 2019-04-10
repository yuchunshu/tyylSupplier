/**
 *
 */
package cn.com.chaochuang.common.desktop.domain;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.Table;

import cn.com.chaochuang.common.data.domain.NotGenerateLongIdEntity;
import cn.com.chaochuang.common.dictionary.IDictionary;
import cn.com.chaochuang.common.lookup.annotation.LookUp;
import cn.com.chaochuang.oa.notice.reference.SmsFlag;
import cn.com.chaochuang.oa.notice.reference.SmsFlagConverter;

/**
 * @author hzr 2016年7月21日 
 * 用户个人桌面参数设置 
 * 当userId=0，表示是默认的设置，也就是当用户没有设置个人桌面时调用userId=0的记录来显示。
 */
@Entity
@Table(name = "sys_user_profile")
@LookUp
@AttributeOverrides({ @AttributeOverride(name = "id", column = @Column(name = "user_id")) })
public class UserProfile extends NotGenerateLongIdEntity implements IDictionary {

    private static final long serialVersionUID = -4311211405715240885L;

    /** 桌面背景图 */
    private String            background;

    /** 是否接收短信提醒 0=不接收，1=接收 */
    @Convert(converter = SmsFlagConverter.class)
    private SmsFlag           smsFlag;

    /** 桌面显示的模块，如待办事项、通知公告等，包括排序信息。jason格式存储  */
    private String            displayModels;

    /** 个人主题（样式） */
    private String            themes;

    public UserProfile() {

    }

    /**
     * @param background
     * @param smsFlag
     * @param displayModels
     */
    public UserProfile(Long id, String background, SmsFlag smsFlag, String displayModels) {
        super();
        this.id = id;
        this.background = background;
        this.smsFlag = smsFlag;
        this.displayModels = displayModels;
        this.themes = "default";
    }

    public String getBackground() {
        return background;
    }

    public void setBackground(String background) {
        this.background = background;
    }

    public SmsFlag getSmsFlag() {
        return smsFlag;
    }

    public void setSmsFlag(SmsFlag smsFlag) {
        this.smsFlag = smsFlag;
    }

    public String getDisplayModels() {
        return displayModels;
    }

    public void setDisplayModels(String displayModels) {
        this.displayModels = displayModels;
    }

    public String getThemes() {
        return themes;
    }

    public void setThemes(String themes) {
        this.themes = themes;
    }

    @Override
    public String getKey() {
        return this.id.toString();
    }

    @Override
    public String getValue() {
        return this.themes;
    }

    @Override
    public Object getObject() {
        return this;
    }

}
