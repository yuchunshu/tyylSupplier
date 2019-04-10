/**
 *
 */
package cn.com.chaochuang.common.desktop.service;

import cn.com.chaochuang.common.data.service.CrudRestService;
import cn.com.chaochuang.common.desktop.bean.UserAgentInfo;
import cn.com.chaochuang.common.desktop.domain.UserAgent;

/**
 * @author hzr 2016年10月9日
 *
 */
public interface UserAgentService extends CrudRestService<UserAgent, Long> {

    /** 根据userId取得用户当前的代理人，无代理的直接返回原userId */
    public Long getAgentByUserId(Long userId);

    /** 保存代理人 */
    public Long saveUserAgent(UserAgentInfo info);

    /** 根据userId取得用户当前的代理人，无代理的直接返回null */
    public UserAgent getAgentByUser(Long userId);

    /** 删除代理人 */
    public boolean delAgent(Long id);

}
