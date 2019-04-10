

package cn.com.chaochuang.doc.event.service;




import java.util.List;

import cn.com.chaochuang.common.attach.domain.SysAttach;
import cn.com.chaochuang.common.bean.ReturnBean;
import cn.com.chaochuang.common.data.service.CrudRestService;
import cn.com.chaochuang.common.user.domain.SysUser;
import cn.com.chaochuang.doc.event.bean.OaDocFileEditBean;
import cn.com.chaochuang.doc.event.bean.OuterDocFileEditBean;
import cn.com.chaochuang.doc.event.domain.OaOuterDocFile;

/**
 * @author yuchunshu 2017.11.25
 *
 */

public interface OaOuterDocFileService extends CrudRestService<OaOuterDocFile, String> {

    public ReturnBean saveOuterDocFile(OaDocFileEditBean bean);
    
    /**
     * 签收公文
     */
    boolean signOuterDoc(OuterDocFileEditBean bean, SysUser currentUser);
    
    /**
     * 批量签收公文
     */
    boolean signOuterDocList(OuterDocFileEditBean bean, SysUser currentUser);
    
    /** 
     * 转内部公文的时候复制三级贯通的附件到系统附件表
     * 
     */
    public List<SysAttach> copyAttach(String outerId);

}