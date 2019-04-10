package cn.com.chaochuang.common.attach.bean;

public class AttachBean {
	/** 附件保存名 */
    private String            saveName;
    /** 附件原名 */
    private String            trueName;
    /** 附件保存路径 */
    private String            savePath;
	public String getSaveName() {
		return saveName;
	}
	public void setSaveName(String saveName) {
		this.saveName = saveName;
	}
	public String getTrueName() {
		return trueName;
	}
	public void setTrueName(String trueName) {
		this.trueName = trueName;
	}
	public String getSavePath() {
		return savePath;
	}
	public void setSavePath(String savePath) {
		this.savePath = savePath;
	}
    
    
}
