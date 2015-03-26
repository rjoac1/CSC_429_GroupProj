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
        {

        final JPanel value = new JPanel();
        value.setLayout(new GridLayout(0, 1, 0, 0));

        final JPanel panel = new JPanel();
        value.add(panel);
        final GridBagLayout gbl_panel = new GridBagLayout();
        gbl_panel.columnWidths = new int[]{0, 213, 128, 253, 0};
        gbl_panel.rowHeights = new int[]{16, 29, 0};
        gbl_panel.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
        gbl_panel.rowWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
        panel.setLayout(gbl_panel);

        final JLabel lblLayout = new JLabel("Layout");
        final GridBagConstraints gbc_lblLayout = new GridBagConstraints();
        gbc_lblLayout.anchor = GridBagConstraints.WEST;
        gbc_lblLayout.insets = new Insets(0, 0, 0, 5);
        gbc_lblLayout.gridx = 1;
        gbc_lblLayout.gridy = 1;
        panel.add(lblLayout, gbc_lblLayout);

        final JButton btnCheckin = new JButton("CheckIn");
        final GridBagConstraints gbc_btnCheckin = new GridBagConstraints();
        gbc_btnCheckin.anchor = GridBagConstraints.NORTHWEST;
        gbc_btnCheckin.insets = new Insets(0, 0, 0, 5);
        gbc_btnCheckin.gridx = 2;
        gbc_btnCheckin.gridy = 1;
        panel.add(btnCheckin, gbc_btnCheckin);

        final JButton btnCheckout = new JButton("CheckOut");
        final GridBagConstraints gbc_btnCheckout = new GridBagConstraints();
        gbc_btnCheckout.anchor = GridBagConstraints.NORTHWEST;
        gbc_btnCheckout.gridx = 3;
        gbc_btnCheckout.gridy = 1;
        panel.add(btnCheckout, gbc_btnCheckout);

        final JPanel panel_1 = new JPanel();
        value.add(panel_1);
        final GridBagLayout gbl_panel_1 = new GridBagLayout();
        gbl_panel_1.columnWidths = new int[]{0, 93, 155, 165, 155, 0, 0};
        gbl_panel_1.rowHeights = new int[]{0, 29, 0};
        gbl_panel_1.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
        gbl_panel_1.rowWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
        panel_1.setLayout(gbl_panel_1);

        final JLabel lblAdd = new JLabel("Add");
        final GridBagConstraints gbc_lblAdd = new GridBagConstraints();
        gbc_lblAdd.fill = GridBagConstraints.BOTH;
        gbc_lblAdd.insets = new Insets(0, 0, 0, 5);
        gbc_lblAdd.gridx = 1;
        gbc_lblAdd.gridy = 1;
        panel_1.add(lblAdd, gbc_lblAdd);

        final JButton btnUser = new JButton("User");
        final GridBagConstraints gbc_btnUser = new GridBagConstraints();
        gbc_btnUser.fill = GridBagConstraints.BOTH;
        gbc_btnUser.insets = new Insets(0, 0, 0, 5);
        gbc_btnUser.gridx = 2;
        gbc_btnUser.gridy = 1;
        panel_1.add(btnUser, gbc_btnUser);

        final JButton btnWorker = new JButton("Worker");
        final GridBagConstraints gbc_btnWorker = new GridBagConstraints();
        gbc_btnWorker.fill = GridBagConstraints.BOTH;
        gbc_btnWorker.insets = new Insets(0, 0, 0, 5);
        gbc_btnWorker.gridx = 3;
        gbc_btnWorker.gridy = 1;
        panel_1.add(btnWorker, gbc_btnWorker);

        final JButton btnNike = new JButton("Bike");
        final GridBagConstraints gbc_btnNike = new GridBagConstraints();
        gbc_btnNike.insets = new Insets(0, 0, 0, 5);
        gbc_btnNike.fill = GridBagConstraints.BOTH;
        gbc_btnNike.gridx = 4;
        gbc_btnNike.gridy = 1;
        panel_1.add(btnNike, gbc_btnNike);

        final JPanel panel_3 = new JPanel();
        value.add(panel_3);
        final GridBagLayout gbl_panel_3 = new GridBagLayout();
        gbl_panel_3.columnWidths = new int[]{0, 93, 155, 165, 155, 0, 0};
        gbl_panel_3.rowHeights = new int[]{0, 29, 0};
        gbl_panel_3.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
        gbl_panel_3.rowWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
        panel_3.setLayout(gbl_panel_3);

        final JLabel label = new JLabel("Find/Modify");
        final GridBagConstraints gbc_label = new GridBagConstraints();
        gbc_label.fill = GridBagConstraints.BOTH;
        gbc_label.insets = new Insets(0, 0, 0, 5);
        gbc_label.gridx = 1;
        gbc_label.gridy = 1;
        panel_3.add(label, gbc_label);

        final JButton button = new JButton("User");
        final GridBagConstraints gbc_button = new GridBagConstraints();
        gbc_button.fill = GridBagConstraints.BOTH;
        gbc_button.insets = new Insets(0, 0, 0, 5);
        gbc_button.gridx = 2;
        gbc_button.gridy = 1;
        panel_3.add(button, gbc_button);

        final JButton button_1 = new JButton("Worker");
        final GridBagConstraints gbc_button_1 = new GridBagConstraints();
        gbc_button_1.fill = GridBagConstraints.BOTH;
        gbc_button_1.insets = new Insets(0, 0, 0, 5);
        gbc_button_1.gridx = 3;
        gbc_button_1.gridy = 1;
        panel_3.add(button_1, gbc_button_1);

        final JButton button_2 = new JButton("Bike");
        final GridBagConstraints gbc_button_2 = new GridBagConstraints();
        gbc_button_2.fill = GridBagConstraints.BOTH;
        gbc_button_2.insets = new Insets(0, 0, 0, 5);
        gbc_button_2.gridx = 4;
        gbc_button_2.gridy = 1;
        panel_3.add(button_2, gbc_button_2);

        final JPanel panel_2 = new JPanel();
        value.add(panel_2);

        final JButton btnLogout = new JButton("Logout");
        panel_2.add(btnLogout);

        final JPanel panel_4 = new JPanel();
        value.add(panel_4);
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


