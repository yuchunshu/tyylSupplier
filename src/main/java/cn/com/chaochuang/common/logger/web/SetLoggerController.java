package cn.com.chaochuang.common.logger.web;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Level;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("setlogger")
public class SetLoggerController {

    private String getRefeshString() {
        return "?_t=" + Long.toString(System.currentTimeMillis());
    }

    @RequestMapping("")
    public String defaultIndex(HttpServletRequest request) {
        return "redirect:/setlogger/index" + getRefeshString();
    }

    @RequestMapping("**")
    public String otherIndex(HttpServletRequest request) {
        return "redirect:/setlogger/index" + getRefeshString();
    }

    private Logger getHibernateSqlLogger() {
        return LogManager.getLogger("org.hibernate.SQL");
    }

    private Logger getRootLogger() {
        return LogManager.getRootLogger();
    }

    @RequestMapping("index")
    public @ResponseBody String index(HttpServletResponse response) {
        Logger logger = getRootLogger();

        boolean hasHibernateSqlLogger = false;
        Logger hibernateSqlLogger = getHibernateSqlLogger();
        Level hl = hibernateSqlLogger.getLevel();
        hasHibernateSqlLogger = (null != hl) && Level.DEBUG.equals(hl);

        String currentLevel = logger.getLevel().toString();
        StringBuilder result = new StringBuilder();

        result.append("<html><head><meta charset='utf-8'/></head><body><div>");
        result.append("当前全局日志设置：[").append(currentLevel).append("]<br/>");

        result.append("当前Hibernate ShowSql设置：[").append(Boolean.toString(hasHibernateSqlLogger)).append("]<br/>");

        String fs = getRefeshString();

        result.append("<br/>");
        result.append("<br/>");
        result.append("修改全局日志设置：<br/>");
        result.append("&nbsp;&nbsp;<a href='error").append(fs).append("' >设置error</a>").append("<br/>");
        result.append("&nbsp;&nbsp;<a href='warn").append(fs).append("' >设置warn</a>").append("<br/>");
        result.append("&nbsp;&nbsp;<a href='info").append(fs).append("' >设置info</a>").append("<br/>");
        result.append("&nbsp;&nbsp;<a href='debug").append(fs).append("' >设置debug</a>").append("<br/>");
        result.append("&nbsp;&nbsp;<a href='trace").append(fs).append("' >设置trace</a>").append("<br/>");

        result.append("<br/>");
        result.append("<br/>");
        result.append("修改Hibernate ShowSql设置：<br/>");
        if (hasHibernateSqlLogger) {
            result.append("&nbsp;&nbsp;<a href='hidehibernatesql").append(fs).append("' >隐藏hibernate的sql语句</a>")
                            .append("<br/>");
        } else {
            result.append("&nbsp;&nbsp;<a href='showhibernatesql").append(fs).append("' >显示hibernate的sql语句</a>")
                            .append("<br/>");
        }

        result.append("</div></doby></html>");

        try {
            response.getWriter().write(result.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @RequestMapping("/trace")
    public String trace() {
        Logger logger = LogManager.getRootLogger();
        logger.setLevel(Level.TRACE);
        return "redirect:/setlogger/index" + getRefeshString();
    }

    @RequestMapping("/debug")
    public String debug() {
        Logger logger = LogManager.getRootLogger();
        logger.setLevel(Level.DEBUG);
        return "redirect:/setlogger/index" + getRefeshString();
    }

    @RequestMapping("/info")
    public String info() {
        Logger logger = LogManager.getRootLogger();
        logger.setLevel(Level.INFO);
        return "redirect:/setlogger/index" + getRefeshString();
    }

    @RequestMapping("/warn")
    public String warn() {
        Logger logger = LogManager.getRootLogger();
        logger.setLevel(Level.WARN);
        return "redirect:/setlogger/index" + getRefeshString();
    }

    @RequestMapping("/error")
    public String error() {
        Logger logger = LogManager.getRootLogger();
        logger.setLevel(Level.ERROR);
        return "redirect:/setlogger/index" + getRefeshString();
    }

    @RequestMapping("/showhibernatesql")
    public String showhibernatesql() {
        Logger hibernateSqlLogger = getHibernateSqlLogger();
        if (null != hibernateSqlLogger) {
            hibernateSqlLogger.setLevel(Level.DEBUG);
        }
        return "redirect:/setlogger/index" + getRefeshString();
    }

    @RequestMapping("/hidehibernatesql")
    public String hidehibernatesql() {
        Logger hibernateSqlLogger = getHibernateSqlLogger();
        if (null != hibernateSqlLogger) {
            hibernateSqlLogger.setLevel(null);
        }
        return "redirect:/setlogger/index" + getRefeshString();
    }
}
