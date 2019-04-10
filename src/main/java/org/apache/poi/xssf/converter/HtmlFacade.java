package org.apache.poi.xssf.converter;

import java.util.LinkedHashMap;
import java.util.Map;

public class HtmlFacade {
    private Map<String, Map<String, String>> stylesheet = new LinkedHashMap<String, Map<String, String>>();

    public String getOrCreateCssClass(String classNamePrefix, String style) {
        if (!stylesheet.containsKey(classNamePrefix))
            stylesheet.put(classNamePrefix, new LinkedHashMap<String, String>(1));

        Map<String, String> styleToClassName = stylesheet.get(classNamePrefix);
        String knownClass = styleToClassName.get(style);
        if (knownClass != null)
            return knownClass;

        String newClassName = classNamePrefix + (styleToClassName.size() + 1);
        styleToClassName.put(style, newClassName);
        return newClassName;
    }

    protected String buildStylesheet(final Map<String, Map<String, String>> prefixToMapOfStyles) {
        StringBuilder stringBuilder = new StringBuilder();
        for (Map<String, String> byPrefix : prefixToMapOfStyles.values()) {
            for (Map.Entry<String, String> byStyle : byPrefix.entrySet()) {
                String style = byStyle.getKey();
                String className = byStyle.getValue();

                stringBuilder.append(".");
                stringBuilder.append(className);
                stringBuilder.append("{");
                stringBuilder.append(style);
                stringBuilder.append("}\n");
            }
        }
        final String stylesheetText = stringBuilder.toString();
        return stylesheetText;
    }

    public String updateStylesheet() {
        return buildStylesheet(stylesheet);
    }
}
