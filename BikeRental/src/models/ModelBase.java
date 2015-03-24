package models;

/**
 * Created by Hippolyte on 3/17/15.
 */

import impres.database.Persistable;
import impres.exception.InvalidPrimaryKeyException;

import java.sql.SQLException;
import java.util.Properties;
import java.util.Vector;


public abstract class ModelBase extends Persistable {

    private final String mTableName;
    private final Properties mSchema;
    protected Properties mProperties;

    ModelBase(final String tableName) {
        mTableName = tableName;
        mSchema = getSchemaInfo(mTableName);
    }

    void initFromId(int id) throws InvalidPrimaryKeyException {
        final String query = "SELECT * FROM " + mTableName + " WHERE id= " + id + ";";
        initFromQuery(query);
    }

    void initFromQuery(final String query) throws InvalidPrimaryKeyException {

        final Vector<Properties> elem = getSelectQueryResult(query);
        if (elem == null || elem.size() == 0) {
            throw new InvalidPrimaryKeyException("No elem found");
        }
        if (elem.size() != 1) {
            throw new InvalidPrimaryKeyException("Multiple entry matching id found.");
        }

        mProperties = elem.get(0);
    }

    abstract public int getId();

    public void update() throws SQLException {
        final Properties whereClause = new Properties();
        whereClause.setProperty("id", Integer.toString(getId()));
        updatePersistentState(mSchema, mProperties, whereClause);
    }

    public int insert() throws SQLException {
        System.err.println(mProperties);
        final int id = insertAutoIncrementalPersistentState(mSchema, mProperties);
        mProperties.setProperty("bookId", Integer.toString(id));
        return id;
    }

}
