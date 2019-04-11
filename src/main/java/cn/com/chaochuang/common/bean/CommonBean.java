/**
 * 
 */
package cn.com.chaochuang.common.bean;

/**
 * @author hzr 2016年9月8日
 *
 */
public class CommonBean {
    
    private String       id;
    
    private String       name;

    
    public CommonBean() {
        super();
    }

    public CommonBean(String id, String name) {
        super();
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    
}
