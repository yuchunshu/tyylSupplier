/*
 * FileName:    AbstractCommandNode.java
 * Description:
 * Company:     
 * Copyright:    (c) 2016
 * History:     2016年2月3日 (LJX) 1.0 Create
 */

package cn.com.chaochuang.common.excelview;

/**
 * @author LJX
 *
 */
public abstract class AbstractCommandNode implements ICommandNode {

    protected int    col;
    protected int    row;
    protected String command;

    public AbstractCommandNode(int col, int row, final String command) {
        this.col = col;
        this.row = row;
        this.command = command;
    }

    /**
     * （non Javadoc）
     * 
     * @see com.spower.excel.engine.parse.ICommandNode#getColumn()
     */
    public int getColumn() {
        return this.col;
    }

    /**
     * （non Javadoc）
     * 
     * @see com.spower.excel.engine.parse.ICommandNode#getRow()
     */
    public int getRow() {
        return this.row;
    }

}
