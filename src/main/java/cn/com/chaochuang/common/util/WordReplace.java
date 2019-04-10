/*
 * FileName:    WordReplace.java
 * Description:
 * Company:     南宁超创信息工程有限公司
 * Copyright:   ChaoChuang (c) 2015
 * History:     2015年12月24日 (LJX) 1.0 Create
 */

package cn.com.chaochuang.common.util;

import java.io.File;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.poi.POIXMLDocument;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;

/**
 * @author LJX
 *
 */
public class WordReplace {

    // 替换word中需要替换的特殊字符
    public static boolean replaceAndGenerateWord(String srcPath, String destPath, String fileName,
                    Map<String, String> map) {
        try {
            XWPFDocument document = new XWPFDocument(POIXMLDocument.openPackage(srcPath));
            // 替换段落中的指定文字
            Iterator<XWPFParagraph> itPara = document.getParagraphsIterator();
            while (itPara.hasNext()) {
                XWPFParagraph paragraph = (XWPFParagraph) itPara.next();
                List<XWPFRun> runs = paragraph.getRuns();
                for (int i = 0; i < runs.size(); i++) {
                    String oneparaString = runs.get(i).getText(runs.get(i).getTextPosition());
                    for (Map.Entry<String, String> entry : map.entrySet()) {
                        oneparaString = oneparaString.replace(entry.getKey(), entry.getValue());
                    }
                    runs.get(i).setText(oneparaString, 0);
                }
            }

            // 替换表格中的指定文字
            Iterator<XWPFTable> itTable = document.getTablesIterator();
            while (itTable.hasNext()) {
                XWPFTable table = (XWPFTable) itTable.next();
                int rcount = table.getNumberOfRows();
                for (int i = 0; i < rcount; i++) {
                    XWPFTableRow row = table.getRow(i);
                    List<XWPFTableCell> cells = row.getTableCells();
                    for (XWPFTableCell cell : cells) {
                        List<XWPFTable> tables = cell.getTables();
                        if (tables != null && tables.size() > 0) {
                            for (XWPFTable t : tables) {
                                int rc = table.getNumberOfRows();
                                for (int j = 0; j < rc; j++) {
                                    XWPFTableRow r = t.getRow(j);
                                    if (r != null) {
                                        List<XWPFTableCell> cs = r.getTableCells();
                                        if (cs != null && cs.size() > 0) {
                                            for (XWPFTableCell c : cs) {
                                                String cellTextStr = c.getText();
                                                for (Entry<String, String> ee : map.entrySet()) {
                                                    if (cellTextStr.contains(ee.getKey())) {
                                                        cellTextStr = cellTextStr.replace(ee.getKey(), ee.getValue());
                                                        List<XWPFParagraph> pList = c.getParagraphs();
                                                        if (pList != null && pList.size() > 0) {
                                                            XWPFParagraph pp = c.getParagraphs().get(0);
                                                            List<XWPFRun> rList = pp.getRuns();
                                                            if (rList != null && rList.size() > 0) {
                                                                for (int ri = rList.size() - 1; ri >= 0; ri--) {
                                                                    pp.removeRun(ri);
                                                                }
                                                            }
                                                            XWPFRun newRun = pp.createRun();
                                                            newRun.setFontSize(12);
                                                            newRun.setFontFamily("宋体");
                                                            newRun.setBold(false);
                                                            newRun.setText(cellTextStr);
                                                            pp.addRun(newRun);
                                                        }

                                                        // replaceInPara(c.getParagraphArray(0), "仿宋_GB2312", 13);
                                                    }

                                                }

                                            }
                                        }
                                    }
                                }
                            }
                        }
                        String cellTextString = cell.getText();
                        for (Entry<String, String> e : map.entrySet()) {
                            if (cellTextString.contains(e.getKey())) {
                                cellTextString = cellTextString.replace(e.getKey(), e.getValue());
                                List<XWPFParagraph> pList = cell.getParagraphs();
                                if (pList != null && pList.size() > 0) {
                                    XWPFParagraph pp = cell.getParagraphs().get(0);
                                    List<XWPFRun> rList = pp.getRuns();
                                    if (rList != null && rList.size() > 0) {
                                        for (int ri = rList.size() - 1; ri >= 0; ri--) {
                                            pp.removeRun(ri);
                                        }
                                    }

                                    XWPFRun newRun = pp.createRun();
                                    newRun.setFontSize(12);
                                    newRun.setFontFamily("宋体");
                                    newRun.setBold(false);
                                    newRun.setText(cellTextString);
                                    pp.addRun(newRun);
                                }
                            }
                        }

                    }
                }
            }
            FileOutputStream outStream = null;
            File outFile = new File(destPath);
            if (!outFile.exists()) {
                outFile.mkdirs();
            }
            outStream = new FileOutputStream(new File(destPath, fileName));
            document.write(outStream);
            outStream.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }

    public static void replaceInPara(XWPFParagraph para, String fontFamily, int fontSize) {
        if (para != null) {
            List<XWPFRun> runs = para.getRuns();
            if (runs != null && runs.size() > 0) {
                for (int i = 0; i < runs.size(); i++) {
                    XWPFRun run = runs.get(i);
                    para.removeRun(i);
                    XWPFRun cr = para.insertNewRun(i);
                    cr.setFontSize(fontSize);
                    cr.setFontFamily(fontFamily);
                    cr.setText(run.toString());

                }
            }
        }
    }

    public static void main(String[] args) {
        // TODO Auto-generated method stub
        String filepathString = "e:/sign.docx";
        String destpathString = "e:/";
        Map<String, String> map = new HashMap<String, String>();
        map.put("${prjName}", "王五王五啊王力宏的辣味回答侯卫东拉网");
        map.put("${title}", "王五王五啊王力味回答侯卫东拉网");
        map.put("${applyInfo}", "王五王五啊王力宏侯卫东拉网");
        map.put("${opinion1}", "王五王五啊王力宏的辣味回答东拉网");
        map.put("${opinion2}", "王五王五啊王的辣味回答侯卫东拉网");
        map.put("${opinion3}", "王五王五啊王力侯卫东拉网");
        map.put("${opinion4}", "王五王五辣味回答侯卫东拉网");
        map.put("${opinion5}", "王五力宏的辣味回答侯卫东拉网");
        map.put("${opinion6}", "王五力宏的辣味回答侯卫东拉网");
        System.out.println(replaceAndGenerateWord(filepathString, destpathString, "xxx.docx", map));
    }
}
