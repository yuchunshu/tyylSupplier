package cn.com.chaochuang.doc.event.web;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author hzr 2017-5-25
 *
 */
@Controller
@RequestMapping("export")
public class WordExportController {

    private String          regEx_html = "<[^>]+>"; // 定义HTML标签的正则表达式


    @RequestMapping("/exportWord")
    public ModelAndView exportWord(String id, HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mav = new ModelAndView("/model/doctemp01");
        mav.addObject("obj", "测试TMP");
        mav.addObject("fileName","ttttt");
        return mav;
    }

    /**
     * 按照段落解析
     */
    private List<String> parseContent(String source) {
        List<String> list = new ArrayList<String>();
        if (StringUtils.isNotEmpty(source)) {
            source = StringEscapeUtils.unescapeHtml(source);
            String[] sourceArr = source.split("<p>");
            Pattern p_html = Pattern.compile(regEx_html, Pattern.CASE_INSENSITIVE);
            for (String sec : sourceArr) {
                if (StringUtils.isNotEmpty(sec)) {
                    Matcher m_html = p_html.matcher(sec);
                    sec = m_html.replaceAll(""); // 过滤html标签
                    list.add(sec);
                }
            }
        }
        return list;
    }
}
