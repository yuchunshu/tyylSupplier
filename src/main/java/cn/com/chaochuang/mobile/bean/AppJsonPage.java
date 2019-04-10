/*
 * FileName:    JsonPage.java
 * Description:
 * Company:     南宁超创信息工程有限公司
 * Copyright:   ChaoChuang (c) 2016
 * History:     2016年5月24日 (HM) 1.0 Create
 */

package cn.com.chaochuang.mobile.bean;

import java.util.List;

/**
 * @author hzr 2017.7.20
 *
 */
public class AppJsonPage {

    /** 总记录数 */
    private Long    total     = 0L;

    /** 总页数 */
    private Long    pageCount = 0L;

    /** 当前页 */
    private Long    curPage   = 0L;

    /** 数据list */
    private List<?> list;



    public AppJsonPage() {
        super();
    }

    public AppJsonPage(Long total, List<?> list) {
        super();
        this.total = total;
        this.list = list;
    }

    public AppJsonPage(Long total, Long pageCount, Long curPage, List<?> list) {
        super();
        this.total = total;
        this.pageCount = pageCount;
        this.curPage = curPage;
        this.list = list;
    }

	public Long getTotal() {
		return total;
	}

	public void setTotal(Long total) {
		this.total = total;
	}

	public Long getPageCount() {
		return pageCount;
	}

	public void setPageCount(Long pageCount) {
		this.pageCount = pageCount;
	}

	public Long getCurPage() {
		return curPage;
	}

	public void setCurPage(Long curPage) {
		this.curPage = curPage;
	}

	public List<?> getList() {
		return list;
	}

	public void setList(List<?> list) {
		this.list = list;
	}


}
