package models;

import java.util.ResourceBundle;
import java.util.Vector;

/**
 * Created by Ryan on 4/23/2015.
 */
public class RentalTableModel extends TableModelBase {

    private ResourceBundle messages = LocaleStore.getLocale().getResourceBundle();

    public RentalTableModel(Vector rentalData)
    {
        super(rentalData);
    }

    @Override
    public int getColumnCount() {
        return 8;
    }

    @Override
    public String getColumnName(int columnIndex)
    {
        if(columnIndex == 0)
            return messages.getString("rentalID");
        else
        if(columnIndex == 1)
            return messages.getString("vehicleID");
        else
        if(columnIndex == 2)
            return messages.getString("renterID");
        else
        if(columnIndex == 3)
            return messages.getString("dateRented");
        else
        if(columnIndex == 4)
            return messages.getString("dateDue");
        else
        if(columnIndex == 5)
            return messages.getString("dateReturned");
        else
        if(columnIndex == 6)
            return messages.getString("checkoutWorkerID");
        else
        if(columnIndex == 7)
            return messages.getString("checkinWorkerID");
        else
            return "??";
    }
}
