/*
 * FileName:    ReceiveProcessServiceImpl.java
 * Description:
 * Company:     南宁超创信息工程有限公司
 * Copyright:   ChaoChuang (c) 2016
 * History:     2016年4月27日 (huangwq) 1.0 Create
 */

package cn.com.chaochuang.doc.personal.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.com.chaochuang.common.beancopy.BeanCopyUtil;
import cn.com.chaochuang.common.data.repository.SimpleDomainRepository;
import cn.com.chaochuang.common.data.service.SimpleLongIdCrudRestService;
import cn.com.chaochuang.common.security.util.UserTool;
import cn.com.chaochuang.common.user.domain.SysUser;
import cn.com.chaochuang.doc.personal.bean.PersonalWordBean;
import cn.com.chaochuang.doc.personal.domain.PersonalWord;
import cn.com.chaochuang.doc.personal.repository.PersonalWordRepository;

/**
 * @author huangwq
 *
 */
@Service
@Transactional
public class PersonalWordServiceImpl extends SimpleLongIdCrudRestService<PersonalWord> implements PersonalWordService {

    @Autowired
    private PersonalWordRepository repository;

    @Override
    public SimpleDomainRepository<PersonalWord, Long> getRepository() {
        return this.repository;
    }

    @Override
    public Long savePersonalWord(PersonalWordBean bean) {
        PersonalWord personalWord = null;
        SysUser user = (SysUser) UserTool.getInstance().getCurrentUser();
        if (bean != null && bean.getId() != null) {
            personalWord = this.repository.findOne(bean.getId());
        } else {
            personalWord = new PersonalWord();

        }

        personalWord = BeanCopyUtil.copyBean(bean, PersonalWord.class);
        personalWord.setUserId(user.getId());
        this.repository.save(personalWord);
        return personalWord.getId();
    }

    /**
     * (non-Javadoc)
     *
     * @see cn.com.chaochuang.doc.personal.service.PersonalWordService#getOaPersonalWord(java.lang.Long)
     */
    @Override
    public List<PersonalWord> getOaPersonalWord(Long id) {
        if (id != null) {// 若id不为空则按倒叙查找新增的内容
            // System.out.println("getContactCard id:" + id);
            return this.repository.findByIdGreaterThanOrderByIdAsc(id);
        }
        return this.repository.findAll();
    }

}
