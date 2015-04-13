package controllers;

import impres.exception.InvalidPrimaryKeyException;
import impres.impresario.IModel;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.TextArea;
import models.Worker;

import java.util.Arrays;
import java.util.Properties;

/**
 * Created by vhb on 4/12/15.
 */
public class AddWorkerCtrl extends CtrlBase {

    @FXML
    private TextField mFirstName;

    @FXML
    private TextField mLastName;

    @FXML
    private TextArea mNotes;

    @FXML
    private PasswordField mPassword;

    @FXML
    private DatePicker mDate;

    @FXML
    private ComboBox mStatus;

    @FXML
    private ComboBox mCredentials;

    @FXML
    private TextField mEmail;

    @FXML
    private TextField mCountryCode;

    @FXML
    private TextField mPhoneNumber;

    @FXML
    private TextField mWorkerId;

    private IModel mModel = new Worker();

    public AddWorkerCtrl() {
        mModel.subscribe("UpdateStatusMessage", this);
    }

    public void updateState(String key, Object value) {
        switch(key) {
            case "UpdateStatusMessage":
                displayErrorMessage((String) value);
                break;
        }
    }

    @FXML
    public void initialize() {
        populateComboBox(mStatus, new String[] {"active", "inactive"});
        populateComboBox(mCredentials, new String[]{"administrator", "user"});
        mSubmitWrapper.addAll(Arrays.asList(
                new SubmitWrapper("firstName", mFirstName, textGetter, empty),
                new SubmitWrapper("workerId", mWorkerId, textGetter, empty),
                new SubmitWrapper("lastName", mLastName, textGetter, empty),
                new SubmitWrapper("phoneNumber", mPhoneNumber, textGetter, empty),
                new SubmitWrapper("countryCode", mCountryCode, textGetter, empty),
                new SubmitWrapper("emailAddress", mEmail, textGetter, empty),
                new SubmitWrapper("status", mStatus, comboGetter, empty),
                new SubmitWrapper("credential", mCredentials, comboGetter, empty),
                new SubmitWrapper("password", mPassword, textGetter, empty),
                new SubmitWrapper("dateOfInitialReg", mDate, dateGetter, empty),
                new SubmitWrapper("notes", mNotes, textAreaGetter, ok),
                new SubmitWrapper("dateStatusUpdated", null, dateNowGetter, ok)));
        mWorkerId.focusedProperty().addListener(
                (arg0, oldPropertyValue, newPropertyValue) -> {
                    if (!newPropertyValue) {
                        loadWorker(mWorkerId.getText());
                    }
                });
        setupDateFormat(mDate);
    }

    public void loadWorker(String workerId) {
        try {
            Worker w = new Worker(workerId);
            loadProperties(w.getProperties());
        } catch (InvalidPrimaryKeyException e) {
            // DO Nothing
            System.err.println("The user does not exist " + e.toString());
        }
    }

    public void onSubmitClick() {
        final Properties p = getProperties();
        if (p != null) {
            mModel.stateChangeRequest("ProcessInsertion", p);
        } else {
            System.err.println("Add User submit KO");
        }
    }

    public void onBackClick() {
        mClerk.stateChangeRequest("Done", null);
    }

}
