package controllers;

//import views.Test;

import impres.impresario.ControlRegistry;
import javafx.fxml.FXML;

import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.util.Properties;


/**
 * Created by Hippolyte on 3/17/15.
 */
public class LoginCtrl extends CtrlBase {
    @FXML
    private TextField mBannerIdText;

    @FXML
    private PasswordField mPasswordField;

    public LoginCtrl() {
        myRegistry = new ControlRegistry("LoginCtrl");
        mClerk.subscribe("LoginError", this);
    }

    public void updateState(String key, Object value) {
        if (key.equals("LoginError") == true && !value.equals("")) {
            displayErrorMessage((String)value);
        }
    }

    public void loginHit() {
        String bannerId = mBannerIdText.getText();
        String password = mPasswordField.getText();
        if (bannerId.length() == 0 || password.length() == 0) {
            displayErrorMessage("Please entrer bannerId and Password");
        } else {
            login(bannerId, password);
        }
    }

    public void login(String bannerId, String password) {
        Properties props = new Properties();

        props.setProperty("ID", bannerId);
        props.setProperty("Password", password);

        try {
            mClerk.stateChangeRequest("Login", props);
        }
        catch (Exception e) {
            System.err.println(e);
            e.printStackTrace();
            displayErrorMessage(e.getMessage());
        }
    }
}
