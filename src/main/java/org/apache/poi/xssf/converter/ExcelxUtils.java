package org.apache.poi.xssf.converter;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFFont;

/**
 * @author yuandl 2017-01-16
 *
 */
public class ExcelxUtils {
    /**
     * 获取垂直对齐
     * 
     * @param alignment
     * @return
     */
    public static String getVerticalAlign(short alignment) {
        String valign = "middle";
        switch (alignment) {
        case CellStyle.VERTICAL_BOTTOM:
            valign = "bottom";
            break;
        case CellStyle.VERTICAL_CENTER:
            valign = "center";
        case CellStyle.VERTICAL_TOP:
            valign = "top";
        default:
            break;
        }

        return valign;
    }

    /**
     * 设置垂直样式
     * 
     * @param style
     * @param alignment
     */
    public static void appendVerticalAlign(StringBuilder style, short alignment) {
        String cssValign = getVerticalAlign(alignment);
        if (StringUtils.isBlank(cssValign)) {
            return;
        }

        style.append("valign='");
        style.append(cssValign);
        style.append("';");
    }

    public static void buildStyle_border(StringBuilder style, String type, short xlsBorder, XSSFColor borderColor) {
        if (xlsBorder == XSSFCellStyle.BORDER_NONE)
            return;

        StringBuilder borderStyle = new StringBuilder();
        borderStyle.append(ExcelxUtils.getBorderWidth(xlsBorder));
        borderStyle.append(' ');
        borderStyle.append(ExcelxUtils.getBorderStyle(xlsBorder));
        borderStyle.append(ExcelxUtils.getColor(borderColor));

        style.append("border-" + type + ":" + borderStyle + ";");
    }

    public static void buildStyle_font(StringBuilder style, XSSFFont font) {
        switch (font.getBoldweight()) {
        case XSSFFont.BOLDWEIGHT_BOLD:
            style.append("font-weight:bold;");
            break;
        case XSSFFont.BOLDWEIGHT_NORMAL:
            // by default, not not increase HTML size
            // style.append( "font-weight: normal; " );
            break;
        }
        XSSFColor fontColor = font.getXSSFColor();
        if (fontColor != null)
            style.append("color: #" + fontColor.getARGBHex().substring(2) + "; ");

        if (font.getFontHeightInPoints() != 0)
            style.append("font-size:" + font.getFontHeightInPoints() + "pt;");

        if (font.getItalic()) {
            style.append("font-style:italic;");
        }
        if (StringUtils.isNotBlank(font.getFontName())) {
            style.append("font-family:'" + font.getFontName() + "';");
        }
    }

    public static String getBorderWidth(short xlsBorder) {
        final String borderWidth;
        switch (xlsBorder) {
        case XSSFCellStyle.BORDER_MEDIUM_DASH_DOT:
        case XSSFCellStyle.BORDER_MEDIUM_DASH_DOT_DOT:
        case XSSFCellStyle.BORDER_MEDIUM_DASHED:
            borderWidth = "2pt";
            break;
        case XSSFCellStyle.BORDER_THICK:
            borderWidth = "thick";
            break;
        default:
            borderWidth = "1pt";
            break;
        }
        return borderWidth;
    }

    public static String getBorderStyle(short xlsBorder) {
        final String borderStyle;
        switch (xlsBorder) {
        case XSSFCellStyle.BORDER_NONE:
            borderStyle = "none";
            break;
        case XSSFCellStyle.BORDER_DASH_DOT:
        case XSSFCellStyle.BORDER_DASH_DOT_DOT:
        case XSSFCellStyle.BORDER_DOTTED:
        case XSSFCellStyle.BORDER_HAIR:
        case XSSFCellStyle.BORDER_MEDIUM_DASH_DOT:
        case XSSFCellStyle.BORDER_MEDIUM_DASH_DOT_DOT:
        case XSSFCellStyle.BORDER_SLANTED_DASH_DOT:
            borderStyle = "dotted";
            break;
        case XSSFCellStyle.BORDER_DASHED:
        case XSSFCellStyle.BORDER_MEDIUM_DASHED:
            borderStyle = "dashed";
            break;
        case XSSFCellStyle.BORDER_DOUBLE:
            borderStyle = "double";
            break;
        default:
            borderStyle = "solid";
            break;
        }
        return borderStyle;
    }

    public static String getColor(XSSFColor color) {
        String colorStyle = "";
        if (color != null) {
            if (color.getIndexed() != 0) {
                for (IndexedColors c : IndexedColors.values()) {
                    if (c.getIndex() == color.getIndexed()) {
                        colorStyle = " " + (c.name().equals("AUTOMATIC") ? "BLACK" : c.name());
                        break;
                    }
                }
            } else {
                colorStyle = " #" + color.getARGBHex().substring(2);
            }

        }
        return colorStyle;
    }
}
