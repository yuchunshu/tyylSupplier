package cn.com.chaochuang.common.desktop.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.com.chaochuang.common.data.repository.SimpleDomainRepository;
import cn.com.chaochuang.common.data.service.SimpleNotGenerateLongIdCrudRestService;
import cn.com.chaochuang.common.desktop.bean.DisplayModelInfo;
import cn.com.chaochuang.common.desktop.bean.UserProfileInfo;
import cn.com.chaochuang.common.desktop.domain.UserProfile;
import cn.com.chaochuang.common.desktop.repository.UserProfileRepository;
import cn.com.chaochuang.common.dictionary.IDictionary;
import cn.com.chaochuang.common.dictionary.IDictionaryBuilder;
import cn.com.chaochuang.common.dictionary.support.DictionaryRefresher;
import cn.com.chaochuang.common.security.util.UserTool;
import cn.com.chaochuang.common.user.domain.SysUser;
import cn.com.chaochuang.common.util.Tools;
import cn.com.chaochuang.oa.notice.reference.SmsFlag;

import com.alibaba.fastjson.JSONArray;

@Service
@Transactional
public class UserProfileServiceImpl extends SimpleNotGenerateLongIdCrudRestService<UserProfile> implements
                UserProfileService, InitializingBean, IDictionaryBuilder {

    @Autowired
    private UserProfileRepository repository;

    /** 默认显示顺序 */
    private final String          _DEFAULT_MODELS = "[{name:\"taskContent\",location:\"left\",status:\"block\"},{name:\"readContent\",location:\"left\",status:\"block\"},{name:\"noticeContent\",location:\"right\",status:\"block\"},{name:\"mailContent\",location:\"right\",status:\"block\"}]";

    @Override
    public SimpleDomainRepository<UserProfile, Long> getRepository() {
        return repository;
    }

    @Override
    public List<DisplayModelInfo> getUserDisplayModels() {
        UserProfile setting = repository.findOne(Long.valueOf(UserTool.getInstance().getCurrentUserId()));
        String models = _DEFAULT_MODELS;
        if (setting != null) {
            models = setting.getDisplayModels();
            if (StringUtils.isBlank(models)) {
                return null; // 设置过桌面，但是显示模块无数据
            }
        }
        List<DisplayModelInfo> displayModels = JSONArray.parseArray(models, DisplayModelInfo.class);
        return displayModels; // 该用户若无桌面设置记录， 显示默认的模块及顺序
    }

    @Override
    public Long saveDisplayModels(String displayModels) {
        SysUser curUser = (SysUser) UserTool.getInstance().getCurrentUser();
        if (curUser != null) {
            Long curUserId = curUser.getId();
            UserProfile setting = repository.findOne(curUserId);
            if (setting == null) { // 数据不存在时创建一条
                setting = new UserProfile(curUserId, "", SmsFlag.不提醒, displayModels);
            } else {
                setting.setDisplayModels(displayModels);
            }
            repository.save(setting);
            return setting.getId();
        }
        return null;

    }

    @Override
    public Long saveProfile(UserProfileInfo info) {
        if (info != null) {
            Long id = Long.valueOf(UserTool.getInstance().getCurrentUserId());
            UserProfile setting = repository.findOne(id);
            if (setting == null) {
                setting = new UserProfile();
            }
            info.setId(id);
            BeanUtils.copyProperties(info, setting);
            repository.save(setting);
            DictionaryRefresher.getInstance().refreshIDictionaryBuilder(this);
            return setting.getId();
        }
        return null;
    }

    // 以下是实现 IDictionaryBuilder 接口

    @Override
    public String getDictionaryName() {
        return UserProfile.class.getSimpleName();
    }

    @Override
    public Map<String, IDictionary> getDictionaryMap() {
        List<UserProfile> list = this.repository.findAllThemes();
        Map data = new HashMap<String, IDictionary>();
        if (Tools.isNotEmptyList(list)) {
            for (UserProfile setting : list) {
                data.put(new String(setting.getId().toString()), setting);
            }
        }
        return data;
    }

    @Override
    public boolean isRefreshable() {
        return false;
    }

    @Override
    public void refresh() {
        DictionaryRefresher.getInstance().refreshIDictionaryBuilder(this);
    }

    @Override
    public Class getDictionaryClass() {
        return UserProfile.class;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        // DictionaryRefresher.getInstance().refreshIDictionaryBuilder(this);
    }

}
