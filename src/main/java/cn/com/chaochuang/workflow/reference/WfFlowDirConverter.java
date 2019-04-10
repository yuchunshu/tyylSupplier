package cn.com.chaochuang.workflow.reference;

import javax.persistence.AttributeConverter;

import cn.com.chaochuang.common.dictionary.EnumDictConverter;

public class WfFlowDirConverter extends EnumDictConverter<WfFlowDir> implements AttributeConverter<WfFlowDir, String> {

}
