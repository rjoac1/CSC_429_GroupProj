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


public class User extends ModelBase implements IView {

    private static final String myTableName = "Users";

    public User()
    {
        super(myTableName);
        setDependencies();
        persistentState = new Properties();
    }

    public User(String userID)throws InvalidPrimaryKeyException{
        super(myTableName);
        String query = "SELECT * FROM " + myTableName + " WHERE `userID` =" + userID;
        initFromQuery(query);

    }

    public User(Properties props)
    {
        super(myTableName);
        persistentState = (Properties)props.clone();
    }

    @Override
    public String getIdFieldName() {
        return "userID";
    }

    @Override
    public void stateChangeRequest(String key, Object value)
    {
        //STEP 4: Write the sCR method component for the key you
        // just set up dependencies for
        // DEBUG System.out.println("Teller.sCR: key = " + key);
        if (key.equals("ShowUser") == true)
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

    /**
     * Verify ownership
     */
    public boolean verifyOwnership(AccountHolder cust)
    {
        if (cust == null)
        {
            return false;
        }
        else
        {
            String custid = (String)cust.getState("ID");
            String myOwnerid = (String)getState("OwnerId");

            return (custid.equals(myOwnerid));
        }
    }

    public static int compare(User a, User b)
    {
        String aNum = (String)a.getState("userID");
        String bNum = (String)b.getState("userID");

        return aNum.compareTo(bNum);
    }

    private void createAndShowUserView() {
        View localView = (View)myViews.get("UserView");

        if (localView == null) {
            // create our initial view
            localView = ViewFactory.createView("UserView", this); // USE VIEW FACTORY

            myViews.put("UserView", localView);
            swapToView(localView);
            // make the view visible by installing it into the frame
            myFrame.getContentPane().add(localView); // just the main panel in this case
            myFrame.pack();
        }
        else {
            swapToView(localView);
        }
    }
}
