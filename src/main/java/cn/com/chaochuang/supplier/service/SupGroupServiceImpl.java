package cn.com.chaochuang.supplier.service;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.com.chaochuang.common.beancopy.BeanCopyBuilder;
import cn.com.chaochuang.common.data.repository.SimpleDomainRepository;
import cn.com.chaochuang.common.data.service.SimpleLongIdCrudRestService;
import cn.com.chaochuang.common.util.Tools;
import cn.com.chaochuang.supplier.bean.SupGroupTreeBean;
import cn.com.chaochuang.supplier.domain.SupGroup;
import cn.com.chaochuang.supplier.domain.SupUnit;
import cn.com.chaochuang.supplier.repository.SupGroupRepository;
import cn.com.chaochuang.supplier.repository.SupUnitRepository;

@Service
@Transactional
public class SupGroupServiceImpl extends SimpleLongIdCrudRestService<SupGroup> implements SupGroupService{

    @Autowired
    private SupGroupRepository   repository;

    @Autowired
    private SupUnitRepository    unitRepository;

    @Override
    public SimpleDomainRepository<SupGroup, Long> getRepository() {
        return repository;
    }

    @Override
    public List<SupGroupTreeBean> lazyGroupTree(Long parentId) {
        List<SupGroupTreeBean> returnList = new ArrayList<SupGroupTreeBean>(); // 返回的list
        List<SupGroup> folders = null; // 数据list
        if(parentId == null) {
            folders = repository.getRoots(); // 根节点
        } else {
            folders = repository.findByParentIdOrderByGroupCodeAsc(parentId); // 子节点
        }
        if (folders != null && folders.size() > 0) {
            for (SupGroup f : folders) {
                if (parentId != null && f.getId() == f.getParentId()) {  // 排除根节点
                    break;
                }
                SupGroupTreeBean bean = BeanCopyBuilder.buildObject(f, SupGroupTreeBean.class);

                // 是否含有子节点
                Integer childrenCount = repository.haveChildren(f.getId());
                if (childrenCount != null && childrenCount > 0) {
                    bean.setState("closed");
                } else {
                    bean.setState("open");
                }
                returnList.add(bean);
            }
        }
        return returnList;
    }

    @Override
    public String getNewGroupCode(Long parentId) {
        List<SupGroup> folders = null;
        if (null == parentId) {
            folders = repository.getRoots();
        } else {
            folders = repository.findByParentIdOrderByGroupCodeAsc(parentId);
        }
        if (folders != null && folders.size() > 0) {
            String folderCode = folders.get(folders.size() - 1).getGroupCode();
            if (StringUtils.isNotBlank(folderCode)) {
                int length = folderCode.length();
                StringBuilder fmStr = new StringBuilder();
                for (int i = 0; i < length; i++) {
                    fmStr.append("0");
                }
                DecimalFormat format = new DecimalFormat(fmStr.toString());
                Long code = Long.valueOf(folderCode);
                code++; // 同级别最大值 + 1
                return format.format(code);
            }
        } else {
            if (parentId != null) {
                SupGroup parent = repository.findOne(parentId);
                if (parent != null) {
                    return parent.getGroupCode() + "001";
                }
            }
        }
        return "001";
    }

    @Override
    public SupGroupTreeBean ceateNewNode(Long parentId) {
        SupGroup folder = new SupGroup();
        folder.setGroupCode(getNewGroupCode(parentId));
        String folderName = "NewGroup_" + Tools.DATE_TIME_FORMAT2.format(new Date());
        folder.setGroupName(folderName);
        folder.setParentId(parentId);
        if (null == parentId) {
            folder.setFullGroupName(folderName);
        } else {
            SupGroup parent = repository.findOne(parentId);
            if (parent != null) {
                folder.setFullGroupName(parent.getFullGroupName() + "/" + folderName);
            }
        }
        repository.save(folder);

        SupGroupTreeBean bean = BeanCopyBuilder.buildObject(folder, SupGroupTreeBean.class);
        bean.setState("open");
        return bean;
    }

    @Override
    public String delGroup(Long id) {
        if (id != null) {
            SupGroup group = repository.findOne(id);
            if (group != null) {
                List<SupUnit> docsList = unitRepository.findByGroupId(group.getId());
//                List docsList = null;
                if (docsList != null && docsList.size() > 0) {
                    return "目录不为空，无法删除！";
                } else {
                    repository.delete(group);
                }
            } else {
                return "该目录不存在！";
            }
        } else {
            return "该目录不存在！";
        }
        return null;
    }

    @Override
    public Long updateGroup(Long id, String folderName) {
        if(id != null) {
            SupGroup folder = repository.findOne(id);
            if (folder != null) {
                String full = folder.getFullGroupName();
                if (StringUtils.isNotBlank(full)) {
                    full = full.replaceAll(folder.getGroupName(), folderName);
                }
                folder.setGroupName(folderName);
                folder.setFullGroupName(full);
                repository.save(folder);
                return folder.getId();
            }
        }
        return null;
    }

}
