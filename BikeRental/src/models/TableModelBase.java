package models;

import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import static java.text.DateFormat.*;

import java.util.Date;
import java.util.Vector;

/**
 * Created by Ryan on 4/23/2015.
 */
public abstract class TableModelBase extends AbstractTableModel implements TableModel
{
    static protected String datePattern = "yyyy-MM-dd";
    static protected SimpleDateFormat dateFormatter = new SimpleDateFormat(datePattern);

    private Vector myState;

    public TableModelBase(Vector rentalData) {
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
    public Object getValueAt(int rowIndex, int columnIndex) {
        Vector entity = (Vector)myState.elementAt(rowIndex);
//        if (entity.elementAt(columnIndex) is)
        if (columnIndex == 3 || columnIndex == 5) {
            try {
                DateFormat f = DateFormat.getDateInstance(SHORT, LocaleStore.getLocale().getLocaleObject());
                String s = (String)entity.elementAt(columnIndex);
                Date d = dateFormatter.parse(s);
                String value = f.format(d);
                return value;
            } catch (ParseException e) {
                e.printStackTrace();
                return "";
            }
        }
        return "    " + entity.elementAt(columnIndex);
    }

    //--------------------------------------------------------------------------
    public abstract String getColumnName(int columnIndex);
}