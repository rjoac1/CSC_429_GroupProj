//specify the package
package model;

//system imports
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.Vector;
import java.util.Properties;
import java.util.Enumeration;

import javax.swing.JFrame;
//project imports
import database.*;
import event.Event;
import exception.InvalidPrimaryKeyException;

import impresario.*;

import userinterface.View;
import userinterface.ViewFactory;
import userinterface.MainFrame;

//=========================================
public class BookCollection extends EntityBase implements IView, IModel, ISlideShow
{
	private static final String myTableName = "Book";
    protected Properties dependencies;
	private Vector books;
	//GUI Components

	//Constructor
	//--------------------------------
	public BookCollection()
	{
		super(myTableName);
		books = new Vector();
        setDependencies();
	}

	//Methods
	//---------------------------------
	public Vector findBooksOlderThanDate(String date) //throws Exception
	{
		String query = "SELECT * FROM " + myTableName + " WHERE ( pubYear < " + date + " )";

		Vector allDataRetrieved = getSelectQueryResult(query);

		if(allDataRetrieved != null)
		{
			for(int cnt = 0; cnt < allDataRetrieved.size();cnt++)
			{
				Properties nextBookData = (Properties)allDataRetrieved.elementAt(cnt);

				Book book = new Book(nextBookData);

				if(book != null)
				{
					addBook(book);
				}
			}
		}
		else
		{
			//System.out.println("No books older than " + date + " found in database");
			//throw new Exception("No books older than " + date + " found in database");
		}
		return books;
	}
	//---------------------------------------------------------------
	public Vector findBooksNewerThanDate(String date)// throws Exception
	{
		String query = "SELECT * FROM " + myTableName + " WHERE (pubYear > "+ date +")";

		Vector allDataRetrieved = getSelectQueryResult(query);

		if(allDataRetrieved != null)
		{
			for(int cnt = 0; cnt < allDataRetrieved.size();cnt++)
			{
				Properties nextBookData = (Properties)allDataRetrieved.elementAt(cnt);

				Book book = new Book(nextBookData);

				if(book != null)
				{
					addBook(book);
				}
			}
		}
		else
		{
			//System.out.println("No books newer than " + date + " found in database");
			//throw new Exception("No books newer than " + date + " found in database");
		}
		return books;
	}
//------------------------------------------------------------
	public Vector findBooksWithTitleLike(String title) //throws Exception
	{
		String query = "SELECT * FROM " + myTableName + " WHERE (title LIKE '%" + title + "%')";
		Vector allDataRetrieved = getSelectQueryResult(query);

		if(allDataRetrieved != null)
		{
			for(int cnt = 0; cnt < allDataRetrieved.size();cnt++)
			{
				Properties nextBookData = (Properties)allDataRetrieved.elementAt(cnt);

				Book book = new Book(nextBookData);

				if(book != null)
				{
					addBook(book);
				}
			}
		}
		else
		{
			//System.out.println("No books with title matching " + title + " found in database");
			//throw new Exception("No books with title matching " + title + " found in database");
		}
		return books;
	}
//------------------------------------------------------------
	public Vector findBooksWithAuthorLike(String author) //throws Exception
	{
		String query = "SELECT * FROM " + myTableName + " WHERE (author LIKE '%" + author + "%')";
		Vector allDataRetrieved = getSelectQueryResult(query);

		if(allDataRetrieved != null)
		{
			for(int cnt = 0; cnt < allDataRetrieved.size();cnt++)
			{
				Properties nextBookData = (Properties)allDataRetrieved.elementAt(cnt);

				Book book = new Book(nextBookData);

				if(book != null)
				{
					addBook(book);
				}
			}
		}
		else
		{
			//System.out.println("No books with author matching " + author + " found in database");
			//throw new Exception("No books with author matching " + author + " found in database");
		}

		return books;

	}
    protected void setDependencies()
    {
        dependencies = new Properties();
        myRegistry.setDependencies(dependencies);
    }
/*
	public findBooksWithTitleLike()
	public findBooksWithAuthorLike()*/
	//----------------------------------------------------------------------------------
	private void addBook(Book a)
	{
		//users.add(u);
		books.add(a);
	}
    //----------------------------------------------------------------------------------
    private int findIndexToAdd(Book a)
    {
        //users.add(u);
        int low=0;
        int high = books.size()-1;
        int middle;

        while (low <=high)
        {
            middle = (low+high)/2;

            Book midSession = (Book)books.elementAt(middle);

            int result = Book.compare(a,midSession);

            if (result ==0)
            {
                return middle;
            }
            else if (result<0)
            {
                high=middle-1;
            }
            else
            {
                low=middle+1;
            }


        }
        return low;
    }
	public void printData()
	{
		Enumeration e = books.elements();
		while(e.hasMoreElements())
		{
			Book b = (Book)e.nextElement();
			b.printData();
		}
	}

	//----------------------------------------------------------
	public Object getState(String key)
	{
        if (key.equals("Books"))
            return books;
        else
        if (key.equals("BookList"))
            return this;
        return persistentState.getProperty(key);
        //return null;
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
                case "CreateAndShowView":
                    createAndShowView();
                    break;
            }
			myRegistry.updateSubscribers(key, this);
	}

    //------------------------------------------------------
    protected void createAndShowView()
    {

        View localView = (View)myViews.get("BookCollectionView");

        if (localView == null)
        {
            // create our initial view
            localView = ViewFactory.createView("BookCollectionView", this);

            myViews.put("BookCollectionView", localView);

            // make the view visible by installing it into the frame
            swapToView(localView);
        }
        else
        {
            // make the view visible by installing it into the frame
            swapToView(localView);
        }
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