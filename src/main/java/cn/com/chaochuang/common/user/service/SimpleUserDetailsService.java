/*
 * FileName:    SimpleUserDetailsService.java
 * Description:
 * Company:     
 * Copyright:    (c) 2014
 * History:     2014年7月22日 (guig) 1.0 Create
 */

package cn.com.chaochuang.common.user.service;

import java.util.ArrayList;
import java.util.Collection;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import cn.com.chaochuang.common.security.UserInfo;
import cn.com.chaochuang.common.user.bean.SimpleCurrentUserInfo;
import cn.com.chaochuang.common.user.domain.SysUser;
import cn.com.chaochuang.common.user.repository.SysUserRepository;

/**
 * @author guig
 *
 */
@Service("SimpleUserDetailsService")
public class SimpleUserDetailsService implements UserDetailsService {

    @Value("${security.default_admin_account}")
    private String            defaultAdminUserAccount;

    @Value("${security.default_admin_password}")
    private String            defaultAdminUserPassword;

    @Autowired
    private SysUserRepository userRepository;

    private boolean doForAddFirstUser() {
        if (0 == userRepository.count()) {
            if (StringUtils.isNotBlank(defaultAdminUserAccount) && StringUtils.isNotBlank(defaultAdminUserPassword)) {
                SysUser adminUser = new SysUser();
                adminUser.setAccount(defaultAdminUserAccount);
                adminUser.setPassword(defaultAdminUserPassword);
                adminUser.setUserName(defaultAdminUserAccount);

                userRepository.save(adminUser);

                return true;
            }
        }
        return false;
    }

    private SysUser doLoadUser(String username) {
        return userRepository.findByAccountAndValid(username, SysUser.VALID);
    }

    /**
     * (non-Javadoc)
     *
     * @see org.springframework.security.core.userdetails.UserDetailsService#loadUserByUsername(java.lang.String)
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        SysUser user = doLoadUser(username);
        if (null == user) {
            if (doForAddFirstUser()) {
                user = doLoadUser(username);
            }
        }
        if (null != user && null != user.getId()) {
            Collection<GrantedAuthority> auths = new ArrayList<GrantedAuthority>();

            boolean accountNonLocked = (user.getAccountLocked() == null) ? true : (!user.getAccountLocked());
            return new UserInfo(new SimpleCurrentUserInfo(user), user.getPassword(), auths, true, true, true,
                            accountNonLocked);

        } else {
            throw new UsernameNotFoundException("没有用户[" + username + "]的数据！");
        }
    }

    /**
     * @return the defaultAdminUserAccount
     */
    public String getDefaultAdminUserAccount() {
        return defaultAdminUserAccount;
    }

    /**
     * @return the defaultAdminUserPassword
     */
    public String getDefaultAdminUserPassword() {
        return defaultAdminUserPassword;
    }

}
