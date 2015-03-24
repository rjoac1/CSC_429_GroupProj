package controllers;

/**
 * Created by Hippolyte on 3/17/15.
 */

import javax.swing.JPanel;

public abstract class CtrlBase {

    protected final ViewController mViewCtrl;

    public CtrlBase(final ViewController viewCtrl) {
        mViewCtrl = viewCtrl;
    }

    protected boolean validateDate(final String s) {
        return s.matches("\\d{4}-\\d{2}-\\d{2}");
    }

    public void redirectTo(final String s) {
        mViewCtrl.redirect(s);
    }

    public abstract JPanel getView();

}
