/*
 * FileName:    OaPersonalGroupServiceImpl.java
 * Description:
 * Company:     南宁超创信息工程有限公司
 * Copyright:   ChaoChuang (c) 2016
 * History:     2016年1月25日 (HM) 1.0 Create
 */

package cn.com.chaochuang.oa.personal.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.com.chaochuang.common.bean.CommonTreeBean;
import cn.com.chaochuang.common.data.repository.SimpleDomainRepository;
import cn.com.chaochuang.common.data.service.SimpleLongIdCrudRestService;
import cn.com.chaochuang.common.security.util.UserTool;
import cn.com.chaochuang.common.user.domain.SysUser;
import cn.com.chaochuang.common.user.service.SysUserService;
import cn.com.chaochuang.common.util.Tools;
import cn.com.chaochuang.common.attach.service.SysAttachService;
import cn.com.chaochuang.oa.personal.bean.GroupEditBean;
import cn.com.chaochuang.oa.personal.bean.GroupShowBean;
import cn.com.chaochuang.oa.personal.domain.OaPersonalGroup;
import cn.com.chaochuang.oa.personal.domain.OaPersonalGroupMember;
import cn.com.chaochuang.oa.personal.repository.OaPersonalGroupRepository;

/**
 * @author HM
 *
 */
@Service
@Transactional
public class OaPersonalGroupServiceImpl extends SimpleLongIdCrudRestService<OaPersonalGroup> implements
                OaPersonalGroupService {

    private SimpleDateFormat             sdf = new SimpleDateFormat("yyyy-MM-dd");

    @Autowired
    private OaPersonalGroupRepository    repository;

    @Autowired
    private OaPersonalGroupMemberService groupMemberService;

    @Autowired
    private SysUserService               userService;

    @Autowired
    private SysAttachService             attachService;

    @Override
    public SimpleDomainRepository<OaPersonalGroup, Long> getRepository() {
        return this.repository;
    }

    @Override
    public Long saveGroup(GroupEditBean bean) {
        if (bean != null) {
            OaPersonalGroup group = null;
            if (bean.getId() != null) {
                group = this.findOne(bean.getId());
                Set<OaPersonalGroupMember> set = group.getMemberSet();
                if (set != null && set.size() > 0) {
                    for (OaPersonalGroupMember m : set) {
                        this.groupMemberService.delete(m);
                    }
                }
            }
            if (group == null) {
                group = new OaPersonalGroup();
                group.setCreateDate(new Date());
                group.setUser((SysUser) UserTool.getInstance().getCurrentUser());
            }
            group.setGroupName(bean.getGroupName());
            group.setOrderNum(bean.getOrderNum());
            this.repository.save(group);
            String memberIds = bean.getGroupMemberIds();
            if (StringUtils.isNotBlank(memberIds)) {
                String[] idArr = memberIds.split(",");
                for (String idStr : idArr) {
                    Long id = Long.valueOf(idStr);
                    OaPersonalGroupMember member = new OaPersonalGroupMember();
                    member.setGroupId(group.getId());
                    member.setMember(this.userService.findOne(id));
                    this.groupMemberService.persist(member);
                }
            }
            return group.getId();
        }
        return null;
    }

    @Override
    public List<GroupShowBean> getShowList(List<OaPersonalGroup> datas) {
        List<GroupShowBean> returnList = new ArrayList<GroupShowBean>();
        if (datas != null && datas.size() > 0) {

            for (OaPersonalGroup data : datas) {
                GroupShowBean bean = new GroupShowBean();
                bean.setId(data.getId());
                bean.setGroupName(data.getGroupName());
                bean.setOrderNum(data.getOrderNum());
                Date createDate = data.getCreateDate();
                if (createDate != null) {
                    bean.setCreateDateShow(sdf.format(createDate));
                }
                SysUser user = data.getUser();
                if (user != null) {
                    bean.setUserName(user.getUserName());
                }
                Set<OaPersonalGroupMember> members = data.getMemberSet();
                if (members != null && members.size() > 0) {
                    String memberNames = "";
                    for (OaPersonalGroupMember m : members) {
                        memberNames += m.getMember().getUserName() + ",";
                    }
                    if (StringUtils.isNoneBlank(memberNames)) {
                        memberNames = memberNames.substring(0, memberNames.length() - 1);
                        if (memberNames.length() > 30) {
                            memberNames = memberNames.substring(0, 30) + "...";
                        }
                        bean.setGroupMembers(memberNames);
                    }
                }
                returnList.add(bean);

            }
        }
        return returnList;
    }

    @Override
    public boolean deleteGroups(String ids) {
        if (StringUtils.isNotBlank(ids)) {
            String[] idArr = ids.split(",");
            for (String id : idArr) {
                OaPersonalGroup group = this.repository.findOne(Long.valueOf(id));
                if (group != null) {
                    Set<OaPersonalGroupMember> members = group.getMemberSet();
                    if (members != null && members.size() > 0) {
                        for (OaPersonalGroupMember m : members) {
                            this.groupMemberService.delete(m); // 删除群组成员
                        }
                    }
                    this.repository.delete(group);
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public GroupShowBean getGroupForShow(Long id) {
        if (id != null) {
            OaPersonalGroup group = this.repository.findOne(id);
            if (group != null) {
                GroupShowBean returnBean = new GroupShowBean();
                returnBean.setGroupName(group.getGroupName());
                returnBean.setId(group.getId());
                returnBean.setOrderNum(group.getOrderNum());
                if (group.getUser() != null) {
                    returnBean.setUserName(group.getUser().getUserName());
                }
                if (group.getCreateDate() != null) {
                    returnBean.setCreateDateShow(sdf.format(group.getCreateDate()));
                }
                Set<OaPersonalGroupMember> members = group.getMemberSet();
                String names = "";
                String ids = "";
                if (members != null && members.size() > 0) {
                    for (OaPersonalGroupMember m : members) {
                        names += m.getMember().getUserName() + ",";
                        ids += m.getMember().getId().toString() + ",";
                    }
                    if (StringUtils.isNotBlank(names)) {
                        names = names.substring(0, names.length() - 1);
                    }
                    if (StringUtils.isNotBlank(ids)) {
                        ids = ids.substring(0, ids.length() - 1);
                    }
                }
                returnBean.setGroupMemberIds(ids);
                returnBean.setGroupMembers(names);

                return returnBean;
            }
        }
        return null;
    }

    @Override
    public List<SysUser> getUsersByGroupId(Long id) {
        List<SysUser> returnList = new ArrayList<SysUser>();
        OaPersonalGroup group = this.findOne(id);
        if (group != null) {
            Set<OaPersonalGroupMember> members = group.getMemberSet();
            if (members != null && members.size() > 0) {
                for (OaPersonalGroupMember m : members) {
                    returnList.add(m.getMember());
                }
            }
        }
        return returnList;
    }

    @Override
    public List<CommonTreeBean> mailGroupTree() {
        List<OaPersonalGroup> groups = repository.findByUserOrderByOrderNumAsc((SysUser)UserTool.getInstance().getCurrentUser());
        List<CommonTreeBean> returnList = new ArrayList<CommonTreeBean>();
        final String type = "group";
        final String idPrefix = "g_";
        final String state = "open";
        if (Tools.isNotEmptyList(groups)) {
            CommonTreeBean rootBean = new CommonTreeBean("root_group", "个人群组", "open", null, null);
            List<CommonTreeBean> children = new ArrayList<CommonTreeBean>();
            for (OaPersonalGroup group : groups) {
                CommonTreeBean bean = new CommonTreeBean(idPrefix + group.getId().toString(), group.getGroupName(), state, null, null);
                bean.setType(type);
                children.add(bean);
            }
            rootBean.setChildren(children);
            returnList.add(rootBean);
        }
        return returnList;
    }

}
