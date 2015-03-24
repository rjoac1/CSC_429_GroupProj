package models;

/**
 * Created by Hippolyte on 3/17/15.
 */

import impres.exception.InvalidPrimaryKeyException;
import java.util.Properties;


public class Rental extends ModelBase {

    private static final String mTableName = "rentals";
    private int id;

    public Rental(final int id) throws InvalidPrimaryKeyException {
        super(mTableName);
        super.initFromId(id);
    }

    public Rental(Properties datas) {
        super(mTableName);
        // TODO: maybe add a check on data validity here ?
        mProperties = datas;
    }

    public int getId() {
        return Integer.parseInt(mProperties.getProperty("id"));
    }

    public String getDate() {
        return mProperties.getProperty("date");
    }

    public void setDate(String date) {
        mProperties.setProperty("date", date);
    }

    public int getUserId() {
        return Integer.parseInt(mProperties.getProperty("userId"));
    }

    public void setUserId(int userId) {
        mProperties.setProperty("userId", Integer.toString(userId));
    }

    public void setBicycleId(int id) {
        mProperties.setProperty("bicycleId", Integer.toString(id));
    }

    public int getBicycleId() {
        return Integer.parseInt(mProperties.getProperty("bicycleId"));
    }

    public String getStatus() {
        return mProperties.getProperty("status");
    }

    public void setStatus(String status) {
        mProperties.setProperty("status", status);
    }

}
