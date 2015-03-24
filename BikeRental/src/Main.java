import views.Frame;

import java.awt.EventQueue;

/**
 * Created by Hippolyte on 3/17/15.
 */


public class Main {

    public static void main(final String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                final Frame frame = new Frame();
                frame.setVisible(true);
            } catch (final Exception e) {
                e.printStackTrace();
            }
        });
    }

}
