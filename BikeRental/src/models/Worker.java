package models;

//system imports
import java.util.Vector;
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
    public Worker()
    {
        super(myTableName);
        setDependencies();
    }

    public Worker(String workerId) throws InvalidPrimaryKeyException {
        super(myTableName);
        initFromQuery(workerId);
    }

    //Constructor for logging in worker.
    //----------------------------------------------------------------------
    public Worker(Properties props) throws InvalidPrimaryKeyException, PasswordMismatchException
    {
        super(myTableName);

        String idToQuery = props.getProperty("ID");
        initFromQuery(idToQuery);

        //Verify Password for login
        String password = props.getProperty("Password");
        String accountPassword = persistentState.getProperty("password");
        if (accountPassword != null) {
            boolean passwordCheck = accountPassword.equals(password);
            if (!passwordCheck) {
                throw new PasswordMismatchException(messages.getString("passwordMismatchError"));
            }
        }
        else {
            throw new PasswordMismatchException(messages.getString("passwordMissing"));
        }
    }

    public Boolean isAdmin() {
        return persistentState.getProperty("credential").equals("administrator");
    }

    public Vector getEntryListView() {
        Vector<String> v = new Vector<>();

        v.addElement(persistentState.getProperty("workerId"));
        v.addElement(persistentState.getProperty("firstName"));
        v.addElement(persistentState.getProperty("lastName"));
        v.addElement(persistentState.getProperty("emailAddress"));
        v.addElement(persistentState.getProperty("credential"));
        v.addElement(persistentState.getProperty("dateOfInitialReg"));
        v.addElement(persistentState.getProperty("notes"));
        v.addElement(persistentState.getProperty("status"));
        v.addElement(persistentState.getProperty("dateStatusUpdated"));
        return v;
    }

    public String getIdFieldName()
    {
        return "workerId";
    }
    public String getViewName(){ return "WorkerView"; }
    public boolean checkIfExists(String id) {
        try {
            new Worker(id);
            return true;
        }
        catch (InvalidPrimaryKeyException ex) {
            //System.out.println(ex.getMessage()); //test
            return false;
        }
    }
}

