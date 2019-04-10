/*
 * FileName:    DocsFolderTreeBean.java
 * Description:
 * Company:     南宁超创信息工程有限公司
 * Copyright:   ChaoChuang (c) 2016
 * History:     2016年7月13日 (HM) 1.0 Create
 */

package cn.com.chaochuang.oa.docs.bean;

import java.util.List;

import org.dozer.Mapping;

/**
 * @author HM
 *
 */
public class DocsFolderTreeBean {

    private Long                         id;

    @Mapping("folderName")
    private String                       text;

    private String                       state;

    private String                       iconCls;

    private List<DocsFolderTreeBean>     children;

    /**目录全称，即绝对目录，如aaa/bbb/ccc */
    private String                       fullFloderName;

    /**目录编码，如001001001，为方便向下级包含搜索*/
    private String                       folderCode;


    public DocsFolderTreeBean() {

    }

    /**
     * @param id
     * @param text
     * @param state
     */
    public DocsFolderTreeBean(Long id, String text, String state) {
        super();
        this.id = id;
        this.text = text;
        this.state = state;
    }

    /**
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return the text
     */
    public String getText() {
        return text;
    }

    /**
     * @param text the text to set
     */
    public void setText(String text) {
        this.text = text;
    }

    /**
     * @return the state
     */
    public String getState() {
        return state;
    }

    /**
     * @param state the state to set
     */
    public void setState(String state) {
        this.state = state;
    }

    /**
     * @return the iconCls
     */
    public String getIconCls() {
        return iconCls;
    }

    /**
     * @param iconCls the iconCls to set
     */
    public void setIconCls(String iconCls) {
        this.iconCls = iconCls;
    }

    /**
     * @return the children
     */
    public List<DocsFolderTreeBean> getChildren() {
        return children;
    }

    /**
     * @param children the children to set
     */
    public void setChildren(List<DocsFolderTreeBean> children) {
        this.children = children;
    }

    /**
     * @return the fullFloderName
     */
    public String getFullFloderName() {
        return fullFloderName;
    }

    /**
     * @param fullFloderName the fullFloderName to set
     */
    public void setFullFloderName(String fullFloderName) {
        this.fullFloderName = fullFloderName;
    }

    /**
     * @return the folderCode
     */
    public String getFolderCode() {
        return folderCode;
    }

    /**
     * @param folderCode the folderCode to set
     */
    public void setFolderCode(String folderCode) {
        this.folderCode = folderCode;
    }


}
