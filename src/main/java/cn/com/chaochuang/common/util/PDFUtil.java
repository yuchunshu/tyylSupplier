/*
 * FileName:    PDFUtil.java
 * Description:
 * Company:     
 * Copyright:    (c) 2016
 * History:     2016年1月10日 (LJX) 1.0 Create
 */

package cn.com.chaochuang.common.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorkerHelper;

/**
 * @author LJX
 *
 */
public class PDFUtil {

    public static void createPdf(String html, String pdf) throws IOException, DocumentException {

        File file = new File(pdf);
        file.getParentFile().mkdirs();

        Document document = new Document();
        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(pdf));
        document.open();
        XMLWorkerHelper.getInstance().parseXHtml(writer, document, new FileInputStream(html), Charset.forName("UTF-8"));
        document.close();
    }

    public static void main(String[] args) throws IOException, DocumentException {
    }
}
