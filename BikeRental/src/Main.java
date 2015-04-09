
/*
Command line code:
=======================
Compile tester:
javac -classpath classes;. DatabaseAccessorTester.java
----------------------------------------
Run the tester:
java -cp mysql-connector-java-5.1.7-bin.jar;classes;. DatabaseAccessorTester
----------------------------------------
*/
//Project imports
import models.Clerk;
import views.MainFrame;
import views.WindowPosition;

import java.awt.*;


public class Main
{
    private Clerk myClerk;

    /*Main frame of the application*/
    private MainFrame mainFrame;


    /*Constructor for this class, main application object*/
    //------------------------------------------------------
    public Main(String[] args)
    {
        // Create the top-level container (main frame) and add contents to it.
        mainFrame = MainFrame.getInstance("Bike Rental System v1.0");
        try {
            myClerk = new Clerk();
        }
        catch (Exception exc) {
            exc.printStackTrace(System.err);
            System.out.println("Could not create Clerk." + exc.getMessage());
        }
        mainFrame.pack();
        WindowPosition.placeCenter(mainFrame);

        mainFrame.setVisible(true);
    }

    public static void main(String[] args) throws Exception
    {
        EventQueue.invokeLater(() -> {
            try {
                new Main(args);
            } catch (final Exception e) {
                e.printStackTrace();
            }
        });

    }

}

