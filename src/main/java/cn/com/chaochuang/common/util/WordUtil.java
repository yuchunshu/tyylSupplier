package cn.com.chaochuang.common.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PushbackInputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.output.ByteArrayOutputStream;
import org.apache.poi.POIXMLDocument;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.converter.PicturesManager;
import org.apache.poi.hwpf.converter.WordToHtmlConverter;
import org.apache.poi.hwpf.usermodel.PictureType;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.xwpf.converter.core.Base64URIResolver;
import org.apache.poi.xwpf.converter.core.FileImageExtractor;
import org.apache.poi.xwpf.converter.xhtml.XHTMLConverter;
import org.apache.poi.xwpf.converter.xhtml.XHTMLOptions;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.w3c.dom.Document;

public class WordUtil {

    /**
     * 用一个docx文档作为模板，然后替换其中的内容，再写入目标文档中。
     *
     * @throws Exception
     */
    public static void main(String[] args) {
        Map<String, String> params = new HashMap<String, String>();
        params.put("depName", "我是信gdgdfgfdgd息das");
        params.put("title", "我fdgdgdgdg是aaa信息");
        String filePath = "e:/sign.docx";
        String outfile = "e:";
        replaceAndGenerateWord(filePath, outfile, "write.docx", params, "仿宋_GB2312", 16);
    }

    public static Boolean replaceAndGenerateWord(String srcPath, String destPath, String outFileName,
                    Map<String, String> params, String fontFamily, int fontSize) {
        InputStream is = null;
        OutputStream os = null;
        try {
            XWPFDocument doc = new XWPFDocument(new FileInputStream(srcPath));
            // 替换段落里面的变量
            replaceInPara(doc, params, fontFamily, fontSize);
            // 替换表格里面的变量
            replaceInTable(doc, params, fontFamily, fontSize);
            File outPathFile = new File(destPath);
            if (!outPathFile.exists()) {
                outPathFile.mkdirs();
            }
            os = new FileOutputStream(new File(outPathFile, outFileName));
            doc.write(os);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (is != null) {
                    is.close();
                }
                if (os != null) {
                    os.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        return true;
    }

    /**
     * 替换段落里面的变量
     *
     * @param doc
     *            要替换的文档
     * @param params
     *            参数
     */
    public static void replaceInPara(XWPFDocument doc, Map<String, String> params, String fontFamily, int fontSize) {
        Iterator<XWPFParagraph> iterator = doc.getParagraphsIterator();
        XWPFParagraph para;
        while (iterator.hasNext()) {
            para = iterator.next();
            replaceInPara(para, params, fontFamily, fontSize);
        }
    }

    /**
     * 替换段落里面的变量
     *
     * @param para
     *            要替换的段落
     * @param params
     *            参数
     */
    public static void replaceInPara(XWPFParagraph para, Map<String, String> params, String fontFamily, int fontSize) {
        List<XWPFRun> runs;
        Matcher matcher;
        if (matcher(para.getParagraphText()).find()) {
            runs = para.getRuns();
            for (int i = 0; i < runs.size(); i++) {
                XWPFRun run = runs.get(i);
                String runText = run.toString();
                matcher = matcher(runText);
                if (matcher.find()) {
                    while ((matcher = matcher(runText)).find()) {
                        runText = matcher.replaceFirst(String.valueOf(params.get(matcher.group(1))));
                    }
                    // 直接调用XWPFRun的setText()方法设置文本时，在底层会重新创建一个XWPFRun，把文本附加在当前文本后面，
                    // 所以我们不能直接设值，需要先删除当前run,然后再自己手动插入一个新的run。
                    para.removeRun(i);
                    XWPFRun cr = para.insertNewRun(i);
                    cr.setFontSize(fontSize);
                    cr.setFontFamily(fontFamily);
                    cr.setText(runText);

                }
            }
        }
    }

    /**
     * 替换表格里面的变量
     *
     * @param doc
     *            要替换的文档
     * @param params
     *            参数
     */
    public static void replaceInTable(XWPFDocument doc, Map<String, String> params, String fontFamily, int fontSize) {
        Iterator<XWPFTable> iterator = doc.getTablesIterator();
        XWPFTable table;
        List<XWPFTableRow> rows;
        List<XWPFTableCell> cells;
        List<XWPFParagraph> paras;
        while (iterator.hasNext()) {
            table = iterator.next();
            rows = table.getRows();
            for (XWPFTableRow row : rows) {
                cells = row.getTableCells();
                for (XWPFTableCell cell : cells) {
                    paras = cell.getParagraphs();
                    for (XWPFParagraph para : paras) {
                        replaceInPara(para, params, fontFamily, fontSize);
                    }
                }
            }
        }
    }

    /**
     * 正则匹配字符串
     *
     * @param str
     * @return
     */
    public static Matcher matcher(String str) {
        Pattern pattern = Pattern.compile("\\$\\{(.+?)\\}", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(str);
        return matcher;
    }

    /**
     * 关闭输入流
     *
     * @param is
     */
    public static void close(InputStream is) {
        if (is != null) {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 关闭输出流
     *
     * @param os
     */
    public static void close(OutputStream os) {
        if (os != null) {
            try {
                os.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * word转html
     *
     * @param fileName
     * @param tempPath
     * @param os
     * @return
     * @throws TransformerException
     * @throws IOException
     * @throws ParserConfigurationException
     */
    public static void convert2Html(InputStream is, String tempPath, OutputStream os)
                    throws TransformerException, IOException, ParserConfigurationException {
        if (!is.markSupported()) {
            is = new PushbackInputStream(is, 8);
        }

        try {
            if (POIFSFileSystem.hasPOIFSHeader(is)) {
                // 尝试以doc解析
                HWPFDocument wordDocument = new HWPFDocument(is);
                WordToHtmlConverter wordToHtmlConverter = new WordToHtmlConverter(
                                DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument());
                wordToHtmlConverter.setPicturesManager(new PicturesManager() {
                    public String savePicture(byte[] content, PictureType pictureType, String suggestedName,
                                    float widthInches, float heightInches) {
                        return "data:image/png;base64," + Base64.encodeBase64String(content);
                    }
                });
                wordToHtmlConverter.processDocument(wordDocument);
                Document htmlDocument = wordToHtmlConverter.getDocument();
                ByteArrayOutputStream out = new ByteArrayOutputStream();
                DOMSource domSource = new DOMSource(htmlDocument);
                StreamResult streamResult = new StreamResult(out);

                TransformerFactory tf = TransformerFactory.newInstance();
                Transformer serializer = tf.newTransformer();
                serializer.setOutputProperty(OutputKeys.ENCODING, "gbk");
                serializer.setOutputProperty(OutputKeys.INDENT, "yes");
                serializer.setOutputProperty(OutputKeys.METHOD, "html");
                serializer.transform(domSource, streamResult);
                out.close();
                os.write(new String("<!doctype html>").getBytes("gbk"));
                os.write(new String(out.toByteArray()).getBytes("gbk"));
            } else if (POIXMLDocument.hasOOXMLHeader(is)) {
                // 尝试以docx解析
                XWPFDocument document = new XWPFDocument(is);
                XHTMLOptions options = XHTMLOptions.create().indent(4);
                // 导出图片
                File imageFolder = new File(FilenameUtils.normalize(tempPath, true));
                options.setExtractor(new FileImageExtractor(imageFolder));
                // URI resolver
                options.URIResolver(new Base64URIResolver());
                os.write(new String("<!doctype html>").getBytes());
                XHTMLConverter.getInstance().convert(document, os, options);
            }
        } catch (Exception e) {
            os.write(new String("<!doctype html><html><head><meta charset='utf-8' />"
                            + "<meta http-equiv='X-UA-Compatible' content='IE=edge' />"
                            + "<meta name='viewport' content='width=device-width, initial-scale=1' /></head>"
                            + "<body>无法预览此文档</body></html>").getBytes("utf-8"));
        }
    }

}