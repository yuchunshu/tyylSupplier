package cn.com.chaochuang.mobile.util;

import cn.com.chaochuang.common.reference.IsValid;
import cn.com.chaochuang.mobile.domain.SysMobileDevice;
import cn.com.chaochuang.mobile.reference.RegisterStatus;
import cn.com.chaochuang.mobile.repository.SysMobileDeviceRepository;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 2017-9-18
 *
 * @author Shicx
 */
public class MobileParamsFilter implements Filter {

    /**各参数的对应名称*/
    private static final String KEY_TOKEN ="token";
    private static final String KEY_PARAM_NAME ="data";
    private static final String KEY_ENCODE_FLAG="__encodeFlag";
    private static final String KEY_UID ="uid";
    private static final String KEY_UKEY ="ukey";
    /**请求的有效时间（单位：分钟）*/
    private static final Integer overMinu=20;
    /**不验证token的请求*/
    private List<String> excludeUriPath;
    /**是否进行加密解密和token验证，在web.xml中配置*/
    private boolean encodeFlag;

    private SysMobileDeviceRepository sysMobileDeviceRepository;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        String path = filterConfig.getInitParameter("excludePath");
        String encodeFlagStr = filterConfig.getInitParameter("encodeFlag");
        if(!this.isBlank(path)){
            String[] pathArr = path.split(";");
            excludeUriPath = Arrays.asList(pathArr);
        }
        if(!this.isBlank(encodeFlagStr)){
            encodeFlag = Boolean.valueOf(encodeFlagStr);
        }else{
            encodeFlag = true;
        }
        //获取sysMobileDeviceRepository
        ServletContext context = filterConfig.getServletContext();
        ApplicationContext ac = WebApplicationContextUtils.getWebApplicationContext(context);
        sysMobileDeviceRepository = (SysMobileDeviceRepository) ac.getBean("sysMobileDeviceRepository");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        response.setCharacterEncoding(AesTool.EN_UTF8);
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        String uriPath = httpRequest.getRequestURI();
        try {
            if(encodeFlag||StringUtils.isNotBlank(httpRequest.getParameter(KEY_ENCODE_FLAG))) {
                String dataStr = httpRequest.getParameter(KEY_PARAM_NAME);
                //附件下载请求
                if(uriPath.indexOf("attach/download.mo")!=-1){
                    dataStr = URLDecoder.decode(dataStr,"utf-8");
                }
                //解密
                String decStr = AesTool.decode(dataStr);
                //解析参数
                Map<String, Object> dataMap = this.deserializeData(decStr);
                //加密标识
                dataMap.put("aesFlag_",true);
                int result = this.checkToken(dataMap, uriPath);
                if (result==0) {
                    //使用自定义的 ServletRequest
                    filterChain.doFilter(new MobileRequestWrapper(httpRequest, dataMap), response);
                } else {
                    httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    httpResponse.setContentType("application/json;charset=utf-8");
                    httpResponse.getWriter().write("{\"success\":"+result+",\"message\":\"参数错误\"}");
                }
            }else{
                //不进行解密
                filterChain.doFilter(request, response);
            }
        }catch (Exception e){
            e.printStackTrace();
            httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            httpResponse.setContentType("application/json;charset=utf-8");
            //success:0 错误
            httpResponse.getWriter().write("{\"success\":0,\"message\":\"系统错误\"}");
        }

    }

    /**
     * 检查token是否有效
     * @param dataMap
     * @param uriPath
     * @return 0:验证通过 1:参数错误 2:token无效 3:验证不通过 4:url过期
     */
    private int checkToken(Map<String, Object> dataMap, String uriPath) {
        //检查uri是否需要验证
        if(this.checkIsTokenPath(uriPath)) {
            //检查用户id、ukey、token是否存在
            Object uidObj = dataMap.get(KEY_UID);
            Object ukeyAes = dataMap.get(KEY_UKEY);
            Object token = dataMap.get(KEY_TOKEN);
            //在token中加入的参数,要与移动端一致
            Object tsObj = dataMap.get("ts");
            String p1,p2;
            if(tsObj==null){
                return 1;
            }
            Long ts = Long.valueOf(tsObj.toString());
            Long t = System.currentTimeMillis();
            Long abs = Math.abs(t-ts);
            //超过时间则不通过
            if(abs/(1000*60)>overMinu){
                return 4;
            }
            p1=ts/3+"";
            p2=ts/5+"";
            if (uidObj == null || StringUtils.isEmpty(uidObj.toString()) || token == null) {
                return 1;
            }
            if(ukeyAes == null || StringUtils.isEmpty(ukeyAes.toString())){
                return 2;
            }
            String uid = uidObj.toString();
            //ukey
            String ukey = AesTool.decode(ukeyAes.toString());
            //验证ukey
            List<SysMobileDevice> deviceList = sysMobileDeviceRepository.findByUserIdAndUkeyAndValid(new Long(uid),ukey, IsValid.有效);
            if(deviceList==null||deviceList.size()==0){
                //无效的ukey
                return 2;
            }
            for(SysMobileDevice device: deviceList){
                if(!RegisterStatus.注册通过.equals(device.getRegisterStatus())){
                    return 3;
                }
            }
            //判断token
            String md5Token = AesTool.md5(AesTool.encode(uid + ukey+p1+p2,ukey));
            //清空map 中的 token
            dataMap.put(KEY_TOKEN, null);
            if (md5Token.toLowerCase().equals(token.toString().toLowerCase())) {
                return 0;
            } else {
                return 1;
            }
        }
        return 0;
    }


    /**
     * 检查请求是否需要验证
     * @param uriPath
     * @return true:需要验证 false: 不需要验证
     */
    private boolean checkIsTokenPath(String uriPath) {
        if(excludeUriPath!=null){
            for(String regPath:excludeUriPath){
                Pattern pattern = Pattern.compile(regPath);
                Matcher matcher = pattern.matcher(uriPath);
                if(matcher.matches()){
                    //不需要验证
                    return false;
                }
            }
        }
        //需要验证
        return true;
    }

    /**
     * 解析请求的字符串参数(p1=xx&p2=xx...)
     * @param decStr
     * @return
     * @throws UnsupportedEncodingException
     */
    public Map<String, Object> deserializeData(String decStr) {
        Map<String, Object> dataMap = new HashMap<String, Object>();
        try {
            if (decStr != null && !"".equals(decStr)) {

                String[] dataArr = decStr.split("&");
                for (String str : dataArr) {
                    String[] params = new String[2];
                    int i = str.indexOf("=");
                    if (i >= 0) {
                        params[0] = str.substring(0, i);
                        if (i + 1 < str.length()) {
                            params[1] = str.substring(i + 1);
                        }
                        if (params[1] == null) {
                            params[1] = "";
                        }
                    }
                    if (params[0] != null) {
                        if(KEY_UKEY.equals(params[0])){
                            //ukey不进行转码
                            dataMap.put(params[0],params[1]);
                        }else{
                            Object oldVal = dataMap.put(URLDecoder.decode(params[0], AesTool.EN_UTF8), URLDecoder.decode(params[1], AesTool.EN_UTF8));
                            if (oldVal != null) {
                                if (oldVal instanceof String) {
                                    dataMap.put(URLDecoder.decode(params[0], AesTool.EN_UTF8), new String[]{URLDecoder.decode(params[1], AesTool.EN_UTF8), oldVal.toString()});
                                }
                                if (oldVal instanceof String[]) {
                                    List<String> strArr = new ArrayList<String>(Arrays.asList((String[]) oldVal));
                                    strArr.add(URLDecoder.decode(params[1], AesTool.EN_UTF8));
                                    dataMap.put(URLDecoder.decode(params[0], AesTool.EN_UTF8), strArr.toArray());
                                }
                            }
                        }
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        return dataMap;
    }

    @Override
    public void destroy() {

    }

    private boolean isBlank(String str) {
        return str == null || "".equals(str.trim());
    }

    public static void main(String[] args) {
        MobileParamsFilter paramsFilter = new MobileParamsFilter();
        try {
//            paramsFilter.excludeUriPath=Arrays.asList(new String[]{".*mobile/sys/login.mo"});
//            boolean result = paramsFilter.checkIsTokenPath("swfda_oa/mobile/sys/login.mo");
            System.out.println(paramsFilter.deserializeData("a=&b=c=&d=1"));
            System.out.println(JSONObject.toJSONString(""));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
