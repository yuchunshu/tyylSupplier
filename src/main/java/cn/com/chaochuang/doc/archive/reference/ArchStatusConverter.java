package cn.com.chaochuang.doc.archive.reference;

import javax.persistence.AttributeConverter;

import cn.com.chaochuang.common.dictionary.EnumDictConverter;

/**
 * @author dengl 2017.11.29
 *
 */
public class ArchStatusConverter extends EnumDictConverter<ArchStatus>  implements AttributeConverter<ArchStatus,String>{

}
