package models;

//system imports
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.Vector;
import java.util.Properties;

//project imports
import impres.exception.InvalidPrimaryKeyException;
import impres.impresario.*;
import views.View;
import views.ViewFactory;


public class Vehicle extends ModelBase
{

    private static final String myTableName = "Vehicle";	//name of database table
    private String updateStatusMessage = "";
    protected Properties dependencies;

    //Constructor
    //-----------------------------------------------------
    public Vehicle()
    {
        super(myTableName);
        subTitleText = "AddVehicleTitle";
        persistentState = new Properties();
    }

    public Vehicle(String vehicleID) throws InvalidPrimaryKeyException
    {
        super(myTableName);
        subTitleText = "ModifyVehicle";
        initFromQuery(vehicleID);
    }

    //Still needed with Super class change? -MW
    //----------------------------------------------------------------------
    public Vehicle(Properties props)
    {
        super(myTableName);

        setDependencies();
        persistentState = new Properties();
        Enumeration allKeys = props.propertyNames();
        while(allKeys.hasMoreElements() == true)
        {
            String nextKey = (String)allKeys.nextElement();
            String nextValue = props.getProperty(nextKey);

            if(nextValue != null)
            {
                persistentState.setProperty(nextKey, nextValue);
            }
        }
    }

    public Vector getEntryListView()
    {
        Vector v = new Vector();
        v.addElement(persistentState.getProperty("vehicleId"));
        v.addElement(persistentState.getProperty("make"));
        v.addElement(persistentState.getProperty("modelNumber"));
        v.addElement(persistentState.getProperty("serialNumber"));
        v.addElement(persistentState.getProperty("color"));
        v.addElement(persistentState.getProperty("description"));
        v.addElement(persistentState.getProperty("location"));
        v.addElement(persistentState.getProperty("physicalCondition"));
        v.addElement(persistentState.getProperty("status"));
        v.addElement(persistentState.getProperty("dateStatusUpdated"));

        return v;
    }

    //-----------------------------------------------------------------------------------
    public static int compare(Vehicle a, Vehicle b)
    {
        String aNum = (String)a.getState("vehicleId");
        String bNum = (String)b.getState("vehicleId");

        return aNum.compareTo(bNum);
    }

    public String getIdFieldName()
    {
        return "vehicleId";
    }

    public String getViewName(){
        if(subTitleText == "AddVehicleTitle")
            return "NewVehicleView";
        else if(subTitleText == "ModifyVehicle")
            return "ModifyVehicleView";
        else { return ""; }
        }

    public boolean checkIfExists(String id)
    {
        try {
            Vehicle vehicle = new Vehicle(id);
            return true;
        }
        catch (InvalidPrimaryKeyException ex) {
            //System.out.println(ex.getMessage()); //test
            return false;
        }
    }

    public String getVehicleID(){ return this.persistentState.getProperty("vehicleId"); }

}

