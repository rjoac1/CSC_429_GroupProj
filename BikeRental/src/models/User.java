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


public class User extends ModelBase{
    private static final String myTableName = "User";
    private String updateStatusMessage = "";
    protected Properties dependencies;

    public User() {
        super(myTableName);
        subTitleText = "InsertUsers";
        persistentState = new Properties();
    }

    public User(String userID)throws InvalidPrimaryKeyException{
        super(myTableName);
        subTitleText = "ModifyUsers";
        initFromQuery(userID);
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

    public Vector getEntryListView()
    {
        Vector v = new Vector();
        v.addElement(persistentState.getProperty("userID"));
        v.addElement(persistentState.getProperty("firstName"));
        v.addElement(persistentState.getProperty("lastName"));
        v.addElement(persistentState.getProperty("phoneNumber"));
        v.addElement(persistentState.getProperty("emailAdress"));
        v.addElement(persistentState.getProperty("userType"));
        v.addElement(persistentState.getProperty("dateOfMembershipExpired"));
        v.addElement(persistentState.getProperty("dateOfMembershipReg"));
        v.addElement(persistentState.getProperty("status"));
        v.addElement(persistentState.getProperty("dateStatusUpdated"));
        v.addElement(persistentState.getProperty("notes"));
        return v;
    }

    public String getIdFieldName()
    {
        return "userId";
    }

    public String getViewName(){
        if(subTitleText == "InsertUsers")
            return "NewUserView";
        else if(subTitleText == "ModifyUsers")
            return "ModifyUserView";
        else { return ""; }
    }
    public boolean checkIfExists(String id)
    {
        try {
            User user = new User(id);
            return true;
        }


        catch (InvalidPrimaryKeyException ex) {
            //System.out.println(ex.getMessage()); //test
            return false;
        }
    }
}
