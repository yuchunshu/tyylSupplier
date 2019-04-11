/*
 * FileName:    DirMatchFindUrlInFileMethod.java
 * Description:
 * Company:     
 * Copyright:    (c) 2014
 * History:     2014年12月25日 (guig) 1.0 Create
 */

package cn.com.chaochuang.common.syspower.service;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Component;

import cn.com.chaochuang.common.power.url.FindUrlInFileMethod;

/**
 * @author guig
 *
 */
@Component
public class DirMatchFindUrlInFileMethod implements FindUrlInFileMethod {

    private final static String MATCH_STR_PREFIX = "\"${link.directory()}";
    private final static String MATCH_STR_SUFFIX = "\"";

    private String buildMatchString(final String s) {
        return MATCH_STR_PREFIX + s + MATCH_STR_SUFFIX;
    }

    private String getFilename(final String url) {
        return FilenameUtils.getName(url);
    }

    private boolean isMatchDir(final File file, final String url) {
        String target = FilenameUtils.getFullPath(url) + file.getName();
        try {
            String filePath = FilenameUtils.separatorsToUnix(file.getCanonicalPath());
            return filePath.endsWith(target);
        } catch (IOException e) {
        }
        return false;
    }

    /**
     * (non-Javadoc)
     *
     * @see cn.com.chaochuang.common.power.url.FindUrlInFileMethod#find(java.lang.String, java.io.File,
     *      java.lang.String)
     */
    @Override
    public boolean find(String content, File file, String url) {
        if (isMatchDir(file, url)) {
            return (content.indexOf(buildMatchString(getFilename(url))) >= 0);
        }
        return false;
    }
}
