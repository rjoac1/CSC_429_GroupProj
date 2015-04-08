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

    public User()
    {
        super(myTableName);

        setDependencies();
        persistentState = new Properties();
    }

    public User(String userID)throws InvalidPrimaryKeyException{
        super(myTableName);
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


    public String getIdFieldName()
    {
        return "userId";
    }
    public String getViewName(){ return "UserView"; }
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