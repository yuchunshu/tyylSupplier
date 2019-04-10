package cn.com.chaochuang.workflow.his.service;

import java.util.Date;

import javax.transaction.Transactional;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.com.chaochuang.common.data.repository.SimpleDomainRepository;
import cn.com.chaochuang.common.data.service.SimpleUuidCrudRestService;
import cn.com.chaochuang.workflow.his.bean.HisDealerEditBean;
import cn.com.chaochuang.workflow.his.domain.WfHisDealer;
import cn.com.chaochuang.workflow.his.repository.WfHisDealerRepository;

/**
 * @author hzr 2016-12-24
 *
 */
@Service
@Transactional
public class WfHisDealerServiceImpl extends SimpleUuidCrudRestService<WfHisDealer> implements WfHisDealerService {
    @Autowired
    private WfHisDealerRepository repository;

    @Override
    public SimpleDomainRepository<WfHisDealer, String> getRepository() {
        return repository;
    }

    @Override
    public String saveHisDealer(HisDealerEditBean bean) {
        WfHisDealer mans = null;
        if (bean != null && StringUtils.isNotBlank(bean.getId())) {
            mans = repository.findOne(bean.getId());
        } else {
            mans = new WfHisDealer();
        }
        BeanUtils.copyProperties(bean, mans);
        repository.save(mans);

        // 如果设置了多名
        if (StringUtils.isNotBlank(bean.getDealerIds())) {
            String[] dealerArr = bean.getDealerIds().split(",");
            for (String id : dealerArr) {
                if (StringUtils.isNotBlank(id)) {
                    mans = new WfHisDealer();
                    BeanUtils.copyProperties(bean, mans);
                    mans.setDealerId(Long.valueOf(id));
                    repository.save(mans);
                }
            }
        }

        return mans.getId();
    }

    @Override
    public void delHisDealer(String id) {
        if (StringUtils.isNotBlank(id)) {
            WfHisDealer mans = repository.findOne(id);
            if (mans != null) {
                repository.delete(mans);
            }
        }

    }

    @Override
    public void updateHisDealer(HisDealerEditBean bean) {
        if (StringUtils.isNotBlank(bean.getNodeId()) && bean.getOwnerId() != null) {
            String nodeId = bean.getNodeId();
            Long ownerId = bean.getOwnerId();
            Date now = new Date();
            WfHisDealer hisDealer = null;

            if (bean.getDealerId() != null) {
                hisDealer = repository.getByNodeIdAndOwnerIdAndDealerId(nodeId, ownerId, bean.getDealerId());
                if (hisDealer != null) {
                    hisDealer.setUseTime(now);
                    this.persist(hisDealer);
                } else {
                    hisDealer = new WfHisDealer();
                    BeanUtils.copyProperties(bean, hisDealer);
                    repository.save(hisDealer);
                }
            }

            if (StringUtils.isNotBlank(bean.getDealerIds())) {
                String[] dealerArr = bean.getDealerIds().split(",");
                for (String userId : dealerArr) {
                    hisDealer = repository.getByNodeIdAndOwnerIdAndDealerId(nodeId, ownerId, Long.valueOf(userId));

                    if (hisDealer != null) {
                        hisDealer.setUseTime(now);
                        this.persist(hisDealer);
                    } else {
                        hisDealer = new WfHisDealer();
                        BeanUtils.copyProperties(bean, hisDealer);
                        hisDealer.setDealerId(Long.valueOf(userId));
                        repository.save(hisDealer);
                    }
                }
            }
        }
    }

}
