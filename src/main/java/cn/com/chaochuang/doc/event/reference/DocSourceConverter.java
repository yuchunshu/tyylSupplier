package cn.com.chaochuang.doc.event.reference;

import javax.persistence.AttributeConverter;

import cn.com.chaochuang.common.dictionary.EnumDictConverter;

public class DocSourceConverter extends EnumDictConverter<DocSource> implements
                AttributeConverter<DocSource, String> {
}
