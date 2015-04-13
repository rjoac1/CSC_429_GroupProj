package controllers;

/**
 * Created by Hippolyte on 4/9/15.
 */
public class MenuCtrl extends CtrlBase {
    public void onAddBike() {
        mClerk.stateChangeRequest("AddBike", null);
    }

    public void onAddUser() {
        mClerk.stateChangeRequest("AddUser", null);
    }

    public void onAddWorker() {
        mClerk.stateChangeRequest("AddWorker", null);
    }

    public void onLogout() {
        System.err.println("logout");
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
