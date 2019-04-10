/*
 * FileName:    DocStatisticQueryData.java
 * Description:
 * Company:     南宁超创信息工程有限公司
 * Copyright:   ChaoChuang (c) 2017
 * History:     2017年01月04日 (Shicx) 1.0 Create
 */

package cn.com.chaochuang.doc.remotedocswap.bean;

import java.util.Date;


/** 
 * @ClassName: DocStatisticQueryData 
 * @Description: 公文台账列表查询条件
 * @author: chunshu
 * @date: 2017年7月18日 下午10:31:26  
 */
public class DocStatisticQueryData {

    public static final String QUERY_TYPE_SEND="sendList";
    public static final String QUERY_TYPE_RM_SEND="sendCount";
    public static final String QUERY_TYPE_RECEIVE="receiveList";
    public static final String QUERY_TYPE_RM_RECEIVE="receiveCount";
    public static final String QUERY_TYPE_YWLSH="ywlsh";
    public static final String QUERY_TYPE_LOG="log";
    public static final String QUERY_TYPE_SEND_COUNT="sendCount";
    public static final String QUERY_TYPE_RECEIVE_COUNT="receiveCount";
    public static final String QUERY_TYPE_TOTAL_COUNT="totalCount";
    public static final String QUERY_TYPE_OUTFAILED="outFailed";
    public static final String QUERY_TYPE_WORK_DATE="workDate4Week";
    public static final String QUERY_TYPE_BEAT_DATA="beatData";
    public static final String QUERY_TYPE_ERROR_DOC="errorDoc";

    //查询类型，对应上面的QUERY_TYPE_*
    private String 			   queryType;
    //查询标识字符串，用于区分每次查询
    private String 			   queryUuid;
    private boolean 		   startQuery;

    //以下是接口查询需要的信息
    /** 公文来源 */
    private Integer 		   docFrom = 0;
    /** 发送开始日期 */
    private String 			   startDate;
    /** 发送结束日期 */
    private String 			   endDate;
    /** 每页的记录数 */
    private Integer 		   pageSize;
    /** 获取记录的第几页 */
    private Integer 		   pageIndex;
    private String 			   orderBy;
    /** 公文状态，0：在办，1：已办，2：已撤销，3：已退文，4：超时未办结，5：超时办结，6：错误文，默认空：全部（正常在办、已办的文）*/
    private String 			   docStatus;
    private String 			   ywlsh;
    private String 			   type;

    private Integer 		   totalCount;
    private Integer 		   totalPage;

    //工作日查询
    private Date 			   workCurDate;
    private Integer 		   days;

    public Integer getTotalPage() {
        if(totalCount!=null&&totalCount!=0){
            return (int)Math.ceil(totalCount/(double)this.getPageSize());
        }
        return totalPage;
    }

    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

    public boolean isStartQuery() {
        return startQuery;
    }

    public void setStartQuery(boolean startQuery) {
        this.startQuery = startQuery;
    }

    public String getQueryUuid() {
        if(queryUuid==null||queryUuid.trim()==""){
            return "none";
        }
        return queryUuid;
    }

    public void setQueryUuid(String queryUuid) {
        this.queryUuid = queryUuid;
    }

    public String getQueryType() {
        return queryType;
    }

    public void setQueryType(String queryType) {
        this.queryType = queryType;
    }

    public Integer getDocFrom() {
        if(docFrom==null){
            return 1;
        }
        return docFrom;
    }

    public void setDocFrom(Integer docFrom) {
        this.docFrom = docFrom;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public Integer getPageSize() {
        if(pageSize==null||pageSize<=0){
            return 20;
        }
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getPageIndex() {
        if(pageIndex==null||pageIndex<=0){
            return 1;
        }
        return pageIndex;
    }

    public void setPageIndex(Integer pageIndex) {
        this.pageIndex = pageIndex;
    }

    public String getOrderBy() {
        if(orderBy==null){
            return "";
        }
        return orderBy;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }

    public String getDocStatus() {
        if(docStatus==null){
            return "";
        }
        return docStatus;
    }

    public void setDocStatus(String docStatus) {
        this.docStatus = docStatus;
    }

    public String getYwlsh() {
        return ywlsh;
    }

    public void setYwlsh(String ywlsh) {
        this.ywlsh = ywlsh;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getDays() {
        return days;
    }

    public void setDays(Integer days) {
        this.days = days;
    }

    public Date getWorkCurDate() {
        return workCurDate;
    }

    public void setWorkCurDate(Date workCurDate) {
        this.workCurDate = workCurDate;
    }
}

