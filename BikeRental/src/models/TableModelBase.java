package models;

import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;
import java.util.Vector;

/**
 * Created by Ryan on 4/23/2015.
 */
public abstract class TableModelBase extends AbstractTableModel implements TableModel
{
    private Vector myState;

    public TableModelBase(Vector rentalData)
    {
        myState = rentalData;
    }

    //--------------------------------------------------------------------------
    public abstract int getColumnCount();

    //--------------------------------------------------------------------------
    public int getRowCount()
    {
        return myState.size();
    }

    //--------------------------------------------------------------------------
    public Object getValueAt(int rowIndex, int columnIndex)
    {
        Vector entity = (Vector)myState.elementAt(rowIndex);
        return "    " + entity.elementAt(columnIndex);
    }

    //--------------------------------------------------------------------------
    public abstract String getColumnName(int columnIndex);
}