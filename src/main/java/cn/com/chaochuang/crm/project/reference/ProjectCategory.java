package cn.com.chaochuang.crm.project.reference;

import cn.com.chaochuang.common.dictionary.IDictionary;
import cn.com.chaochuang.common.dictionary.support.DictionaryRefresher;

public enum ProjectCategory implements IDictionary {

	宣传专题片("1", "宣传（专题）片"), 微电影电影短片("2", "微电影（电影短片）"), 视频后期("3", "视频后期"), 文化建设("4", "文化建设"), 
	展馆展陈("5", "展馆展陈"), 物料制作("6", "物料制作"), 平面("7", "平面"), 规划("8", "规划"), 多媒体集成("9", "多媒体集成"), 
	软件开发("10", "软件开发"), 舞台工程("11", "舞台工程"), 策划活动执行("12", "策划活动执行"), 媒体推广("13", "媒体推广"), 产品销售("14", "产品销售");

	
    private String key;
    private String value;

    private ProjectCategory(String key) {
        this(key, null);
        DictionaryRefresher.getInstance().refreshIDictionary(this);
    }

    private ProjectCategory(String key, String value) {
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
