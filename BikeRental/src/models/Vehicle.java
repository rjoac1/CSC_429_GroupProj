package models;

//system imports
import java.util.Properties;

//project imports
import impres.exception.InvalidPrimaryKeyException;
import impres.impresario.*;
import views.View;
import views.ViewFactory;


public class Vehicle extends ModelBase implements IView, IModel, ISlideShow
{

    private static final String mTableName = "Vehicle";

    public Vehicle()
    {
        super(mTableName);
    }

    public Vehicle(String vehicleID) throws InvalidPrimaryKeyException
    {
        super(mTableName);
        String query = "SELECT * FROM " + mTableName
                + " WHERE (vehicleID = " + vehicleID + ")";
        initFromQuery(query);
    }

    public Vehicle(Properties props)
    {
        super(mTableName);
        persistentState = (Properties)props.clone();
    }

    public void createAndShowDataEntryView()
    {
        View localView = (View)myViews.get("VehicleView");
        if (localView == null) {
            localView = ViewFactory.createView("VehicleView", this);
            myViews.put("VehicleView", localView);
            swapToView(localView);
        }
        else {
            swapToView(localView);
        }
    }

    @Override
    public void stateChangeRequest(String key, Object value) {
        switch (key) {
            case "ProcessInsertion":
                processInsertion((Properties) value);
                break;
            case "ShowDataEntryView":
                createAndShowDataEntryView();
                break;
        }
        myRegistry.updateSubscribers(key, this);
    }

    //-----------------------------------------------------------------------------------
    public static boolean equals(Vehicle a, Vehicle b) {
        String aNum = (String)a.getState("vehicleID");
        String bNum = (String)b.getState("vehicleID");
        return aNum.equals(bNum);
    }

    protected String getIdFieldName() {
        return "vehicleId";
    }
}

