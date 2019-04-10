package cn.com.chaochuang.sms.domain;

import cn.com.chaochuang.common.data.domain.LongIdEntity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
// 区局oracle数据库
//@Table(name = "GX_FDA_SYS.SMS_PHRASE")
@Table(name = "SMS_PHRASE")
public class SmsPhrase implements Serializable {

	@Id
	// 区局oracle数据库 使用序列
//	@GeneratedValue(strategy = GenerationType.SEQUENCE)
//	@SequenceGenerator(name = "SMS_PHRASE_ID")
	// 本地mysql
	@GeneratedValue
	private Long smsPhraseId;
	private Long smsPhraseTypeId;
	@ManyToOne
	@JoinColumn(name = "smsPhraseTypeId", insertable = false, updatable = false)
	private SmsPhraseType smsPhraseType;
	private Long userId;
	private String userName;
	private String phraseName;
	private Long sortFlag;

	/** default constructor */
	public SmsPhrase() {
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

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
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

	public SmsPhraseType getSmsPhraseType() {
		return smsPhraseType;
	}

	public void setSmsPhraseType(SmsPhraseType smsPhraseType) {
		this.smsPhraseType = smsPhraseType;
	}
}