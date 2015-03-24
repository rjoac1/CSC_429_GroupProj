package models;

/**
 * Created by Hippolyte on 3/17/15.
 */

import impres.exception.InvalidPrimaryKeyException;
import java.util.ArrayList;

import java.util.Properties;

public class User extends ModelBase {
    private static final String mTableName = "users";
    private int id;

    public User(final int id) throws InvalidPrimaryKeyException {
        super(mTableName);
        super.initFromId(id);
    }

    public User(Properties datas) {
        super(mTableName);
        // TODO: maybe add a check on data validity here ?
        mProperties = datas;
    }

    public int getId() {
        return Integer.parseInt(mProperties.getProperty("id"));
    }

    public String getName() {
        return mProperties.getProperty("name");
    }

    public void setName(String name) {
        mProperties.setProperty("name", name);
    }

    public String getBannerId() {
        return mProperties.getProperty("bannerId");
    }

    public void setBannerId(String bannerId) {
        mProperties.setProperty("bannerId", bannerId);
    }

    public String getFirstName() {
        return mProperties.getProperty("firstName");
    }

    public void setFirstName(String firstName) {
        mProperties.setProperty("firstName", firstName);
    }

    public String getPhoneNumber() {
        return mProperties.getProperty("phoneNumber");
    }

    public void setPhoneNumber(String phoneNumber) {
        mProperties.setProperty("phoneNumber", phoneNumber);
    }

    public void setEmailAddress(String emailAddress) {
        mProperties.setProperty("emailAddress", emailAddress);
    }

    public String getEmailAddress() {
        return mProperties.getProperty("emailAddress");
    }

    // TODO: La ca devient complique, parce que je dois avoir du vrais relationnal mapping entre mes objects.
    //  Je ne sais pas encore vraiment comment faire ca pour l'instant
    // FIXME
    public ArrayList<Rental> getRentals() {
        return null;
    }
}
