package views;

import com.sun.deploy.ui.UIFactory;
import models.LocaleStore;

import javax.swing.*;
import java.awt.*;

/**
 * Created by vhb on 4/28/15.
 */
public class ValidateInput extends InputVerifier {
    private final View.Validator mValidator;
    private final View.Getter mGetter;
    private final JDialog mPopup;
    private final JLabel mMessage;

    public ValidateInput(final View.Validator v,
                         final View.Getter getter,
                         final String message) {
        mValidator = v;
        mGetter = getter;
        mPopup = new JDialog(MainFrame.getInstance());
        mMessage = new JLabel(LocaleStore.getLocale().getResourceBundle().getString(message));
        initPopup();
    }

    @Override
    public boolean verify(final JComponent input) {
        final String s = mGetter.get(input);
        final Boolean value = mValidator.validate(s);
        if (!value) {
            mPopup.setSize(0, 0);
            mPopup.setLocationRelativeTo(input);
            final Point point = mPopup.getLocation();
            final Dimension cDim = input.getSize();
            mPopup.setLocation(point.x - (int) cDim.getWidth() / 2,
                    point.y + (int) cDim.getHeight() / 2);
            mPopup.pack();
            input.setBorder(BorderFactory.createLineBorder(Color.RED));
            mPopup.setVisible(true);
        } else {
            input.setBorder(UIManager.getBorder("TextField.border"));
            mPopup.setVisible(false);
        }
        return value;
    }

    public void hidePopup() {
        mPopup.setVisible(false);
    }

    private void initPopup() {
        Color color = new Color(243, 255, 159);
        mPopup.getContentPane().setLayout(new FlowLayout());
        mPopup.setUndecorated(true);
        mPopup.getContentPane().setBackground(color);
        // mPopup.getContentPane().add(image);
        mPopup.getContentPane().add(mMessage);
        mPopup.setFocusableWindowState(false);
    }

}
