/*
 * FileName:    OaPersonalGroupService.java
 * Description:
 * Company:     
 * Copyright:    (c) 2016
 * History:     2016年1月25日 (HM) 1.0 Create
 */

package cn.com.chaochuang.oa.personal.service;

import java.util.List;

import cn.com.chaochuang.common.bean.CommonTreeBean;
import cn.com.chaochuang.common.data.service.CrudRestService;
import cn.com.chaochuang.common.user.domain.SysUser;
import cn.com.chaochuang.oa.personal.bean.GroupEditBean;
import cn.com.chaochuang.oa.personal.bean.GroupShowBean;
import cn.com.chaochuang.oa.personal.domain.OaPersonalGroup;

/**
 * @author HM
 *
 */
public interface OaPersonalGroupService extends CrudRestService<OaPersonalGroup, Long> {

    public Long saveGroup(GroupEditBean bean);

    public List<GroupShowBean> getShowList(List<OaPersonalGroup> datas);

    public boolean deleteGroups(String ids);

    public GroupShowBean getGroupForShow(Long id);

    public List<SysUser> getUsersByGroupId(Long id);

    /**
     * 邮件用bean
     * 
     * @return
     */
    public List<CommonTreeBean> mailGroupTree();
}
