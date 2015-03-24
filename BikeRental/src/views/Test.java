package views;

import controllers.LoginCtrl;

import javax.swing.*;

/**
 * Created by Hippolyte on 3/22/15.
 */

public class Test extends JPanel {
    LoginCtrl mCtrl;
    public Test(LoginCtrl ctrl) {
        mCtrl = ctrl;
    }

    private JPanel panel1;
    private JTextField textField1;
    private JFormattedTextField formattedTextField1;
    private JButton submitButton;
}
