/*
 * FileName:    ParsedWritableSheet.java
 * Description:
 * Company:     南宁超创信息工程有限公司
 * Copyright:   ChaoChuang (c) 2016
 * History:     2016年2月3日 (LJX) 1.0 Create
 */

package cn.com.chaochuang.common.excelview;

import jxl.Cell;
import jxl.CellView;
import jxl.Hyperlink;
import jxl.Image;
import jxl.LabelCell;
import jxl.Range;
import jxl.Sheet;
import jxl.SheetSettings;
import jxl.format.CellFormat;
import jxl.format.PageOrientation;
import jxl.format.PaperSize;
import jxl.write.WritableCell;
import jxl.write.WritableHyperlink;
import jxl.write.WritableImage;
import jxl.write.WritableSheet;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;
import jxl.write.biff.SheetUtils;

/**
 * @author LJX
 *
 */
public class ParsedWritableSheet implements WritableSheet {

    private WritableSheet theTargetSheet;

    private int           currentRowIndex       = 0;

    private int           currentOriginRowIndex = 0;

    private boolean       isNewLine             = true;

    public ParsedWritableSheet(WritableSheet sheet) {
        super();
        this.setTheTargetSheet(sheet);
    }

    /**
     * @return 返回 isNewLine。
     */
    public boolean isNewLine() {
        return isNewLine;
    }

    /**
     * @param isNewLine
     *            要设置的 isNewLine。
     */
    public void setNewLine(boolean isNewLine) {
        this.isNewLine = isNewLine;
    }

    /**
     * @param theTargetSheet
     *            要设置的 theTargetSheet。
     */
    public void setTheTargetSheet(WritableSheet theTargetSheet) {
        if (this.theTargetSheet != theTargetSheet) {
            this.setCurrentRowIndex(0);
            this.setCurrentOriginRowIndex(0);
        }
        this.theTargetSheet = theTargetSheet;
    }

    /**
     * @return 返回 theTargetSheet。
     */
    public WritableSheet getTheTargetSheet() {
        return theTargetSheet;
    }

    /**
     * @return 返回 currentRowIndex。
     */
    public int getCurrentRowIndex() {
        return currentRowIndex;
    }

    /**
     * @param currentRowIndex
     *            要设置的 currentRowIndex。
     */
    public void setCurrentRowIndex(int currentRowIndex) {
        this.currentRowIndex = currentRowIndex;
    }

    /**
     * @return 返回 currentOriginRowIndex。
     */
    public int getCurrentOriginRowIndex() {
        return currentOriginRowIndex;
    }

    /**
     * @param currentOriginRowIndex
     *            要设置的 currentOriginRowIndex。
     */
    public void setCurrentOriginRowIndex(int currentOriginRowIndex) {
        this.currentOriginRowIndex = currentOriginRowIndex;
    }

    public void insertRowsFromTemplate(final Sheet template, int startRow, int endRow) throws WriteException {
        int targetRow = this.getCurrentRowIndex();
        for (int i = startRow; i < endRow; i++) {
            Cell[] cells = template.getRow(i);
            this.insertRow(targetRow);
            this.setRowView(targetRow, template.getRowView(i).getSize());
            SheetUtils.copyRow(cells, targetRow, this);
            targetRow++;
        }

        // 处理合并的cell
        Range[] merged = template.getMergedCells();
        for (int i = 0; i < merged.length; i++) {
            Range m = merged[i];
            Cell tl = m.getTopLeft();
            Cell br = m.getBottomRight();
            if (tl.getRow() >= startRow && br.getRow() < endRow) {
                this.mergeCells(tl.getColumn(), this.currentRowIndex + (tl.getRow() - startRow), br.getColumn(),
                                this.currentRowIndex + (br.getRow() - startRow));
            }
        }
    }

    private void NotUseThisFunction(final String funcName) {
        throw new RuntimeException("不要使用函数[" + funcName + "],请查看函数说明使用其他替代的方法。");
    }

    // //////////
    // //////////
    // ////////// interface for WritableSheet
    // //////////
    // //////////

    /**
     * （non Javadoc）
     *
     * @see jxl.write.WritableSheet#addCell(jxl.write.WritableCell)
     */
    public void addCell(WritableCell cell) throws WriteException, RowsExceededException {
        this.theTargetSheet.addCell(cell);
    }

    /**
     * （non Javadoc）
     *
     * @see jxl.write.WritableSheet#setName(java.lang.String)
     */
    public void setName(String name) {
        this.theTargetSheet.setName(name);
    }

    /**
     * （non Javadoc） // * @see jxl.write.WritableSheet#setHidden(boolean)
     */
    public void setHidden(boolean hidden) {
        NotUseThisFunction("void setHidden(boolean hidden)");
        // this.theTargetSheet.setHidden(hidden);
    }

    /**
     * （non Javadoc） // * @see jxl.write.WritableSheet#setProtected(boolean)
     */
    public void setProtected(boolean prot) {
        NotUseThisFunction("void setProtected(boolean prot)");
        // this.theTargetSheet.setProtected(prot);
    }

    /**
     * （non Javadoc）
     *
     * @see jxl.write.WritableSheet#setColumnView(int, int)
     */
    public void setColumnView(int col, int width) {
        this.theTargetSheet.setColumnView(col, width);
    }

    /**
     * （non Javadoc） // * @see jxl.write.WritableSheet#setColumnView(int, int, jxl.format.CellFormat)
     */
    public void setColumnView(int col, int width, CellFormat format) {
        NotUseThisFunction("void setColumnView(int col, int width, CellFormat format)");
        // this.theTargetSheet.setColumnView(col, width, format);
    }

    /**
     * （non Javadoc）
     *
     * @see jxl.write.WritableSheet#setColumnView(int, jxl.CellView)
     */
    public void setColumnView(int col, CellView view) {
        this.theTargetSheet.setColumnView(col, view);
    }

    /**
     * （non Javadoc）
     *
     * @see jxl.write.WritableSheet#setRowView(int, int)
     */
    public void setRowView(int row, int height) throws RowsExceededException {
        this.theTargetSheet.setRowView(row, height);
    }

    /**
     * （non Javadoc）
     *
     * @see jxl.write.WritableSheet#setRowView(int, boolean)
     */
    public void setRowView(int row, boolean collapsed) throws RowsExceededException {
        this.theTargetSheet.setRowView(row, collapsed);
    }

    /**
     * （non Javadoc）
     *
     * @see jxl.write.WritableSheet#setRowView(int, int, boolean)
     */
    public void setRowView(int row, int height, boolean collapsed) throws RowsExceededException {
        this.theTargetSheet.setRowView(row, height, collapsed);
    }

    /**
     * （non Javadoc）
     *
     * @see jxl.write.WritableSheet#getWritableCell(int, int)
     */
    public WritableCell getWritableCell(int column, int row) {
        return this.theTargetSheet.getWritableCell(column, row);
    }

    /**
     * （non Javadoc）
     *
     * @see jxl.write.WritableSheet#getWritableCell(java.lang.String)
     */
    public WritableCell getWritableCell(String loc) {
        return this.theTargetSheet.getWritableCell(loc);
    }

    /**
     * （non Javadoc）
     *
     * @see jxl.write.WritableSheet#getWritableHyperlinks()
     */
    public WritableHyperlink[] getWritableHyperlinks() {
        return this.theTargetSheet.getWritableHyperlinks();
    }

    /**
     * （non Javadoc）
     *
     * @see jxl.write.WritableSheet#insertRow(int)
     */
    public void insertRow(int row) {
        this.theTargetSheet.insertRow(row);
    }

    /**
     * （non Javadoc）
     *
     * @see jxl.write.WritableSheet#insertColumn(int)
     */
    public void insertColumn(int col) {
        this.theTargetSheet.insertColumn(col);
    }

    /**
     * （non Javadoc）
     *
     * @see jxl.write.WritableSheet#removeColumn(int)
     */
    public void removeColumn(int col) {
        this.theTargetSheet.removeColumn(col);
    }

    /**
     * （non Javadoc）
     *
     * @see jxl.write.WritableSheet#removeRow(int)
     */
    public void removeRow(int row) {
        this.theTargetSheet.removeRow(row);
    }

    /**
     * （non Javadoc）
     *
     * @see jxl.write.WritableSheet#mergeCells(int, int, int, int)
     */
    public Range mergeCells(int col1, int row1, int col2, int row2) throws WriteException, RowsExceededException {
        return this.theTargetSheet.mergeCells(col1, row1, col2, row2);
    }

    /**
     * （non Javadoc）
     *
     * @see jxl.write.WritableSheet#unmergeCells(jxl.Range)
     */
    public void unmergeCells(Range r) {
        this.theTargetSheet.unmergeCells(r);
    }

    /**
     * （non Javadoc）
     *
     * @see jxl.write.WritableSheet#addHyperlink(jxl.write.WritableHyperlink)
     */
    public void addHyperlink(WritableHyperlink h) throws WriteException, RowsExceededException {
        this.theTargetSheet.addHyperlink(h);
    }

    /**
     * （non Javadoc）
     *
     * @see jxl.write.WritableSheet#removeHyperlink(jxl.write.WritableHyperlink)
     */
    public void removeHyperlink(WritableHyperlink h) {
        this.removeHyperlink(h);
    }

    /**
     * （non Javadoc）
     *
     * @see jxl.write.WritableSheet#removeHyperlink(jxl.write.WritableHyperlink, boolean)
     */
    public void removeHyperlink(WritableHyperlink h, boolean preserveLabel) {
        this.removeHyperlink(h, preserveLabel);
    }

    /**
     * （non Javadoc） // * @see jxl.write.WritableSheet#setHeader(java.lang.String, java.lang.String, java.lang.String)
     */
    public void setHeader(String l, String c, String r) {
        NotUseThisFunction("void setHeader(String l, String c, String r)");
        // this.theTargetSheet.setHeader(l, c, r);
    }

    /**
     * （non Javadoc） // * @see jxl.write.WritableSheet#setFooter(java.lang.String, java.lang.String, java.lang.String)
     */
    public void setFooter(String l, String c, String r) {
        NotUseThisFunction("void setFooter(String l, String c, String r)");
        // this.theTargetSheet.setFooter(l, c, r);
    }

    /**
     * （non Javadoc）
     *
     * @see jxl.write.WritableSheet#setPageSetup(jxl.format.PageOrientation)
     */
    public void setPageSetup(PageOrientation p) {
        this.setPageSetup(p);
    }

    /**
     * （non Javadoc）
     *
     * @see jxl.write.WritableSheet#setPageSetup(jxl.format.PageOrientation, double, double)
     */
    public void setPageSetup(PageOrientation p, double hm, double fm) {
        this.setPageSetup(p, hm, fm);
    }

    /**
     * （non Javadoc）
     *
     * @see jxl.write.WritableSheet#setPageSetup(jxl.format.PageOrientation, jxl.format.PaperSize, double, double)
     */
    public void setPageSetup(PageOrientation p, PaperSize ps, double hm, double fm) {
        this.theTargetSheet.setPageSetup(p, ps, hm, fm);
    }

    /**
     * （non Javadoc）
     *
     * @see jxl.write.WritableSheet#addRowPageBreak(int)
     */
    public void addRowPageBreak(int row) {
        this.theTargetSheet.addRowPageBreak(row);
    }

    /**
     * （non Javadoc）
     *
     * @see jxl.write.WritableSheet#addImage(jxl.write.WritableImage)
     */
    public void addImage(WritableImage image) {
        this.addImage(image);
    }

    /**
     * （non Javadoc）
     *
     * @see jxl.write.WritableSheet#getNumberOfImages()
     */
    public int getNumberOfImages() {
        return this.theTargetSheet.getNumberOfImages();
    }

    /**
     * （non Javadoc）
     *
     * @see jxl.write.WritableSheet#getImage(int)
     */
    public WritableImage getImage(int i) {
        return this.theTargetSheet.getImage(i);
    }

    /**
     * （non Javadoc）
     *
     * @see jxl.write.WritableSheet#removeImage(jxl.write.WritableImage)
     */
    public void removeImage(WritableImage wi) {
        this.theTargetSheet.removeImage(wi);
    }

    /**
     * （non Javadoc）
     *
     * @see jxl.Sheet#getCell(int, int)
     */
    public Cell getCell(int column, int row) {
        return this.theTargetSheet.getCell(column, row);
    }

    /**
     * （non Javadoc）
     *
     * @see jxl.Sheet#getCell(java.lang.String)
     */
    public Cell getCell(String loc) {
        return this.theTargetSheet.getCell(loc);
    }

    /**
     * （non Javadoc）
     *
     * @see jxl.Sheet#getRows()
     */
    public int getRows() {
        return this.theTargetSheet.getRows();
    }

    /**
     * （non Javadoc）
     *
     * @see jxl.Sheet#getColumns()
     */
    public int getColumns() {
        return this.theTargetSheet.getColumns();
    }

    /**
     * （non Javadoc）
     *
     * @see jxl.Sheet#getRow(int)
     */
    public Cell[] getRow(int row) {
        return this.theTargetSheet.getRow(row);
    }

    /**
     * （non Javadoc）
     *
     * @see jxl.Sheet#getColumn(int)
     */
    public Cell[] getColumn(int col) {
        return this.theTargetSheet.getColumn(col);
    }

    /**
     * （non Javadoc）
     *
     * @see jxl.Sheet#getName()
     */
    public String getName() {
        return this.theTargetSheet.getName();
    }

    /**
     * （non Javadoc） // * @see jxl.Sheet#isHidden()
     */
    public boolean isHidden() {
        NotUseThisFunction("boolean isHidden()");
        return false;
        // return this.theTargetSheet.isHidden();
    }

    /**
     * （non Javadoc） // * @see jxl.Sheet#isProtected()
     */
    public boolean isProtected() {
        NotUseThisFunction("boolean isProtected()");
        return false;
        // return this.theTargetSheet.isProtected();
    }

    /**
     * （non Javadoc）
     *
     * @see jxl.Sheet#findCell(java.lang.String)
     */
    public Cell findCell(String contents) {
        return this.theTargetSheet.findCell(contents);
    }

    /**
     * （non Javadoc）
     *
     * @see jxl.Sheet#findLabelCell(java.lang.String)
     */
    public LabelCell findLabelCell(String contents) {
        return this.theTargetSheet.findLabelCell(contents);
    }

    /**
     * （non Javadoc）
     *
     * @see jxl.Sheet#getHyperlinks()
     */
    public Hyperlink[] getHyperlinks() {
        return this.theTargetSheet.getHyperlinks();
    }

    /**
     * （non Javadoc）
     *
     * @see jxl.Sheet#getMergedCells()
     */
    public Range[] getMergedCells() {
        return this.theTargetSheet.getMergedCells();
    }

    /**
     * （non Javadoc）
     *
     * @see jxl.Sheet#getSettings()
     */
    public SheetSettings getSettings() {
        return this.theTargetSheet.getSettings();
    }

    /**
     * （non Javadoc） // * @see jxl.Sheet#getColumnFormat(int)
     */
    public CellFormat getColumnFormat(int col) {
        NotUseThisFunction("CellFormat getColumnFormat(int col)");
        return null;
        // return this.theTargetSheet.getColumnFormat(col);
    }

    /**
     * （non Javadoc） // * @see jxl.Sheet#getColumnWidth(int)
     */
    public int getColumnWidth(int col) {
        NotUseThisFunction("int getColumnWidth(int col)");
        return 0;
        // return this.theTargetSheet.getColumnWidth(col);
    }

    /**
     * （non Javadoc）
     *
     * @see jxl.Sheet#getColumnView(int)
     */
    public CellView getColumnView(int col) {
        return this.theTargetSheet.getColumnView(col);
    }

    /**
     * （non Javadoc） // * @see jxl.Sheet#getRowHeight(int)
     */
    public int getRowHeight(int row) {
        NotUseThisFunction("int getRowHeight(int row)");
        return 0;
        // return this.theTargetSheet.getRowHeight(row);
    }

    /**
     * （non Javadoc）
     *
     * @see jxl.Sheet#getRowView(int)
     */
    public CellView getRowView(int row) {
        return this.theTargetSheet.getRowView(row);
    }

    /**
     * （non Javadoc）
     *
     * @see jxl.Sheet#getDrawing(int)
     */
    public Image getDrawing(int i) {
        return this.theTargetSheet.getDrawing(i);
    }

    /**
     * （non Javadoc）
     *
     * @see jxl.Sheet#getRowPageBreaks()
     */
    public int[] getRowPageBreaks() {
        return this.theTargetSheet.getRowPageBreaks();
    }

    /**
     * (non-Javadoc)
     * 
     * @see jxl.Sheet#getColumnPageBreaks()
     */
    public int[] getColumnPageBreaks() {
        // TODO Auto-generated method stub
        return null;
    }

}
