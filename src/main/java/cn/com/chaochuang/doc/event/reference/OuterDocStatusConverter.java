package cn.com.chaochuang.doc.event.reference;

import javax.persistence.AttributeConverter;

import cn.com.chaochuang.common.dictionary.EnumDictConverter;

/**
 * @author yuchunshu 2017.11.25
 *
 */
public class OuterDocStatusConverter extends EnumDictConverter<OuterDocStatus> implements
                AttributeConverter<OuterDocStatus, String> {

}
