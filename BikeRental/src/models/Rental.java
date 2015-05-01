// specify the package
package models;

import impres.exception.InvalidPrimaryKeyException;
import views.DateLabelFormatter;

import java.util.Properties;

public class Rental extends ModelBase {
    private static final String myTableName = "Rental";
    private VehicleCatalog vehicleCatalog;
    private Worker currentWorker;

    public Rental(final Worker mWorker) {
        super(myTableName);

        setDependencies();
        persistentState = new Properties();
        vehicleCatalog = new VehicleCatalog();
        currentWorker = mWorker;
    }

    public Rental(final String userID) throws InvalidPrimaryKeyException {
        super(myTableName);
        initFromQuery(userID);
    }

    public Rental(Properties props) {
        super(myTableName, props);
    }

    public String getIdFieldName() {
        return "rentalID";
    }

    @Override
    public Object getState(final String key) {
        switch (key) {
            case "VehicleIDs":
                return vehicleCatalog.getState("VehicleIDs");
            case "workerId":
                return currentWorker.getState("workerId");
            case "UpdateStatusMessage":
                return updateStatusMessage;
        }
        return persistentState.getProperty(key);
    }

    public String getViewName() {
        return "RentalView";
    }

    public void setReturned(final String workerId) {
        final DateLabelFormatter dates = new DateLabelFormatter();
        final String dateString = dates.getCurrentDate();
        final String timeString = dates.getCurrentTime();
        persistentState.setProperty("dateReturned",dateString);
        persistentState.setProperty("timeReturned",timeString);
        persistentState.setProperty("checkinWorkerID", workerId);
        update();
    }

    public String[] getBikes(){
        return (String[])vehicleCatalog.getState("VehicleIDs");
    }
}