package cn.com.chaochuang.workflow.reference;

import javax.persistence.AttributeConverter;

import cn.com.chaochuang.common.dictionary.EnumDictConverter;

public class WfApprResultConverter extends EnumDictConverter<WfApprResult>
                implements AttributeConverter<WfApprResult, String> {

}
