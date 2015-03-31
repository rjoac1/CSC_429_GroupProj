package views;

/**
 * Created by Hippolyte on 3/17/15.
 */

import impres.exception.InvalidPrimaryKeyException;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import utils.Observable;
import utils.Observer;

import controllers.ViewController;

public class Frame extends JFrame implements Observer {

    private static final long serialVersionUID = 8793149097818728013L;
    private final ViewController mViewController;

    public Frame() throws InvalidPrimaryKeyException {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 600, 600);
        mViewController = new ViewController();
        mViewController.addObserver(this);
        setContentPane(mViewController.getView());
    }

    @Override
    public void update(final Observable o, final Object arg) {
        if (arg == null) {
            getContentPane().setVisible(false);
            final JPanel j = mViewController.getView();
            j.setVisible(true);
            setContentPane(j);
        }
        else {
            JOptionPane.showMessageDialog(this, arg);
        }
    }

}
