package cn.com.chaochuang.crm.project.reference;

import javax.persistence.AttributeConverter;

import cn.com.chaochuang.common.dictionary.EnumDictConverter;

public class ProjectCategoryConverter extends EnumDictConverter<ProjectCategory> implements AttributeConverter<ProjectCategory, String> {
}
