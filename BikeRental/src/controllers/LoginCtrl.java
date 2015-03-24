package controllers;

import views.Test;

import javax.swing.*;

/**
 * Created by Hippolyte on 3/17/15.
 */

public class LoginCtrl extends CtrlBase {

    private Test mView;

    LoginCtrl(ViewController v) {
        super(v);
        mView = new Test(this);
    }

    public JPanel getView() {
        return mView;
    }

    public boolean login(String login, String password) {
        // Do the check here.
        //  Think about encryption, salt
        return false;
    }

}
