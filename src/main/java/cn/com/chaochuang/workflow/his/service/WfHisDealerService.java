package cn.com.chaochuang.workflow.his.service;

import cn.com.chaochuang.common.data.service.CrudRestService;
import cn.com.chaochuang.workflow.his.bean.HisDealerEditBean;
import cn.com.chaochuang.workflow.his.domain.WfHisDealer;

/**
 * @author hzr 2016-12-24
 *
 */
public interface WfHisDealerService extends CrudRestService<WfHisDealer, String> {

    /**
     * 保存环节历史选择
     * 
     * @param bean
     * @return
     */
    public String saveHisDealer(HisDealerEditBean bean);

    /**
     * 更新环节历史选择
     * 
     * @param bean
     */
    public void updateHisDealer(HisDealerEditBean bean);

    /**
     * 删除环节历史选择
     * 
     * @param id
     */
    public void delHisDealer(String id);
}
