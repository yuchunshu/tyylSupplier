/*
 * FileName:    DisplayType.java
 * Description:
 * Company:     
 * Copyright:    (c) 2016
 * History:     2016年1月25日 (HM) 1.0 Create
 */

package cn.com.chaochuang.common.reference;

import cn.com.chaochuang.common.dictionary.IDictionary;
import cn.com.chaochuang.common.dictionary.support.DictionaryRefresher;

/**
 * @author pangj
 *
 */
public enum NationType implements IDictionary {

    汉族("0", "汉族"), 壮族("1", "壮族"), 回族("2", "回族"), 苗族("3", "苗族"),傣族("4", "傣族"), 阿昌族 ("5", "阿昌族 "),
    白族("6", "白族"), 保安族("7", "保安族"), 布朗族("8", "布朗族"), 布依族("9","布依族"),朝鲜族("10", "朝鲜族"), 达斡尔族("11", "达斡尔族"),
    德昂族("12", "德昂族"), 东乡族("13", "东乡族"), 侗族("14", "侗族"), 独龙族("15","独龙族"),鄂温克族("16", "鄂温克族"), 鄂伦春族("17", "鄂伦春族"),
    俄罗斯族("18", "俄罗斯族"), 高山族("19", "高山族"), 哈萨克族("20", "哈萨克族"), 哈尼族("21","哈尼族"),赫哲族 ("22", "赫哲族 "), 基诺族("23", "基诺族"),
    景颇族("24", "景颇族"), 京族("25", "京族"), 柯尔克孜族("26", "柯尔克孜族"), 博仡佬族("27","仡佬族"),拉祜族("28", "拉祜族"), 黎族("29", "黎族"),
    傈僳族("30", "傈僳族"), 珞巴族("31", "珞巴族"), 满族("32", "满族"), 毛南族("33","毛南族"),门巴族("34", "门巴族"), 蒙古族("35", "蒙古族"),
    仫佬族("36", "仫佬族"), 纳西族("37", "纳西族"), 怒族("38", "怒族"), 普米族("39","普米族"),羌族("40", "羌族"), 撒拉族("41", "撒拉族"),
    畲族("42", "畲族"), 水族("43", "水族"), 塔塔尔族("44", "塔塔尔族"), 塔吉克族("45","塔吉克族"),土族("46", "土族"), 土家族("47", "土家族"),
    佤族("48", "佤族"), 维吾尔族("49", "维吾尔族"), 乌孜别克族("50", "乌孜别克族"), 锡伯族("51","锡伯族"),瑶族("52", "瑶族"), 彝族("53", "彝族"),
    裕固族("54", "裕固族"),藏族("55", "藏族");

    private String key;
    private String value;

    private NationType(String key) {
        this(key, null);
        DictionaryRefresher.getInstance().refreshIDictionary(this);
    }

    private NationType(String key, String value) {
        this.key = key;
        this.value = value;
        DictionaryRefresher.getInstance().refreshIDictionary(this);
    }

    @Override
    public String getKey() {
        return key;
    }

    @Override
    public String getValue() {
        return (null == value) ? name() : value;
    }

    @Override
    public Object getObject() {
        return this;
    }

    @Override
    public String toString() {
        return this.key;
    }

}
