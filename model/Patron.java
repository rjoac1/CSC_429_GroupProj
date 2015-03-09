//specify the package
package model;

//system imports
import java.sql.SQLException;
import java.util.Vector;
import java.util.Properties;
import java.util.Enumeration;

//project imports
import event.Event;
import database.*;
import exception.InvalidPrimaryKeyException;

import impresario.*;

import userinterface.View;
import userinterface.ViewFactory;

//=========================================
public class Patron extends EntityBase implements IView, IModel, ISlideShow
{

	private static final String myTableName = "Patron";	//name of database table
    protected Properties dependencies;
    private String updateStatusMessage = "";

	//Constructor
	//-----------------------------------------------------
	public Patron()
    {
        super(myTableName);
        setDependencies();
    }
    public Patron(String patronId) throws InvalidPrimaryKeyException
	{
		super(myTableName);

        setDependencies();

		String query = "SELECT * FROM " + myTableName + " WHERE (patronId = " + patronId + ")";

		Vector allDataRetrieved = getSelectQueryResult(query);

		//Must get at least one book
		if (allDataRetrieved != null)
		{
			int size = allDataRetrieved.size();

			//There should be EXACTLY one book retrieved, more than that is an error
			if (size != 1)
			{
				//System.out.println("Multiple patrons matching patronId: " + patronId + " found.");
				throw new InvalidPrimaryKeyException("Multiple patrons matching patronId: " + patronId + " found.");
			}
			else
			{
				//copy all the retrieved data into persistant state
				Properties retrievedPatronData = (Properties)allDataRetrieved.elementAt(0);
				persistentState = new Properties();

				Enumeration allKeys = retrievedPatronData.propertyNames();
				while (allKeys.hasMoreElements() == true)
				{
					String nextKey = (String)allKeys.nextElement();
					String nextValue = retrievedPatronData.getProperty(nextKey);

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
			throw new InvalidPrimaryKeyException("No books matching patronId: " + patronId + " found.");
		}

	}
	//----------------------------------------------------------------------
	public Patron(Properties props)
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
    private void setPatronValues(Properties props)
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
        View localView = (View)myViews.get("PatronView");

        if(localView == null)
        {
            localView = ViewFactory.createView("PatronView", this);

            myViews.put("PatronView", localView);

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
        setPatronValues(props);
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
			if(persistentState.getProperty("patronId") != null)
			{
				Properties whereClause = new Properties();
				whereClause.setProperty("patronId", persistentState.getProperty("patronId"));
				updatePersistentState(mySchema, persistentState, whereClause);
				//System.out.println("Patron data for patronId: " + persistentState.getProperty("patronID") + " updated successfully in database.");
				updateStatusMessage = "Patron data for patronId: " + persistentState.getProperty("patronID") + " updated successfully in database.";
			}
			else
			{
				Integer patronId = insertAutoIncrementalPersistentState(mySchema, persistentState);
				persistentState.setProperty("patronId", "" + patronId.intValue());
				//System.out.println("Patron data for new patron: " + persistentState.getProperty("patronId") + " installed successfully in database.");
				updateStatusMessage = "Patron data for new patron: " + persistentState.getProperty("patronId") + " installed successfully in database.";
			}
		}
		catch (SQLException ex)
		{
			updateStatusMessage = "Error in installing account data in database!";
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