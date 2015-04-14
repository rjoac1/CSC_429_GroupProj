package controllers;

import impres.impresario.ControlRegistry;
import impres.impresario.IControl;
import impres.impresario.IModel;
import impres.impresario.IView;
import javafx.scene.control.*;
import javafx.util.StringConverter;
import models.Clerk;
import models.LocaleStore;
import org.controlsfx.dialog.Dialogs;
import utils.EmailValidator;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.List;
import java.util.stream.Stream;

/**
 * Created by Hippolyte on 3/17/15.
 */

public abstract class CtrlBase implements IView, IControl {
    protected ResourceBundle mMessages;

    @FunctionalInterface
    protected interface Getter {
        String get(Control ctrl);
    }

    @FunctionalInterface
    protected interface Validator {
        Boolean validate(String content);
    }

    @FunctionalInterface
    protected interface InvalidParameterHandler {
        void handle(Control c, String s, Boolean focus);
    }

    protected InvalidParameterHandler focusHandler = (control, string, focus) -> {
        control.setStyle("-fx-border-color: red ; -fx-border-width: 2px ;");
        if (focus)
            control.requestFocus();
    };

    protected Getter textGetter = (v) -> ((TextField)v).getText();
    protected Getter comboGetter = (v) -> {
        String value = ((ComboBox<String>)v).getValue();

        // (!) Worse complexity is O(n log n), but it should be on very small list
        if (value == null) return null;
        for (String key : Collections.list(mMessages.getKeys())) {
            final String s = mMessages.getString(key);
            if (s.equals(value)) {
                System.err.println("value\t" + s + "\t" + key);
                return key;
            }
        }
        return "";
    };

    protected  Getter textAreaGetter = (v) -> ((TextArea)v).getText();

    static private final String mDbDateFormat = "yyyy-MM-dd";
    protected Getter dateNowGetter = (v) ->
            new SimpleDateFormat(mDbDateFormat).format(new Date());
    protected Getter dateGetter = (v) -> {
        // TODO: ajouter la gestion des dates fr (conversion vers une date us)
        final LocalDate date = ((DatePicker)v).getValue();
        if (date == null) return "";
        String value = date.toString();
        System.err.println("Date value\t" + value);
        return value;
    };

    protected Validator empty = (s) ->
            (s != null && s.length() > 0);
    protected Validator phoneNumber = (s) ->
            (s != null && s.length() >= 9 && s.length() <= 11
                    && s.matches("[0-9]+")
        );
    protected Validator countryCode = (s) ->
            (s != null && s.length() >= 1 && s.length() <= 4
                    && s.matches("[0-9]+"));
    protected Validator emailValidator = (s) ->
            (s != null && EmailValidator.validate(s));
    protected Validator ok = (s) -> true;

    protected class SubmitWrapper {
        public final String propertyName;
        public final Control control;
        public final Getter getter;
        public final Validator validator;
        public final InvalidParameterHandler handler;

        public SubmitWrapper(String name, Control ctl, Getter get,
                             Validator v) {
            propertyName = name;
            validator = v;
            control = ctl;
            getter = get;
            handler = focusHandler;
            setupOnTheFlyValidation();
        }

        public void setupOnTheFlyValidation() {
            if (control != null)
                control.focusedProperty().addListener(
                        (arg0, oldPropertyValue, newPropertyValue) -> {
                            if (!newPropertyValue) {
                                final String content = getter.get(control);
                                if (!validator.validate(content))
                                    handler.handle(control, content, false);
                                else
                                    control.setStyle("-fx-border-width: 0px;");
                            }
                        });
        }
    }

    protected IModel mClerk;
    protected ControlRegistry myRegistry;
    protected List<SubmitWrapper> mSubmitWrapper = new ArrayList<>();

    protected CtrlBase() {
        mClerk = Clerk.getInstance();
        mMessages = LocaleStore.getLocale().getResourceBundle();
        mClerk.subscribe("UpdateStatusMessage", this);
    }

    protected Properties getProperties() {
        Properties value = new Properties();
        for (SubmitWrapper i : mSubmitWrapper) {
            final String s = i.getter.get(i.control);
            if (!i.validator.validate(s)) {
                i.handler.handle(i.control, s, true);
                value = null;
            }
            if (value != null && s != null) {
                value.setProperty(i.propertyName, s);
            }
        }
        return value;
    }

    protected void setupDateFormat(final DatePicker p) {
        p.setConverter(new StringConverter<LocalDate>() {
            private DateTimeFormatter dateTimeFormatter =
                    DateTimeFormatter.ofPattern(mMessages.getString("dateFormat"));

            @Override
            public String toString(final LocalDate localDate) {
                if (localDate == null) return "";
                return dateTimeFormatter.format(localDate);
            }


            @Override
            public LocalDate fromString(final String dateString) {
                if (dateString == null || dateString.trim().isEmpty())
                    return null;
                return LocalDate.parse(dateString, dateTimeFormatter);
            }
        });
    }

    protected void populateComboBox(final ComboBox cb, final String[] values) {
        cb.getItems().addAll(
                Stream.of(values).map(
                        (s) -> mMessages.getString(s)
                ).toArray(String[]::new)
        );
    }

    protected LocalDate dateFromString(final String s) {
        final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        final LocalDate date = LocalDate.parse(s, formatter);
        return date;
    }

    protected void loadProperties(final Properties p) {
        for (SubmitWrapper i : mSubmitWrapper) {
            final String content = p.getProperty(i.propertyName);
            if (i.control instanceof TextArea)
                ((TextArea)(i.control)).setText(content);
            else if (i.control instanceof  TextField)
                ((TextField)(i.control)).setText(content);
            else if (i.control instanceof ComboBox){
                ((ComboBox)(i.control)).getSelectionModel().select(
                        mMessages.getString(content)
                );
            }
            else if (i.control instanceof DatePicker) {
                final LocalDate d = dateFromString(content);
                if (d != null)
                    ((DatePicker)(i.control)).setValue(d);
            }
        }
    }

    protected void displayErrorMessage(String message) {
        System.err.println(message);
        Dialogs.create()
                .title("FastTrax")
                .masthead("FastTRAX")
                .message(message)
                .showInformation();
    }

    public void setRegistry(ControlRegistry registry) {
        myRegistry = registry;
    }

    public void subscribe(String key, IModel subscriber) {
        myRegistry.subscribe(key, subscriber);
    }

    public void unSubscribe(String key, IModel subscriber) {
        myRegistry.unSubscribe(key, subscriber);
    }

}
