package cn.com.chaochuang.common.syspower.web;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;

import cn.com.chaochuang.common.cache.RefreshCache;
import cn.com.chaochuang.common.data.bean.SearchResult;
import cn.com.chaochuang.common.data.persistence.SearchBuilder;
import cn.com.chaochuang.common.data.web.SimpleCrudController;
import cn.com.chaochuang.common.syspower.domain.SysPower;
import cn.com.chaochuang.common.syspower.reference.PowerType;
import cn.com.chaochuang.common.syspower.service.SysPowerService;
import cn.com.chaochuang.common.syspower.service.SysPowerSupportService;
import cn.com.chaochuang.velocity.toolbox2.VelocityToolbox2ViewResolver;

@Controller
@RequestMapping("syspower")
public class SysPowerController extends SimpleCrudController<SysPower, SysPowerService> {

    protected final Log                  logger = LogFactory.getLog(getClass());

    @Autowired(required = false)
    private SysPowerSupportService       sysPowerSupportService;

    @Autowired(required = false)
    private VelocityToolbox2ViewResolver velocityViewResolver;

    @Value("${syspower.powermenu.viewname}")
    private String                       powerMenuViewName;

    @Autowired(required = false)
    private RefreshCache[]               refreshCaches;

    @RequestMapping(value = "/save", params = "ids")
    public ModelAndView savePowerType(PowerType powerTypeFlag, String ids, HttpServletRequest request) {
        ModelAndView mav = new ModelAndView("redirect:list");

        if ((null != powerTypeFlag) && StringUtils.isNotBlank(ids)) {
            String[] idarray = ids.split(",");
            for (String id : idarray) {
                Long longid = NumberUtils.toLong(id, -1);
                if (-1 != longid) {
                    SysPower power = new SysPower();
                    power.setId(longid);
                    power.setPowerTypeFlag(powerTypeFlag);

                    power = mergeEntityIfNotNew(power);

                    getCrudRestService().persist(power);
                }
            }
        }

        return mav;
    }

    @RequestMapping(value = "/refreshpower")
    public ModelAndView refreshPower(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mav = new ModelAndView("redirect:list");

        if (null != refreshCaches) {
            for (RefreshCache rc : refreshCaches) {
                rc.refreshCache();
            }
        }

        if ((null != sysPowerSupportService) && (null != velocityViewResolver)) {
            sysPowerSupportService.syncSystemUrlToSysPower();
            sysPowerSupportService.startUpdateShowMenu();
            try {

                try {
                    View v = velocityViewResolver.resolveViewName(powerMenuViewName, null);
                    v.render(new HashMap(), request, response);
                } catch (Exception e) {
                    if (logger.isErrorEnabled()) {
                        logger.error("error in refreshPower.", e);
                    }
                }
            } finally {
                sysPowerSupportService.stopUpdateShowMenu();
            }
        }

        return mav;
    }

    /**
     * (non-Javadoc)
     *
     * @see cn.com.chaochuang.common.data.web.SimpleCrudServiceController#beforeList(org.springframework.web.servlet.ModelAndView,
     *      java.lang.Integer, java.lang.Integer, java.lang.String, java.lang.String,
     *      cn.com.chaochuang.common.data.persistence.SearchBuilder, javax.servlet.http.HttpServletRequest,
     *      java.lang.Boolean)
     */
    @Override
    protected SearchResult beforeList(ModelAndView mav, Integer pageNum, Integer numPerPage, String orderField,
                    String orderDirection, SearchBuilder<SysPower, Long> searchBuilder, HttpServletRequest request,
                    Boolean isPageBreak) {
        searchBuilder.appendSort("powerCode");
        return super.beforeList(mav, pageNum, numPerPage, orderField, orderDirection, searchBuilder, request,
                        isPageBreak);
    }

    /**
     * (non-Javadoc)
     *
     * @see cn.com.chaochuang.common.data.web.SimpleCrudServiceController#beforeSave(org.springframework.web.servlet.ModelAndView,
     *      java.lang.Object, org.springframework.validation.BindingResult, javax.servlet.http.HttpServletRequest)
     */
    @Override
    protected void beforeSave(ModelAndView mav, SysPower entity, BindingResult bindingResult, HttpServletRequest request) {
        throw new RuntimeException("do not save syspower");
    }

    /**
     * (non-Javadoc)
     *
     * @see cn.com.chaochuang.common.data.web.SimpleCrudServiceController#beforeDel(org.springframework.web.servlet.ModelAndView,
     *      java.lang.String[])
     */
    @Override
    protected void beforeDel(ModelAndView mav, String[] idset) {
        throw new RuntimeException("do not del syspower");
    }

    /**
     * (non-Javadoc)
     *
     * @see cn.com.chaochuang.common.data.web.CrudController#isPageBreakForList()
     */
    @Override
    protected boolean isPageBreakForList() {
        return false;
    }

}
