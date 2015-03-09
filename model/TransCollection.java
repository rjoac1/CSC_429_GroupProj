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
public class TransCollection extends EntityBase
{
	private static final String myTableName = "Transaction";

	private Vector transactions;
	//GUI Components

	//Constructor
	//--------------------------------
	public TransCollection(String bookId, String patronId, String dateOfTransaction)
	{
		super(myTableName);
		transactions = new Vector();
		String query;
		if(bookId == null && patronId == null)
		{
			query = "SELECT * FROM " + myTableName + " WHERE (dateOfTrans = '" + dateOfTransaction + "')";
		}
		else if(bookId == null && dateOfTransaction == null)
		{
			query = "SELECT * FROM " + myTableName + " WHERE (patronId = '" + patronId + "')";
		}
		else if(patronId == null && dateOfTransaction == null)
		{
			query = "SELECT * FROM " + myTableName + " WHERE (bookId = '" + bookId + "')";
		}
		else if(bookId == null)
		{
			query = "SELECT * FROM " + myTableName + " WHERE (patronId = '" + patronId + "' AND dateOfTrans = '" + dateOfTransaction + "')";
		}
		else if(patronId == null)
		{
			query = "SELECT * FROM " + myTableName + " WHERE (bookId = '" + bookId + "' AND dateOfTrans = '" + dateOfTransaction + "')";
		}
		else if(dateOfTransaction == null)
		{
			query = "SELECT * FROM " + myTableName + " WHERE (bookId = '" + bookId + "' AND patronId = '" + patronId + "')";
		}
		else
		{
			query = "SELECT * FROM " + myTableName + " WHERE (bookId = '" + bookId + "' AND patronId = '" + patronId + "' AND dateOfTrans = '" + dateOfTransaction + "')";
		}

		Vector allDataRetrieved = getSelectQueryResult(query);

		if(allDataRetrieved != null)
		{
			for(int cnt = 0; cnt < allDataRetrieved.size();cnt++)
			{
				Properties nextTransData = (Properties)allDataRetrieved.elementAt(cnt);

				Trans trans = new Trans(nextTransData);

				if(trans != null)
				{
					addTrans(trans);
				}
			}
		}
		else
		{
			System.out.println("No transactions matching the provided criteria found in database");
			//throw new InvalidPrimaryKeyException("No accounts for customer : " + accountHolderId + ". Name : " + cust.getState("Name"));
		}
	}
	//----------------------------------------------------------------------------------
	private void addTrans(Trans a)
	{
		//users.add(u);
		transactions.add(a);
	}
	public void printData()
	{
		Enumeration e = transactions.elements();
		while(e.hasMoreElements())
		{
			Trans t = (Trans)e.nextElement();
			t.printData();
		}
	}
	//----------------------------------------------------------
	public Object getState(String key)
	{
		return transactions;
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