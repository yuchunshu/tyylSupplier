/*
 * FileName:    ForMijiVelocityLayout2View.java
 * Description:
 * Company:     
 * Copyright:    (c) 2014
 * History:     2014年12月25日 (guig) 1.0 Create
 */

package cn.com.chaochuang.common.velocity;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.com.chaochuang.common.util.MijiHelper;
import cn.com.chaochuang.velocity.toolbox2.VelocityLayout2View;

/**
 * @author guig
 *
 */
public class ForMijiVelocityLayout2View extends VelocityLayout2View {

    /**
     * (non-Javadoc)
     *
     * @see org.springframework.web.servlet.view.AbstractView#render(java.util.Map,
     *      javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
     */
    @Override
    public void render(Map<String, ?> model, HttpServletRequest request, HttpServletResponse response) throws Exception {
        MijiHelper.enterViewRender();

        try {
            super.render(model, request, response);
        } finally {
            MijiHelper.quitViewRender();
        }

    }

}
