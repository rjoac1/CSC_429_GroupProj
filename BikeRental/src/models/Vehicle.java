package models;

//system imports
import java.util.Properties;

//project imports
import impres.exception.InvalidPrimaryKeyException;


public class Vehicle extends ModelBase
{

    private static final String myTableName = "Vehicle";	//name of database table

    //Constructor
    //-----------------------------------------------------
    public Vehicle() {
        super(myTableName);
    }

    public Vehicle(final String vehicleID) throws InvalidPrimaryKeyException {
        super(myTableName);
        initFromQuery(vehicleID);
    }

    //Still needed with Super class change? -MW
    //----------------------------------------------------------------------
    public Vehicle(final Properties props) {
        super(myTableName, props);
    }

    //-----------------------------------------------------------------------------------
    public static boolean equals(Vehicle a, Vehicle b) {
        String aNum = (String)a.getState("vehicleID");
        String bNum = (String)b.getState("vehicleID");
        return aNum.equals(bNum);
    }

    public String getIdFieldName() {
        return "vehicleId";
    }

    public String getViewName() {
        return "VehicleView";
    }

    public String getVehicleID() {
        return this.persistentState.getProperty("vehicleID");
    }

}

