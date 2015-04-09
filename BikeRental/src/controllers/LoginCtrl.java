package controllers;

//import views.Test;

import impres.impresario.ControlRegistry;
import impres.impresario.IControl;
import impres.impresario.IModel;
import impres.impresario.IView;
import javafx.fxml.FXML;

import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.*;
import models.Clerk;
import models.LocaleStore;
import org.controlsfx.dialog.Dialogs;

import java.awt.event.ActionListener;
import java.awt.event.FocusListener;
import java.util.Properties;
import java.util.ResourceBundle;


/**
 * Created by Hippolyte on 3/17/15.
 */

public class LoginCtrl implements IView, IControl {

    IModel mModel;

    @FXML
    private Button mLoginBtn;

    @FXML
    private TextField mBannerIdText;

    @FXML
    private PasswordField mPasswordField;

    protected ResourceBundle mMessages;
    protected ControlRegistry myRegistry;

    public LoginCtrl() {
        mModel = Clerk.getInstance();
        mModel.subscribe("LoginError", this);
        mMessages = LocaleStore.getLocale().getResourceBundle();
        myRegistry = new ControlRegistry("LoginCtrl");
    }

    //----------------------------------------------------------
    public void setRegistry(ControlRegistry registry) {
        myRegistry = registry;
    }

    // Allow models to register for state updates
    //----------------------------------------------------------
    public void subscribe(String key,  IModel subscriber) {
        myRegistry.subscribe(key, subscriber);
    }


    // Allow models to unregister for state updates
    //----------------------------------------------------------
    public void unSubscribe(String key, IModel subscriber) {
        myRegistry.unSubscribe(key, subscriber);
    }

    public void updateState(String key, Object value) {
        if (key.equals("LoginError") == true && !value.equals(""))
        {
            // display the passed text
            displayErrorMessage(mMessages.getString((String)value));
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

    public void displayErrorMessage(String message) {
        Dialogs.create()
//                .owner(stage)
                .title("Error Dialog")
                .masthead("An error occured")
                .message(message)
                .showError();
    }

    public void login(String bannerId, String password) {
        Properties props = new Properties();

        props.setProperty("ID", bannerId);
        props.setProperty("Password", password);

        System.err.println("login");
        try {
            mModel.stateChangeRequest("Login", props);
        }
        catch (Exception e) {
            displayErrorMessage(e.getMessage());
        }
    }
//
//
//    public boolean login(String login, String password) {
//        // Do the check here.
//        //  Think about encryption, salt
//        return false;
//    }

}
