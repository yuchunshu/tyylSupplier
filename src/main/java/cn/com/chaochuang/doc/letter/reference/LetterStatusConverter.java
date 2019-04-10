package cn.com.chaochuang.doc.letter.reference;

import javax.persistence.AttributeConverter;

import cn.com.chaochuang.common.dictionary.EnumDictConverter;

/**
 *
 * @author LJX
 *
 */
public class LetterStatusConverter extends EnumDictConverter<LetterStatus> implements
                AttributeConverter<LetterStatus, String> {
}
