/*
 * FileName:    ImMessageServiceImpl.java
 * Description:
 * Company:     南宁超创信息工程有限公司
 * Copyright:   ChaoChuang (c) 2016
 * History:     2016年5月24日 (HM) 1.0 Create
 */

package cn.com.chaochuang.oa.message.im.service;

import java.lang.reflect.InvocationTargetException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.com.chaochuang.common.data.domain.ValidAble;
import cn.com.chaochuang.common.data.repository.SimpleDomainRepository;
import cn.com.chaochuang.common.data.service.SimpleLongIdCrudRestService;
import cn.com.chaochuang.common.log.service.LogService;
import cn.com.chaochuang.common.reference.YesNo;
import cn.com.chaochuang.common.security.util.UserTool;
import cn.com.chaochuang.common.syspower.bean.DepAndUserTreeBean;
import cn.com.chaochuang.common.user.domain.SysDepartment;
import cn.com.chaochuang.common.user.domain.SysUser;
import cn.com.chaochuang.common.user.repository.SysDepartmentRepository;
import cn.com.chaochuang.common.user.repository.SysUserRepository;
import cn.com.chaochuang.common.user.service.SysUserService;
import cn.com.chaochuang.common.util.Tools;
import cn.com.chaochuang.oa.message.app.domain.OaAppMessage;
import cn.com.chaochuang.oa.message.app.reference.MesStatus;
import cn.com.chaochuang.oa.message.app.reference.MesType;
import cn.com.chaochuang.oa.message.app.repository.AppMessageRepository;
import cn.com.chaochuang.oa.message.app.service.AppMessageService;
import cn.com.chaochuang.oa.message.im.bean.ImMessageSqlBean;
import cn.com.chaochuang.oa.message.im.domain.OaImMessage;
import cn.com.chaochuang.oa.message.im.reference.ImDelFlag;
import cn.com.chaochuang.oa.message.im.reference.ImMesStatus;
import cn.com.chaochuang.oa.message.im.repository.ImMessageRepository;

/**
 * @author HM
 *
 */
@Service
@Transactional
public class ImMessageServiceImpl extends SimpleLongIdCrudRestService<OaImMessage> implements ImMessageService {

    @Autowired
    private ImMessageRepository     repository;

    @Autowired
    private LogService              logService;

    @Autowired
    private AppMessageService       appMsgService;

    @Autowired
    private SysUserService          userService;

    @Autowired
    private SysDepartmentRepository depRepository;

    @Autowired
    private SysUserRepository       userRepository;

    @Autowired
    private AppMessageRepository    appMessageRepository;

    @PersistenceContext
    private EntityManager           em;

    private final SimpleDateFormat  _SDF_CHS           = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒");

    /** 树节点状态 ： 关闭 */
    private final String            TREE_STATUS_CLOSED = "closed";

    /** 树节点状态 ： 打开 */
    private final String            TREE_STATUS_OPEN   = "open";

    /** 树节点部门id前缀 */
    private final String            TREE_DEP_ID_PREFIX = "root_";

    @Override
    public SimpleDomainRepository<OaImMessage, Long> getRepository() {
        return repository;
    }

    @Override
    public Long sendMessage(Long incepterId, String content, HttpServletRequest request) {
        if (incepterId != null) {
            SysUser incepter = userService.findOne(incepterId);
            SysUser curUser = (SysUser) UserTool.getInstance().getCurrentUser();
            if (incepter != null && curUser != null) {
                OaImMessage imMsg = new OaImMessage();
                Date now = new Date();
                imMsg.setIncepterId(incepterId);
                imMsg.setSenderId(curUser.getId());
                imMsg.setContent(content);
                imMsg.setCreateTime(now);
                imMsg.setStatus(ImMesStatus.发送中);
                imMsg.setIndelFlag(ImDelFlag.未删除);
                imMsg.setOutdelFlag(ImDelFlag.未删除);
                repository.save(imMsg);

                appMsgService.insertOaAppMsg(incepterId, curUser.getId(),
                                curUser.getUserName() + "于" + _SDF_CHS.format(now) + "发送一条消息给您，请及时查阅！", MesType.消息);

                logService.saveDefaultLog("发送及时消息给" + incepter.getUserName() + "/" + incepterId, request);
                return imMsg.getId();
            }
        }
        return null;
    }

    @Override
    public List<OaImMessage> receiveMessage(Long senderId) {
        if (senderId != null) {
            String curUserId = UserTool.getInstance().getCurrentUserId();
            if (StringUtils.isNotBlank(curUserId)) {
                Long incepterId = Long.valueOf(curUserId);
                List<OaImMessage> datas = repository.findBySenderIdAndIncepterIdAndStatusInAndIndelFlag(senderId,
                                incepterId, new ImMesStatus[] { ImMesStatus.发送中, ImMesStatus.已接收 }, ImDelFlag.未删除);
                if (datas != null && datas.size() > 0) {
                    for (OaImMessage oim : datas) {
                        // 消息已读
                        oim.setStatus(ImMesStatus.已读);
                        this.persist(oim);
                    }
                }

                // 已接收过的就不在右下角提示了
                List<OaAppMessage> appMesList = appMessageRepository.findByIncepterIdAndSenderIdAndMesTypeAndStatus(
                                incepterId, senderId, MesType.消息, MesStatus.未提示);
                if (Tools.isNotEmptyList(appMesList)) {
                    for (OaAppMessage oam : appMesList) {
                        oam.setStatus(MesStatus.已提示);
                        oam.setArrivalTime(new Date());
                        oam.setIsRead(YesNo.是);
                        appMessageRepository.save(oam);
                    }
                }
                return datas;
            }
        }
        return null;
    }

    @Override
    public List<Long> messageAlert() {
        List<Long> senderIdList = new ArrayList<Long>();
        String curUserId = UserTool.getInstance().getCurrentUserId();
        if (StringUtils.isNotBlank(curUserId)) {
            Long incepterId = Long.valueOf(curUserId);
            List<OaImMessage> datas = repository.findByIncepterIdAndStatusInAndIndelFlag(incepterId, new ImMesStatus[] {
                            ImMesStatus.发送中, ImMesStatus.已接收 }, ImDelFlag.未删除);
            if (datas != null && datas.size() > 0) {
                for (OaImMessage oim : datas) {
                    // 消息接收
                    oim.setStatus(ImMesStatus.已接收);
                    oim.setArrivalTime(new Date());
                    this.persist(oim);

                    Long senderId = oim.getSenderId();
                    // 判断id是否存在
                    if (!senderIdList.contains(senderId)) {
                        senderIdList.add(senderId);

                        // 已接收过的就不在右下角提示了
                        List<OaAppMessage> appMesList = appMessageRepository
                                        .findByIncepterIdAndSenderIdAndMesTypeAndStatus(incepterId, senderId,
                                                        MesType.消息, MesStatus.未提示);
                        if (Tools.isNotEmptyList(appMesList)) {
                            for (OaAppMessage oam : appMesList) {
                                oam.setStatus(MesStatus.已提示);
                                oam.setArrivalTime(new Date());
                                oam.setIsRead(YesNo.是);
                                appMessageRepository.save(oam);
                            }
                        }
                    }
                }
            }
        }
        return senderIdList;
    }

    @Override
    public Map<String, Object> historyMsg(Long senderId, Long curUserId, Integer page, Integer rows, String condition) {
        Map<String, Object> map = new HashMap<String, Object>();

        StringBuilder sqlBuilder = new StringBuilder(
                        " select t.id as id, t.senderId as senderId, t.sender.userName as senderName, t.incepterId as incepterId,"
                                        + " t.incepter.userName as incepterName, t.content as content, t.createTime as createTime, t.arrivalTime as arrivalTime,"
                                        + "t.status as status, t.outdelFlag as outdelFlag, t.indelFlag as indelFlag");
        sqlBuilder.append(" from OaImMessage t ");
        sqlBuilder.append(" where ((t.senderId=" + senderId + " and t.incepterId=" + curUserId + " and t.indelFlag="
                        + ImDelFlag.未删除.getKey() + ") ");
        sqlBuilder.append(" or (t.senderId=" + curUserId + " and t.incepterId=" + senderId + " and t.outdelFlag="
                        + ImDelFlag.未删除.getKey() + ")) ");
        if (StringUtils.isNotBlank(condition)) {
            sqlBuilder.append(condition);
        }
        sqlBuilder.append(" order by t.id desc ");
        Query query = em.createQuery(sqlBuilder.toString());
        Long total = 0l;
        List totalList = query.getResultList();
        if (totalList != null) {
            total = Long.valueOf(totalList.size());
        }
        query.setFirstResult((page - 1) * rows);
        query.setMaxResults(rows);
        List<Object> queryList = query.getResultList();
        List<ImMessageSqlBean> dataList = new ArrayList<ImMessageSqlBean>();
        if (Tools.isNotEmptyList(queryList)) {
            String[] names = { "id", "senderId", "senderName", "incepterId", "incepterName", "content", "createTime",
                            "arrivalTime", "status", "outdelFlag", "indelFlag" };
            for (Object o : queryList) {
                Map<String, Object> propertyMap = toMap(names, (Object[]) o);
                ImMessageSqlBean bean = new ImMessageSqlBean();
                try {
                    BeanUtils.copyProperties(bean, propertyMap);
                    dataList.add(bean);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }

        map.put("datas", dataList);
        map.put("total", total);
        return map;
    }

    @Override
    public void deleteMsg(Long[] ids) {
        if (ids != null) {
            List<OaImMessage> datas = this.repository.findAll(Arrays.asList(ids));
            if (datas != null && datas.size() > 0) {
                String curUserId = UserTool.getInstance().getCurrentUserId();
                Long deleterId = Long.valueOf(curUserId); // 删除人员id
                for (OaImMessage oim : datas) {
                    // 删除别人发给当前用户的消息
                    if (deleterId.equals(oim.getIncepterId())) {
                        oim.setIndelFlag(ImDelFlag.已删除);
                    }

                    // 删除当前用户发给别人的消息
                    if (deleterId.equals(oim.getSenderId())) {
                        oim.setOutdelFlag(ImDelFlag.已删除);
                    }
                    repository.save(oim);
                }
            }
        }
    }

    private Map<String, Object> toMap(String[] propertyName, Object[] list) {
        Map<String, Object> map = new HashedMap();
        for (int i = 0; i < list.length; i++) {
            for (int j = 0; j < propertyName.length; j++) {
                map.put(propertyName[j], list[j]);
            }
        }
        return map;
    }

    @Override
    public List<DepAndUserTreeBean> getUserTree() {
        List<DepAndUserTreeBean> tree = new ArrayList<DepAndUserTreeBean>();

        List<SysDepartment> firstDeps = depRepository.findByRootDepartment(ValidAble.VALID);
        if (firstDeps != null && firstDeps.size() > 0) {
            for (SysDepartment fd : firstDeps) { // 第一层
                DepAndUserTreeBean firstDepBean = new DepAndUserTreeBean(TREE_DEP_ID_PREFIX + fd.getId(),
                                fd.getDeptName(), false, TREE_STATUS_OPEN, "icon-home");
                List<DepAndUserTreeBean> firstChilren = new ArrayList<DepAndUserTreeBean>();
                boolean firstManFlag = false;

                List<SysDepartment> secondDeps = depRepository.findByDeptParentAndValidOrderByOrderNumAsc(fd.getId(),
                                ValidAble.VALID);
                if (secondDeps != null && secondDeps.size() > 0) {
                    for (SysDepartment sd : secondDeps) { // 第二层
                        if (sd.getId() == fd.getId())
                            continue;
                        DepAndUserTreeBean secondDepBean = new DepAndUserTreeBean(TREE_DEP_ID_PREFIX + sd.getId(),
                                        sd.getDeptName(), false, TREE_STATUS_CLOSED, "icon-home");
                        List<DepAndUserTreeBean> secondChilren = new ArrayList<DepAndUserTreeBean>();
                        boolean secondManFlag = false;

                        List<SysDepartment> thirdDeps = depRepository.findByDeptParentAndValidOrderByOrderNumAsc(
                                        sd.getId(), ValidAble.VALID);
                        if (thirdDeps != null && thirdDeps.size() > 0) {
                            for (SysDepartment td : thirdDeps) { // 第三层
                                if (td.getId() == sd.getId())
                                    continue;

                                List<DepAndUserTreeBean> thirdUsers = getDepUserListAndChecked(td.getId());
                                if ((thirdUsers == null) || (thirdUsers.size() <= 0)) {
                                    continue;
                                }
                                DepAndUserTreeBean thirdDepBean = new DepAndUserTreeBean(TREE_DEP_ID_PREFIX
                                                + td.getId(), td.getDeptName(), false, TREE_STATUS_CLOSED, "icon-home");
                                thirdDepBean.setChildren(thirdUsers);
                                secondChilren.add(thirdDepBean);
                                secondManFlag = true;
                            }

                        }

                        List<DepAndUserTreeBean> secondUsers = getDepUserListAndChecked(sd.getId());
                        if ((!secondManFlag) && ((secondUsers == null) || (secondUsers.size() <= 0))) {
                            continue;
                        }

                        secondChilren.addAll(secondUsers);
                        secondDepBean.setChildren(secondChilren);
                        firstChilren.add(secondDepBean);
                        firstManFlag = true;
                    }
                }

                List<DepAndUserTreeBean> firstUsers = getDepUserListAndChecked(fd.getId());
                if (!firstManFlag && ((firstUsers == null) || (firstUsers.size() <= 0))) {
                    continue;
                }
                firstChilren.addAll(firstUsers);
                firstDepBean.setChildren(firstChilren);
                tree.add(firstDepBean);
            }
        }

        return tree;
    }

    /**
     * 角色授权：查找同部门人员
     *
     * @param depId
     * @param users
     * @return
     */
    private List<DepAndUserTreeBean> getDepUserListAndChecked(Long depId) {
        List<DepAndUserTreeBean> beanList = new ArrayList<DepAndUserTreeBean>();
        if (depId != null) {
            List<SysUser> userList = this.userRepository.findByDepartmentIdAndValidOrderBySortAsc(depId,
                            ValidAble.VALID);
            SysUser curUser = (SysUser) UserTool.getInstance().getCurrentUser();
            for (SysUser u : userList) {
                if (u.equals(curUser)) {
                    continue;
                }
                DepAndUserTreeBean bean = new DepAndUserTreeBean(u.getId().toString(), u.getUserName(), false,
                                TREE_STATUS_OPEN, "icon-man");
                bean.setUserFlag(true);
                beanList.add(bean);
            }
        }

        return beanList;
    }
}
