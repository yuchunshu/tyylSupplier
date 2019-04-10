package cn.com.chaochuang.doc.event.reference;

import javax.persistence.AttributeConverter;

import cn.com.chaochuang.common.dictionary.EnumDictConverter;

public class DocCateConverter extends EnumDictConverter<DocCate> implements
                AttributeConverter<DocCate, String> {
}
