package controllers;

import impres.impresario.IModel;
import javafx.fxml.FXML;

import javafx.scene.control.*;
import models.Vehicle;

import java.util.Arrays;
import java.util.Properties;

/**
 * Created by Hippolyte on 4/9/15.
 */
public class AddBikeCtrl extends CtrlBase {

    @FXML
    private TextField mMakeText;

    @FXML
    private TextField mSerialNumberText;

    @FXML
    private TextField mVehicleModelNum;

    @FXML
    private TextField mVehicleLocation;

    @FXML
    private TextField mVehicleDescription;

    @FXML
    private ComboBox<String> mVehicleColor;

    @FXML
    private ComboBox<String> mVehiclePhysicalCondition;

    @FXML
    private ComboBox<String> mVehicleStatus;

    private IModel mModel = new Vehicle();

    public AddBikeCtrl() {
        mModel.subscribe("UpdateStatusMessage", this);
    }

    @FXML
    private void initialize() {
        populateComboBox(mVehicleColor, new String[]{
                "red", "blue", "green", "yellow", "black",
                "white", "gray", "purple", "orange"
        });
        populateComboBox(mVehiclePhysicalCondition, new String[] {
                "mint", "good", "satisfactory", "poor"
        });
        populateComboBox(mVehicleStatus, new String[]{
                "available", "taken"
        });
        mSubmitWrapper.addAll(Arrays.asList(
                new SubmitWrapper("make", mMakeText, textGetter, empty),
                new SubmitWrapper("modelNumber", mVehicleModelNum, textGetter, empty),
                new SubmitWrapper("serialNumber", mSerialNumberText, textGetter, empty),
                new SubmitWrapper("color", mVehicleColor, comboGetter, empty),
                new SubmitWrapper("location", mVehicleLocation, textGetter, empty),
                new SubmitWrapper("description", mVehicleDescription, textGetter, ok),
                new SubmitWrapper("physicalCondition", mVehiclePhysicalCondition, comboGetter, empty),
                new SubmitWrapper("status", mVehicleStatus, comboGetter, ok),
                new SubmitWrapper("dateStatusUpdated", null, dateNowGetter, ok)
        ));
    }

    public void updateState(String key, Object value) {
        switch (key) {
            case "UpdateStatusMessage":
                displayErrorMessage((String)value);
                break;
            default:
                break;
        }
    }

    public void onBackClick() {
        mClerk.stateChangeRequest("Done", null);
    }

    public void onSubmitClick() {
        Properties p = getProperties();
        if (p != null) {
            System.out.println("OK");
            mModel.stateChangeRequest("ProcessInsertion", p);
        }
        else {
            System.err.println("KO");
        }
    }
}
