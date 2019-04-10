package cn.com.chaochuang.sms.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
// 区局oracle数据库
//@Table(name = "GX_FDA_SYS.SMS_PHRASE")
@Table(name = "SMS_PHRASE_TYPE")
public class SmsPhraseType implements Serializable {

	@Id
	// 区局oracle数据库 使用序列
	//	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	//	@SequenceGenerator(name = "SMS_PHRASE_TYPE_ID")
	// 本地mysql
	@GeneratedValue
	private Long smsPhraseTypeId;
	private String phraseTypeName;
	private Long userId;
	private String userName;

	/** default constructor */
	public SmsPhraseType() {
	}

	public Long getSmsPhraseTypeId() {
		return smsPhraseTypeId;
	}

	public void setSmsPhraseTypeId(Long smsPhraseTypeId) {
		this.smsPhraseTypeId = smsPhraseTypeId;
	}

	public String getPhraseTypeName() {
		return phraseTypeName;
	}

	public void setPhraseTypeName(String phraseTypeName) {
		this.phraseTypeName = phraseTypeName;
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

}