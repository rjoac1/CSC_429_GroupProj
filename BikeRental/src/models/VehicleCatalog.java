package models;

//system imports
import java.util.*;


public class VehicleCatalog extends CatalogBase<String> {

    private static final String myTableName = "Vehicle";	//name of database table
    private final List<String> availableBikeIDs;

    public VehicleCatalog() {
        super(myTableName, String.class);
        final String query = "SELECT VehicleId FROM " + myTableName + " WHERE " +
                "vehicleId not in (select vehicleID From Rental Where dateReturned = '')" +
                " and status = 'Active'";
        availableBikeIDs = getDatasFromQuery(query, (e) -> e.getProperty("vehicleID"));
        Collections.sort(availableBikeIDs);
        /*
        select VehicleId from Vehicle where vehicleId not in(
            select vehicleId From Rental Where dateReturned = '')
        and status = 'Active'
        */
    }

    @Override
    public Object getState(final String key) {
        switch (key) {
            case "VehicleIDs":
                return availableBikeIDs;
            case "VehicleList":
                return this;
            case "UpdateStatusMessage":
                return updateStatusMessage;
        }
        return persistentState.getProperty(key);
    }

    public String getIdFieldName() {
        return "vehicleId";
    }

    public String getViewName() {
        return "VehicleView";
    }
}

