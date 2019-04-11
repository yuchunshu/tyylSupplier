package cn.com.chaochuang.common.desktop.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import cn.com.chaochuang.common.data.repository.SimpleDomainRepository;
import cn.com.chaochuang.common.desktop.domain.UserProfile;

/**
 * @author HM
 *
 */
public interface UserProfileRepository extends SimpleDomainRepository<UserProfile, Long> {

    @Query("select d from UserProfile d where d.themes is not null and d.themes <> ''")
    public List<UserProfile> findAllThemes();

}
