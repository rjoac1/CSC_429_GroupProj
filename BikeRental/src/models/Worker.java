package models;

//system imports
import java.sql.SQLException;
import java.util.Vector;
import java.util.Properties;
import java.util.Enumeration;

//project imports
import impres.exception.InvalidPrimaryKeyException;
import impres.exception.PasswordMismatchException;

import impres.impresario.*;

import views.View;
import views.ViewFactory;

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
        if (accountPassword != null)
        {
            boolean passwordCheck = accountPassword.equals(password);
            if (passwordCheck == false)
            {
                throw new PasswordMismatchException("passwordMismatch");
            }
        }
        else
        {
            throw new PasswordMismatchException("passwordMissing");
        }
    }

    public Vector getEntryListView()
    {
        Vector v = new Vector();

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
    public boolean checkIfExists(String id)
    {
        try {
            Worker worker = new Worker(id);
            return true;
        }
        catch (InvalidPrimaryKeyException ex) {
            System.out.println(ex.getMessage());
            return false;
        }
    }
}

