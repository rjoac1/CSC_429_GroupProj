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

    static private LocaleStore mStaticInstance;

    private LocaleStore() {
        PropertyFile mProps = new PropertyFile("userConfig.ini");

        mLocale = new Locale(mProps.getProperty("language"),
                mProps.getProperty("country"));
        mResourceBundle = ResourceBundle.getBundle("MessagesBundle", mLocale);
    }

    public Locale getLocaleObject() {
        return mLocale;
    }

    public ResourceBundle getResourceBundle() {
        return mResourceBundle;
    }

    static public LocaleStore getLocale() {
        if (mStaticInstance == null) {
            mStaticInstance = new LocaleStore();
        }
        return mStaticInstance;
    }
    public boolean equals(Locale that)
    {
        if()
    }

}
