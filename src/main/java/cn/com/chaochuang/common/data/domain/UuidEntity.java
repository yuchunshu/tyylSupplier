package cn.com.chaochuang.common.data.domain;

import java.io.Serializable;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonIgnore;

@MappedSuperclass
public abstract class UuidEntity<ID extends Serializable> implements Serializable {

    private static final long serialVersionUID = -8576323123959946826L;

    @Id
    @GeneratedValue(generator = "uuidgenerator")
    @GenericGenerator(name = "uuidgenerator", strategy = "uuid")
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
        if ((obj instanceof UuidEntity) && (id != null)) {
            return id.equals(((UuidEntity) obj).id);
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
