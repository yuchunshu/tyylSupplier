/*
 * FileName:    GBRedirectView.java
 * Description:
 * Company:     
 * Copyright:    (c) 2016
 * History:     2016年2月15日 (LJX) 1.0 Create
 */

package cn.com.chaochuang.common.excelview;

import java.net.URLEncoder;

import org.springframework.web.servlet.view.RedirectView;

/**
 * @author LJX
 *
 */
public class GBRedirectView extends RedirectView {
    private boolean isGBChar(char c) {
        return ((c < 0) || (c > 0x007f));
    }

    private String encodeGBUrl(final String url) {
        int index;
        if ((null != url) && ((index = url.indexOf('?')) >= 0)) {
            StringBuffer urlBuffer = new StringBuffer();
            urlBuffer.append(url.substring(0, index + 1));

            int i = index + 1;
            int j;
            while (i < url.length()) {
                if (isGBChar(url.charAt(i))) {
                    j = i + 1;
                    while ((j < url.length()) && isGBChar(url.charAt(j))) {
                        j++;
                    }
                    urlBuffer.append(URLEncoder.encode(url.substring(i, j)));
                    i = j - 1;
                } else {
                    j = i + 1;
                    while ((j < url.length()) && (!isGBChar(url.charAt(j)))) {
                        j++;
                    }
                    urlBuffer.append(url.substring(i, j));
                    i = j - 1;
                }
                i++;
            }
            return urlBuffer.toString();
        }
        return url;
    }

    /**
     * @see org.springframework.web.servlet.view.AbstractUrlBasedView#setUrl(java.lang.String)
     */
    public void setUrl(String url) {
        super.setUrl(encodeGBUrl(url));
    }

    /**
     * Constructor for use as a bean.
     */
    public GBRedirectView() {
        super();
        super.setEncodingScheme("UTF-8");
    }

    /**
     * Create a new RedirectView with the given URL.
     * <p>
     * The given URL will be considered as relative to the web server, not as relative to the current ServletContext.
     *
     * @param url
     *            the URL to redirect to
     * @see #GBRedirectView(String, boolean)
     */
    public GBRedirectView(String url) {
        super(url);
        super.setEncodingScheme("UTF-8");
    }

    /**
     * Create a new RedirectView with the given URL.
     *
     * @param url
     *            the URL to redirect to
     * @param contextRelative
     *            whether to interpret the given URL as relative to the current ServletContext
     */
    public GBRedirectView(String url, boolean contextRelative) {
        super(url, contextRelative);
        super.setEncodingScheme("UTF-8");
    }

    /**
     * Create a new RedirectView with the given URL.
     *
     * @param url
     *            the URL to redirect to
     * @param contextRelative
     *            whether to interpret the given URL as relative to the current ServletContext
     * @param http10Compatible
     *            whether to stay compatible with HTTP 1.0 clients
     */
    public GBRedirectView(String url, boolean contextRelative, boolean http10Compatible) {
        super(url, contextRelative, http10Compatible);
        super.setEncodingScheme("UTF-8");
    }
}
