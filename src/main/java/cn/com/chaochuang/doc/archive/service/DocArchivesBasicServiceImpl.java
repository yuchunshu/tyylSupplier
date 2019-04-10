package cn.com.chaochuang.doc.archive.service;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cn.com.chaochuang.common.beancopy.BeanCopyUtil;
import cn.com.chaochuang.common.data.repository.SimpleDomainRepository;
import cn.com.chaochuang.common.data.service.SimpleUuidCrudRestService;
import cn.com.chaochuang.common.security.util.UserTool;
import cn.com.chaochuang.common.user.domain.SysUser;
import cn.com.chaochuang.common.user.service.SysUserService;
import cn.com.chaochuang.common.util.Tools;
import cn.com.chaochuang.doc.archive.bean.DocArchivesBasicEditBean;
import cn.com.chaochuang.doc.archive.domain.DocArchivesBasic;
import cn.com.chaochuang.doc.archive.repository.DocArchivesBasicRepository;
/**
 * @author shiql 2017.11.22
 *
 */
@Service
@Transactional
public class DocArchivesBasicServiceImpl extends SimpleUuidCrudRestService<DocArchivesBasic> implements DocArchivesBasicService {

    @Autowired
    private DocArchivesBasicRepository 	repository;
    
    @Autowired
    private SysUserService 				userService;
    
    @Override
    public SimpleDomainRepository<DocArchivesBasic, String> getRepository() {
        return this.repository;
    }

    @Override
    public DocArchivesBasic saveDocArchives(DocArchivesBasicEditBean bean) {
        DocArchivesBasic archives = null;
        if (bean != null && StringUtils.isNotBlank(bean.getId())) {
            archives = this.repository.findOne(bean.getId());
            //设置修改人信息
            bean.setUpdatorId(Long.valueOf(UserTool.getInstance().getCurrentUserId()));
            //设置修改时间
            bean.setUpdateDate(new Date());
            bean.setCreateDate(archives.getCreateDate());
            bean.setCreatorId(archives.getCreatorId());
        } else {
            archives = new DocArchivesBasic();
            //设置创建时间
            SysUser createor = this.userService.findOne(Long.valueOf(UserTool.getInstance().getCurrentUserId()));
            bean.setCreateDate(new Date());
            bean.setCreatorId(createor.getId());
        }
        archives = BeanCopyUtil.copyBean(bean, DocArchivesBasic.class);
        if (archives.getDepId() == null) {
            archives.setDepId(Long.valueOf(UserTool.getInstance().getCurrentUserDepartmentId()));
        }
        if(archives.getCreatorId() == null){       	
        archives.setCreatorId(Long.valueOf(UserTool.getInstance().getCurrentUserId()));
        }
        
        archives =this.repository.save(archives);

        return archives;

    }

    @Override
    public boolean deleteDocArchivesBasic(String basicId, HttpServletRequest request) {
        if (StringUtils.isNotBlank(basicId)) {
        	repository.delete(basicId);
        	return true;
        }
        return false;
    }

    @Override
    public boolean checkBasicCode(String basicCode, String basicId) {
        if (Tools.isEmptyString(basicCode)) {
            return true;
        }

        List list = this.repository.findByBasicCode(basicCode);
        if (basicId != null) {
            DocArchivesBasic archive = this.repository.findOne(basicId);
            if (Tools.isNotEmptyString(archive.getBasicCode().trim(), basicCode.trim())) {
                return true;
            } else {
                if (Tools.isNotEmptyList(list)) {
                    return false;
                } else {
                    return true;
                }
            }
        } else {
            if (Tools.isNotEmptyList(list)) {
                return false;
            } else {
                return true;
            }
        }
    }
}
