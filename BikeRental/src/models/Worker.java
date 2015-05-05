package models;

//system imports
import java.util.Properties;

//project imports
import impres.exception.InvalidPrimaryKeyException;
import impres.exception.PasswordMismatchException;


//=========================================
public class Worker extends ModelBase
{
    private static final String myTableName = "Worker";	//name of database table

    //Constructor
    //-----------------------------------------------------
    public Worker() {
        super(myTableName);
    }

    public Worker(final String workerId) throws InvalidPrimaryKeyException {
        super(myTableName);
        initFromQuery(workerId);
    }

    //Constructor for logging in worker.
    //----------------------------------------------------------------------
    public Worker(final Properties props)
            throws InvalidPrimaryKeyException, PasswordMismatchException {
        super(myTableName);

        final String idToQuery = props.getProperty("ID");
        initFromQuery(idToQuery);

        //Verify Password for login
        final String password = props.getProperty("Password");
        final String accountPassword = persistentState.getProperty("password");
        if (accountPassword != null) {
            boolean passwordCheck = accountPassword.equals(password);
            if (!passwordCheck) {
                throw new PasswordMismatchException(messages.getString("passwordMismatchError"));
            }
        }
        else
        {
            throw new PasswordMismatchException(messages.getString("passwordMissing"));
        }
    }

    public String getIdFieldName() {
        return "workerId";
    }

    public String getViewName() {
        return "WorkerView";
    }
}

