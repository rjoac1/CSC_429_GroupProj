package models;

import impres.exception.InvalidPrimaryKeyException;

import java.text.MessageFormat;
import java.util.Enumeration;
import java.util.Properties;
import java.util.Vector;

/**
 * Created by Ryan on 4/23/2015.
 */
public class RentalCollection extends ModelBase{

    private static final String myTableName = "Rental";

    private Vector rentals;
    //GUI Components

    //Constructor
    //--------------------------------
    public RentalCollection()
    {
        super(myTableName);
        rentals = new Vector();
    }
    //Methods
    //---------------------------------
    public Vector findActiveRentals() throws InvalidPrimaryKeyException
    {
        String query = "SELECT * FROM " + myTableName + " WHERE (dateReturned = '')";

        Vector allDataRetrieved = getSelectQueryResult(query);

        if(allDataRetrieved != null)
        {
            for(int cnt = 0; cnt < allDataRetrieved.size();cnt++)
            {
                Properties nextRentalData = (Properties)allDataRetrieved.elementAt(cnt);

                Rental rental = new Rental(nextRentalData);

                if(rental != null)
                {
                    addRental(rental);
                }
            }
        }
        else
        {
            //System.out.println("No patrons older than " + date + " found in database");
            throw new InvalidPrimaryKeyException(messages.getString("noActiveRentalsFound"));


        }
        return rentals;
    }
    //----------------------------------------------------------------------------------
    private void addRental(Rental a)
    {
        //users.add(u);
        rentals.add(a);
    }
    public String getViewName(){
        return "RentalCollectionView";
    }

    @Override
    public Object getState(String key) {
        if (key.equals("Rentals"))
            return rentals;
        else
        if (key.equals("RentalList"))
            return this;
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
        switch(key)
        {
            case "ProcessReturn":
                processReturn((Rental) value);
                break;
            case "ShowDataEntryView":
                createAndShowDataEntryView();
                break;
        }
        myRegistry.updateSubscribers(key, this);
    }
    private void processReturn(Rental r)
    {
        r.setReturned();
    }

    @Override
    public String getIdFieldName() {
        return "rentalID";
    }

    @Override
    public boolean checkIfExists(String idToQuery) {
        return false;
    }
}
