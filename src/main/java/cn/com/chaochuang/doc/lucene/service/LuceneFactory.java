package cn.com.chaochuang.doc.lucene.service;

import java.io.IOException;

import cn.com.chaochuang.doc.lucene.bean.LuceneInfo;

/**
 * 
 * @author LJX
 *
 */
public abstract class LuceneFactory {
	/** 工厂类静态对象 */
    private static LuceneFactory me;

    /**
     * 获取全文检索实例
     * @return
     */
    public static LuceneFactory getInstance() {
        return me;
    }

    /**
     * 设置全文检索实例
     *
     * @param instance
     */
    protected void setInstance(LuceneFactory instance) {
        me = instance;
    }

    /**
     * 建立文件索引
     *
     * @param info
     * @return
     * @throws IOException
     */
    abstract public boolean writeIndex(LuceneInfo info) throws Exception;

    /**
     * 删除索引
     *
     * @param info
     * @return
     * @throws IOException
     */
    abstract public boolean removeIndex(LuceneInfo info) throws IOException;
}
