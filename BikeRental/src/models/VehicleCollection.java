package models;

import impres.exception.InvalidPrimaryKeyException;

import java.util.Properties;
import java.util.Vector;

/**
 * Created by Max on 4/26/2015.
 */
public class VehicleCollection extends ModelBase{

    private static final String myTableName = "Rental";

    private Vector vehicles;
    //GUI Components

    //Constructor
    //--------------------------------
    public VehicleCollection()
    {
        super(myTableName);
        try {
            vehicles = findAvailableVehicles();
        }catch (InvalidPrimaryKeyException e){}
    }
    //Methods
    //---------------------------------
    public Vector findAvailableVehicles() throws InvalidPrimaryKeyException
    {
        String query = "SELECT * FROM " + myTableName + " WHERE " +
                "vehicleId not in (select * From Rental Where dateReturned = '')" +
                " and status = 'Active'";

        Vector allDataRetrieved = getSelectQueryResult(query);

        if(allDataRetrieved != null)
        {
            for(int cnt = 0; cnt < allDataRetrieved.size();cnt++)
            {
                Properties nextVehicleData = (Properties)allDataRetrieved.elementAt(cnt);

                Vehicle vehicle = new Vehicle(nextVehicleData);

                if(vehicle != null)
                {
                    addVehicle(vehicle);
                }
            }
        }
        else
        {
            //System.out.println("No patrons older than " + date + " found in database");
            throw new InvalidPrimaryKeyException(messages.getString("noAvailableBikesFound"));


        }
        return vehicles;
    }
    //----------------------------------------------------------------------------------
    private void addVehicle(Vehicle a)
    {
        //users.add(u);
        vehicles.add(a);
    }
    public String getViewName(){
        return "VehicleCollectionView";
    }

    @Override
    public Object getState(String key) {
        if (key.equals("Vehicles"))
            return vehicles;
        else
        if (key.equals("VehicleList"))
            return this;
        else if (key.equals("VehicleIDs")){ return  getAvailableIDs();}
        else if (key.equals("UpdateStatusMessage"))
        {
            return updateStatusMessage;
        }
        return persistentState.getProperty(key);
        //return null;
    }
    @Override
    public void stateChangeRequest(String key, Object value)
    {
        /*
        switch(key)
        {
            case "ProcessReturn":
                processReturn((String) value);
                break;
            case "ShowDataEntryView":
                createAndShowDataEntryView();
                break;
        }
        myRegistry.updateSubscribers(key, this);
        */
    }

    public String[] getAvailableIDs(){
        String[] ids = new String[vehicles.size()];
        for(int i = 0; i < vehicles.size(); i++){
            Vehicle v = (Vehicle) vehicles.elementAt(i);
            ids[i] = v.getVehicleID();
        }
        return ids;
    }

    @Override
    public String getIdFieldName() {
        return "vehicleID";
    }

    @Override
    public boolean checkIfExists(String idToQuery) {
        return false;
    }
}
