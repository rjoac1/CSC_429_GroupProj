//specify the package
package model;

//system imports
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.Vector;
import java.util.Properties;

//project imports
import event.Event;
import database.*;
import exception.InvalidPrimaryKeyException;

import impresario.*;

import userinterface.View;
import userinterface.ViewFactory;

//=========================================
public class Trans extends EntityBase implements IView, IModel, ISlideShow
{

	private static final String myTableName = "Transaction";	//name of database table
    protected Properties dependencies;
    private String updateStatusMessage = "";

	//Constructor
	//-----------------------------------------------------
	public Trans()
    {
        super(myTableName);
        setDependencies();
    }
    public Trans(String transId) throws InvalidPrimaryKeyException
	{
		super(myTableName);

        setDependencies();

		String query = "SELECT * FROM " + myTableName + " WHERE (transId = " + transId + ")";

		Vector allDataRetrieved = getSelectQueryResult(query);

		//Must get at least one book
		if (allDataRetrieved != null)
		{
			int size = allDataRetrieved.size();

			//There should be EXACTLY one book retrieved, more than that is an error
			if (size != 1)
			{
				//System.out.println("Multiple transactions matching transId: " + transId + " found.");
				throw new InvalidPrimaryKeyException("Multiple transactions matching transId: " + transId + " found.");
			}
			else
			{
				//copy all the retrieved data into persistant state
				Properties retrievedTransData = (Properties)allDataRetrieved.elementAt(0);
				persistentState = new Properties();

				Enumeration allKeys = retrievedTransData.propertyNames();
				while (allKeys.hasMoreElements() == true)
				{
					String nextKey = (String)allKeys.nextElement();
					String nextValue = retrievedTransData.getProperty(nextKey);

					if(nextValue != null)
					{
						persistentState.setProperty(nextKey, nextValue);
					}
				}
			}
		}
		else
		{
			//System.out.println("No transactions matching transId: " + transId + " found.");
			throw new InvalidPrimaryKeyException("No transactions matching transId: " + transId + " found.");
		}

	}
	//----------------------------------------------------------------------
	public Trans(Properties props)
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
    private void setTransValues(Properties props)
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
        View localView = (View)myViews.get("TransView");

        if(localView == null)
        {
            localView = ViewFactory.createView("TransView", this);

            myViews.put("TransView", localView);

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

		v.addElement(persistentState.getProperty("transId"));
		v.addElement(persistentState.getProperty("bookId"));
		v.addElement(persistentState.getProperty("patronId"));
		v.addElement(persistentState.getProperty("transType"));
		v.addElement(persistentState.getProperty("dateOfTrans"));

		return v;
	}
	public void printData()
	{
		System.out.println("transId: " + persistentState.getProperty("transId"));
		System.out.println("bookId: " + persistentState.getProperty("bookId"));
		System.out.println("patronId: " + persistentState.getProperty("patronId"));
		System.out.println("transType: " + persistentState.getProperty("transType"));
		System.out.println("dateOfTrans: " + persistentState.getProperty("dateOfTrans"));
	}
    private void processInsertion(Properties props)
    {
        setTransValues(props);
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
			if(persistentState.getProperty("transId") != null)
			{
				Properties whereClause = new Properties();
				whereClause.setProperty("transId", persistentState.getProperty("transId"));
				updatePersistentState(mySchema, persistentState, whereClause);
				//System.out.println("Transaction data for transId: " + persistentState.getProperty("transID") + " updated successfully in database.");
				updateStatusMessage = "Transaction data for transId: " + persistentState.getProperty("transID") + " updated successfully in database.";
			}
			else
			{
				Integer transId = insertAutoIncrementalPersistentState(mySchema, persistentState);
				persistentState.setProperty("transId", "" + transId.intValue());
				//System.out.println("Transaction data for new transaction: " + persistentState.getProperty("transId") + " installed successfully in database.");
				updateStatusMessage = "Transaction data for new transaction: " + persistentState.getProperty("transId") + " installed successfully in database.";
			}
		}
		catch (SQLException ex)
		{
			updateStatusMessage = "Error in installing account data in database!";
			//System.out.println("Error in installing book data in database!");
		}
	}


	/**
	 *
	 */
//----------------------------------------------------------
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
	protected void initializeSchema(String tableName)
	{
		if (mySchema == null)
		{
			mySchema = getSchemaInfo(tableName);
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