// specify the package
package models;

// system imports

import impres.exception.InvalidPrimaryKeyException;

import java.util.Enumeration;
import java.util.Properties;
import java.util.Vector;

// project imports


public class Rental extends ModelBase{
    private static final String myTableName = "Rental";
    private String updateStatusMessage = "";
    protected Properties dependencies;

    public Rental()
    {
        super(myTableName);

        setDependencies();
        persistentState = new Properties();
    }

    public Rental(String userID)throws InvalidPrimaryKeyException{
        super(myTableName);
        initFromQuery(userID);

    }
    //----------------------------------------------------------
    public Rental(Properties props)
    {
        super(myTableName);

        setDependencies();
        persistentState = new Properties();
        Enumeration allKeys = props.propertyNames();
        while (allKeys.hasMoreElements() == true)
        {
            String nextKey = (String)allKeys.nextElement();
            String nextValue = props.getProperty(nextKey);

            if (nextValue != null)
            {
                persistentState.setProperty(nextKey, nextValue);
            }
        }
    }

    public Vector getEntryListView()
    {
        Vector v = new Vector();
        v.addElement(persistentState.getProperty("rentalID"));
        v.addElement(persistentState.getProperty("vehicleID"));
        v.addElement(persistentState.getProperty("renterID"));
        v.addElement(persistentState.getProperty("dateRented"));
        //v.addElement(persistentState.getProperty("timeRented"));
        v.addElement(persistentState.getProperty("dateDue"));
        //v.addElement(persistentState.getProperty("timeDue"));
        v.addElement(persistentState.getProperty("dateReturned"));
        //v.addElement(persistentState.getProperty("timeReturned"));
        v.addElement(persistentState.getProperty("checkoutWorkerID"));
        v.addElement(persistentState.getProperty("checkinWorkerID"));
        return v;
    }

    public String getIdFieldName()
    {
        return "rentalID";
    }
    public String getViewName(){ return "rentalView"; }
    public boolean checkIfExists(String id)
    {
        try {
            Rental user = new Rental(id);
            return true;
        }


        catch (InvalidPrimaryKeyException ex) {
            //System.out.println(ex.getMessage()); //test
            return false;
        }
    }
    public void setReturned()
    {
        //persistentState.setProperty("","");
    }
}