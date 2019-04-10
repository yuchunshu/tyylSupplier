package cn.com.chaochuang.doc.remotedocswap;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.RandomAccessFileOrArray;
import com.itextpdf.text.pdf.codec.TiffImage;

/**
 * @author Polukov Evgen
 * @version 1.0
 * @since jdk1.6.0_20
 * Date: 26.10.13
 * Time: 20:35
 */
public class ConvectorUtils {

    private static final int FIT_WIDTH = 500;
    private static final int FIT_HEIGHT = 900;

    public static OutputStream convert(InputStream tifFile, OutputStream pdfFile) throws IOException, DocumentException {
        Document pdfDocument = null;
        RandomAccessFileOrArray tifWrapperDocument = null;
        PdfWriter pdfWriter = null;

        try {
            pdfDocument = new Document();

            tifWrapperDocument = new RandomAccessFileOrArray(tifFile);
            try {
                pdfWriter = PdfWriter.getInstance(pdfDocument, pdfFile);
            } catch (DocumentException e) {
                throw e;
            }

            pdfDocument.open();

            for (int index = 1; index <= TiffImage.getNumberOfPages(tifWrapperDocument); index++) {
                Image tempImage = TiffImage.getTiffImage(tifWrapperDocument, index);
                tempImage.scaleToFit(FIT_WIDTH, FIT_HEIGHT);
                try {
                    pdfDocument.add(tempImage);
                } catch (DocumentException e) {
                    throw e;
                }
            }
            return pdfFile;
        } finally {
            closeResources(pdfDocument, tifWrapperDocument,pdfWriter);
        }
    }

    private static void closeResources(Document pdfDocument, RandomAccessFileOrArray tifWrapperDocument, PdfWriter pdfWriter) throws IOException {
        try {
            if (pdfDocument != null) {
                pdfDocument.close();
            }
        } finally {
            try {
                if (tifWrapperDocument != null) {
                    tifWrapperDocument.close();
                }
            } catch (IOException e) {
                throw e;
            }finally {
                if (pdfWriter != null) {
                    pdfWriter.close();
                }
            }
        }
    }

    /**
     * tif 转 pdf 文件
     * @param tifFilePath
     * @param pdfFilePath
     * @throws IOException
     * @throws DocumentException
     */
    public static void convertTifToPdf(String tifFilePath,String pdfFilePath) throws IOException, DocumentException {
        InputStream tifFile = null;
        OutputStream pdfDocument = null;

        try{
            tifFile = new FileInputStream(tifFilePath);

            pdfDocument = new FileOutputStream(pdfFilePath);

            pdfDocument = ConvectorUtils.convert(tifFile, pdfDocument);
        }finally {
            if (tifFile != null) {
                try{
                    tifFile.close();
                }catch (IOException e){
                    throw e;
                }finally{
                    try{
                        if (pdfDocument != null){
                            pdfDocument.close();
                        }
                    }catch (IOException e){
                        throw e;
                    }
                }
            }
        }
    }
}
