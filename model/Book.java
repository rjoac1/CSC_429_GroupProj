//command line commands
/*
cd Documents\Brockport Spring 2015\OO Design\ATM_Case_Study\ATM Case Study\Implementation\
javac -d classes -classpath classes;. model\*.java
*/
//specify the package
package model;

//system imports
import java.sql.SQLException;
import java.util.Enumeration;
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
public class Book extends EntityBase implements IView, IModel, ISlideShow
{

	private static final String myTableName = "Book";	//name of database table
    protected Properties dependencies;
    private String updateStatusMessage = "";


	//Constructor
	//-----------------------------------------------------
	public Book()
    {
        super(myTableName);
        setDependencies();
    }
    public Book(String bookId) throws InvalidPrimaryKeyException
	{
		super(myTableName);


        setDependencies();

		String query = "SELECT * FROM " + myTableName + " WHERE (bookId = " + bookId + ")";

		Vector allDataRetrieved = getSelectQueryResult(query);

		//Must get at least one book
		if (allDataRetrieved != null)
		{
			int size = allDataRetrieved.size();

			//There should be EXACTLY one book retrieved, more than that is an error
			if (size != 1)
			{
				throw new InvalidPrimaryKeyException("Multiple books matching bookId: " + bookId + " found.");
				//System.out.println("Multiple books matching bookId: " + bookId + " found.");
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
			throw new InvalidPrimaryKeyException("No books matching bookId: " + bookId + " found.");
			//System.out.println("No books matching bookId: " + bookId + " found.");
		}

	}
	//----------------------------------------------------------------------
	public Book(Properties props)
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
    private void setBookValues(Properties props)
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
    public void createAndShowDataEntryView()
    {
        View localView = (View)myViews.get("BookView");

        if(localView == null)
        {
            localView = ViewFactory.createView("BookView", this);

            myViews.put("BookView", localView);

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

		v.addElement(persistentState.getProperty("bookId"));
		v.addElement(persistentState.getProperty("author"));
		v.addElement(persistentState.getProperty("title"));
		v.addElement(persistentState.getProperty("pubYear"));
		v.addElement(persistentState.getProperty("status"));

		return v;
	}
    //Only used for initial pre-GUI-testing when connecting to database
	public void printData()
	{
		System.out.println("bookId: " + persistentState.getProperty("bookId"));
		System.out.println("author: " + persistentState.getProperty("author"));
		System.out.println("title: " + persistentState.getProperty("title"));
		System.out.println("pubYear: " + persistentState.getProperty("pubYear"));
		System.out.println("status: " + persistentState.getProperty("status"));
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
        setBookValues(props);
        update();
    }
    //-----------------------------------------------------------------------------------
    public static int compare(Book a, Book b)
    {
        String aNum = (String)a.getState("author");
        String bNum = (String)b.getState("author");

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
			if(persistentState.getProperty("bookId") != null)
			{
				Properties whereClause = new Properties();
				whereClause.setProperty("bookId", persistentState.getProperty("bookId"));
				updatePersistentState(mySchema, persistentState, whereClause);
				//System.out.println("Book data for bookId: " + persistentState.getProperty("bookID") + " updated successfully in database.");
				updateStatusMessage = "Book data for bookId: " + persistentState.getProperty("bookID") + " updated successfully in database.";
			}
			else
			{
				Integer bookId = insertAutoIncrementalPersistentState(mySchema, persistentState);
				persistentState.setProperty("bookId", "" + bookId.intValue());
				//System.out.println("Book data for new book: " + persistentState.getProperty("bookId") + " installed successfully in database.");
				updateStatusMessage = "Book data for new book: " + persistentState.getProperty("bookId") + " installed successfully in database.";
			}
		}
		catch (SQLException ex)
		{
			updateStatusMessage = "Error in installing account data in database!";
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