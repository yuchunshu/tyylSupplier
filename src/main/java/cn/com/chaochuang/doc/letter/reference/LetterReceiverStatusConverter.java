package cn.com.chaochuang.doc.letter.reference;

import javax.persistence.AttributeConverter;

import cn.com.chaochuang.common.dictionary.EnumDictConverter;

/**
 *
 * @author LJX
 *
 */
public class LetterReceiverStatusConverter extends EnumDictConverter<LetterReceiverStatus> implements
                AttributeConverter<LetterReceiverStatus, String> {

}
