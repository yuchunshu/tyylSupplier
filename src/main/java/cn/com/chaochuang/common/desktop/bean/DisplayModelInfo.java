/*
 * FileName:    DisplayModel.java
 * Description:
 * Company:     南宁超创信息工程有限公司
 * Copyright:   ChaoChuang (c) 2016
 * History:     2016年8月18日 (HM) 1.0 Create
 */

package cn.com.chaochuang.common.desktop.bean;

/**
 *
 * 显示模块
 *
 * @author HM
 *
 */
public class DisplayModelInfo {

    /** 模块名称 */
    private String name;

    /** 模块位置（左left/右right） */
    private String location;

    /** 模块状态（关闭none/展开block）对应css的display:none/block */
    private String status;

    public DisplayModelInfo() {
    };

    /**
     * @param name
     * @param location
     * @param status
     */
    public DisplayModelInfo(String name, String location, String status) {
        super();
        this.name = name;
        this.location = location;
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "{name=\"" + name + "\", location=\"" + location + "\", status=\"" + status + "\"}";
    }

}
