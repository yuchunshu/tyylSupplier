/*
 * FileName:    DocDepLetterReceiverServiceImpl.java
 * Description:
 * Company:     南宁超创信息工程有限公司
 * Copyright:   ChaoChuang (c) 2016
 * History:     2016年11月25日 (LJX) 1.0 Create
 */

package cn.com.chaochuang.doc.letter.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.com.chaochuang.common.data.repository.SimpleDomainRepository;
import cn.com.chaochuang.common.data.service.SimpleUuidCrudRestService;
import cn.com.chaochuang.doc.letter.domain.DocDepLetterReceiver;
import cn.com.chaochuang.doc.letter.repository.DocDepLetterReceiverRepository;

/**
 * @author LJX
 *
 */
@Service
@Transactional
public class DocDepLetterReceiverServiceImpl extends SimpleUuidCrudRestService<DocDepLetterReceiver> implements
                DocDepLetterReceiverService {

    @Autowired
    private DocDepLetterReceiverRepository repository;

    @Override
    public SimpleDomainRepository<DocDepLetterReceiver, String> getRepository() {
        return repository;
    }

}
