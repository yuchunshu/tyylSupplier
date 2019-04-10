package cn.com.chaochuang.workflow.reference;

import javax.persistence.AttributeConverter;

import cn.com.chaochuang.common.dictionary.EnumDictConverter;

public class WfFuncButtonConverter extends EnumDictConverter<WfFuncButton>
                implements AttributeConverter<WfFuncButton, String> {

}
