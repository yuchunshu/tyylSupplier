/*
 * FileName:    SysPowerSupportService.java
 * Description:
 * Company:     南宁超创信息工程有限公司
 * Copyright:   ChaoChuang (c) 2014
 * History:     2014年11月28日 (guig) 1.0 Create
 */

package cn.com.chaochuang.common.syspower.service;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

import javax.transaction.Transactional;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Service;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

import cn.com.chaochuang.common.power.PowerSupport;
import cn.com.chaochuang.common.power.PowerUtil;
import cn.com.chaochuang.common.power.url.PowerUrl;
import cn.com.chaochuang.common.security.util.UserTool;
import cn.com.chaochuang.common.syspower.domain.SysPower;
import cn.com.chaochuang.common.syspower.domain.SysPowerModule;
import cn.com.chaochuang.common.syspower.reference.PowerType;
import cn.com.chaochuang.common.syspower.repository.SysPowerRepository;
import cn.com.chaochuang.common.util.ModuleUtils;

/**
 * @author guig
 *
 */
@Service
@Transactional
public class SysPowerSupportService implements PowerSupport, ApplicationListener<ContextRefreshedEvent> {

    private final static Integer                UPDATE_SHOW_MENU_FLAG      = new Integer(9999);

    private final static String                 AUTO_ADD_POWER_NAME_PREFIX = "自动增加_";

    private final static String                 AUTO_ADD_POWER_CODE_PREFIX = "9999";

    protected final Log                         logger                     = LogFactory.getLog(getClass());

    private volatile ThreadLocal<AtomicInteger> powerCodeSeed              = new ThreadLocal<AtomicInteger>() {
                                                                               protected AtomicInteger initialValue() {
                                                                                   return new AtomicInteger(1);
                                                                               };
                                                                           };

    private volatile ThreadLocal<Integer>       updateShowMenuFlag         = new ThreadLocal<Integer>();

    @Autowired
    private SysPowerRepository                  repository;

    @Autowired
    private UserPowerService                    userPowerService;

    @Autowired
    private SysPowerModuleService               powerModuleService;

    /** 访问权限 */
    @Value(value = "${security.PowerUrlVoter.allPowerUrlGranted}")
    private boolean                             allPowerUrlGranted;

    private Map<String, SysPower> getAllSysPowerForUrlMap() {
        Map<String, SysPower> resultMap = Maps.newLinkedHashMap();

        for (SysPower p : repository.findAll()) {
            if (StringUtils.isNotBlank(p.getUrl())) {
                resultMap.put(PowerUtil.standPowerUrl(p.getUrl().trim()), p);
            }
        }

        return resultMap;
    }

    private String buildPowerCodeString(int c) {
        String s = "0000" + Integer.toString(c * 100);
        return s.substring(s.length() - 4);
    }

    private SysPower buildNewSysPower(String idStr, String url) {
        SysPower power = new SysPower();

        power.setPowerCode(AUTO_ADD_POWER_CODE_PREFIX + idStr);
        power.setPowerName(AUTO_ADD_POWER_NAME_PREFIX + idStr);
        power.setPowerTypeFlag(PowerType.自动增加);

        power.setUrl(url);

        //根据规则匹配模块和属性
        power.setModule(ModuleUtils.matchModuleName(this.getAllPowerModule(), url));
        power.setOperate(ModuleUtils.matchOperate(url));

        return power;
    }

    @Cacheable
    private List<SysPowerModule> getAllPowerModule(){
    	return this.powerModuleService.getRepository().findAll();
    }

    public void syncSystemUrlToSysPower() {
        PowerUrl powerUrl = PowerUtil.getPowerUrl();

        if (null != powerUrl) {
            Map<String, SysPower> powerMap = getAllSysPowerForUrlMap();

            Set<String> appendUrlSet = Sets.newLinkedHashSet();

            for (String url : powerUrl.getExistsResourceUrl()) {
                String surl = PowerUtil.standPowerUrl(url);
                if (!powerMap.containsKey(surl)) {
                    appendUrlSet.add(surl);
                }
            }

            if (!appendUrlSet.isEmpty()) {
                for (String url : appendUrlSet) {
                    String idStr = Integer.toString(url.hashCode());

                    SysPower power = buildNewSysPower(idStr, url);

                    if (logger.isWarnEnabled()) {
                        logger.warn("fine new url and add power[" + power.toString() + "]");
                    }

                    repository.save(power);
                }
            }
        }
    }

    /**
     * (non-Javadoc)
     *
     * @see cn.com.chaochuang.common.power.PowerSupport#getCurrentAccountName()
     */
    @Override
    public String getCurrentAccountName() {
        return UserTool.getInstance().getCurrentUserAccount();
    }

    /**
     * (non-Javadoc)
     *
     * @see cn.com.chaochuang.common.power.PowerSupport#checkUrlPowerForCurrentUser(java.lang.String)
     */
    @Override
    public boolean checkUrlPowerForCurrentUser(String url) {
        // true是未开启访问权限
        if (allPowerUrlGranted) {
            return true;
        }
        String userId = UserTool.getInstance().getCurrentUserId();
        if(this.userPowerService.validateUrlPower(url,userId)){
        	return true;
        }
        if (null != userId) {
            Set<String> urlSet = userPowerService.userPowerUrlSet(userId);
            if (null != urlSet) {
                return urlSet.contains(url);
            }
        }
        return false;
    }

    /**
     * (non-Javadoc)
     *
     * @see org.springframework.context.ApplicationListener#onApplicationEvent(org.springframework.context.ApplicationEvent)
     */
    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        new Thread(new Runnable() {
            public void run() {
                syncSystemUrlToSysPower();
            }
        }).start();
    }

    private boolean updateOtherPowerByPowerNameRul(final String nameTemplate, final String parentCode,
                    final String parentUrl, final String selfUrl) {
        boolean result = false;

        String goodParentUrl = parentUrl;

        if (!goodParentUrl.endsWith("/")) {
            goodParentUrl += "/";
        }

        Map<String, String> otherUrlMap = Maps.newLinkedHashMap();
        Map<String, String> datamap = PowerUtil.getParentUrlDetailMap().get(parentUrl);
        if (null != datamap) {
            otherUrlMap.putAll(datamap);
        }
        datamap = PowerUtil.getParentUrlDetailMap().get(selfUrl);
        if (null != datamap) {
            otherUrlMap.putAll(datamap);
        }
        datamap = PowerUtil.getParentUrlDetailMap().get(nameTemplate);
        if (null != datamap) {
            otherUrlMap.putAll(datamap);
        }

        Map<String, String> ruleMap = PowerUtil.getPowerNameRuleMap();

        int seed = 1;
        for (String name : ruleMap.keySet()) {
            String url = goodParentUrl + name;
            SysPower power = repository.findByUrl(url);

            if ((null != power) && !selfUrl.equals(power.getUrl())) {
                String powerName = nameTemplate + ruleMap.get(name);
                int codeid = seed++;
                String pcode = parentCode + buildPowerCodeString(codeid);

                if (otherUrlMap.containsKey(url)) {
                    powerName = nameTemplate + otherUrlMap.get(url);
                    otherUrlMap.remove(url);
                }

                if (!powerName.equals(power.getPowerName()) || !pcode.equals(power.getPowerCode())) {
                    power.setPowerCode(pcode);
                    power.setPowerName(powerName);
                    repository.save(power);

                    result = true;
                }
            }
        }

        if (MapUtils.isNotEmpty(otherUrlMap)) {
            result = updateOtherPowerByOtherUrlMap(nameTemplate, parentCode, seed, otherUrlMap) || result;
        }

        return result;
    }

    private boolean updateOtherPowerByOtherUrlMap(final String nameTemplate, final String parentCode,
                    final int startIndex, final Map<String, String> otherUrlMap) {
        boolean result = false;

        int codeseed = startIndex;
        for (String url : otherUrlMap.keySet()) {
            SysPower power = repository.findByUrl(url);

            if (null != power) {
                String powerName = nameTemplate + otherUrlMap.get(url);
                String pcode = parentCode + buildPowerCodeString(codeseed++);
                if (!powerName.equals(power.getPowerName()) || !pcode.equals(power.getPowerCode())) {
                    power.setPowerCode(pcode);
                    power.setPowerName(powerName);
                    repository.save(power);

                    result = true;
                }
            }
        }
        return result;
    }

    /**
     * (non-Javadoc)
     *
     * @see cn.com.chaochuang.common.power.PowerSupport#updateShowMenu(java.lang.String, java.lang.String,
     *      cn.com.chaochuang.common.power.PowerSupport.IgnoreCallback)
     */
    @Override
    public boolean updateShowMenu(String menutitle, String menuurl, IgnoreCallback callback) {
        if (!canUpdateShowMenu())
            return false;

        if (logger.isInfoEnabled()) {
            logger.info("[" + menutitle + "] = [" + menuurl + "]");
        }

        boolean result = false;
        if (null != menuurl) {
            if ((null == callback) || !callback.isIgnore(menutitle, menuurl)) {
                String surl = PowerUtil.standPowerUrl(menuurl);

                SysPower power = repository.findByUrl(surl);

                int codeid = powerCodeSeed.get().getAndIncrement();
                String pcode = buildPowerCodeString(codeid);

                if (null != power) {
                    if (!menutitle.equals(power.getPowerName()) || !pcode.equals(power.getPowerCode())) {
                        power.setPowerCode(pcode);
                        power.setPowerName(menutitle);

                        repository.save(power);

                        result = true;
                    }

                    result = updateOtherPowerByPowerNameRul(power.getPowerName(), power.getPowerCode(),
                                    PowerUtil.parentPath(power.getUrl()), StringUtils.defaultString(power.getUrl(), ""))
                                    || result;
                }
            } else {
                if (logger.isInfoEnabled()) {
                    logger.info("  ==>> ignore [" + menutitle + "] = [" + menuurl + "]");
                }
            }
        }

        return result;
    }

    /**
     * (non-Javadoc)
     *
     * @see cn.com.chaochuang.common.power.PowerSupport#updateShowMenu(java.lang.String, java.util.Map,
     *      cn.com.chaochuang.common.power.PowerSupport.IgnoreCallback)
     */
    @Override
    public boolean updateShowMenu(String menutitle, Map<String, String> submenu, IgnoreCallback callback) {
        if (!canUpdateShowMenu())
            return false;

        if (logger.isInfoEnabled()) {
            logger.info("[" + menutitle + "] = " + submenu.toString());
        }

        boolean result = false;

        Map<String, String> targetSubmenu = Maps.newLinkedHashMap();
        for (String key : submenu.keySet()) {
            String url = submenu.get(key);
            if ((null == callback) || !callback.isIgnore(key, url)) {
                targetSubmenu.put(key, url);
            } else {
                if (logger.isInfoEnabled()) {
                    logger.info("  ==>> ignore [" + key + "] = [" + url + "]");
                }
            }
        }

        submenu = targetSubmenu;
        if (!submenu.isEmpty()) {
            SysPower pp = repository.findByPowerName(menutitle);
            String parentCodeStr = "";

            int codeid = powerCodeSeed.get().getAndIncrement();
            String pcode = buildPowerCodeString(codeid);

            if (null == pp) {
                String idStr = Integer.toString(menutitle.hashCode());
                pp = buildNewSysPower(idStr, "");
                pp.setPowerCode(pcode);
                pp.setPowerName(menutitle);
                pp.setUrl("");

                repository.save(pp);
                result = true;
            } else {
                if (!menutitle.equals(pp.getPowerName()) || !pcode.equals(pp.getPowerCode())) {
                    pp.setPowerCode(pcode);
                    pp.setPowerName(menutitle);
                    pp.setUrl("");

                    repository.save(pp);
                    result = true;
                }
            }
            parentCodeStr = pp.getPowerCode();

            int childIndex = 1;

            for (String childmenutitle : submenu.keySet()) {
                String url = submenu.get(childmenutitle);
                if (StringUtils.isNotBlank(url)) {
                    url = PowerUtil.standPowerUrl(url);

                    SysPower childpower = repository.findByUrl(url);

                    String childcode = parentCodeStr + buildPowerCodeString(childIndex++);
                    if (null != childpower) {
                        if (!childmenutitle.equals(childpower.getPowerName())
                                        || !childcode.equals(childpower.getPowerCode())) {
                            childpower.setPowerName(childmenutitle);
                            childpower.setPowerCode(childcode);

                            repository.save(childpower);
                            result = true;
                        }

                        result = updateOtherPowerByPowerNameRul(childpower.getPowerName(), childpower.getPowerCode(),
                                        PowerUtil.parentPath(childpower.getUrl()),
                                        StringUtils.defaultString(childpower.getUrl(), ""))
                                        || result;
                    }
                }
            }

            Map<String, String> otherUrlMap = PowerUtil.getParentUrlDetailMap().get(menutitle);
            if (null != otherUrlMap) {
                result = updateOtherPowerByOtherUrlMap(menutitle, parentCodeStr, childIndex, otherUrlMap) || result;
            }
        }

        return result;
    }

    /**
     * (non-Javadoc)
     *
     * @see cn.com.chaochuang.common.power.PowerSupport#isUpdateShowMenuEnable()
     */
    @Override
    public boolean isUpdateShowMenuEnable() {
        return canUpdateShowMenu();
    }

    private boolean canUpdateShowMenu() {
        return UPDATE_SHOW_MENU_FLAG.equals(updateShowMenuFlag.get());
    }

    public void startUpdateShowMenu() {
        powerCodeSeed.get().set(1);
        updateShowMenuFlag.set(UPDATE_SHOW_MENU_FLAG);
    }

    public void stopUpdateShowMenu() {
        updateShowMenuFlag.set(Integer.MIN_VALUE);
        powerCodeSeed.get().set(1);
    }
}
