package models;

import java.util.ResourceBundle;
import java.util.Vector;

/**
 * Created by Ryan on 4/23/2015.
 */
public class RentalTableModel extends TableModelBase {

    private ResourceBundle messages = LocaleStore.getLocale().getResourceBundle();
    private String[] mColumnNames;

    public RentalTableModel(Vector rentalData) {
        super(rentalData);
        mColumnNames = new String[] {
                messages.getString("rentalID"),
                messages.getString("vehicleID"),
                messages.getString("renterID"),
                messages.getString("dateRented"),
                messages.getString("timeRented"),
                messages.getString("dateDue"),
                messages.getString("timeDue"),
                //messages.getString("checkinWorkerID"),
                messages.getString("checkoutWorkerID")
        };
    }

    public ResourceBundle getMessages() {
        return messages;
    }

    @Override
    public int getColumnCount() {
        return mColumnNames.length;
    }

    @Override
    public String getColumnName(int columnIndex) {
        return mColumnNames[columnIndex];
    }
}
