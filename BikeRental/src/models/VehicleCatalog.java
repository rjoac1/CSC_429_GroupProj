package models;

//system imports

import impres.exception.InvalidPrimaryKeyException;

import java.util.Enumeration;
import java.util.Properties;
import java.util.Vector;

//project imports


public class VehicleCatalog extends ModelBase
{

    private static final String myTableName = "Vehicle";	//name of database table
    private String[] availableBikeIDs;
    //Constructor
    //-----------------------------------------------------
    public VehicleCatalog()
    {
        super(myTableName);
        String query = "SELECT VehicleId FROM " + myTableName + " WHERE " +
                "vehicleId not in (select vehicleID From Rental Where dateReturned = '')" +
                " and status = 'Active'";
        /*
        select VehicleId from Vehicle where vehicleId not in(
            select vehicleId From Rental Where dateReturned = '')
        and status = 'Active'
        */

        Vector allDataRetrieved = getSelectQueryResult(query);

        if (allDataRetrieved != null)
        {
            availableBikeIDs = new String[allDataRetrieved.size()];
            for(int cnt = 0; cnt < allDataRetrieved.size();cnt++)
            {
                Properties nextVehicleData = (Properties)allDataRetrieved.elementAt(cnt);
                //System.out.println(nextVehicleData.toString());
                if(nextVehicleData != null)
                {
                    availableBikeIDs[cnt] = nextVehicleData.getProperty("vehicleId").toString();
                }
            }
        }


        setDependencies();
    }

    @Override
    public Object getState(String key) {
        if (key.equals("VehicleIDs"))
            return availableBikeIDs;
        else
        if (key.equals("VehicleList"))
            return this;
        else if (key.equals("UpdateStatusMessage"))
        {
            return updateStatusMessage;
        }
        return persistentState.getProperty(key);
        //return null;
    }

    public String getIdFieldName()
    {
        return "vehicleId";
    }

    public String getViewName(){ return "VehicleView"; }

    public boolean checkIfExists(String id)
    {
        try {
            Vehicle vehcile = new Vehicle(id);
            return true;
        }
        catch (InvalidPrimaryKeyException ex) {
            //System.out.println(ex.getMessage()); //test
            return false;
        }
    }
}

