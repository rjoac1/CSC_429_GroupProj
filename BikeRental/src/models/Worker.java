package models;

//system imports
import java.sql.SQLException;
import java.util.Vector;
import java.util.Properties;
import java.util.Enumeration;

//project imports
import com.sun.tools.internal.ws.processor.model.Model;
import impres.exception.InvalidPrimaryKeyException;
import impres.exception.PasswordMismatchException;

import impres.impresario.*;

import views.View;
import views.ViewFactory;


public class Worker extends ModelBase implements IView, IModel, ISlideShow
{

    private static final String myTableName = "Worker";	//name of database table


    public Worker()
    {
        super(myTableName);
        setDependencies();
    }

    public Worker(String workerId) throws InvalidPrimaryKeyException
    {
        super(myTableName);

        String query = "SELECT * FROM " + myTableName + " WHERE (workerId = "
                + workerId + ")";
        initFromQuery(query);
    }

    public Worker(Properties props) throws InvalidPrimaryKeyException, PasswordMismatchException {
        super(myTableName);

        String idToQuery = props.getProperty("ID");
        String query = "SELECT * FROM " + myTableName + " WHERE (workerId = "
                + idToQuery + ")";
        initFromQuery(query);

        String password = props.getProperty("Password");
        String accountPassword = persistentState.getProperty("password");
        if (accountPassword == null || !accountPassword.equals(password))
            throw new PasswordMismatchException("Password mismatch");
    }

    @Override
    protected String getIdFieldName() {
        return "workerId";
    }

    @Override
    public void stateChangeRequest(String key, Object value) {
        switch(key) {
            case "ProcessInsertion":
                processInsertion((Properties) value);
                break;
            case "ShowDataEntryView":
                createAndShowDataEntryView();
                break;
        }
        myRegistry.updateSubscribers(key, this);
    }

    public void createAndShowDataEntryView() {
        View localView = (View)myViews.get("WorkerView");

        if (localView == null) {
            localView = ViewFactory.createView("WorkerView", this);
            myViews.put("WorkerView", localView);
            swapToView(localView);
        } else {
            swapToView(localView);
        }
    }


}

