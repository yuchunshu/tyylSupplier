/*
 * FileName:    NotGenerateIdEntity.java
 * Description:
 * Company:     
 * Copyright:    (c) 2016
 * History:     2016年7月23日 (HM) 1.0 Create
 */

package cn.com.chaochuang.common.data.domain;

import java.io.Serializable;

import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * 不自动生成ID
 * 
 * @author HM
 *
 */
@MappedSuperclass
public abstract class NotGenerateIdEntity<ID extends Serializable> implements Serializable {

    private static final long serialVersionUID = -2835403005776895789L;

    @Id
    protected ID              id;

    @Transient
    @JsonIgnore
    public boolean isNew() {
        return null == getId();
    }

    public ID getId() {
        return id;
    }

    public void setId(ID id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object obj) {
        if ((obj instanceof NotGenerateIdEntity) && (id != null)) {
            return id.equals(((NotGenerateIdEntity) obj).id);
        } else {
            return super.equals(obj);
        }
    }

    @Override
    public int hashCode() {
        if (id != null) {
            return id.hashCode();
        } else {
            return super.hashCode();
        }
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
