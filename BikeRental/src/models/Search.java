package models;

import impres.exception.InvalidPrimaryKeyException;
import views.DateLabelFormatter;

import java.util.Enumeration;
import java.util.Properties;
import java.util.Vector;

/**
 * Created by Maximus on 4/30/2015.
 */
public class Search extends ModelBase{
    private static final String myTableName = "";
    private String searchType;

    public Search()
    {
        super(myTableName);

        setDependencies();
        persistentState = new Properties();
    }

    public Search(String typeOfSearch)throws InvalidPrimaryKeyException {
        super(myTableName);
        searchType = typeOfSearch;
    }


    public String getIdFieldName()
    {
        return "";
    }

    @Override
    public Object getState(String key) {

        return persistentState.getProperty(key);
    }

    public String getViewName(){ return "Rental" + searchType + "View"; }

    public boolean checkIfExists(String id){ return true; }

}