// specify the package
package views;

import java.awt.*;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.util.*;
import java.text.*;

// project imports
import impres.impresario.IModel;
import models.LocaleStore;

/** The class containing the Transaction Choice View  for the ATM application */
//==============================================================
public class BikeTransactionChoiceView extends View
{
    private MainFrame mainFrame;
    // other private data
    private final int labelWidth = 120;
    private final int labelHeight = 25;

    // GUI components

    private JButton addUserButton = null;
    private JButton addWorkerButton = null;
    private JButton addBikeButton = null;

    private JButton fndmodUserButton = null;
    private JButton fndmodWorkerButton = null;
    private JButton fndmodBikeButton = null;

    private JButton rentButton = null;
    private JButton returnButton = null;

    private JButton logoutButton = null;

    private MessageView statusLog;
    private String workerCred = "";

    //resource bundle for internationalization

    // constructor for this class -- takes a model object
    //----------------------------------------------------------
    public BikeTransactionChoiceView(IModel clerk)
    {
        super(clerk, "BikeTransactionChoiceView");
        subTitleText = "transChoiceSubTitle";
        //Get and store the current workers credential status
        getWorkerAdminStatus();

        // set the layout for this panel
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));	// vertical
        //this.setSize(new Dimension(300, 400));
        // Add a title for this panel
        add(createTitle());
        // Add the welcome message string
        add(createWelcomeMessage());
        // create our GUI components, add them to this panel
        add(createNavigationButtons());

        //add(createStatusLog("             "));

        mainFrame = MainFrame.getInstance(messages.getString("titleLine"));


        //myModel.subscribe("TransactionError", this);
        //myModel.subscribe("NoRentalsFoundError",this);
    }

    protected JPanel createWelcomeMessage() {
        JPanel temp = new JPanel();
        temp.setLayout(new FlowLayout(FlowLayout.CENTER));

        String workerGreetingLastName = (String) myModel.getState("LastName");
        String workerGreetingFirstName = (String) myModel.getState("FirstName");
        String workerGreetingId = (String) myModel.getState("WorkerId");
        String workerGreetingCredentials = messages.getString((String) myModel.getState("Credential"));


        Object[] messageArguments = {
                workerGreetingLastName,
                workerGreetingFirstName,
                workerGreetingId,
                workerGreetingCredentials
        };

        MessageFormat formatter = new MessageFormat("");
        formatter.setLocale(LocaleStore.getLocale().getLocaleObject());

        formatter.applyPattern(messages.getString("greetingTemplate"));
        String greeting = formatter.format(messageArguments);

        JLabel lbl3 = new JLabel(greeting);
        lbl3.setFont(myFont2);
        temp.add(lbl3);
        return temp;
    };


    // Create the navigation buttons
    //-------------------------------------------------------------
    protected JPanel createNavigationButtons()
    {
        final JPanel choicePanel = new JPanel();
        choicePanel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        final JPanel rr = new JPanel();

        rentButton = new JButton(messages.getString("checkoutButton"));
        rentButton.addActionListener(this);

        returnButton = new JButton(messages.getString("checkinButton"));
        returnButton.addActionListener(this);

        rr.add(rentButton);
        rr.add(returnButton);

        c.gridx=0;
        c.gridy=0;
        c.insets = new Insets(10, 10, 5, 5);
        choicePanel.add(rr, c);

        final JPanel addButtons = new JPanel();
        addUserButton = new JButton(messages.getString("add")+" "+messages.getString("user"));
        addUserButton.addActionListener(this);

        addWorkerButton = new JButton(messages.getString("add")+" "+messages.getString("worker"));
        addWorkerButton.addActionListener(this);

        addBikeButton = new JButton(messages.getString("add")+" "+messages.getString("bike"));
        addBikeButton.addActionListener(this);

        addButtons.add(addUserButton);
        addButtons.add(addWorkerButton);
        addButtons.add(addBikeButton);

        c.gridx=0;
        c.gridy=1;
        c.insets = new Insets(10, 10, 5, 5);
        choicePanel.add(addButtons, c);

        final JPanel modButtons = new JPanel();
        fndmodUserButton = new JButton(messages.getString("Find/Modify")+" "+messages.getString("user"));
        fndmodUserButton.addActionListener(this);

        fndmodWorkerButton = new JButton(messages.getString("Find/Modify")+" "+messages.getString("worker"));
        fndmodWorkerButton.addActionListener(this);

        fndmodBikeButton = new JButton(messages.getString("Find/Modify")+" "+messages.getString("bike"));
        fndmodBikeButton.addActionListener(this);

        modButtons.add(fndmodUserButton);
        modButtons.add(fndmodWorkerButton);
        modButtons.add(fndmodBikeButton);

        c.gridx=0;
        c.gridy=2;
        c.insets = new Insets(10, 10, 5, 5);
        choicePanel.add(modButtons, c);

        logoutButton = new JButton(messages.getString("logout"));
        logoutButton.addActionListener(this);
        c.gridx=0;
        c.gridy=3;
        choicePanel.add(logoutButton, c);


        JLabel space = new JLabel();
        c.gridx=0;
        c.gridy=4;
        c.insets = new Insets(25, 20, 0, 0);
        choicePanel.add(space, c);


        return choicePanel;

        /*
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

        returnButton = new JButton(messages.getString("returnButton"));
        returnButton.addActionListener(this);
        final GridBagConstraints gbc_btnCheckin = new GridBagConstraints();
        gbc_btnCheckin.anchor = GridBagConstraints.NORTHWEST;
        gbc_btnCheckin.insets = new Insets(0, 0, 0, 5);
        gbc_btnCheckin.gridx = 2;
        gbc_btnCheckin.gridy = 1;
        panel.add(returnButton, gbc_btnCheckin);

        rentButton = new JButton(messages.getString("rentButton"));
        rentButton.addActionListener(this);
        final GridBagConstraints gbc_btnCheckout = new GridBagConstraints();
        gbc_btnCheckout.anchor = GridBagConstraints.NORTHWEST;
        gbc_btnCheckout.gridx = 3;
        gbc_btnCheckout.gridy = 1;
        panel.add(rentButton, gbc_btnCheckout);

        //declare adding panel
        final JPanel panel_1 = new JPanel();
        value.add(panel_1);
        final GridBagLayout gbl_panel_1 = new GridBagLayout();
        gbl_panel_1.columnWidths = new int[]{0, 93, 155, 165, 155, 0, 0};
        gbl_panel_1.rowHeights = new int[]{0, 29, 0};
        gbl_panel_1.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
        gbl_panel_1.rowWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
        panel_1.setLayout(gbl_panel_1);



        //add string
        final JLabel lblAdd = new JLabel(messages.getString("add"));
        final GridBagConstraints gbc_lblAdd = new GridBagConstraints();
        gbc_lblAdd.fill = GridBagConstraints.BOTH;
        gbc_lblAdd.insets = new Insets(0, 0, 0, 5);
        gbc_lblAdd.gridx = 1;
        gbc_lblAdd.gridy = 1;
        panel_1.add(lblAdd, gbc_lblAdd);

        addUserButton = new JButton(messages.getString("user"));
        addUserButton.addActionListener(this);
        final GridBagConstraints gbc_btnUser = new GridBagConstraints();
        gbc_btnUser.fill = GridBagConstraints.BOTH;
        gbc_btnUser.insets = new Insets(0, 0, 0, 5);
        gbc_btnUser.gridx = 2;
        gbc_btnUser.gridy = 1;
        panel_1.add(addUserButton, gbc_btnUser);

        addWorkerButton = new JButton(messages.getString("worker"));
        addWorkerButton.addActionListener(this);
        final GridBagConstraints gbc_btnWorker = new GridBagConstraints();
        gbc_btnWorker.fill = GridBagConstraints.BOTH;
        gbc_btnWorker.insets = new Insets(0, 0, 0, 5);
        gbc_btnWorker.gridx = 3;
        gbc_btnWorker.gridy = 1;
        panel_1.add(addWorkerButton, gbc_btnWorker);

        addBikeButton = new JButton(messages.getString("bike"));
        addBikeButton.addActionListener(this);
        final GridBagConstraints gbc_btnBike = new GridBagConstraints();
        gbc_btnBike.insets = new Insets(0, 0, 0, 5);
        gbc_btnBike.fill = GridBagConstraints.BOTH;
        gbc_btnBike.gridx = 4;
        gbc_btnBike.gridy = 1;
        panel_1.add(addBikeButton, gbc_btnBike);


        final JPanel panel_3 = new JPanel();
        value.add(panel_3);
        final GridBagLayout gbl_panel_3 = new GridBagLayout();
        gbl_panel_3.columnWidths = new int[]{0, 93, 155, 165, 155, 0, 0};
        gbl_panel_3.rowHeights = new int[]{0, 29, 0};
        gbl_panel_3.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
        gbl_panel_3.rowWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
        panel_3.setLayout(gbl_panel_3);

        final JLabel label = new JLabel(messages.getString("Find/Modify"));
        final GridBagConstraints gbc_label = new GridBagConstraints();
        gbc_label.fill = GridBagConstraints.BOTH;
        gbc_label.insets = new Insets(0, 0, 0, 5);
        gbc_label.gridx = 1;
        gbc_label.gridy = 1;
        panel_3.add(label, gbc_label);

        fndmodUserButton = new JButton(messages.getString("user"));
        fndmodUserButton.addActionListener(this);
        final GridBagConstraints gbc_button = new GridBagConstraints();
        gbc_button.fill = GridBagConstraints.BOTH;
        gbc_button.insets = new Insets(0, 0, 0, 5);
        gbc_button.gridx = 2;
        gbc_button.gridy = 1;
        panel_3.add(fndmodUserButton, gbc_button);

        fndmodWorkerButton = new JButton(messages.getString("worker"));
        fndmodWorkerButton.addActionListener(this);
        final GridBagConstraints gbc_button_1 = new GridBagConstraints();
        gbc_button_1.fill = GridBagConstraints.BOTH;
        gbc_button_1.insets = new Insets(0, 0, 0, 5);
        gbc_button_1.gridx = 3;
        gbc_button_1.gridy = 1;
        panel_3.add(fndmodWorkerButton, gbc_button_1);

        fndmodBikeButton = new JButton(messages.getString("bike"));
        fndmodBikeButton.addActionListener(this);
        final GridBagConstraints gbc_button_2 = new GridBagConstraints();
        gbc_button_2.fill = GridBagConstraints.BOTH;
        gbc_button_2.insets = new Insets(0, 0, 0, 5);
        gbc_button_2.gridx = 4;
        gbc_button_2.gridy = 1;
        panel_3.add(fndmodBikeButton, gbc_button_2);

        final JPanel panel_2 = new JPanel();
        value.add(panel_2);

        logoutButton = new JButton(messages.getString("logout"));
        logoutButton.addActionListener(this);
        panel_2.add(logoutButton);

        final JPanel panel_4 = new JPanel();
        value.add(panel_4);
        return value;*/
    }

    // process events generated from our GUI components
    //-------------------------------------------------------------
    public void processAction(EventObject e) {
        // DEBUG: System.out.println("TransactionChoiceView.actionPerformed()");

        //clearErrorMessage();

        if (e.getSource() == returnButton) {
            myModel.stateChangeRequest("Return", null);
        } else if (e.getSource() == rentButton) {
            myModel.stateChangeRequest("Rent", null);
        } else if (e.getSource() == addUserButton) {
            myModel.stateChangeRequest("AddUser", null);
        } else if (e.getSource() == addWorkerButton) {
            if(checkWorkerAdminStatus()){ myModel.stateChangeRequest("AddWorker", null); }
            else{displayMessage(messages.getString("requireAdminCred"));}
        } else if (e.getSource() == addBikeButton) {
            if (checkWorkerAdminStatus()){ myModel.stateChangeRequest("AddBike", null); }
            else{displayMessage(messages.getString("requireAdminCred"));}
        } else if (e.getSource() == fndmodUserButton)
        {
            myModel.stateChangeRequest("fndModUser", null);
        }else if (e.getSource() == fndmodWorkerButton)
        {
            if (checkWorkerAdminStatus()) {
                myModel.stateChangeRequest("fndModWorker", null); }
            else{displayMessage(messages.getString("requireAdminCred"));}
        }else if (e.getSource() == fndmodBikeButton)
        {
            if (checkWorkerAdminStatus()) {
                myModel.stateChangeRequest("fndModBike", null); }
            else{displayMessage(messages.getString("requireAdminCred"));}
        }
        else if (e.getSource() == logoutButton) {
            processLogout();
        }
    }

    /**
     * Cancel button hit.
     * Action here is to ask the teller to switch to the main teller view.
     */
    //----------------------------------------------------------
    private void processLogout()
    {
        myModel.stateChangeRequest("Logout", null);
    }

    //---------------------------------------------------------
    public void updateState(String key, Object value)
    {
        if (key.equals("TransactionError") == true)
        {
            // display the passed text
            displayMessage((String)value);
        }
        else if (key.equals("NoRentalsFoundError") == true)
        {
            // display the passed text
            displayMessage((String)value);
        }
    }

    private void getWorkerAdminStatus(){
        workerCred = (String) myModel.getState("Credential");
        workerCred = workerCred.trim();
    }

    private boolean checkWorkerAdminStatus(){
        //return workerCred.equals("Administrator");
        return (workerCred.equals("administrator") || workerCred.equals("Administrator"));
    }
}