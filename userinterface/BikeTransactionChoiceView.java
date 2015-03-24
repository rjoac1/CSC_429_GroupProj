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

    private JButton checkout;
    private JButton checkin;

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
        Font myFont2 = new Font("Helvetica", Font.BOLD, 10);
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

        String workerGreetingName = (String)myModel.getState("Name");
        String workerGreetingId = (String)myModel.getState("WorkerId");
        String workerGreetingCredentials = (String)myModel.getState("Credentials");

        Object[] messageArguments = {
                workerGreetingName,
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

        JPanel temp = new JPanel();		// default BoxLayout is fine
        /*BoxLayout f = new BoxLayout(temp, BoxLayout.Y_AXIS);
        temp.setLayout(f);

        // create the buttons, listen for events, add them to the panel
        JPanel temp_1 = new JPanel();
        FlowLayout f_1 = new FlowLayout(FlowLayout.CENTER);
        temp_1.setLayout(f_1);

        depositButton = new JButton("Deposit");
        depositButton.addActionListener(this);
        temp_1.add(depositButton);

        temp.add(temp_1);

        temp.add(Box.createRigidArea(new Dimension(400, 25)));

        JPanel temp_2 = new JPanel();
        FlowLayout f_2 = new FlowLayout(FlowLayout.CENTER);
        temp_2.setLayout(f_2);

        withdrawButton = new JButton("Withdraw");
        withdrawButton.addActionListener(this);
        temp_2.add(withdrawButton);

        temp.add(temp_2);

        temp.add(Box.createRigidArea(new Dimension(400, 25)));

        JPanel temp_3 = new JPanel();
        FlowLayout f_3 = new FlowLayout(FlowLayout.CENTER);
        temp_3.setLayout(f_3);

        transferButton = new JButton("Transfer");
        transferButton.addActionListener(this);
        temp_3.add(transferButton);

        temp.add(temp_3);

        temp.add(Box.createRigidArea(new Dimension(400, 25)));

        JPanel temp_4 = new JPanel();
        FlowLayout f_4 = new FlowLayout(FlowLayout.CENTER);
        temp_4.setLayout(f_4);

        balanceInquiryButton = new JButton("Balance Inquiry");
        balanceInquiryButton.addActionListener(this);
        temp_4.add(balanceInquiryButton);

        temp.add(temp_4);

        temp.add(Box.createRigidArea(new Dimension(400, 50)));

        JPanel temp_4_1 = new JPanel();
        FlowLayout f_4_1 = new FlowLayout(FlowLayout.CENTER);
        temp_4_1.setLayout(f_4_1);

        imposeServiceChargeButton = new JButton("Impose Service Charge");
        imposeServiceChargeButton.addActionListener(this);
        temp_4_1.add(imposeServiceChargeButton);

        temp.add(temp_4_1);

        temp.add(Box.createRigidArea(new Dimension(400, 50)));

        JPanel temp_5 = new JPanel();
        FlowLayout f_5 = new FlowLayout(FlowLayout.CENTER);
        temp_5.setLayout(f_5);
        cancelButton = new JButton("Done");
        cancelButton.addActionListener(this);
        temp_5.add(cancelButton);

        temp.add(temp_5);*/

        return temp;
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


