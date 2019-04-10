/*
 * FileName:    NameAndIdEntity.java
 * Description:
 * Company:     南宁超创信息工程有限公司
 * Copyright:   ChaoChuang (c) 2015
 * History:     2015年7月24日 (Shicx) 1.0 Create
 */

package cn.com.chaochuang.webservice.bean;

/**
 * @author Shicx 只包含名称和对应id的实体，用于页面显示
 */
public class NameAndIdEntity {
    private String id;
    private String name;

    /**
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id
     *            the id to set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name
     *            the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

}
