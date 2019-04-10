package cn.com.chaochuang.webservice.service;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.jws.WebService;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import cn.com.chaochuang.common.reference.StatusFlag;
import cn.com.chaochuang.common.user.domain.SysDepartment;
import cn.com.chaochuang.common.user.domain.SysUser;
import cn.com.chaochuang.common.user.service.SysDepartmentService;
import cn.com.chaochuang.common.user.service.SysUserService;
import cn.com.chaochuang.common.util.Tools;
import cn.com.chaochuang.doc.personal.domain.PersonalWord;
import cn.com.chaochuang.doc.personal.service.PersonalWordService;
import cn.com.chaochuang.oa.address.domain.OaAddressPersonal;
import cn.com.chaochuang.oa.address.service.OaAddressPersonalService;
import cn.com.chaochuang.common.attach.domain.SysAttach;
import cn.com.chaochuang.common.attach.service.SysAttachService;
import cn.com.chaochuang.oa.datachange.domain.DataChange;
import cn.com.chaochuang.oa.datachange.service.DataChangeService;
import cn.com.chaochuang.oa.notice.domain.OaNotice;
import cn.com.chaochuang.oa.notice.service.OaNoticeService;
import cn.com.chaochuang.webservice.bean.ContactCardsBean;
import cn.com.chaochuang.webservice.bean.DataChangeBean;
import cn.com.chaochuang.webservice.bean.DepartmentBean;
import cn.com.chaochuang.webservice.bean.DocFileAttachInfo;
import cn.com.chaochuang.webservice.bean.DocLemmaBean;
import cn.com.chaochuang.webservice.bean.PubInfoBean;
import cn.com.chaochuang.webservice.bean.UserBean;
import net.sf.json.JSONObject;

/**
 * @author huangwq
 *
 */
@Component
@WebService
public class TransferServiceImpl implements TransferService {
    @Autowired
    private OaNoticeService          noticeService;
    @Autowired
    private SysUserService           userService;
    @Autowired
    private DataChangeService        dataChangeService;
    @Autowired
    private PersonalWordService      personalWordService;
    // @Autowired
    // private AuditOpinionService docOpinionService;

    @Autowired
    private SysAttachService         attachService;

    @Autowired
    private OaAddressPersonalService personalService;

    @Autowired
    private SysDepartmentService     departmentService;

    @Value("${upload.rootpath}")
    private String                   rootPath;

    /**
     * 获取公共通讯录数据
     *
     * @return
     */
    public String getDocLemmaInfo(String changeScript) {

        String json = "";
        // changeScript的结构为id=1359945490302 不符合该格式的字符串均不能执行变更
        if (Tools.isEmptyString(changeScript)) {
            return json;
        }
        String[] items = changeScript.split("=");
        // 记录标识字段必须为id或者为doc_lemma_id
        if (items.length != 2 || (!items[0].equals("id"))) {
            return json;
        }
        try {
            if (items[0].equals("id")) {
                String id = (items[0].equals("id")) ? items[1] : null;
                PersonalWord personalWord = personalWordService.getRepository().findOne(new Long(id));
                DocLemmaBean docLemma = new DocLemmaBean();

                docLemma.setCommonLemma('1');
                docLemma.setDisplay('0');
                docLemma.setLemmaName(personalWord.getWord());
                docLemma.setLemmaType("公文");
                docLemma.setMdfCode(null);
                docLemma.setRmLemmaId(personalWord.getId());
                docLemma.setUserId(personalWord.getUserId());

                ObjectMapper mapper = new ObjectMapper();
                try {
                    json += mapper.writeValueAsString(docLemma) + ",";
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
        return json;

    }

    public String getPersonalWordData(Long id) {

        String json = "";
        try {
            List<PersonalWord> list = this.personalWordService.getOaPersonalWord(id);
            DocLemmaBean docLemma;
            // 获取数据待办数据后整合成移动平台可识别的json对象
            ObjectMapper mapper = new ObjectMapper();
            for (PersonalWord personalWord : list) {

                docLemma = new DocLemmaBean();

                docLemma.setCommonLemma('1');
                docLemma.setDisplay('0');
                docLemma.setLemmaName(personalWord.getWord());
                docLemma.setLemmaType("公文");
                docLemma.setMdfCode(null);
                docLemma.setRmLemmaId(personalWord.getId());
                docLemma.setUserId(personalWord.getUserId());

                json += mapper.writeValueAsString(docLemma) + ",";

            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        if (!json.equals("")) {
            return "[" + json.substring(0, json.length() - 1) + "]";
        }

        return json;

    }


    /**
     * 获取公共通讯录数据(根据ID获取新增通讯录)
     */
    @Override
    public String getContactCardsData(Long id) {
        String json = "";
        try {

            List<OaAddressPersonal> list = this.personalService.getOaAddressPersonal(id);
            ContactCardsBean contactCards;
            // 获取数据待办数据后整合成移动平台可识别的json对象
            ObjectMapper mapper = new ObjectMapper();
            for (OaAddressPersonal contacts : list) {

                contactCards = new ContactCardsBean();
                contactCards.setStaffName(contacts.getPname());
                contactCards.setDepartment(null);// 部门名称
                contactCards.setDuty(contacts.getDuty());
                contactCards.setPhone(contacts.getPhone());
                contactCards.setMobile(contacts.getMobile());
                contactCards.setEmail(contacts.getEmail());
                contactCards.setQQ(null);// QQ
                contactCards.setDepId(0);// 单位ID
                contactCards.setUnitName(contacts.getUnitName());
                contactCards.setInputDate(contacts.getCreateDate());
                contactCards.setZip(contacts.getPostcode());
                contactCards.setRmLinkmanId(contacts.getId());// 原系统通讯录编号
                contactCards.setHomePhone(contacts.getHomePhone());
                contactCards.setAddress(contacts.getAddress());
                contactCards.setCreatorId(contacts.getCreatorId());

                json += mapper.writeValueAsString(contactCards) + ",";

            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        if (!json.equals("")) {
            return "[" + json.substring(0, json.length() - 1) + "]";
        }

        return json;

    }

    /**
     * 获取待办事宜信息
     *
     * @param forDoId
     * @param oaPendingHandleDtsId
     * @return
     */
    public String selectPendingItemInfo(String oaPendingHandleDtsId) {
        String json = "";
//        if (oaPendingHandleDtsId != null) {
//            List<Fordo> list = this.fordoService.selectMaxFordo(Long.valueOf(oaPendingHandleDtsId));
//            for (Fordo fd : list) {
//                FordoInfo info = new FordoInfo();
//                info.setRmPendingId(fd.getId());
//                info.setRecipientId(fd.getRecepterId());
//                info.setSenderName(fd.getSenderName());
//                info.setTitle(fd.getTitle());
//                info.setSendTime(fd.getSendTime());
//                ObjectMapper mapper = new ObjectMapper();
//                try {
//                    json += mapper.writeValueAsString(info) + ",";
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        }

        if (!json.equals("")) {
            return "[" + json.substring(0, json.length() - 1) + "]";
        }
        return json;

    }

    /**
     * 获取公文办理数据
     *
     * @param jsonStr
     *            参数Json
     * @return
     */
    public String getDocTransactInfo(String instNoId, String lastOutputTime) {
        return null;
    }

    /**
     * 获取公共通讯录数据
     *
     * @return
     */
    public String getDepLinkmanInfo(String changeScript) {

        String json = "";
        // changeScript的结构为id=1359945490302 不符合该格式的字符串均不能执行变更
        if (Tools.isEmptyString(changeScript)) {
            return json;
        }
        String[] items = changeScript.split("=");
        // 记录标识字段必须为id或者为email_id
        if (items.length != 2 || (!items[0].equals("addr_id"))) {
            return json;
        }
        try {
            if (items[0].equals("addr_id")) {
                String id = (items[0].equals("addr_id")) ? items[1] : null;
                OaAddressPersonal contacts = personalService.getRepository().findOne(new Long(id));
                if (contacts == null) {
                    return null;
                }
                ContactCardsBean contactCards = new ContactCardsBean();

                contactCards.setStaffName(contacts.getPname());
                contactCards.setDepartment(null);// 部门名称
                contactCards.setDuty(contacts.getDuty());
                contactCards.setPhone(contacts.getPhone());
                contactCards.setMobile(contacts.getMobile());
                contactCards.setEmail(contacts.getEmail());
                contactCards.setQQ(null);// QQ
                contactCards.setDepId(0);// 单位ID
                contactCards.setUnitName(contacts.getUnitName());
                contactCards.setInputDate(contacts.getCreateDate());
                contactCards.setZip(contacts.getPostcode());
                contactCards.setRmLinkmanId(contacts.getId());// 原系统通讯录编号
                contactCards.setHomePhone(contacts.getHomePhone());
                contactCards.setAddress(contacts.getAddress());
                contactCards.setCreatorId(contacts.getCreatorId());
                ObjectMapper mapper = new ObjectMapper();
                try {
                    json += mapper.writeValueAsString(contactCards) + ",";
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
        return json;

    }

    /**
     * 获取OA公告信息
     *
     * @param lastOutputTime
     *            最后数据导入时间
     * @return
     */
    public String getPublicDataInfo(String id) {

        String json = "";
        try {
            // 若发送时间条件不为空则设置发送时间
            // Date lastSendTime = Tools.DATE_TIME_FORMAT.parse(lastOutputTime);
            JSONObject jsonObject = null;
            List<OaNotice> list = this.noticeService.getOaNotice(id);
            PubInfoBean pubInfo;
            // 获取数据待办数据后整合成移动平台可识别的json对象
            ObjectMapper mapper = new ObjectMapper();
            for (OaNotice notice : list) {
                // com.alibaba.fastjson.JSONObject.toJSONString(object)
                pubInfo = new PubInfoBean();
                pubInfo.setRmNoticeId(notice.getId());
                if (notice.getContent() != null) {
                    pubInfo.setContent(this.filterHtmlTag(notice.getContent().toString()));
                }
                pubInfo.setTitle(notice.getTitle());
                // String nowday = Tools.DATE_FORMAT.format(new Date());
                // pubInfo.setInputDate(Tools.DATE_FORMAT.parse(nowday));
                pubInfo.setPublishTime(notice.getPublishDate());
                if (notice.getPublishDept() != null) {
                    pubInfo.setPublishDep(notice.getPublishDept().getDeptName());
                }
                SysUser pubUser = userService.findOne(notice.getCreatorId());
                if (pubUser != null) {
                    pubInfo.setPublishName(pubUser.getUserName());
                }
                pubInfo.setPubInfoType("公告");
                if (notice.getPublishType() != null) {
                    pubInfo.setPubRangeId(Long.valueOf(notice.getPublishType().getKey()));
                    pubInfo.setPubRange(notice.getPublishType().getValue());
                }

                pubInfo.setPubLevel("1");
                pubInfo.setPublishDepId(notice.getPublishDepId());
                json += mapper.writeValueAsString(pubInfo) + ",";

            }
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
        if (!json.equals("")) {
            return "[" + json.substring(0, json.length() - 1) + "]";
        }
        return json;

    }



    /**
     * 去除内容中的html标签
     *
     * @param source
     * @return
     */
    private String filterHtmlTag(String source) {
        if (Tools.isEmptyString(source)) {
            return "";
        }
        String content = source.replaceAll("</?[^>]+>", ""); // 剔出<html>的标签
        content = content.replaceAll("<a>\\s*|\t|\r|\n</a>", "");// 去除字符串中的空格,回车,换行符,制表符
        return content;
    }

    /**
     * 获取OA公告变更信息
     *
     * @param changeScript
     *
     * @return
     */
    public String getChangePubInfo(String changeScript) {
        String json = "";
        // changeScript的结构为id=1359945490302 不符合该格式的字符串均不能执行变更
        if (Tools.isEmptyString(changeScript)) {
            return json;
        }
        String[] items = changeScript.split("=");
        // 记录标识字段必须为id或者为email_id
        if (items.length != 2 || (!items[0].equals("notice_id"))) {
            return json;
        }
        try {
            if (items[0].equals("notice_id")) {
                String id = (items[0].equals("notice_id")) ? items[1] : null;
                OaNotice notice = noticeService.getRepository().findOne(id);
                if (notice == null || notice.getStatus() == StatusFlag.暂存) {
                    System.out.println("公告ID不存在：" + changeScript);
                    return null;
                }
                PubInfoBean pubInfo = new PubInfoBean();
                pubInfo.setContent(this.filterHtmlTag(notice.getContent()));
                pubInfo.setTitle(notice.getTitle());
                pubInfo.setInputDate(new Date());
                pubInfo.setPublishTime(notice.getPublishDate());

                if (notice.getPublishDept() != null) {
                    pubInfo.setPublishDep(notice.getPublishDept().getId().toString());
                }
                SysUser user = userService.findOne(notice.getCreatorId());
                pubInfo.setPublishName(user.getUserName());
                pubInfo.setPubLevel("1");
                pubInfo.setPubInfoType("公告");
                if (notice.getPublishType() != null) {
                    pubInfo.setPubInfoType(notice.getPublishType().getKey());
                    pubInfo.setPubRangeId(Long.valueOf(notice.getPublishType().getKey()));
                    pubInfo.setPubRange(notice.getPublishType().getValue());
                }
                pubInfo.setInputDate(new Date());
                pubInfo.setRmNoticeId(notice.getId());
                if (notice.getStatus() != null) {
                    pubInfo.setStatus(notice.getStatus().getKey());
                }
                ObjectMapper mapper = new ObjectMapper();
                try {
                    json += mapper.writeValueAsString(pubInfo) + ",";
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
        return json;
    }

    /**
     * 获取公文的附件
     *
     * @param fileName
     * @param offset
     * @param reads
     * @return
     */
    public byte[] uploadStreamAttachFile(String fileName, Long offset, Integer reads) {
        RandomAccessFile randomFile = null;
        byte[] buffer = new byte[reads];
        try {
            File file = new File(fileName);
            // 文件不存在
            if (!file.exists()) {
                return new byte[0];
            }
            // 当文件获取偏移值超过文件大小则不进行读取
            if (offset >= file.length()) {
                return new byte[0];
            }
            // 算出适合的缓存读取大小
            if ((offset + reads) > file.length()) {
                reads = Long.valueOf(file.length() - offset).intValue();
            }
            randomFile = new RandomAccessFile(fileName, "r");
            randomFile.seek(offset);
            int readed = randomFile.read(buffer);
            // 文件已经上传完
            if (readed == -1) {
                return new byte[0];
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        } finally {
            if (randomFile != null) {
                try {
                    randomFile.close();
                } catch (IOException exf) {
                    return new byte[0];
                }
            }
        }
        return buffer;

    }

    /**
     * 获取OA用户表变更的数据
     *
     * @param changeScript
     * @return
     */
    public String getChangeUser(String changeScript) {
        String json = "";
        // changeScript的结构为id=1359945490302 不符合该格式的字符串均不能执行变更
        if (Tools.isEmptyString(changeScript)) {
            return json;
        }
        String[] items = changeScript.split("=");

        if (items.length != 2 || (!items[0].equals("user_id"))) {
            return json;
        }
        try {
            if (items[0].equals("user_id")) {
                String id = (items[0].equals("user_id")) ? items[1] : null;
                SysUser sysUser = userService.getRepository().findOne(new Long(id));
                if (sysUser == null) {
                    System.out.println("ID不存在：" + changeScript);
                    return null;
                }
                UserBean user = new UserBean();
                user.setAccount(sysUser.getAccount());
                user.setDutyName(sysUser.getDuty() != null ? sysUser.getDuty().getDutyName() : null);
                if (sysUser.getDepartment() != null) {
                    user.setRmDepId(sysUser.getDepartment().getId());
                }
                user.setMobile(sysUser.getMobile());
                user.setPassword(sysUser.getPassword());
                user.setSex(sysUser.getSex());
                user.setRmUserId(sysUser.getId());
                user.setValid(sysUser.getValid());
                user.setUserName(sysUser.getUserName());
                if (sysUser.getSort() != null) {
                    // user.setOrderNum(Long.parseLong(sysUser.getSort())); // 2016-8-16 修改（user.sort 已改为 Long 类型）
                    user.setOrderNum(sysUser.getSort());
                }
                ObjectMapper mapper = new ObjectMapper();
                try {
                    json += mapper.writeValueAsString(user) + ",";
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
        return json;
    }

    /**
     * 获取OA组织机构表变更的数据
     *
     * @param changeScript
     * @return
     */
    public String getChangeDepartment(String changeScript) {
        String json = "";
        // changeScript的结构为id=1359945490302 不符合该格式的字符串均不能执行变更
        if (Tools.isEmptyString(changeScript)) {
            return json;
        }
        String[] items = changeScript.split("=");

        if (items.length != 2 || (!items[0].equals("dept_id"))) {
            return json;
        }
        try {
            if (items[0].equals("dept_id")) {
                String id = (items[0].equals("dept_id")) ? items[1] : null;
                SysDepartment sysDeparment = departmentService.getRepository().findOne(new Long(id));
                if (sysDeparment == null) {
                    System.out.println("ID不存在：" + changeScript);
                    return null;
                }
                DepartmentBean departmentBean = new DepartmentBean();

                departmentBean.setAncestorDep(sysDeparment.getUnitId());
                if (sysDeparment.getValid() != null) {
                    departmentBean.setDelFlag(String.valueOf(sysDeparment.getValid()));
                }
                departmentBean.setDepAlias(sysDeparment.getDeptName());
                departmentBean.setDepName(sysDeparment.getDeptName());
                departmentBean.setOrderNum(sysDeparment.getOrderNum());
                departmentBean.setParentDep(sysDeparment.getDeptParent());
                departmentBean.setRmDepId(sysDeparment.getId());
                departmentBean.setValid(sysDeparment.getValid());

                ObjectMapper mapper = new ObjectMapper();
                try {
                    json += mapper.writeValueAsString(departmentBean) + ",";
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
        return json;
    }

    /**
     * 获取OA的已办节点信息
     */
    public String getOAHistoryNodes(Long instId) {
        return null;
    }

    /**
     * 获取OA的改变数据
     *
     * @param lastOutputTime
     * @return
     */
    public String getDataChange() {
        String json = "";
        List<DataChange> datas = this.dataChangeService.getData();
        List<DataChangeBean> list = new ArrayList();
        if (datas != null && datas.size() > 0) {
            for (int i = 0; i < datas.size(); i++) {
                DataChangeBean bean = new DataChangeBean();
                bean.setChangeScript(datas.get(i).getChangeScript());
                if (datas.get(i).getChangeTableName() != null)
                    bean.setChangeTableName(datas.get(i).getChangeTableName().getKey());
                if (datas.get(i).getOperationType() != null)
                    bean.setOperationType(datas.get(i).getOperationType().getKey());
                bean.setChangeDate(datas.get(i).getChangeDate());
                list.add(bean);

                this.dataChangeService.delete(datas.get(i).getId());
            }
            try {
                if (list.isEmpty()) {
                    return json;
                }
                ObjectMapper mapper = new ObjectMapper();
                json = mapper.writeValueAsString(list);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }

        return json;
    }

    /**
     * 可以选择的人员信息
     *
     */
    public String getSelectNodesAndButtonInfo(String nextNodeInfo) {
        return null;
    }

    /**
     * 获取OA的法律法规信息
     *
     * @return
     */
    public String getOAPolicyInfo(String lastInputTime) {
        return null;
    }

    /**
     * 获取法律法规附件
     *
     * @param MaxRmPolicyId
     * @return
     */
    public String getPolicyAttachFile(String MaxRmPolicyId) {
        return null;
    }

    /**
     * 获取法律法规数据变更
     */
    public String getChangePolicyInfo(String changeScript) {
        return null;
    }

    /**
     * 提交公文办理数据
     *
     * @param jsonStr
     *            参数Json
     * @return
     */
    public String setDocTransactInfo(String jsonStr) {
        return null;
    }

    /**
     * 根据流程实例id查看远程OA是否可以取回
     */
    public String isRollback(String instId, Long userId) {
        return null;
    }

    /**
     * 保存会议已阅操作
     */
    public void signMeetingRecord(Long attendeeId) {

    }

    /**
     * 获取会议信息
     */
    public String getChangeMeetingInfo(String maxId) {
        return null;
    }

    /**
     * 获取会议参会人信息
     */
    public String getChangeMeetingAttendee(String maxId) {
        return null;
    }


    @Override
    public String getDocAttachInfo(Long maxRmAttachId) {

        // 查找所有公文附件
        String json = "";
        ObjectMapper mapper = new ObjectMapper();
        List<SysAttach> attcllist = this.attachService.selectdocAttach();
        List<DocFileAttachInfo> docAttach = new ArrayList<DocFileAttachInfo>();
        for (SysAttach attch : attcllist) {
            DocFileAttachInfo dinfo = new DocFileAttachInfo();
            dinfo.setFileSize(attch.getFileSize());
            if (attch.getIsImage() != null) {
                dinfo.setIsImage(attch.getIsImage().getKey());
            }
            if (attch.getId() != null) {
                dinfo.setRmAttachId(attch.getId().toString());
            }
            dinfo.setSaveName(attch.getSaveName());
            if ("mainFile".equals(attch.getAttachType())) {
                // 过滤为空的正文
                File file = new File(attch.getSavePath() + "/" + attch.getSaveName());
                if (file.length() == 0) {
                    continue;
                }
                dinfo.setTrueName("公文正文");
                dinfo.setSavePath(attch.getSavePath() + "/");
            } else {
                dinfo.setTrueName(attch.getTrueName());
                dinfo.setSavePath(this.rootPath + "/" + attch.getSavePath());
            }
            dinfo.setDocId(attch.getOwnerId());
            docAttach.add(dinfo);
            try {
                json += mapper.writeValueAsString(dinfo) + ",";
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }

        }
        if (!json.equals("")) {
            return "[" + json.substring(0, json.length() - 1) + "]";
        }
        return json;
    }

    @Override
    public String getPubInfoAttachInfo(Long id) {
        // 查找所有公文附件
        String json = "";

        /**
        ObjectMapper mapper = new ObjectMapper();
        List<SysAttach> attcllist = this.attachService.selectPubInfoAttach(id);
        List<PubInfoAttachInfo> docAttach = new ArrayList<PubInfoAttachInfo>();
        for (SysAttach attch : attcllist) {
            // 判断当前取到的附件，在公告表中，是否有对象的公告
            if (attch.getOwnerId() != null) {
                if (noticeService.findOne(Long.parseLong(attch.getOwnerId())) == null) {
                    continue;
                }
            }
            PubInfoAttachInfo pubinfo = new PubInfoAttachInfo();
            pubinfo.setFileSize(attch.getFileSize());
            if (attch.getIsImage() != null) {
                pubinfo.setIsImage(attch.getIsImage().getKey());
            }
            pubinfo.setSaveName(attch.getSaveName());
            pubinfo.setTrueName(attch.getTrueName());
            pubinfo.setSavePath(this.rootPath + "/" + attch.getSavePath());
            if (attch.getOwnerId() != null) {
                pubinfo.setPubInfoId(Long.parseLong(attch.getOwnerId()));
            }
            pubinfo.setLocalData('0');
            pubinfo.setRmAttachId(attch.getId());
            docAttach.add(pubinfo);
            try {
                json += mapper.writeValueAsString(pubinfo) + ",";
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }

        }
        if (!json.equals("")) {
            return "[" + json.substring(0, json.length() - 1) + "]";
        }

        */
        return json;
    }

    @Override
    public String getFinishDocFile(String lastInputTime) {
        String json = "";
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date dt1 = null;
        try {
            if (StringUtils.isNotBlank(lastInputTime)) {
                dt1 = df.parse(lastInputTime + " 00:00");
            }
        } catch (ParseException e) {

        }

        // List<Map> list = this.docOpinionService.getDocFile(dt1);
        // for (Map m : list) {
        // FlowTransactInfo info = new FlowTransactInfo();
        // info.setTitle((String) m.get("title"));
        // info.setCreateTime((Date) m.get("createDate"));
        // if ((Long) m.get("creatorId") != null) {
        // SysUser user = this.userService.findOne((Long) m.get("creatorId"));
        // info.setCreatorName(user.getUserName());
        // info.setCreatorDeptName(user.getDepartment().getDeptName());
        // }
        // if (WfBusinessType.收文.getKey().equals((String) m.get("fileType"))) {
        // info.setFileType(WfBusinessType.收文.getKey());
        // } else {
        // info.setFileType(WfBusinessType.发文.getKey());
        // }
        // info.setRmFileId((String) m.get("fileId"));
        // info.setRmTransactId((Long) m.get("auditId"));
        // info.setInputTime((Date) m.get("auditTime"));
        // ObjectMapper mapper = new ObjectMapper();
        // try {
        // json += mapper.writeValueAsString(info) + ",";
        // } catch (Exception e) {
        // e.printStackTrace();
        // }
        // }

        if (!json.equals("")) {
            return "[" + json.substring(0, json.length() - 1) + "]";
        }
        return json;
    }
}
