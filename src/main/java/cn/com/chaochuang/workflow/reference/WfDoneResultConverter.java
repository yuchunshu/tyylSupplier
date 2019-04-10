package cn.com.chaochuang.workflow.reference;

import javax.persistence.AttributeConverter;

import cn.com.chaochuang.common.dictionary.EnumDictConverter;

public class WfDoneResultConverter extends EnumDictConverter<WfDoneResult>
                implements AttributeConverter<WfDoneResult, String> {

}
