package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import models.LoggedWorker;
import models.Worker;
import views.MainFrame;

import java.text.MessageFormat;
import java.util.Objects;
import java.util.Properties;

/**
 * Created by Hippolyte on 4/9/15.
 */
public class MenuCtrl extends CtrlBase {

    @FXML
    private Label mSubtitle;

    @FXML
    void initialize() {
        try {
            final String s = mMessages.getString("greetingTemplate");
            final Worker w = LoggedWorker.getInstance().getCurrentLoggedUser();
            final Properties p = w.getProperties();
            Object t[] = new Object[]{
                    p.getProperty("firstName"),
                    p.getProperty("lastName"),
                    mMessages.getString(p.getProperty("credential"))
            };
            MessageFormat formatter = new MessageFormat("");
            formatter.applyPattern(s);
            String value = formatter.format(t);
            mSubtitle.setText(value);
        } catch (Exception e) {
            System.err.println(e);
            e.printStackTrace();
        }
    }

    public void displayIfAdmin(String scene) {
        if (LoggedWorker.getInstance().isAdmin())
            MainFrame.getInstance().replaceScene(scene);
        else {
            displayErrorMessage(mMessages.getString("needAdmin"));
        }
    }

    public void onAddBike() {
        mClerk.stateChangeRequest("AddBike", null);
    }

    public void onAddUser() {
        mClerk.stateChangeRequest("AddUser", null);
//        mClerk.stateChangeRequest("Add""/views/addUser.fxml");
//        displayIfAdmin("/views/addUser.fxml");
    }

    public void onAddWorker() {
        //  FIXME: should pass by the Clerk
        displayIfAdmin("/views/addWorker.fxml");
    }

    public void onLogout() {
        mClerk.stateChangeRequest("Logout", null);
    }

    public void onSearchUser() {
    }

    public void onSearchWorker() {
    }

    public void onSearchBike() {
    }

    public void updateState(String key, Object value) {
        if (key.equals("TransactionError")) {
            displayErrorMessage((String)value);
        }
    }
}
