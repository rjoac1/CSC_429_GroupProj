// specify the package
package models;

// system imports

import impres.exception.InvalidPrimaryKeyException;
import impres.exception.NoBikesAvailableException;
import views.DateLabelFormatter;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.Properties;
import java.util.Vector;

// project imports


public class Rental extends ModelBase{
    private static final String myTableName = "Rental";
    private VehicleCatalog vehicleCatalog;
    private Worker currentWorker;
    public Rental(Worker mWorker) throws NoBikesAvailableException
    {
        super(myTableName);

        setDependencies();
        persistentState = new Properties();
        vehicleCatalog = new VehicleCatalog();
        String[] vehicles = (String[])vehicleCatalog.getState("VehicleIDs");
        if(vehicles.length == 0)
        {
            throw new NoBikesAvailableException(messages.getString("NoBikesAvailableToRentError"));
        }
        currentWorker = mWorker;
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
        v.addElement(persistentState.getProperty("timeRented"));
        v.addElement(persistentState.getProperty("dateDue"));
        v.addElement(persistentState.getProperty("timeDue"));
        //v.addElement(persistentState.getProperty("dateReturned"));
        //v.addElement(persistentState.getProperty("timeReturned"));
        v.addElement(persistentState.getProperty("checkoutWorkerID"));
        //v.addElement(persistentState.getProperty("checkinWorkerID"));
        return v;
    }

    public String getIdFieldName()
    {
        return "rentalID";
    }

    @Override
    public Object getState(String key) {
        if (key.equals("VehicleIDs")) {
            return vehicleCatalog.getState("VehicleIDs");
        }
        else if (key.equals("workerId")){
            return currentWorker.getState("workerId");
        }
        else if (key.equals("UpdateStatusMessage"))
        {
            return updateStatusMessage;
        }
        return persistentState.getProperty(key);
        //return null;
    }

    public String getViewName(){ return "RentalView"; }
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
    public void setReturned(String workerId)
    {
        DateLabelFormatter dates = new DateLabelFormatter();
        String dateString = dates.getCurrentDate();
        String timeString = dates.getCurrentTime();
        persistentState.setProperty("dateReturned",dateString);
        persistentState.setProperty("timeReturned",timeString);
        persistentState.setProperty("checkinWorkerID", workerId);
        update();
    }

    public String[] getBikes(){
        return (String[]) vehicleCatalog.getState("VehicleIDs");
    }
}