package controllers;

import impres.impresario.IModel;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import models.User;

import java.util.Arrays;
import java.util.Properties;

/**
 * Created by Hippolyte on 4/10/15.
 */
public class AddUser extends CtrlBase {

    @FXML
    private TextField mFirstName;

    @FXML
    private TextField mLastName;

    @FXML
    private TextField mPhoneNumber;

    @FXML
    private TextField mCountryCode;

    @FXML
    private TextField mEmail;

    @FXML
    private DatePicker mMembershipExpireDate;

    @FXML
    private DatePicker mInitialRegistrationDate;

    @FXML
    private ComboBox<String> mStatus;

    @FXML
    private ComboBox<String> mUserType;

    @FXML
    private TextArea mNotes;

    private IModel mModel = new User();

    public AddUser() {
        mModel.subscribe("UpdateStatusMessage", this);
    }

    @FXML
    private void initialize() {
        mSubmitWrapper.addAll(Arrays.asList(
                new SubmitWrapper("firstName", mFirstName, textGetter, empty),
                new SubmitWrapper("lastName", mLastName, textGetter, empty),
                new SubmitWrapper("phoneNumber", mPhoneNumber, textGetter, empty),
                new SubmitWrapper("countryCode", mCountryCode, textGetter, empty),
                new SubmitWrapper("emailAddress", mEmail, textGetter, empty),
                new SubmitWrapper("status", mStatus, comboGetter, empty),
                new SubmitWrapper("userType", mUserType, comboGetter, empty),
                new SubmitWrapper("dateOfMembershipExpired", mMembershipExpireDate, dateGetter, empty),
                new SubmitWrapper("dateOfMembershipReg", mInitialRegistrationDate, dateGetter, empty),
                new SubmitWrapper("notes", mNotes, textAreaGetter, ok),
                new SubmitWrapper("dateStatusUpdated", null, dateNowGetter, ok)
        ));
        populateComboBox(mStatus, new String[]{
                "active", "inactive"
        });
        populateComboBox(mUserType, new String[] {
                "student", "faculty"
        });
        setupDateFormat(mInitialRegistrationDate);
        setupDateFormat(mMembershipExpireDate);
    }

    public void onSubmitClick() {
        final Properties p = getProperties();
        if (p != null) {
            mModel.stateChangeRequest("ProcessInsertion", p);
        } else {
            System.err.println("Add User submit KO");
        }
    }

    public void updateState(String key, Object value) {
        if (key.equals("UpdateStatusMessage")) {
            displayErrorMessage((String) value);
        }
    }

    public void onBackClick() {
        mClerk.stateChangeRequest("Done", null);
    }

}
