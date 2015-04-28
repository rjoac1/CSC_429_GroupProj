package views;

import models.LocaleStore;

import java.util.ResourceBundle;

/**
 * Created by vhb on 4/23/15.
 */
public class ComboxItem {

    final String mKey;
    final ResourceBundle mMessage;

    public ComboxItem(String key) {
        mKey = key;
        mMessage = LocaleStore.getLocale().getResourceBundle();
    }

    @Override
    public String toString() {
        return mMessage.getString(mKey);
    }

    public String getKey() {
        return mKey;
    }
}
