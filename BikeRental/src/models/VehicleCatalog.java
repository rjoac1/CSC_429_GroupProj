package models;

//system imports
import java.util.*;
import java.util.stream.Collectors;


public class VehicleCatalog extends ModelBase {

    private static final String myTableName = "Vehicle";	//name of database table
    private final List<String> availableBikeIDs;

    public VehicleCatalog() {
        super(myTableName);
        final String query = "SELECT VehicleId FROM " + myTableName + " WHERE " +
                "vehicleId not in (select vehicleID From Rental Where dateReturned = '')" +
                " and status = 'Active'";
        /*
        select VehicleId from Vehicle where vehicleId not in(
            select vehicleId From Rental Where dateReturned = '')
        and status = 'Active'
        */

        final Vector<Properties> allDataRetrieved = getSelectQueryResult(query);
        availableBikeIDs = allDataRetrieved.stream().map(
                (e) -> e.getProperty("vehicleId")
        ).collect(Collectors.toList());
        sortVehiclesByID();
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

    private void sortVehiclesByID() {
        Collections.sort(availableBikeIDs);
    }
}

