package cn.com.chaochuang.common.data.domain;

import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class StringUuidEntity extends UuidEntity<String> {

    private static final long serialVersionUID = -8576323123959946828L;

}
