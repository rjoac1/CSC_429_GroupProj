package controllers;

/**
 * Created by Hippolyte on 3/17/15.
 */

import java.util.HashMap;

import javax.swing.JPanel;

import utils.Observable;
import utils.ObservableMixin;

public class ViewController extends ObservableMixin implements Observable {

    private String mPath = "login"; // Entry point
    private final HashMap<String, CtrlBase> mCtrls = new HashMap<>();

    public ViewController() {
//        mCtrls.put("login", new LoginCtrl(this));
//        mCtrls.put("new_book", new InsertBookCtrl(this));
//        mCtrls.put("new_transaction", new InsertTransactionCtrl(this));
//        mCtrls.put("new_patron", new InsertPatronCtrl(this));
//        mCtrls.put("search_book", new SearchBookCtrl(this));
    }

    public void redirect(final String newPath) {
        mPath = newPath;
        updateAll(null);
    }

    public JPanel getView() {
        final JPanel value = mCtrls.get(mPath).getView();
        System.err.println(value);
        return value;
    }

    public void showDialog(final String message) {
        super.updateAll(this, message);
    }

    @Override
    public void updateAll(final Object datas) {
        super.updateAll(this, datas);
    }

}
