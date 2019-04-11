package cn.com.chaochuang.common.desktop.bean;

import cn.com.chaochuang.oa.notice.reference.SmsFlag;

/**
 * @author HM
 *
 */
public class UserProfileInfo {

    private Long    id;

    private String  background;

    private SmsFlag smsFlag;

    private String  displayModels;

    private String  themes;

    /**
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id
     *            the id to set
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return the background
     */
    public String getBackground() {
        return background;
    }

    /**
     * @param background
     *            the background to set
     */
    public void setBackground(String background) {
        this.background = background;
    }

    /**
     * @return the smsFlag
     */
    public SmsFlag getSmsFlag() {
        return smsFlag;
    }

    /**
     * @param smsFlag
     *            the smsFlag to set
     */
    public void setSmsFlag(SmsFlag smsFlag) {
        this.smsFlag = smsFlag;
        if (smsFlag == null) {
            this.smsFlag = SmsFlag.不提醒;
        }
    }

    /**
     * @return the displayModels
     */
    public String getDisplayModels() {
        return displayModels;
    }

    /**
     * @param displayModels
     *            the displayModels to set
     */
    public void setDisplayModels(String displayModels) {
        this.displayModels = displayModels;
    }

    /**
     * @return the themes
     */
    public String getThemes() {
        return themes;
    }

    /**
     * @param themes
     *            the themes to set
     */
    public void setThemes(String themes) {
        this.themes = themes;
    }

}
