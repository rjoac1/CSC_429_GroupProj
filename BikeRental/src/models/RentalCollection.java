package models;

import impres.exception.InvalidPrimaryKeyException;

import java.util.Properties;
import java.util.Vector;

/**
 * Created by Ryan on 4/23/2015.
 */
public class RentalCollection extends ModelBase {

    private static final String myTableName = "Rental";

    private Vector<Rental> rentals;
    private Worker mWorker;

    //Constructor
    //--------------------------------
    public RentalCollection(Worker myWorker) {
        super(myTableName);
        rentals = new Vector<>();
        mWorker = myWorker;
    }

    //Methods
    //---------------------------------
    public Vector<Rental> findActiveRentals() throws InvalidPrimaryKeyException {
        String query = "SELECT * FROM " + myTableName + " WHERE (dateReturned = '')";

        Vector<Properties> allDataRetrieved = getSelectQueryResult(query);
        if (allDataRetrieved == null)
            throw new InvalidPrimaryKeyException(messages.getString("noActiveRentalsFound"));
        allDataRetrieved.stream().map(Rental::new).forEach(rentals::add);
        return rentals;
    }

    @Override
    public String getViewName(){
        return "RentalCollectionView";
    }

    @Override
    public Object getState(String key) {
        switch (key) {
            case "Rentals":
                return rentals;
            case "RentalList":
                return this;
            case "UpdateStatusMessage":
                return updateStatusMessage;
        }
        return persistentState.getProperty(key);
    }

    @Override
    public void stateChangeRequest(String key, Object value) {
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
    }

    private void processReturn(String s) {
        try {
            Rental r = new Rental(s);
            r.setReturned((String)mWorker.getState("workerId"));
            updateStatusMessage = messages.getString("returnSuccessful");
        }
        catch(Exception e) {
            e.getMessage();
            updateStatusMessage = messages.getString("returnUnSuccessful");
        }

    }

    @Override
    public String getIdFieldName() {
        return "rentalID";
    }

    @Override
    public Boolean checkIfExists(String idToQuery) {
        return false;
    }
}
