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


public class Vehicle extends EntityBase implements IView, IModel, ISlideShow
{

    private static final String myTableName = "Vehicle";	//name of database table
    protected Properties dependencies;
    private String updateStatusMessage = "";


    //Constructor
    //-----------------------------------------------------
    public Vehicle()
    {
        super(myTableName);
        setDependencies();
    }

    public Vehicle(String vehicleID) throws InvalidPrimaryKeyException
    {
        super(myTableName);


        setDependencies();

        String query = "SELECT * FROM " + myTableName + " WHERE (vehicleID = " + vehicleID + ")";

        Vector allDataRetrieved = getSelectQueryResult(query);

        //Must get at least one bike
        if (allDataRetrieved != null)
        {
            int size = allDataRetrieved.size();

            //There should be EXACTLY one bike retrieved, more than that is an error
            if (size != 1)
            {
                throw new InvalidPrimaryKeyException("Multiple vehicles matching vehicleID: " + vehicleID + " found.");
                //System.out.println("Multiple books matching vehicleID: " + vehicleID + " found.");
            }
            else
            {
                //copy all the retrieved data into persistant state
                Properties retrievedBookData =(Properties)allDataRetrieved.elementAt(0);
                persistentState = new Properties();

                Enumeration allKeys = retrievedBookData.propertyNames();
                while (allKeys.hasMoreElements() == true)
                {
                    String nextKey = (String)allKeys.nextElement();
                    String nextValue = retrievedBookData.getProperty(nextKey);

                    if(nextValue != null)
                    {
                        persistentState.setProperty(nextKey, nextValue);
                    }
                }
            }
        }
        else
        {
            throw new InvalidPrimaryKeyException("No bikes matching vehicleID: " + vehicleID + " found.");
            //System.out.println("No books matching vehicleID: " + vehicleID + " found.");
        }

    }

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

    private void setVehicleValues(Properties props)
    {
        Enumeration allKeys = props.propertyNames();
        while (allKeys.hasMoreElements() == true)
        {
            String nextKey = (String)allKeys.nextElement();
            String nextValue = props.getProperty(nextKey);

            if(nextValue != null)
            {
                persistentState.setProperty(nextKey, nextValue);
            }
        }
    }


    public void createAndShowDataEntryView()
    {
        View localView = (View)myViews.get("VehicleView");

        if(localView == null)
        {
            localView = ViewFactory.createView("VehicleView", this);

            myViews.put("VehicleView", localView);

            swapToView(localView);
        }
        else
        {
            swapToView(localView);
        }
    }
    public Vector getEntryListView()
    {
        Vector v = new Vector();

        v.addElement(persistentState.getProperty("vehicleID"));
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

    //Only used for initial pre-GUI-testing when connecting to database
    public void printData()
    {
        System.out.println("vehicleID: " + persistentState.getProperty("vehicleID"));
        System.out.println("make: " + persistentState.getProperty("make"));
        System.out.println("modelNumber: " + persistentState.getProperty("modelNumber"));
        System.out.println("serialNumber: " + persistentState.getProperty("serialNumber"));
        System.out.println("color: " + persistentState.getProperty("color"));
        System.out.println("description: " + persistentState.getProperty("description"));
        System.out.println("location: " + persistentState.getProperty("location"));
        System.out.println("physicalCondition: " + persistentState.getProperty("physicalCondition"));
        System.out.println("status: " + persistentState.getProperty("status"));
        System.out.println("dateStatusUpdated: " + persistentState.getProperty("dateStatusUpdated"));
    }

    public void updateState(String key, Object value)
    {
        stateChangeRequest(key, value);
    }

    protected void initializeSchema(String tableName)
    {
        if (mySchema == null)
        {
            mySchema = getSchemaInfo(tableName);
        }
    }

    //----------------------------------------------------------
    public Object getState(String key)
    {
        if (key.equals("UpdateStatusMessage") == true)
        {
            return updateStatusMessage;
        }


        return persistentState.getProperty(key);
    }

    public void stateChangeRequest(String key, Object value)
    {
        switch (key)
        {
            case "ProcessInsertion":
                processInsertion((Properties) value);
                break;
            case "ShowDataEntryView":
                createAndShowDataEntryView();
                break;
        }
        myRegistry.updateSubscribers(key, this);

    }

    private void processInsertion(Properties props)
    {
        setVehicleValues(props);
        update();
    }

    //-----------------------------------------------------------------------------------
    public static int compare(Vehicle a, Vehicle b)
    {
        String aNum = (String)a.getState("vehicleID");
        String bNum = (String)b.getState("vehicleID");

        return aNum.compareTo(bNum);
    }

    public void update()
    {
        updateStateInDatabase();
    }

    private void updateStateInDatabase()
    {
        try
        {
            if(persistentState.getProperty("vehicleID") != null)
            {
                Properties whereClause = new Properties();
                whereClause.setProperty("vehicleID", persistentState.getProperty("vehicleID"));
                updatePersistentState(mySchema, persistentState, whereClause);
                //System.out.println("Book data for bookId: " + persistentState.getProperty("bookID") + " updated successfully in database.");
                updateStatusMessage = "Vehicle data for vehicleID: " + persistentState.getProperty("vehicleID") + " updated successfully in database.";
            }
            else
            {
                Integer bookId = insertAutoIncrementalPersistentState(mySchema, persistentState);
                persistentState.setProperty("vehicleID", "" + bookId.intValue());
                //System.out.println("Book data for new book: " + persistentState.getProperty("bookId") + " installed successfully in database.");
                updateStatusMessage = "Vehcicle data for new vehicle: " + persistentState.getProperty("vehicleID") + " installed successfully in database.";
            }
        }
        catch (SQLException ex)
        {
            updateStatusMessage = "Error in installing vehicle data in database!";
            //System.out.println("Error in installing book data in database!");
        }
    }

    protected void setDependencies()
    {
        dependencies = new Properties();
        dependencies.setProperty("Done", "End");
        dependencies.setProperty("ProcessInsertion", "UpdateStatusMessage");


        myRegistry.setDependencies(dependencies);
    }

    /** Register objects to receive state updates. */
    //----------------------------------------------------------
    public void subscribe(String key, IView subscriber)
    {
        // DEBUG: System.out.println("Cager[" + myTableName + "].subscribe");
        // forward to our registry
        myRegistry.subscribe(key, subscriber);
    }

    /** Unregister previously registered objects. */
    //----------------------------------------------------------
    public void unSubscribe(String key, IView subscriber)
    {
        // DEBUG: System.out.println("Cager.unSubscribe");
        // forward to our registry
        myRegistry.unSubscribe(key, subscriber);
    }

}

