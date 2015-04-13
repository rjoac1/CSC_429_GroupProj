package controllers;

import impres.impresario.ControlRegistry;
import impres.impresario.IControl;
import impres.impresario.IModel;
import impres.impresario.IView;
import javafx.scene.control.*;
import models.Clerk;
import models.LocaleStore;
import org.controlsfx.dialog.Dialogs;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;
import java.util.List;
import java.util.stream.Stream;

/**
 * Created by Hippolyte on 3/17/15.
 */

public abstract class CtrlBase implements IView, IControl {

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
        void handle(Control c, String s);
    }

    protected InvalidParameterHandler focusHandler = (control, string) -> {
        control.requestFocus();
        control.setStyle("-fx-border-color: red ; -fx-border-width: 2px ;");
    };

    protected Getter textGetter = (v) -> ((TextField)v).getText();
    protected Getter comboGetter = (v) -> {
        final String value = ((ComboBox<String>) v).getValue();
        return value == null ? "" : value;
    };
    protected  Getter textAreaGetter = (v) -> ((TextArea)v).getText();
    protected Getter dateNowGetter = (v) ->
            new SimpleDateFormat("yyyy-MM-dd").format(new Date());
    protected Getter dateGetter = (v) -> {
        // TODO: check if it works
        final LocalDate date = ((DatePicker)v).getValue();
        return date.toString();
    };

    protected Validator empty = (s) -> (s != null && s.length() > 0);
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
            control = ctl;
            getter = get;
            validator = v;
            handler = focusHandler;
        }
    }

    protected IModel mClerk;
    protected ResourceBundle mMessages;
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
                i.handler.handle(i.control, s);
                return null;
            }
            value.setProperty(i.propertyName, s);
        }
        return value;
    }

    protected void populateComboBox(ComboBox cb, String[] values) {
        cb.getItems().clear();
        cb.getItems().addAll(
                Stream.of(values).map(
                        (s) -> mMessages.getString(s)
                ).toArray(String[]::new)
        );
    }

    protected void displayErrorMessage(String message) {
        System.err.println(message);
        Dialogs.create()
                .masthead("FastTRAX")
                .message(message)
                .showConfirm();
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
