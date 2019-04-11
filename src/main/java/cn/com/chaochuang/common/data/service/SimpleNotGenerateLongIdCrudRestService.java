package cn.com.chaochuang.common.data.service;

import cn.com.chaochuang.common.data.domain.NotGenerateIdEntity;

public abstract class SimpleNotGenerateLongIdCrudRestService<T_Entity extends NotGenerateIdEntity<Long>> extends
                SimpleCrudRestService<T_Entity, Long> {
}
