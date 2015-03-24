// specify the package
package userinterface;

// system imports
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.util.*;
import java.text.*;

// project imports
import impresario.IModel;

/** The class containing the Transaction Choice View  for the ATM application */
//==============================================================
public class BikeTransactionChoiceView extends View
{

    // other private data
    private final int labelWidth = 120;
    private final int labelHeight = 25;

    // GUI components

    private JButton addUserButton;
    private JButton addWorkerButton;
    private JButton addBikeButton;

    private JButton fndmodUserButton;
    private JButton fndmodWorkerButton;
    private JButton fndmodBikeButton;

    private JButton checkoutButton;
    private JButton checkinButton;

    //private JButton rentBikeButton;
    //private JButton returnBikeButton;

    private JButton cancelButton;

    private MessageView statusLog;

    //resource bundle for internationalization
    private ResourceBundle messages;

    private Locale currentLocale;

    // constructor for this class -- takes a model object
    //----------------------------------------------------------
    public BikeTransactionChoiceView(IModel clerk)
    {
        super(clerk, "BikeTransactionChoiceView");
        currentLocale = (Locale)myModel.getState("CurrentLocale");
        messages = ResourceBundle.getBundle("MessagesBundle", currentLocale);

        // set the layout for this panel
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));	// vertical

        // Add a title for this panel
        add(createTitle());

        // create our GUI components, add them to this panel
        add(createNavigationButtons());

        add(createStatusLog("             "));

        populateFields();

        myModel.subscribe("TransactionError", this);
    }

    // Create the labels and fields
    //-------------------------------------------------------------
    private JPanel createTitle()
    {
        JPanel temp = new JPanel();
        // set the layout for this panel
        temp.setLayout(new BoxLayout(temp, BoxLayout.Y_AXIS));

        JPanel temp1 = new JPanel();
        temp1.setLayout(new FlowLayout(FlowLayout.CENTER));

        JLabel lbl = new JLabel(messages.getString("brockportTitle"));
        Font myFont1 = new Font("Helvetica", Font.BOLD, 20);
        lbl.setFont(myFont1);
        temp1.add(lbl);

        temp.add(temp1);

        JPanel temp2 = new JPanel();
        temp1.setLayout(new FlowLayout(FlowLayout.CENTER));

        JLabel lbl1 = new JLabel(messages.getString("fastTracksTitle"));
        Font myFont2 = new Font("Helvetica", Font.BOLD, 14);
        lbl1.setFont(myFont2);
        temp2.add(lbl1);

        temp.add(temp2);

        JPanel temp3 = new JPanel();
        temp3.setLayout(new FlowLayout(FlowLayout.CENTER));

        JLabel lbl2 = new JLabel(messages.getString("transChoiceSubTitle"));
        Font myFont3 = new Font("Helvetica", Font.BOLD, 15);
        lbl2.setFont(myFont3);
        temp3.add(lbl2);

        temp.add(temp3);

        JPanel temp4 = new JPanel();
        temp4.setLayout(new FlowLayout(FlowLayout.CENTER));

        String workerGreetingLastName = (String)myModel.getState("LastName");
        String workerGreetingFirstName = (String)myModel.getState("FirstName");
        String workerGreetingId = (String)myModel.getState("WorkerId");
        String workerGreetingCredentials = (String)myModel.getState("Credential");



        Object[] messageArguments = {
                workerGreetingLastName,
                workerGreetingFirstName,
                workerGreetingId,
                workerGreetingCredentials
        };

        MessageFormat formatter = new MessageFormat("");
        formatter.setLocale(currentLocale);

        formatter.applyPattern(messages.getString("greetingTemplate"));
        String greeting = formatter.format(messageArguments);

        JLabel lbl3 = new JLabel(greeting);
        lbl3.setFont(myFont2);
        temp4.add(lbl3);

        temp.add(temp4);

        return temp;
    }

    // Create the navigation buttons
    //-------------------------------------------------------------
    private JPanel createNavigationButtons()
    {
        final JPanel value = new JPanel();
        value.setLayout(new BoxLayout(value, BoxLayout.Y_AXIS));
        final JLabel lblLayout = new JLabel("Layout");
        lblLayout.setBounds(37, 80, 42, 16);
        value.add(lblLayout);

        final JButton btnCheckin = new JButton("CheckIn");
        btnCheckin.setBounds(177, 75, 95, 29);
        value.add(btnCheckin);

        final JButton btnCheckout = new JButton("CheckOut");
        btnCheckout.setBounds(284, 73, 106, 29);
        value.add(btnCheckout);

        final JLabel lblAdd = new JLabel("Add");
        lblAdd.setBounds(42, 124, 25, 16);
        value.add(lblAdd);

        final JButton btnUser = new JButton("User");
        btnUser.setBounds(143, 119, 75, 29);
        value.add(btnUser);

        final JButton btnWorker = new JButton("Worker");
        btnWorker.setBounds(236, 119, 88, 29);
        value.add(btnWorker);

        final JButton btnNike = new JButton("Bike");
        btnNike.setBounds(349, 114, 75, 29);
        value.add(btnNike);

        final JLabel lblFindmodify = new JLabel("Find/Modify");
        lblFindmodify.setBounds(37, 172, 77, 16);
        value.add(lblFindmodify);

        final JButton btnUser_1 = new JButton("User");
        btnUser_1.setBounds(143, 167, 75, 29);
        value.add(btnUser_1);

        final JButton btnWorker_1 = new JButton("Worker");
        btnWorker_1.setBounds(236, 167, 88, 29);
        value.add(btnWorker_1);

        final JButton btnBike = new JButton("Bike");
        btnBike.setBounds(349, 167, 75, 29);
        value.add(btnBike);

        final JButton btnLogout = new JButton("Logout");
        btnLogout.setBounds(190, 239, 88, 29);
        value.add(btnLogout);

        final JLabel lblBrockportBikeRental = new JLabel("Brockport Bike Rental System");
        lblBrockportBikeRental.setBounds(121, 33, 213, 16);
        value.add(lblBrockportBikeRental);

        return value;
    }

    // Create the status log field
    //-------------------------------------------------------------
    private JPanel createStatusLog(String initialMessage)
    {

        statusLog = new MessageView(initialMessage);

        return statusLog;
    }

    //-------------------------------------------------------------
    public void populateFields()
    {

    }

    // process events generated from our GUI components
    //-------------------------------------------------------------
    public void processAction(EventObject e)
    {
        // DEBUG: System.out.println("TransactionChoiceView.actionPerformed()");

        clearErrorMessage();

        /*if(e.getSource() == depositButton)
        {
            myModel.stateChangeRequest("Deposit", null);
        }
        else
        if(e.getSource() == withdrawButton)
        {
            myModel.stateChangeRequest("Withdraw", null);
        }
        else
        if(e.getSource() == transferButton)
        {
            myModel.stateChangeRequest("Transfer", null);
        }
        else
        if(e.getSource() == balanceInquiryButton)
        {
            myModel.stateChangeRequest("BalanceInquiry", null);
        }
        else
        if(e.getSource() == imposeServiceChargeButton)
        {
            myModel.stateChangeRequest("ImposeServiceCharge", null);
        }
        else
        if(e.getSource() == cancelButton)
        {
            processCancel();
        }*/

    }


    /**
     * Cancel button hit.
     * Action here is to ask the teller to switch to the main teller view.
     */
    //----------------------------------------------------------
    private void processCancel()
    {
        myModel.stateChangeRequest("Logout", null);
    }

    //---------------------------------------------------------
    public void updateState(String key, Object value)
    {
        if (key.equals("TransactionError") == true)
        {
            // display the passed text
            displayErrorMessage((String)value);
        }
    }

    /**
     * Display error message
     */
    //----------------------------------------------------------
    public void displayErrorMessage(String message)
    {
        statusLog.displayErrorMessage(message);
    }

    /**
     * Clear error message
     */
    //----------------------------------------------------------
    public void clearErrorMessage()
    {
        statusLog.clearErrorMessage();
    }
}


