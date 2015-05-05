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

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.Enumeration;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.Vector;
import java.util.stream.Collectors;


public abstract class ModelBase extends EntityBase
    implements IView, IModel, ISlideShow {

    protected final String myTableName;
    protected String updateStatusMessage = "";
    protected Properties dependencies;
    protected ResourceBundle messages = LocaleStore.getLocale().getResourceBundle();

    abstract public String getIdFieldName();
    abstract public String getViewName();

    protected ModelBase(final String tableName) {
        super(tableName);
        myTableName = tableName;
        mySchema = getSchemaInfo(myTableName);
        setDependencies();
    }

    protected ModelBase(final String tableName, final Properties datas) {
        this(tableName);
        persistentState = (Properties)datas.clone();
    }

    public Properties getProperties() {
        return persistentState;
    }

    public Boolean checkIfExists(String id) {
        try {
            this.getClass().getConstructor(String.class).newInstance(id);
            return true;
        } catch (InvocationTargetException e) {
            return false;
        } catch (InstantiationException e) {
            return false;
        } catch (NoSuchMethodException e) {
            return false;
        } catch (IllegalAccessException e) {
            return false;
        }
    }

    protected void initFromQuery(final String id) throws InvalidPrimaryKeyException {

        String idName = getIdFieldName();
        String query = "SELECT * FROM " + myTableName + " WHERE (" + idName + " = " + id + ")";

        Vector<Properties> allDataRetrieved = getSelectQueryResult(query);
        if (allDataRetrieved == null || allDataRetrieved.size() != 1) {
            throw new InvalidPrimaryKeyException(makeMessage("entityNotFoundError", myTableName));
        }
        else if (allDataRetrieved.size() > 1) {
            throw new InvalidPrimaryKeyException(makeMessage("multipleEntitiesFoundError", myTableName, idName, id));
        }
        else {
            persistentState = (Properties)allDataRetrieved.elementAt(0).clone();
        }
    }

    protected void processInsertion(Properties props) {
        setValues(props);
        update();
    }

    private void setValues(Properties props) {
        Enumeration allKeys = props.propertyNames();
        while (allKeys.hasMoreElements()) {
            String nextKey = (String)allKeys.nextElement();
            String nextValue = props.getProperty(nextKey);

            if (nextValue != null)
                persistentState.setProperty(nextKey, nextValue);
        }
    }

    protected void update() {
        updateStateInDatabase();
    }

    protected String makeMessage(final String format, final String... args) {
        MessageFormat formatter = new MessageFormat("");
        formatter.applyPattern(messages.getString(format));
        return formatter.format(args);
    }

    private void updateStateInDatabase() {
        try {
            String idField = getIdFieldName();
            if (persistentState.getProperty(idField) != null) {
                boolean flag = checkIfExists(persistentState.getProperty(idField));
                String[] messageArguments = {myTableName, idField, persistentState.getProperty(idField)};
                if (flag) {
                    insertPersistentState(mySchema, persistentState);
                    updateStatusMessage = makeMessage("entityInsertedSuccessfully", messageArguments);
                }
                else {
                    Properties whereClause = new Properties();
                    whereClause.setProperty(idField, persistentState.getProperty(idField));
                    updatePersistentState(mySchema, persistentState, whereClause);
                    updateStatusMessage = makeMessage("entityUpdatedSuccessfully", messageArguments);
                }
            }
            else {
                Integer id = insertAutoIncrementalPersistentState(mySchema, persistentState);
                persistentState.setProperty(idField, id.toString());
                updateStatusMessage = makeMessage("entityInsertedSuccessfully",
                        myTableName, idField, persistentState.getProperty(idField));
            }
        }
        catch (SQLException ex) {
            updateStatusMessage = makeMessage("errorInstallingEntity", myTableName);
        }
    }

    public void stateChangeRequest(String key, Object value) {
        switch (key) {
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
        String viewName = getViewName();
        View localView = myViews.get(viewName);

        if (localView == null) {
            localView = ViewFactory.createView(viewName, this);
            myViews.put(viewName, localView);
            swapToView(localView);
        }
        else
            swapToView(localView);
    }

    public Object getState(String key) {
        if (key.equals("UpdateStatusMessage")) {
            return updateStatusMessage;
        }
        return persistentState.getProperty(key);
    }

    protected void initializeSchema(String tableName) {
        if (mySchema == null) {
            mySchema = getSchemaInfo(tableName);
        }
    }

    protected void setDependencies() {
        dependencies = new Properties();
        dependencies.setProperty("Done", "EndTransaction");
        dependencies.setProperty("ProcessInsertion", "UpdateStatusMessageError");
        dependencies.setProperty("ProcessInsertion", "UpdateStatusMessage");
        dependencies.setProperty("ProcessReturn", "UpdateStatusMessage");
        myRegistry.setDependencies(dependencies);
    }

    public Vector<String> getEntryListView() {
        return new Vector<>(persistentState.stringPropertyNames().stream()
                        .map(persistentState::getProperty)
                        .collect(Collectors.toList())
        );
    }

    public void updateState(String key, Object value) {
        stateChangeRequest(key, value);
    }

}
