/*
 * FileName:    OaAddressPersonalService.java
 * Description:
 * Company:     
 * Copyright:    (c) 2016
 * History:     2016年1月25日 (HM) 1.0 Create
 */

package cn.com.chaochuang.oa.address.service;

import java.util.List;

import cn.com.chaochuang.common.data.service.CrudRestService;
import cn.com.chaochuang.oa.address.domain.OaAddressPersonal;

/**
 * @author HM
 *
 */
public interface OaAddressPersonalService extends CrudRestService<OaAddressPersonal, Long> {
    public List<OaAddressPersonal> getOaAddressPersonal(Long id);
}
