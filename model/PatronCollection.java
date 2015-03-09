//specify the package
package model;

//system imports
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.Vector;
import java.util.Properties;
import java.util.Enumeration;

//project imports
import database.*;
import exception.InvalidPrimaryKeyException;

//=========================================
public class PatronCollection extends EntityBase
{
	private static final String myTableName = "Patron";

	private Vector patrons;
	//GUI Components

	//Constructor
	//--------------------------------
	public PatronCollection()
	{
		super(myTableName);
		patrons = new Vector();
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
	public Vector findPatronsYoungerThan(String date) throws Exception
	{
		if (date.matches("\\d{4}-\\d{2}-\\d{2}") == false)
		{
			throw new Exception ("INPUT ERROR: Date not of the correct format.");
		}
		else
		{
			String query = "SELECT * FROM " + myTableName + " WHERE (dateOfBirth > '"+ date +"')";

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
	public Vector findPatronsAtZipCode(String zip)
	{
		String query = "SELECT * FROM " + myTableName + " WHERE (zip = "+ zip +")";

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
			System.out.println("No patrons at zip code: " + zip + " found in database");
			//throw new InvalidPrimaryKeyException("No accounts for customer : " + accountHolderId + ". Name : " + cust.getState("Name"));
		}
		return patrons;
	}
	public Vector findPatronsWithNamesLike(String name)
	{
		String query = "SELECT * FROM " + myTableName + " WHERE (name LIKE '%" + name + "%')";

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
			System.out.println("No patrons with name matching: " + name + " found in database");
			//throw new InvalidPrimaryKeyException("No accounts for customer : " + accountHolderId + ". Name : " + cust.getState("Name"));
		}
		return patrons;
	}
	public void printData()
	{
		Enumeration e = patrons.elements();
		while(e.hasMoreElements())
		{
			Patron p = (Patron)e.nextElement();
			p.printData();
		}
	}
	//----------------------------------------------------------------------------------
	private void addPatron(Patron a)
	{
		//users.add(u);
		patrons.add(a);
	}
	//----------------------------------------------------------
	public Object getState(String key)
	{
		return patrons;
	}

	//----------------------------------------------------------------
	public void stateChangeRequest(String key, Object value)
	{
			// Class is invariant, so this method does not change any attributes

			//myRegistry.updateSubscribers(key, this);
	}


	//-----------------------------------------------------------------------------------
	protected void initializeSchema(String tableName)
	{
		if (mySchema == null)
		{
			mySchema = getSchemaInfo(tableName);
		}
	}
}