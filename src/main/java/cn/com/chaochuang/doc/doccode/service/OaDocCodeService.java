package cn.com.chaochuang.doc.doccode.service;

import java.util.List;

import cn.com.chaochuang.common.data.service.CrudRestService;
import cn.com.chaochuang.doc.doccode.bean.DocCodeEditBean;
import cn.com.chaochuang.doc.doccode.domain.OaDocCode;
import cn.com.chaochuang.doc.event.reference.DocCate;
import cn.com.chaochuang.workflow.reference.WfBusinessType;

/**
 * @author yuandl 2016-12-04
 *
 */
public interface OaDocCodeService extends CrudRestService<OaDocCode, Long> {

    /**
     * 保存字号
     *
     * @param bean
     * @return
     */
    public Long saveDocCode(DocCodeEditBean bean);

    /**
     * 删除字号
     *
     * @param id
     */
    public void delDocCode(Long id);

    /**
     * 查询字号
     *
     * @param codeType
     * @return
     */
    public List<OaDocCode> selectDocCodesByCodeType(WfBusinessType codeType);

    /**
     * 查询文种
     *
     * @param codeType
     * @return
     */
    public List<DocCate> selectCodeTypeGroupByDocCate(WfBusinessType codeType);

    /**
     * 根据文种查询字号
     *
     * @param codeType
     * @param codeType
     * @return
     */
    public List<OaDocCode> selectDocCateAndCodeTypeOrderByCodeSortAsc(DocCate docCate,WfBusinessType codeType);

    /** 根据字号名称和公文类型查找对应 的模板（用于套红）*/
    List<OaDocCode> findByCodeNameAndCodeType(String codeName, WfBusinessType codeType);


}
