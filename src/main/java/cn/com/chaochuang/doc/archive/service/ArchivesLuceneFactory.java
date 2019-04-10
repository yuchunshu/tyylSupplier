package cn.com.chaochuang.doc.archive.service;

import java.io.IOException;

import cn.com.chaochuang.doc.archive.bean.LuceneEditBean;

/**
 * 
 * @author dengl 2017.12.08
 *
 */
public abstract class ArchivesLuceneFactory {
	/** 工厂类静态对象 */
    private static ArchivesLuceneFactory me;

    /**
     * 获取全文检索实例
     * @return
     */
    public static ArchivesLuceneFactory getInstance() {
        return me;
    }

    /**
     * 设置全文检索实例
     *
     * @param instance
     */
    protected void setInstance(ArchivesLuceneFactory instance) {
        me = instance;
    }

    /**
     * 建立文件索引
     *
     * @param info
     * @return
     * @throws IOException
     */
    abstract public boolean writeIndex(LuceneEditBean bean) throws Exception;

    /**
     * 删除索引
     *
     * @param info
     * @return
     * @throws IOException
     */
    abstract public boolean removeIndex(LuceneEditBean bean) throws IOException;
}
