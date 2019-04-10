/*
 * FileName:    processService.java
 * Description:
 * Company:     南宁超创信息工程有限公司
 * Copyright:   ChaoChuang (c) 2016
 * History:     2016年4月27日 (huangwq) 1.0 Create
 */

package cn.com.chaochuang.doc.personal.service;

import java.util.List;

import cn.com.chaochuang.common.data.service.CrudRestService;
import cn.com.chaochuang.doc.personal.bean.PersonalWordBean;
import cn.com.chaochuang.doc.personal.domain.PersonalWord;

/**
 * @author huangwq
 *
 */
public interface PersonalWordService extends CrudRestService<PersonalWord, Long> {
    Long savePersonalWord(PersonalWordBean bean);

    /**
     * 获取词条数据
     */
    public List<PersonalWord> getOaPersonalWord(Long id);
}
