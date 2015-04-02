package models;

/**
 * Created by Hippolyte on 3/29/15.
 */

import impres.exception.InvalidPrimaryKeyException;

import impres.impresario.IView;

import java.sql.SQLException;
import java.util.Properties;
import java.util.Vector;
import java.util.Collections;


public abstract class ModelBase extends EntityBase
    implements IView {

    private final String mTableName;
    private final Properties mSchema;
    protected String updateStatusMessage = "";

    protected ModelBase(final String tableName) {
        super(tableName);
        mTableName = tableName;
        mSchema = getSchemaInfo(mTableName);
        setDependencies();
    }

    @Override
    protected void initializeSchema(String tableName) {
        if (mySchema == null)
            mySchema = getSchemaInfo(tableName);
    }

    @Override
    public Object getState(String key) {
        if (key.equals("UpdateStatusMessage") == true) {
            return updateStatusMessage;
        }
        return persistentState.getProperty(key);
    }

    protected void initFromQuery(final String query) throws InvalidPrimaryKeyException {

        final Vector<Properties> elem = getSelectQueryResult(query);
        if (elem == null || elem.size() == 0) {
            throw new InvalidPrimaryKeyException("No elem found");
        }
        if (elem.size() != 1) {
            throw new InvalidPrimaryKeyException("Multiple entry matching found.");
        }

        persistentState = (Properties)elem.get(0).clone();
    }

    public String getId() {
        return persistentState.getProperty(getIdFieldName());
    }

    public void setValues(Properties p) {
        for (Object k : Collections.list(persistentState.keys())) {
            persistentState.setProperty((String)k, p.getProperty((String)k));
        }
    }

    public Vector<String> getEntryListView() {
        Vector<String> value = new Vector<>();
        for (Object k : Collections.list(persistentState.keys())) {
            value.addElement(persistentState.getProperty((String)k));
        }
        return value;
    }

    public void printData() {
        for (Object k : Collections.list(persistentState.keys())) {
            System.out.println("" + (String)k + ": " + persistentState.getProperty((String)k));
        }
    }

    protected void processInsertion(Properties props) {
        setValues(props);
        update();
    }


    protected void setDependencies()
    {
        Properties value = new Properties();
        value .setProperty("Done", "End");
        value .setProperty("ProcessInsertion", "UpdateStatusMessage");
        myRegistry.setDependencies(value);
    }

    abstract protected String getIdFieldName();

    /** Called via the IView relationship */
    //----------------------------------------------------------
    public void updateState(String key, Object value) {
        stateChangeRequest(key, value);
    }

    public String getClassName() {
        return this.getClass().getSimpleName();
    }

    public void update() {
        System.out.println("Mais heu");
        try {
            final Properties whereClause = new Properties();
            whereClause.setProperty(getIdFieldName(), getId());
            updatePersistentState(mSchema, persistentState, whereClause);
            updateStatusMessage = getClassName() + " data for : "
                    +  persistentState.getProperty("userID")
                    + " updated successfully in database!";
        } catch (SQLException ex) {
            updateStatusMessage = "Error in installing data in database!";
        }
    }

    public int insert() {
        try {
            final int id = insertAutoIncrementalPersistentState(mSchema, persistentState);
            persistentState.setProperty(getIdFieldName(), Integer.toString(id));
            updateStatusMessage = getClassName() + " data for : "
                    +  persistentState.getProperty("userID")
                    + " installed successfully in database!";
            return id;
        } catch (SQLException ex) {
            updateStatusMessage = "Error in installing data in database!";
            return -1;
        }
    }

    public void subscribe(String key, IView subscriber)
    {
        myRegistry.subscribe(key, subscriber);
    }

    public void unSubscribe(String key, IView subscriber)
    {
        myRegistry.unSubscribe(key, subscriber);
    }


}
