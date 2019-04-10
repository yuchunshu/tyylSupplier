/*
 * FileName:    ExcelTemplate.java
 * Description:
 * Company:     南宁超创信息工程有限公司
 * Copyright:   ChaoChuang (c) 2016
 * History:     2016年2月3日 (LJX) 1.0 Create
 */

package cn.com.chaochuang.common.excelview;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;

import jxl.Workbook;
import jxl.read.biff.BiffException;
import jxl.write.WritableWorkbook;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.velocity.Template;
import org.apache.velocity.context.Context;
import org.apache.velocity.context.InternalContextAdapterImpl;
import org.apache.velocity.exception.MethodInvocationException;
import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.apache.velocity.exception.VelocityException;
import org.apache.velocity.runtime.parser.ParseException;
import org.apache.velocity.runtime.parser.node.SimpleNode;

/**
 * @author LJX
 *
 */
public class ExcelTemplate extends Template {
    /** Logger that is available to subclasses */
    protected final Log         logger          = LogFactory.getLog(getClass());

    private static final String EXCEL_EXTENSION = ".xls";

    private VelocityException   errorCondition  = null;

    private ExcelParser         excelParser;

    private boolean isExcelTemplate() {
        return name.endsWith(EXCEL_EXTENSION);
    }

    /**
     * （non Javadoc）
     *
     * @see org.apache.velocity.Template#process()
     */
    public boolean process() throws ResourceNotFoundException, ParseErrorException {
        if (!isExcelTemplate()) {
            return super.process();
        }

        if (logger.isInfoEnabled()) {
            logger.info("ExcelTemplate process entry at " + System.currentTimeMillis());
        }

        data = null;
        excelParser = null;
        InputStream is = null;
        errorCondition = null;
        try {
            /*
             * first, try to get the stream from the loader
             */
            try {
                is = resourceLoader.getResourceStream(name);
            } catch (ResourceNotFoundException rnfe) {
                /*
                 * remember and re-throw
                 */

                errorCondition = rnfe;
                throw rnfe;
            }

            /*
             * if that worked, lets protect in case a loader impl forgets to throw a proper exception
             */

            if (is != null) {
                /*
                 * now parse the template
                 */

                try {
                    Workbook workbook = Workbook.getWorkbook(is);
                    super.setData(workbook);
                    excelParser = new ExcelParser();
                    excelParser.preProcessWorkbook((Workbook) data);
                    return true;
                } catch (IOException pex) {
                    /*
                     * remember the error and convert
                     */

                    errorCondition = new ParseErrorException(pex.getMessage());
                    throw errorCondition;
                } catch (BiffException bex) {
                    /*
                     * remember the error and convert
                     */

                    errorCondition = new ParseErrorException(bex.getMessage());
                    throw errorCondition;
                } catch (RuntimeException e) {

                    throw e;
                } finally {
                    /*
                     * Make sure to close the inputstream when we are done.
                     */
                    try {
                        is.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            } else {
                /*
                 * is == null, therefore we have some kind of file issue
                 */

                errorCondition = new ResourceNotFoundException("Unknown resource error for resource " + name);
                throw errorCondition;
            }
        } finally {
            if (logger.isInfoEnabled()) {
                logger.info("ExcelTemplate process exit at " + System.currentTimeMillis());
            }
        }
    }

    public void merge(Context context, OutputStream outputStream) throws Exception {
        /*
         * we shouldn't have to do this, as if there is an error condition, the application code should never get a
         * reference to the Template
         */

        if (errorCondition != null) {
            throw errorCondition;
        }
        final Context theContext = context;
        if (data != null) {
            // 重新读取文件，建立Workbook
            if (logger.isInfoEnabled()) {
                logger.info("ExcelTemplate merge start open Workbook at " + System.currentTimeMillis());
            }
            InputStream is = resourceLoader.getResourceStream(name);
            Workbook temp = Workbook.getWorkbook(is);
            super.setData(temp);

            if (logger.isInfoEnabled()) {
                logger.info("ExcelTemplate merge end open Workbook at " + System.currentTimeMillis());
            }
            WritableWorkbook workbook = Workbook.createWorkbook(outputStream, (Workbook) data);
            if (logger.isInfoEnabled()) {
                logger.info("ExcelTemplate merge open WritableWorkbook at " + System.currentTimeMillis());
            }
            try {
                excelParser.buildExcelFromTemplate(workbook, (Workbook) data, new IMergeData() {

                    /**
                     * （non Javadoc）
                     *
                     * @see com.spower.excel.engine.IMergeData#evaluate(java.lang.String)
                     */
                    public String evaluate(String data) throws Exception {
                        if (null != data) {
                            StringWriter writer = new StringWriter();
                            StringReader reader = new StringReader(data);
                            if (doEvaluate(theContext, writer, data, reader)) {
                                return writer.toString();
                            } else {
                                return data;
                            }
                        }
                        return null;
                    }

                    /**
                     * （non Javadoc）
                     *
                     * @see com.spower.excel.engine.IMergeData#getContext()
                     */
                    public Context getContext() {
                        return theContext;
                    }
                });
                if (logger.isInfoEnabled()) {
                    logger.info("ExcelTemplate merge build end excel at " + System.currentTimeMillis());
                }
                workbook.write();
                outputStream.flush();
            } finally {
                workbook.close();
            }
        } else {
            /*
             * this shouldn't happen either, but just in case.
             */

            String msg = "ExcelTemplate.merge() failure. The document is null, " + "most likely due to parsing error.";

            rsvc.getLog().error(msg);
            throw new Exception(msg);
        }

    }

    /**
     * Renders the input reader using the context into the output writer. To be used when a template is dynamically
     * constructed, or want to use Velocity as a token replacer.
     *
     * @param context
     *            context to use in rendering input string
     * @param out
     *            Writer in which to render the output
     * @param logTag
     *            string to be used as the template name for log messages in case of error
     * @param reader
     *            Reader containing the VTL to be rendered
     *
     * @return true if successful, false otherwise. If false, see Velocity runtime log
     *
     * @since Velocity v1.1
     */
    public boolean doEvaluate(Context context, Writer writer, String logTag, Reader reader) throws ParseErrorException,
                    MethodInvocationException, ResourceNotFoundException, IOException {
        SimpleNode nodeTree = null;

        try {
            nodeTree = rsvc.parse(reader, logTag);
        } catch (ParseException pex) {
            throw new ParseErrorException(pex.getMessage());
        }

        /*
         * now we want to init and render
         */

        if (nodeTree != null) {
            InternalContextAdapterImpl ica = new InternalContextAdapterImpl(context);

            ica.pushCurrentTemplateName(logTag);

            try {
                try {
                    nodeTree.init(ica, rsvc);
                } catch (Exception e) {
                    rsvc.getLog().error("Velocity.evaluate() : init exception for tag = " + logTag + " : " + e);
                }

                /*
                 * now render, and let any exceptions fly
                 */

                nodeTree.render(ica, writer);
            } finally {
                ica.popCurrentTemplateName();
            }
            return true;
        }
        return false;
    }
}
