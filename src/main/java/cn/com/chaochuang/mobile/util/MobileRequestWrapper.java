package cn.com.chaochuang.mobile.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

/**
 * 2017-9-18
 *
 * @author Shicx
 */
public class MobileRequestWrapper extends HttpServletRequestWrapper {

    /**将参数保存到这个map中*/
    private Map<String, String[]> params = new HashMap<String, String[]>();


    public MobileRequestWrapper(HttpServletRequest request) {
        super(request);
        //将参数表，赋予给当前的Map以便于持有request中的参数
        this.params.putAll(request.getParameterMap());
    }

    public MobileRequestWrapper(HttpServletRequest request, Map<String, Object> extendParams) {
        this(request);
        //这里将扩展参数写入参数表
        addAllParameters(extendParams);
    }

    /**
     * 获取参数值
     *
     * @param name
     * @return
     */
    @Override
    public String getParameter(String name) {
        //重写getParameter，代表参数从当前类中的map获取
        String[] values = params.get(name);
        if (values == null || values.length == 0) {
            return null;
        }
        return values[0];
    }

    @Override
    public Map<String, String[]> getParameterMap() {
        return this.params;
    }

    @Override
    public Enumeration<String> getParameterNames() {
        Vector<String> namesVector = new Vector<String>();
        if (this.params != null) {
            namesVector.addAll(this.params.keySet());
        }
        return namesVector.elements();
    }

    /**
     * 获取参数值
     *
     * @param name
     * @return
     */
    @Override
    public String[] getParameterValues(String name) {
        String[] strArr = params.get(name);
        if (strArr != null) {
            for (int i = 0; i < strArr.length; i++) {
                strArr[i] = strArr[i];
            }
        }
        return strArr;
    }

    /**
     * 增加多个参数
     *
     * @param otherParams
     */
    public void addAllParameters(Map<String, Object> otherParams) {
        for (Map.Entry<String, Object> entry : otherParams.entrySet()) {
            addParameter(entry.getKey(), entry.getValue());
        }
    }


    /**
     * 增加参数
     *
     * @param name
     * @param value
     */
    public void addParameter(String name, Object value) {
        if (value != null) {
            if (value instanceof String[]) {
                params.put(name, (String[]) value);
            } else if (value instanceof String) {
                params.put(name, new String[]{(String) value});
            } else {
                params.put(name, new String[]{String.valueOf(value)});
            }
        }
    }
}
