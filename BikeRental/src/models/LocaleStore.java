package models;

import impres.common.PropertyFile;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Created by Hippolyte on 3/31/15.
 */
public class LocaleStore {

    private final Locale mLocale;
    private final ResourceBundle mResourceBundle;
    private final String lang;
    private final String country;

    static private LocaleStore mStaticInstance = new LocaleStore();

    private LocaleStore() {
        PropertyFile mProps = new PropertyFile("userConfig.ini");
        country = mProps.getProperty("country");
        lang = mProps.getProperty("language");

        mLocale = new Locale(lang, country);
        Locale.setDefault(mLocale);
        mResourceBundle = ResourceBundle.getBundle("MessagesBundle", mLocale);
    }

    public Locale getLocaleObject() {
        return mLocale;
    }

    public ResourceBundle getResourceBundle() {
        return mResourceBundle;
    }

    static public LocaleStore getLocale() {
        return mStaticInstance;
    }

    public String getLang() {
        return lang;
    }

    public String getCountry() {
        return country;
    }
}
