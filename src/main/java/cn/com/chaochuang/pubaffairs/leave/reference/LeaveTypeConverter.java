package cn.com.chaochuang.pubaffairs.leave.reference;

import javax.persistence.AttributeConverter;

import cn.com.chaochuang.common.dictionary.EnumDictConverter;

/**
 * @author dengl 2018.08.08
 *
 */
public class LeaveTypeConverter extends EnumDictConverter<LeaveType> implements
AttributeConverter<LeaveType, String>{

}
