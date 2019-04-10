package cn.com.chaochuang.sms.bean;

import org.dozer.Mapping;

public class SmsPhraseInfo {

	private Long smsPhraseId;
	private Long smsPhraseTypeId;
	@Mapping("smsPhraseType.phraseTypeName")
	private String phraseTypeName;
	private String userName;
	private String phraseName;
	private Long sortFlag;

	/** default constructor */
	public SmsPhraseInfo() {
	}

	public Long getSmsPhraseId() {
		return smsPhraseId;
	}

	public void setSmsPhraseId(Long smsPhraseId) {
		this.smsPhraseId = smsPhraseId;
	}
	public Long getSmsPhraseTypeId() {
		return smsPhraseTypeId;
	}

	public void setSmsPhraseTypeId(Long smsPhraseTypeId) {
		this.smsPhraseTypeId = smsPhraseTypeId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPhraseName() {
		return phraseName;
	}

	public void setPhraseName(String phraseName) {
		this.phraseName = phraseName;
	}

	public Long getSortFlag() {
		return sortFlag;
	}

	public void setSortFlag(Long sortFlag) {
		this.sortFlag = sortFlag;
	}

	public String getPhraseTypeName() {
		return phraseTypeName;
	}

	public void setPhraseTypeName(String phraseTypeName) {
		this.phraseTypeName = phraseTypeName;
	}
}