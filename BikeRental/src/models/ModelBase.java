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
import java.text.MessageFormat;
import java.util.Enumeration;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.Vector;


public abstract class ModelBase extends EntityBase
    implements IView, IModel, ISlideShow {

    protected final String myTableName;
    protected String subTitleText;
    protected String updateStatusMessage = "";
    protected Properties dependencies;
    protected ResourceBundle messages = LocaleStore.getLocale().getResourceBundle();
    MessageFormat formatter = new MessageFormat("");


    protected ModelBase(final String tableName) {
        super(tableName);
        myTableName = tableName;
        mySchema = getSchemaInfo(myTableName);
        setDependencies();
        formatter.setLocale(LocaleStore.getLocale().getLocaleObject());
    }

    public Properties getProperties() {
        return persistentState;
    }

    protected void initFromQuery(final String id) throws InvalidPrimaryKeyException {

        String idName = getIdFieldName();
        String query = "SELECT * FROM " + myTableName + " WHERE (" + idName + " = " + id + ")";
        System.err.println(query);

        Vector allDataRetrieved = getSelectQueryResult(query);

        //Must get at least one book
        if (allDataRetrieved != null && allDataRetrieved.size() != 0)
        {
            int size = allDataRetrieved.size();

            //There should be EXACTLY one book retrieved, more than that is an error
            if (size > 1)
            {
                //System.out.println("Multiple patrons matching patronId: " + patronId + " found.");
                Object[] messageArguments = {
                        myTableName,
                        idName,
                        id
                };

                MessageFormat formatter = new MessageFormat("");
                formatter.setLocale(LocaleStore.getLocale().getLocaleObject());

                formatter.applyPattern(messages.getString("multipleEntitiesFoundError"));
                String error = formatter.format(messageArguments);
                throw new InvalidPrimaryKeyException(error);
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
            Object[] messageArguments = {
                    myTableName
            };
            MessageFormat formatter = new MessageFormat("");
            formatter.setLocale(LocaleStore.getLocale().getLocaleObject());

            formatter.applyPattern(messages.getString("entityNotFoundError"));
            String error = formatter.format(messageArguments);
            throw new InvalidPrimaryKeyException(error);
        }
    }

    protected void processInsertion(Properties props)
    {
        setValues(props);
        System.out.println("id key expected - " + getIdFieldName());
        System.out.println("id value  - " + persistentState.getProperty(getIdFieldName()));
        System.out.println("id key expected - ");
        update();
    }

    private void setValues(Properties props)
    {
        Enumeration allKeys = props.propertyNames();
        while(allKeys.hasMoreElements() == true)
        {
            String nextKey = (String)allKeys.nextElement();
            String nextValue = props.getProperty(nextKey);

            System.out.println("[ " + nextKey + " = " + nextValue + " ]");

            if(nextValue != null)
            {
                persistentState.setProperty(nextKey, nextValue);
            }
        }
        if(persistentState.getProperty(getIdFieldName()) == null) {
            System.out.println("there is no id for this object");
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
            /*  this isn't doing anything -mw
            String idField = getIdFieldName();
            System.out.println("pre-local - " + idField);
            idField = LocaleStore.getLocale().getResourceBundle().getString(idField);
            System.out.println("postlocal - " + idField);
            */

            String message = "";
            if(persistentState.getProperty(getIdFieldName()) != null)
            {
                System.out.println("I Am not null");
                boolean flag = checkIfExists(persistentState.getProperty(getIdFieldName()));
                System.out.println(flag); //test
                Object[] messageArguments = {
                        getIdFieldName(),
                        persistentState.getProperty(getIdFieldName())
                };
                if (flag == false)
                {
                    insertPersistentState(mySchema, persistentState);
                    formatter.applyPattern(messages.getString("entityInsertedSuccessfully"));
                    message = formatter.format(messageArguments);
                }
                else {
                    Properties whereClause = new Properties();
                    whereClause.setProperty(getIdFieldName(), persistentState.getProperty(getIdFieldName()));
                    updatePersistentState(mySchema, persistentState, whereClause);
                    formatter.applyPattern(messages.getString("entityUpdatedSuccessfully"));
                    message = formatter.format(messageArguments);
                    //System.out.println("Patron data for patronId: " + persistentState.getProperty("patronID") + " updated successfully in database.");
                }

                updateStatusMessage = message;
            }
            else
            {
                System.out.println("I Am null");
                Integer id = insertAutoIncrementalPersistentState(mySchema, persistentState);
                persistentState.setProperty(getIdFieldName(), "" + id.intValue());

                Object[] messageArguments = {
                        getIdFieldName(),
                        persistentState.getProperty(getIdFieldName())
                };
                formatter.applyPattern(messages.getString("entityInsertedSuccessfully"));
                updateStatusMessage = formatter.format(messageArguments);
            }
        }
        catch (SQLException ex)
        {
            Object[] messageArguments = {
                    myTableName
            };
            MessageFormat formatter = new MessageFormat("");
            formatter.setLocale(LocaleStore.getLocale().getLocaleObject());

            formatter.applyPattern(messages.getString("errorInstallingEntity"));
            updateStatusMessage = formatter.format(messageArguments);
        }
    }

    public void stateChangeRequest(String key, Object value)
    {
        System.err.println("stateChangeRequest\t" + key);
        switch(key)
        {
            case "ProcessInsertion":
                processInsertion((Properties) value);
                break;
            case "ShowDataEntryView":
                createAndShowDataEntryView(false);
                break;
            case "ShowDataEntryViewWithValues":
                createAndShowDataEntryView(true);
                break;
        }
        myRegistry.updateSubscribers(key, this);
    }

    public void createAndShowDataEntryView(Boolean fillValues) {
        String viewName = getViewName();
        View localView = (View) myViews.get(viewName);

        if (localView == null) {
            localView = ViewFactory.createView(viewName, this);

            myViews.put(viewName, localView);
        }
        if (fillValues) {
            System.err.println("pourquoi");
            localView.populateFields(persistentState);
        }
        swapToView(localView);
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
        dependencies.setProperty("ProcessReturn", "UpdateStatusMessage");
        //dependencies.setProperty("ProcessInsertion", "UpdateStatusMessageError");
        dependencies.setProperty("ProcessInsertion", "UpdateStatusMessage");
        //dependencies.setProperty("ProcessReturn", "UpdateStatusMessage");

        myRegistry.setDependencies(dependencies);
    }

    public void updateState(String key, Object value) {
        stateChangeRequest(key, value);
    }

    abstract public String getIdFieldName();
    abstract public String getViewName();
    abstract public boolean checkIfExists(String idToQuery);

    public String getSubTitleText(){ return subTitleText;}

    public String getIdValue(){
        return persistentState.getProperty(this.getIdFieldName());
    }
}
