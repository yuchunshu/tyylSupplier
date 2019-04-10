package cn.com.chaochuang.doc.event.bean;

/**
 * 部门函件转收文
 * 
 * @author yuandl 2016-12-07
 *
 */
public class OaDocFileFromLetterBean {

    /**  */
    private String letterId;

    /**  */
    private String letterReceiverId;

    public String getLetterId() {
        return letterId;
    }

    public void setLetterId(String letterId) {
        this.letterId = letterId;
    }

    public String getLetterReceiverId() {
        return letterReceiverId;
    }

    public void setLetterReceiverId(String letterReceiverId) {
        this.letterReceiverId = letterReceiverId;
    }

}
