package controllers;

import models.LoggedWorker;
import views.MainFrame;

/**
 * Created by Hippolyte on 4/9/15.
 */
public class MenuCtrl extends CtrlBase {

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
        //  FIXME: should pass by the Clerk
        displayIfAdmin("/views/addWorker.fxml");
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
