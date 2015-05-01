// specify the package
package models;

// system imports
import java.util.Properties;

// project imports
import impres.exception.InvalidPrimaryKeyException;


public class User extends ModelBase{
    private static final String myTableName = "User";

    public User() {
        super(myTableName);
//        persistentState = new Properties();
    }

    public User(final String userID)throws InvalidPrimaryKeyException {
        super(myTableName);
        initFromQuery(userID);
    }

    //----------------------------------------------------------
    public User(final Properties props) {
        super(myTableName, props);
    }

    public String getIdFieldName() {
        return "userId";
    }

    public String getViewName() {
        return "UserView";
    }
}