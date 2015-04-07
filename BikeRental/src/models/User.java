// specify the package
package models;

// system imports
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.Properties;
import java.util.Vector;

// project imports
import impres.exception.InvalidPrimaryKeyException;

import impres.impresario.IView;
import views.View;
import views.ViewFactory;


public class User extends EntityBase implements IView{
    private static final String myTableName = "User";
    private String updateStatusMessage = "";
    protected Properties dependencies;

    public User()
    {
        super(myTableName);

        setDependencies();
        persistentState = new Properties();
    }

    public User(String userID)throws InvalidPrimaryKeyException{
        super(myTableName);
        setDependencies();
        String query = "SELECT * FROM " + myTableName + " WHERE `userID` =" + userID;

        Vector allDataRetrieved = getSelectQueryResult(query);

        // You must get one user at least
        if (allDataRetrieved != null)
        {
            int size = allDataRetrieved.size();

            // There should be EXACTLY one user. More than that is an error
            if (size != 1)
            {
                throw new InvalidPrimaryKeyException("Multiple Users matching id : "
                        + userID + " found.");
            }
            else
            {
                // copy all the retrieved data into persistent state
                Properties retrievedUserData = (Properties)allDataRetrieved.elementAt(0);
                persistentState = new Properties();

                Enumeration allKeys = retrievedUserData.propertyNames();
                while (allKeys.hasMoreElements() == true)
                {
                    String nextKey = (String)allKeys.nextElement();
                    String nextValue = retrievedUserData.getProperty(nextKey);

                    if (nextValue != null)
                    {
                        persistentState.setProperty(nextKey, nextValue);
                    }
                }

            }
        }
        // If no user found for this user name, throw an exception
        else
        {
            throw new InvalidPrimaryKeyException("No user matching id : "
                    + userID + " found.");
        }

    }
    //----------------------------------------------------------
    public User(Properties props)
    {
        super(myTableName);

        setDependencies();
        persistentState = new Properties();
        Enumeration allKeys = props.propertyNames();
        while (allKeys.hasMoreElements() == true)
        {
            String nextKey = (String)allKeys.nextElement();
            String nextValue = props.getProperty(nextKey);

            if (nextValue != null)
            {
                persistentState.setProperty(nextKey, nextValue);
            }
        }
    }
    private void insert()
    {
        //String query = "INSERT INTO" + myTableName + " WHERE (userID = " + userID + ")";

    }
    //-----------------------------------------------------------------------------------

    private void setDependencies()
    {
        dependencies = new Properties();
        dependencies.setProperty("Done", "EndTransaction");
        dependencies.setProperty("ProcessInsertion", "UpdateStatusMessage");

        myRegistry.setDependencies(dependencies);
    }
    //----------------------------------------------------------
    public Object getState(String key)
    {
        if (key.equals("UpdateStatusMessage") == true)
            return updateStatusMessage;

        return persistentState.getProperty(key);
    }

    //----------------------------------------------------------------
    public void stateChangeRequest(String key, Object value)
    {
        //STEP 4: Write the sCR method component for the key you
        // just set up dependencies for
        // DEBUG System.out.println("Teller.sCR: key = " + key);
        if (key.equals("ShowDataEntryView") == true)
        {
            createAndShowUserView();
        }
        if (key.equals("ProcessUser") == true)
        {
            persistentState = (Properties)value;
            update();
        }
        myRegistry.updateSubscribers(key, this);
    }

    /** Called via the IView relationship */
    //----------------------------------------------------------
    public void updateState(String key, Object value)
    {
        stateChangeRequest(key, value);
    }


    //-----------------------------------------------------------------------------------
    public static int compare(User a, User b)
    {
        String aNum = (String)a.getState("userID");
        String bNum = (String)b.getState("userID");

        return aNum.compareTo(bNum);
    }

    //-----------------------------------------------------------------------------------
    public void update()
    {
        //System.out.println(getEntryListView());
        updateStateInDatabase();
    }

    //-----------------------------------------------------------------------------------
    private void updateStateInDatabase()
    {
        try
        {
            if (persistentState.getProperty("userID") != null)
            {
                Properties whereClause = new Properties();
                whereClause.setProperty("lastName",
                        persistentState.getProperty("lastName")); //Double check
                updatePersistentState(mySchema, persistentState, whereClause);
                updateStatusMessage = "User data for user number : " + persistentState.getProperty("userID") + " updated successfully in database!";
            }
            else
            {
                System.out.println("test");
                Integer userNumber = insertAutoIncrementalPersistentState(mySchema, persistentState);
                persistentState.setProperty("userID", "" + userNumber.intValue());
                updateStatusMessage = "User data for new user : " +  persistentState.getProperty("userID")
                        + "installed successfully in database!";
            }
        }
        catch (SQLException ex)
        {
            updateStatusMessage = "Error in installing user data in database!";
        }
        //DEBUG System.out.println("updateStateInDatabase " + updateStatusMessage);
    }


    /**
     * This method is needed solely to enable the User information to be displayable in a table
     *
     */
    //--------------------------------------------------------------------------
    public Vector getEntryListView()
    {
        Vector v = new Vector();
        v.addElement(persistentState.getProperty("userID"));
        v.addElement(persistentState.getProperty("firstName"));
        v.addElement(persistentState.getProperty("lastName"));
        v.addElement(persistentState.getProperty("phoneNumber"));
        v.addElement(persistentState.getProperty("emailAddress"));
        v.addElement(persistentState.getProperty("userType"));
        v.addElement(persistentState.getProperty("dateOfMembershipExpired"));
        v.addElement(persistentState.getProperty("dateOfMembershipReg"));
        v.addElement(persistentState.getProperty("status"));
        v.addElement(persistentState.getProperty("dateStatusUpdated"));
        v.addElement(persistentState.getProperty("notes"));
        return v;
    }

    //-----------------------------------------------------------------------------------
    protected void initializeSchema(String tableName)
    {
        if (mySchema == null)
        {
            mySchema = getSchemaInfo(tableName);
        }
    }
    private void createAndShowUserView()
    {
        View localView = (View)myViews.get("UserView");

        if (localView == null)
        {
            // create our initial view
            localView = ViewFactory.createView("UserView", this); // USE VIEW FACTORY

            myViews.put("UserView", localView);
            swapToView(localView);
            // make the view visible by installing it into the frame
            myFrame.getContentPane().add(localView); // just the main panel in this case
            myFrame.pack();
        }
        else
        {
            swapToView(localView);
        }
    }
}