package cn.com.chaochuang.doc.archive.reference;

import javax.persistence.AttributeConverter;

import cn.com.chaochuang.common.dictionary.EnumDictConverter;

/**
 * @author dengl 2017.11.30
 *
 */
public class BorrowStatusConverter extends EnumDictConverter<BorrowStatus> implements AttributeConverter<BorrowStatus,String>{

}
