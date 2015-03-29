package models;

//system imports
import java.sql.SQLException;
import java.util.Vector;
import java.util.Properties;
import java.util.Enumeration;

//project imports
import impres.exception.InvalidPrimaryKeyException;
import impres.exception.PasswordMismatchException;

import impres.impresario.*;

import views.View;
import views.ViewFactory;

//=========================================
public class Worker extends EntityBase implements IView, IModel, ISlideShow
{

    private static final String myTableName = "Worker";	//name of database table
    protected Properties dependencies;
    private String updateStatusMessage = "";

    //Constructor
    //-----------------------------------------------------
    public Worker()
    {
        super(myTableName);
        setDependencies();
    }

    public Worker(String workerId) throws InvalidPrimaryKeyException
    {
        super(myTableName);

        setDependencies();

        String query = "SELECT * FROM " + myTableName + " WHERE (workerId = " + workerId + ")";

        Vector allDataRetrieved = getSelectQueryResult(query);

        //Must get at least one book
        if (allDataRetrieved != null)
        {
            int size = allDataRetrieved.size();

            //There should be EXACTLY one book retrieved, more than that is an error
            if (size != 1)
            {
                //System.out.println("Multiple patrons matching patronId: " + patronId + " found.");
                throw new InvalidPrimaryKeyException("Multiple workers matching workerId: " + workerId + " found.");
            }
            else
            {
                //copy all the retrieved data into persistant state
                Properties retrievedWorkerData = (Properties)allDataRetrieved.elementAt(0);
                persistentState = new Properties();

                Enumeration allKeys = retrievedWorkerData.propertyNames();
                while (allKeys.hasMoreElements() == true)
                {
                    String nextKey = (String)allKeys.nextElement();
                    String nextValue = retrievedWorkerData.getProperty(nextKey);

                    if(nextValue != null)
                    {
                        persistentState.setProperty(nextKey, nextValue);
                    }
                }
            }
        }
        else
        {
            //System.out.println("No patrons matching patronId: " + patronId + " found.");
            throw new InvalidPrimaryKeyException("No workers matching workerId: " + workerId + " found.");
        }

    }
    //----------------------------------------------------------------------
    public Worker(Properties props) throws InvalidPrimaryKeyException, PasswordMismatchException
    {
        super(myTableName);

        String idToQuery = props.getProperty("ID");

        String query = "SELECT * FROM " + myTableName + " WHERE (workerId = " + idToQuery + ");";

        Vector allDataRetrieved = getSelectQueryResult(query);

        // You must get one account at least
        if (allDataRetrieved != null)
        {
            int size = allDataRetrieved.size();

            // There should be EXACTLY one account. More than that is an error
            if (size != 1)
            {
                throw new InvalidPrimaryKeyException("Multiple accounts matching worker id : "
                        + idToQuery + " found.");
            }
            else
            {
                // copy all the retrieved data into persistent state
                Properties retrievedWorkerData = (Properties)allDataRetrieved.elementAt(0);
                persistentState = new Properties();

                Enumeration allKeys = retrievedWorkerData.propertyNames();
                while (allKeys.hasMoreElements() == true)
                {
                    String nextKey = (String)allKeys.nextElement();
                    String nextValue = retrievedWorkerData.getProperty(nextKey);

                    if (nextValue != null)
                    {
                        persistentState.setProperty(nextKey, nextValue);
                    }
                }

            }
        }
        // If no account found for this user name, throw an exception
        else
        {
            throw new InvalidPrimaryKeyException("No account matching worker id : "
                    + idToQuery + " found.");
        }

        String password = props.getProperty("Password");

        String accountPassword = persistentState.getProperty("password");

        if (accountPassword != null)
        {
            boolean passwordCheck = accountPassword.equals(password);
            if (passwordCheck == false)
            {
                throw new PasswordMismatchException("Password mismatch");
            }
        }
        else
        {
            throw new PasswordMismatchException("Password missing for account");
        }
    }

    private void setWorkerValues(Properties props)
    {
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

    protected void setDependencies()
    {
        dependencies = new Properties();
        dependencies.setProperty("Done", "End");
        dependencies.setProperty("ProcessInsertion", "UpdateStatusMessage");

        myRegistry.setDependencies(dependencies);
    }
    public void createAndShowDataEntryView()
    {
        View localView = (View)myViews.get("WorkerView");

        if(localView == null)
        {
            localView = ViewFactory.createView("WorkerView", this);

            myViews.put("WorkerView", localView);

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

        v.addElement(persistentState.getProperty("patronId"));
        v.addElement(persistentState.getProperty("name"));
        v.addElement(persistentState.getProperty("address"));
        v.addElement(persistentState.getProperty("city"));
        v.addElement(persistentState.getProperty("stateCode"));
        v.addElement(persistentState.getProperty("zip"));
        v.addElement(persistentState.getProperty("email"));
        v.addElement(persistentState.getProperty("dateOfBirth"));
        v.addElement(persistentState.getProperty("status"));

        return v;
    }

    public void printData()
    {
        System.out.println("patronId: " + persistentState.getProperty("patronId"));
        System.out.println("name: " + persistentState.getProperty("name"));
        System.out.println("address: " + persistentState.getProperty("address"));
        System.out.println("city: " + persistentState.getProperty("city"));
        System.out.println("stateCode: " + persistentState.getProperty("stateCode"));
        System.out.println("zip: " + persistentState.getProperty("zip"));
        System.out.println("email: " + persistentState.getProperty("email"));
        System.out.println("dateOfBirth: " + persistentState.getProperty("dateOfBirth"));
        System.out.println("status: " + persistentState.getProperty("status"));
    }

    protected void initializeSchema(String tableName)
    {
        if (mySchema == null)
        {
            mySchema = getSchemaInfo(tableName);
        }
    }

    public Object getState(String key)
    {
        if (key.equals("UpdateStatusMessage") == true)
        {
            return updateStatusMessage;
        }
        return persistentState.getProperty(key);
    }

    public void updateState(String key, Object value)
    {
        stateChangeRequest(key, value);
    }

    //----------------------------------------------------------------
    public void stateChangeRequest(String key, Object value)
    {
        switch(key)
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
        setWorkerValues(props);
        update();
    }

    public void update()
    {
        updateStateInDatabase();
    }

    private void updateStateInDatabase()
    {
        try
        {
            if(persistentState.getProperty("workerId") != null)
            {
                Properties whereClause = new Properties();
                whereClause.setProperty("workerId", persistentState.getProperty("workerId"));
                updatePersistentState(mySchema, persistentState, whereClause);
                //System.out.println("Patron data for patronId: " + persistentState.getProperty("patronID") + " updated successfully in database.");
                updateStatusMessage = "Worker data for workerId: " + persistentState.getProperty("workerId") + " updated successfully in database.";
            }
            else
            {
                Integer workerId = insertAutoIncrementalPersistentState(mySchema, persistentState);
                persistentState.setProperty("workerId", "" + workerId.intValue());
                //System.out.println("Patron data for new patron: " + persistentState.getProperty("patronId") + " installed successfully in database.");
                updateStatusMessage = "Worker data for new worker: " + persistentState.getProperty("workerId") + " installed successfully in database.";
            }
        }
        catch (SQLException ex)
        {
            updateStatusMessage = "Error in installing worker data in database!";
            //System.out.println("Error in installing book data in database!" +ex);
        }
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

