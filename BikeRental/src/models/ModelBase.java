package models;

/**
 * Created by Hippolyte on 3/29/15.
 */

import impres.exception.InvalidPrimaryKeyException;
import impres.impresario.IView;

import java.sql.SQLException;
import java.util.Properties;
import java.util.Vector;


public abstract class ModelBase extends EntityBase
    implements IView {

    private final String mTableName;
    private final Properties mSchema;

    ModelBase(final String tableName) {
        super(tableName);
        mTableName = tableName;
        mSchema = getSchemaInfo(mTableName);
    }

    void initFromQuery(final String query) throws InvalidPrimaryKeyException {

        final Vector<Properties> elem = getSelectQueryResult(query);
        if (elem == null || elem.size() == 0) {
            throw new InvalidPrimaryKeyException("No elem found");
        }
        if (elem.size() != 1) {
            throw new InvalidPrimaryKeyException("Multiple entry matching id found.");
        }

        persistentState = elem.get(0);
    }

    abstract public int getId();

    public void update() throws SQLException {
        final Properties whereClause = new Properties();
        whereClause.setProperty("id", Integer.toString(getId()));
        updatePersistentState(mSchema, persistentState, whereClause);
    }

    public int insert() throws SQLException {
        System.err.println(persistentState);
        final int id = insertAutoIncrementalPersistentState(mSchema, persistentState);
        persistentState.setProperty("bookId", Integer.toString(id));
        return id;
    }

    /** Called via the IView relationship */
    //----------------------------------------------------------
    public void updateState(String key, Object value) {
        stateChangeRequest(key, value);
    }

}
