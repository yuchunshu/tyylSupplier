package cn.com.chaochuang.doc.event.bean;

import java.util.Date;

import cn.com.chaochuang.doc.event.domain.OaDocFile;

/**
 * 公文转办信息
 * 
 * @author yuandl 2016-12-01
 *
 */
public class OaDocFileDispatchBean {

    /** 公文信息 */
    private OaDocFile docFile;

    /** 转办说明 */
    private String    content;

    /** 转办部门names */
    private String    incepterDeptNames;

    /** 转办部门ids */
    private String    incepterDepts;

    /** 处理时限天数 */
    private Integer   limitDay;

    /** 处理时限 */
    private Date      expiryDate;

    public OaDocFile getDocFile() {
        return docFile;
    }

    public void setDocFile(OaDocFile docFile) {
        this.docFile = docFile;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getIncepterDeptNames() {
        return incepterDeptNames;
    }

    public void setIncepterDeptNames(String incepterDeptNames) {
        this.incepterDeptNames = incepterDeptNames;
    }

    public String getIncepterDepts() {
        return incepterDepts;
    }

    public void setIncepterDepts(String incepterDepts) {
        this.incepterDepts = incepterDepts;
    }

    public Integer getLimitDay() {
        return limitDay;
    }

    public void setLimitDay(Integer limitDay) {
        this.limitDay = limitDay;
    }

    public Date getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;
    }

}
