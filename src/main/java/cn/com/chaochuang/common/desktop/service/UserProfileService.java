package cn.com.chaochuang.common.desktop.service;

import java.util.List;

import cn.com.chaochuang.common.data.service.CrudRestService;
import cn.com.chaochuang.common.desktop.bean.DisplayModelInfo;
import cn.com.chaochuang.common.desktop.bean.UserProfileInfo;
import cn.com.chaochuang.common.desktop.domain.UserProfile;

public interface UserProfileService extends CrudRestService<UserProfile, Long> {

    /**
     * 获取当前用户的桌面设置（displayModels）
     *
     * @return
     */
    public List<DisplayModelInfo> getUserDisplayModels();

    /**
     * 保存用户的桌面设置
     *
     * @param displayModels
     * @return
     */
    public Long saveDisplayModels(String displayModels);

    /**
     * 保存用户设置
     *
     * @param info
     * @return
     */
    public Long saveProfile(UserProfileInfo info);

}
