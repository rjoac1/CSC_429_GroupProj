package models;

import impres.exception.InvalidPrimaryKeyException;

import java.util.List;
import java.util.Properties;
import java.util.Vector;
import java.util.stream.Collectors;

/**
 * Created by Max on 4/26/2015.
 */
public class VehicleCollection extends ModelBase{

    private static final String myTableName = "Rental";

    private List<Vehicle> vehicles;

    //Constructor
    //--------------------------------
    public VehicleCollection() {
        super(myTableName);
        try {
            findAvailableVehicles();
        }
        catch (InvalidPrimaryKeyException e){
            e.printStackTrace();
        }
    }

    //Methods
    //---------------------------------
    public void findAvailableVehicles() throws InvalidPrimaryKeyException {
        String query = "SELECT * FROM " + myTableName + " WHERE " +
                "vehicleId not in (select * From Rental Where dateReturned = '')" +
                " and status = 'Active'";

        Vector<Properties> allDataRetrieved = getSelectQueryResult(query);
        if (allDataRetrieved == null)
            throw new InvalidPrimaryKeyException(messages.getString("noAvailableBikesFound"));

        vehicles = allDataRetrieved.stream().map(Vehicle::new).collect(Collectors.toList());
    }

    public String getViewName(){
        return "VehicleCollectionView";
    }

    @Override
    public Object getState(String key) {
        switch (key) {
            case "Vehicles":
                return vehicles;
            case "VehicleList":
                return this;
            case "VehicleIDs":
                return getAvailableIDs();
            case "UpdateStatusMessage":
                return updateStatusMessage;
        }
        return persistentState.getProperty(key);
    }

    @Override
    public void stateChangeRequest(final String key, final Object value) {
    }

    public List<String> getAvailableIDs(){
        return vehicles.stream().map(Vehicle::getVehicleID)
                .collect(Collectors.toList());
    }

    @Override
    public String getIdFieldName() {
        return "vehicleID";
    }

    @Override
    public Boolean checkIfExists(String idToQuery) {
        return false;
    }
}
