package models;

import impres.exception.InvalidPrimaryKeyException;

import java.util.List;

/**
 * Created by Ryan on 4/23/2015.
 */
public class RentalCollection extends CatalogBase<Rental> {

    private static final String myTableName = "Rental";

    private List<Rental> rentals;
    private Worker mWorker;

    //Constructor
    public RentalCollection(final Worker myWorker) {
        super(myTableName, Rental.class);
        mWorker = myWorker;
    }

    //Methods
    public List<Rental> findActiveRentals() throws InvalidPrimaryKeyException {
        String query = "SELECT * FROM " + myTableName + " WHERE (dateReturned = '')";
        rentals = getDatasFromQuery(query);
        return rentals;
    }

    @Override
    public String getViewName() {
        return "RentalCollectionView";
    }

    @Override
    public Object getState(final String key) {
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
    public void stateChangeRequest(final String key, final Object value) {
        switch (key) {
            case "ProcessReturn":
                processReturn((String) value);
                break;
            case "ShowDataEntryView":
                createAndShowDataEntryView();
                break;
        }
        myRegistry.updateSubscribers(key, this);
    }

    private void processReturn(final String s) {
        try {
            Rental r = new Rental(s);
            r.setReturned((String)mWorker.getState("workerId"));
            updateStatusMessage = messages.getString("returnSuccessful");
        }
        catch(Exception e) {
            updateStatusMessage = messages.getString("returnUnSuccessful");
        }
    }

    @Override
    public String getIdFieldName() {
        return "rentalID";
    }

    @Override
    public Boolean checkIfExists(final String idToQuery) {
        return false;
    }
}
