package cn.com.chaochuang.doc.remotedocswap.bean;

import java.util.List;


/** 
 * @ClassName: DocStatisticResult 
 * @Description: 公文台账列表返回结果
 * @author: chunshu
 * @date: 2017年7月18日 下午10:31:40  
 */
public class DocStatisticResult {

    public static final String 	   RESULT_SUCCESS = "success";
    public static final String 	   RESULT_FAIL = "fail";

    private String 			   	   success;
    private List<DocStatisticData> result;
    private String 				   reason;
    private Integer 			   totalCount;

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public List<DocStatisticData> getResult() {
        return result;
    }

    public void setResult(List<DocStatisticData> result) {
        this.result = result;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }
}
