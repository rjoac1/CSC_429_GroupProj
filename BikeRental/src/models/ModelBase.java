package models;

/**
 * Created by Hippolyte on 3/29/15.
 */

import impres.exception.InvalidPrimaryKeyException;
import impres.impresario.IModel;
import impres.impresario.ISlideShow;
import impres.impresario.IView;
import views.View;
import views.ViewFactory;

import java.sql.SQLException;
import java.util.Enumeration;
import java.util.Properties;
import java.util.Vector;


public abstract class ModelBase extends EntityBase
    implements IView, IModel, ISlideShow {

    protected final String myTableName;
    protected String updateStatusMessage = "";
    protected Properties dependencies;

    ModelBase(final String tableName) {
        super(tableName);
        myTableName = tableName;
        mySchema = getSchemaInfo(myTableName);
    }

    protected void initFromQuery(final String id) throws InvalidPrimaryKeyException {

        String idName = getIdFieldName();
        String query = "SELECT * FROM " + myTableName + " WHERE (" + idName + " = " + id + ")";

        Vector allDataRetrieved = getSelectQueryResult(query);

        //Must get at least one book
        if (allDataRetrieved != null && allDataRetrieved.size() != 0)
        {
            int size = allDataRetrieved.size();

            //There should be EXACTLY one book retrieved, more than that is an error
            if (size > 1)
            {
                //System.out.println("Multiple patrons matching patronId: " + patronId + " found.");
                throw new InvalidPrimaryKeyException("Multiple " + myTableName + "'s matching " + idName + ": " + id + " found.");
            }
            else if (size == 1)
            {
                //copy all the retrieved data into persistant state
                Properties retrievedData = (Properties)allDataRetrieved.elementAt(0);
                persistentState = new Properties();

                Enumeration allKeys = retrievedData.propertyNames();
                while (allKeys.hasMoreElements() == true)
                {
                    String nextKey = (String)allKeys.nextElement();
                    String nextValue = retrievedData.getProperty(nextKey);

                    if (nextValue != null)
                    {
                        persistentState.setProperty(nextKey, nextValue);
                    }
                }
            }
        }
        else
        {
            throw new InvalidPrimaryKeyException("userNotFound");
        }
    }

    protected void processInsertion(Properties props)
    {
        setValues(props);
        update();
    }

    private void setValues(Properties props)
    {
        Enumeration allKeys = props.propertyNames();
        while(allKeys.hasMoreElements() == true)
        {
            String nextKey = (String)allKeys.nextElement();
            String nextValue = props.getProperty(nextKey);

            if(nextValue != null)
            {
                persistentState.setProperty(nextKey, nextValue);
            }
        }
    }

    protected void update()
    {
        updateStateInDatabase();
    }

    private void updateStateInDatabase()
    {
        try
        {
            String idField = getIdFieldName();
            System.out.println(idField);//test
            if(persistentState.getProperty(idField) != null)
            {
                boolean flag = checkIfExists(persistentState.getProperty(idField));
                if (flag == false)
                {
                    insertPersistentState(mySchema, persistentState);
                }
                else {
                    Properties whereClause = new Properties();
                    whereClause.setProperty(idField, persistentState.getProperty(idField));
                    updatePersistentState(mySchema, persistentState, whereClause);
                    //System.out.println("Patron data for patronId: " + persistentState.getProperty("patronID") + " updated successfully in database.");
                }
                updateStatusMessage = myTableName + " data for " + idField + ": " + persistentState.getProperty(idField) + " updated successfully in database.";
            }
            else
            {
                Integer id = insertAutoIncrementalPersistentState(mySchema, persistentState);
                persistentState.setProperty(idField, "" + id.intValue());
                updateStatusMessage = myTableName + " data for new " + myTableName + ": " + persistentState.getProperty(idField) + " installed successfully in database.";
            }
        }
        catch (SQLException ex)
        {
            updateStatusMessage = "Error in installing " + myTableName + " data in database!";
        }
    }

    public void stateChangeRequest(String key, Object value)
    {
        switch(key)
        {
            case "ProcessInsertion":
                processInsertion((Properties) value);
                break;
            case "ShowDataEntryView":
                createAndShowDataEntryView();
                break;
        }
        myRegistry.updateSubscribers(key, this);
    }

    public void createAndShowDataEntryView()
    {
        String viewName = getViewName();
        View localView = (View)myViews.get(viewName);

        if(localView == null)
        {
            localView = ViewFactory.createView(viewName, this);

            myViews.put(viewName, localView);

            swapToView(localView);
        }
        else
        {
            swapToView(localView);
        }
    }

    public Object getState(String key)
    {
        if (key.equals("UpdateStatusMessage") == true)
        {
            return updateStatusMessage;
        }
        return persistentState.getProperty(key);
    }

    protected void initializeSchema(String tableName)
    {
        if (mySchema == null)
        {
            mySchema = getSchemaInfo(tableName);
        }
    }

    protected void setDependencies()
    {
        dependencies = new Properties();
        dependencies.setProperty("Done", "EndTransaction");
        dependencies.setProperty("ProcessInsertion", "UpdateStatusMessage");

        myRegistry.setDependencies(dependencies);
    }

    public void updateState(String key, Object value) {
        stateChangeRequest(key, value);
    }

    abstract public String getIdFieldName();
    abstract public String getViewName();
    abstract public boolean checkIfExists(String idToQuery);
}
