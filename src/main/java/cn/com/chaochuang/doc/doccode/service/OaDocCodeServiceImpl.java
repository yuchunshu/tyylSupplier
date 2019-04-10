package cn.com.chaochuang.doc.doccode.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.com.chaochuang.common.data.repository.SimpleDomainRepository;
import cn.com.chaochuang.common.data.service.SimpleLongIdCrudRestService;
import cn.com.chaochuang.doc.doccode.bean.DocCodeEditBean;
import cn.com.chaochuang.doc.doccode.domain.OaDocCode;
import cn.com.chaochuang.doc.doccode.repository.OaDocCodeRepository;
import cn.com.chaochuang.doc.event.reference.DocCate;
import cn.com.chaochuang.doc.template.domain.DocTemplate;
import cn.com.chaochuang.workflow.reference.WfBusinessType;

/**
 * @author yuandl 2016-12-04
 *
 */
@Service
@Transactional
public class OaDocCodeServiceImpl extends SimpleLongIdCrudRestService<OaDocCode> implements OaDocCodeService {

    @Autowired
    private OaDocCodeRepository repository;

    @Override
    public SimpleDomainRepository<OaDocCode, Long> getRepository() {
        return repository;
    }

    @Override
    public Long saveDocCode(DocCodeEditBean bean) {
        OaDocCode code = null;
        if (bean != null && bean.getId() != null) {
            code = repository.findOne(bean.getId());
        } else {
            code = new OaDocCode();
        }
        BeanUtils.copyProperties(bean, code);
        if(!"".equals(bean.getTemplateId()) && null != bean.getTemplateId()){
        	 DocTemplate docTemplate = new DocTemplate();
             docTemplate.setId(Long.parseLong(bean.getTemplateId()));
             code.setTemplateId(Long.parseLong(bean.getTemplateId()));
             code.setDocTemplate(docTemplate);
        }
        repository.save(code);

        return code.getId();
    }

    @Override
    public void delDocCode(Long id) {
        if (id != null) {
            OaDocCode code = repository.findOne(id);
            if (code != null) {
                repository.delete(code);
            }
        }
    }

    @Override
    public List<OaDocCode> selectDocCodesByCodeType(WfBusinessType codeType) {
        return repository.findByCodeTypeOrderByCodeSortAsc(codeType);
    }

    @Override
	public List<DocCate> selectCodeTypeGroupByDocCate(WfBusinessType codeType) {
		return repository.findByCodeTypeGroupByDocCate(codeType);
	}

	@Override
	public List<OaDocCode> selectDocCateAndCodeTypeOrderByCodeSortAsc(DocCate docCate, WfBusinessType codeType) {
		return repository.findByDocCateAndCodeTypeOrderByCodeSortAsc(docCate, codeType);
	}

	@Override
	public List<OaDocCode> findByCodeNameAndCodeType(String codeName, WfBusinessType codeType) {
		return repository.findByCodeNameAndCodeType(codeName, codeType);
	}


}
