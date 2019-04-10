package cn.com.chaochuang.workflow.reference;

import javax.persistence.AttributeConverter;

import cn.com.chaochuang.common.dictionary.EnumDictConverter;

public class WfInstStatusConverter extends EnumDictConverter<WfInstStatus>
                implements AttributeConverter<WfInstStatus, String> {

}
