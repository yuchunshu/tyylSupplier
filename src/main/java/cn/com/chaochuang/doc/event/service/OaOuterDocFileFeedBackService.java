

package cn.com.chaochuang.doc.event.service;




import java.util.Map;

import cn.com.chaochuang.common.data.service.CrudRestService;
import cn.com.chaochuang.doc.event.bean.OuterDocFileEditBean;
import cn.com.chaochuang.doc.event.domain.OaOuterDocFileFeedBack;

/**
 * @author yuchunshu 2017.11.25
 *
 */

public interface OaOuterDocFileFeedBackService extends CrudRestService<OaOuterDocFileFeedBack, String> {


	/** 
     * @Title: findOuterDocMapByOuterId 
     * @Description: 查询反馈详情
     * @param outerId
     * @return
     * @return: Map
     */
    Map findOuterDocMapByOuterId(String outerId);
    
    /** 
     * @Title: saveFeedBack 
     * @Description: 保存公文反馈
     * @param bean
     * @return
     * @return: boolean
     */
    boolean saveFeedBack(OuterDocFileEditBean bean);

}