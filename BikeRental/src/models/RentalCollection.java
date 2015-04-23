package models;

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
    public Vector findPatronsOlderThan(String date) throws Exception
    {
        if (date.matches("\\d{4}-\\d{2}-\\d{2}") == false)
        {
            throw new Exception ("INPUT ERROR: Date not of the correct format.");
        }
        else
        {
            String query = "SELECT * FROM " + myTableName + " WHERE (dateOfBirth < '"+ date +"')";

            Vector allDataRetrieved = getSelectQueryResult(query);

            if(allDataRetrieved != null)
            {
                for(int cnt = 0; cnt < allDataRetrieved.size();cnt++)
                {
                    Properties nextPatronData = (Properties)allDataRetrieved.elementAt(cnt);

                    Patron patron = new Patron(nextPatronData);

                    if(patron != null)
                    {
                        addPatron(patron);
                    }
                }
            }
            else
            {
                System.out.println("No patrons older than " + date + " found in database");
                //throw new InvalidPrimaryKeyException("No accounts for customer : " + accountHolderId + ". Name : " + cust.getState("Name"));
            }

        }
        return patrons;
    }
    //----------------------------------------------------------------------------------
    private void addRental(Rental a)
    {
        //users.add(u);
        rentals.add(a);
    }
    public String getMyTableName()
    {
        return myTableName;
    }
    public String getViewName(){
        return "RentalCollectionView";
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
