package cn.com.chaochuang.common.data.service;

import cn.com.chaochuang.common.data.domain.UuidEntity;

public abstract class SimpleUuidCrudRestService<T_Entity extends UuidEntity<String>> extends
                SimpleCrudRestService<T_Entity, String> {
}
