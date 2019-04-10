/*
 * FileName:    ShortcutRepository.java
 * Description:
 * Company:     南宁超创信息工程有限公司
 * Copyright:   ChaoChuang (c) 2016
 * History:     2016年7月21日 (LJX) 1.0 Create
 */

package cn.com.chaochuang.common.desktop.repository;

import java.util.List;

import cn.com.chaochuang.common.data.repository.SimpleDomainRepository;
import cn.com.chaochuang.common.desktop.domain.DesktopShortcut;

/**
 * @author LJX
 *
 */
public interface ShortcutRepository extends SimpleDomainRepository<DesktopShortcut, Long> {

    List<DesktopShortcut> findByUserIdOrderBySortAsc(Long userId);

}
