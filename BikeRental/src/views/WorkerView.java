package views;

/**
 * Created by Ryan on 4/2/2015.
 */

import java.awt.FlowLayout;
import java.awt.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import java.util.EventObject;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.*;

import impres.impresario.IModel;
import models.DBContentStrategy;
import org.jdatepicker.impl.JDatePickerImpl;

public class WorkerView extends View{

    // GUI stuff
    private JTextField workerIdBox;
    private JTextField firstNameBox;
    private JTextField lastNameBox;
    private JTextField phoneBox1,phoneBox2,phoneBox3;
    private JTextField emailBox;
    private JComboBox<String> credentialBox;
    private JPasswordField passwordBox;
    private JTextField regDateBox1, regDateBox2, regDateBox3;
    private JTextArea notesArea;
    private JComboBox statusBox;

    private JDatePickerImpl regDatePicker;
    //private JButton submit;
    //private JButton done;

    public WorkerView(IModel clerk)
    {
        super(clerk, "WorkerView");
        subTitleText = "AddWorkerTitle";

        // set the layout for this panel
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        // create our GUI components, add them to this panel
        add(createTitle());
        add(createDataEntryFields());
        add(createNavigationButtons());

        //add(createStatusLog("                          "));

        populateFields();

    }

    public void paint(Graphics g)
    {
        super.paint(g);
    }


    private JPanel createDataEntryFields()
    {
        JPanel main = new JPanel();
        main.setLayout(new BorderLayout());

        //Create blank JPanels for side bars
        JPanel empty = new JPanel();
        empty.setPreferredSize(new Dimension(60, 0));

        JPanel empty1 = new JPanel();
        empty1.setPreferredSize(new Dimension(60, 0));

        main.add(empty,BorderLayout.WEST);

        JPanel temp = new JPanel();
        //temp.setLayout(new GridBagLayout());
        temp.setLayout(new BoxLayout(temp, BoxLayout.Y_AXIS));

        //WorkerId Field
        JPanel temp0 = new JPanel();
        temp0.setLayout(new GridLayout(2,0,0,0));

        JLabel workerIdLabel = new JLabel(messages.getString("workerId"));
        temp0.add(workerIdLabel);

        workerIdBox = new JTextField(25);
        workerIdBox.addActionListener(this);
        temp0.add(workerIdBox);

        temp.add(temp0);

        //First Name Field
        JPanel temp1 = new JPanel();
        temp1.setLayout(new GridLayout(2,2,10,0));   // changed layout style to be more like userview -mw


        JLabel firstNameLabel = new JLabel(messages.getString("firstName"));
        temp1.add(firstNameLabel);

        JLabel lastNameLabel = new JLabel(messages.getString("lastName"));
        temp1.add(lastNameLabel);

        firstNameBox = new JTextField(15);
        firstNameBox.addActionListener(this);
        temp1.add(firstNameBox);

        lastNameBox = new JTextField(15);
        lastNameBox.addActionListener(this);
        temp1.add(lastNameBox);

        temp.add(temp1);

        JPanel emailPanel = new JPanel();
        emailPanel.setLayout(new GridLayout(2,0,0,0));
        //emailPanel.setBorder(new EmptyBorder(80, 0, 80, 0) );
        JLabel emailLabel = new JLabel(messages.getString("email"));
        emailPanel.add(emailLabel);
        emailBox = new JTextField(31);
        emailBox.addActionListener(this);
        emailPanel.add(emailBox);
        temp.add(emailPanel);
/*
        //add phone
        JPanel phonePanel = new JPanel();
        phonePanel.setLayout(new GridLayout(2,0,0,0));
        // phonePanel.setBorder(new EmptyBorder(80, 0, 80, 0) );
        JLabel phoneLabel = new JLabel(messages.getString("phone"));
        phonePanel.add(phoneLabel);

        JPanel phoneInputPanel = new JPanel();
        phoneInputPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        //phonePanel.add(new JLabel());
        //phonePanel.add(new JLabel());
        JLabel phonePlus = new JLabel("+");

        phoneInputPanel.add(phonePlus);
        phoneBox1 = new JTextField(3);
        phoneBox1.addActionListener(this);
        phoneBox1.setSize(new Dimension(0,0));

        phoneInputPanel.add(phoneBox1);
        phoneBox2 = new JTextField(20);
        //phoneBox2.setPreferredSize(new Dimension(800,20));
        phoneInputPanel.add(phoneBox2);
        phonePanel.add(phoneInputPanel);
        temp.add(phonePanel);
*/
        //Add password Field
        JPanel temp6 = new JPanel();
        temp6.setLayout(new GridLayout(2,0,0,0));
        //temp6.setLayout(new FlowLayout(FlowLayout.LEFT));

        JLabel passwordPanel = new JLabel(messages.getString("password"));
        temp6.add(passwordPanel);

        passwordBox = new JPasswordField(20);
        passwordBox.addActionListener(this);

        temp6.add(passwordBox);

        temp.add(temp6);



        //add credential field and status field

        JPanel credPanel = new JPanel();
        credPanel.setLayout(new GridLayout(2,2,10,0));
        JLabel credentialLabel = new JLabel(messages.getString("credential"));
        credPanel.add(credentialLabel);
        JLabel statusLabel = new JLabel(messages.getString("status"));
        credPanel.add(statusLabel);

        String[] creds = new String[2];
        creds[0] = messages.getString("administrator");
        creds[1] = messages.getString("user");
        credentialBox = new JComboBox(creds);
        credentialBox.setSelectedIndex(0);
        credentialBox.addActionListener(this);
        credPanel.add(credentialBox);

        String[] choices = new String[2];
        choices[0] = messages.getString("active");
        choices[1] = messages.getString("inactive");
        statusBox = new JComboBox(choices);
        statusBox.setSelectedIndex(0);
        statusBox.addActionListener(this);
        credPanel.add(statusBox);

        temp.add(credPanel);




        //Add date Field
        JPanel dateTemp = new JPanel();
        dateTemp.setLayout(new GridLayout(2,0,0,0));
        //temp6.setLayout(new FlowLayout(FlowLayout.LEFT));

        JLabel datePanel = new JLabel(messages.getString("dateOfInitialReg"));
        dateTemp.add(datePanel);

        JPanel dateTemp1 = new JPanel();
        dateTemp1.setLayout(new FlowLayout(FlowLayout.LEFT));
        regDatePicker = getDatePicker();
        dateTemp1.add(regDatePicker);

        dateTemp.add(dateTemp1);

        temp.add(dateTemp);

        //add phone
        JPanel phonePanel = new JPanel();
        phonePanel.setLayout(new GridLayout(2,0,0,0));
        // phonePanel.setBorder(new EmptyBorder(80, 0, 80, 0) );
        JLabel phoneLabel = new JLabel(messages.getString("phone"));
        phonePanel.add(phoneLabel);

        JPanel phoneInputPanel = new JPanel();
        phoneInputPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        //phonePanel.add(new JLabel());
        //phonePanel.add(new JLabel());
        JLabel phonePlus = new JLabel("+");

        phoneInputPanel.add(phonePlus);
        phoneBox1 = new JTextField(3);
        phoneBox1.addActionListener(this);
        phoneBox1.setSize(new Dimension(0,0));

        phoneInputPanel.add(phoneBox1);
        phoneBox2 = new JTextField(20);
        //phoneBox2.setPreferredSize(new Dimension(800,20));
        phoneInputPanel.add(phoneBox2);
        phonePanel.add(phoneInputPanel);
        temp.add(phonePanel);

/*
        //add credential field and status field

        JPanel credPanel = new JPanel();
        credPanel.setLayout(new GridLayout(2,2,10,0));
        JLabel credentialLabel = new JLabel(messages.getString("credential"));
        credPanel.add(credentialLabel);
        JLabel statusLabel = new JLabel(messages.getString("status"));
        credPanel.add(statusLabel);

        String[] creds = new String[2];
        creds[0] = messages.getString("administrator");
        creds[1] = messages.getString("user");
        credentialBox = new JComboBox(creds);
        credentialBox.setSelectedIndex(0);
        credentialBox.addActionListener(this);
        credPanel.add(credentialBox);

        String[] choices = new String[2];
        choices[0] = messages.getString("active");
        choices[1] = messages.getString("inactive");
        statusBox = new JComboBox(choices);
        statusBox.setSelectedIndex(0);
        statusBox.addActionListener(this);
        credPanel.add(statusBox);

        temp.add(credPanel);

*/
        //add notes section
        JPanel noteLabelPanel = new JPanel();
        noteLabelPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        JLabel notesLabel = new JLabel(messages.getString("notes"));
        noteLabelPanel.add(notesLabel);

        JPanel notePanel = new JPanel();
        notePanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        notesArea = new JTextArea(null,5,35);
        notePanel.add(notesArea);

        temp.add(noteLabelPanel);
        temp.add(notePanel);

        main.add(temp, BorderLayout.CENTER);
        main.add(empty1,BorderLayout.EAST);

        return main;
    }
    //-------------------------------------------------------------
    public void populateFields()
    {
        /*//set date fields based on the locale*****
        if(LocaleStore.getLocale().getLang().equals("fr") && LocaleStore.getLocale().getCountry().equals("FR"))
        {
            regDateBox1.setText("dd");
            regDateBox2.setText("mm");
        }
        else{
            regDateBox1.setText("mm");
            regDateBox2.setText("dd");
        }
        regDateBox3.setText("yyyy");

        // userid.setText("");
        //password.setText("");*/
    }
    public void processAction(EventObject e) {

        //clearErrorMessage();

        if(e.getSource() == submit)
        {
            String workerIdEntered = workerIdBox.getText();
            String firstNameEntered = firstNameBox.getText();
            String lastNameEntered = lastNameBox.getText();
            String emailEntered = emailBox.getText();
            String phone1Entered = phoneBox1.getText();
            String phone2Entered = phoneBox2.getText();
            String phoneEntered = "+(" + phone1Entered + ") - " + phone2Entered;
            String credEntered = DBContentStrategy.getCredentialValue(credentialBox.getSelectedIndex());

            char[] passwordValueEntered = passwordBox.getPassword();
            String passwordEntered = new String(passwordValueEntered);
            for (int cnt = 0; cnt < passwordValueEntered.length; cnt++)
            {
                passwordValueEntered[cnt] = 0;
            }

            Date selectedDate = (Date) regDatePicker.getModel().getValue();

            String notesEntered = notesArea.getText();
            String statusEntered = DBContentStrategy.getStatusValue(statusBox.getSelectedIndex());

            if((workerIdEntered == null) || (workerIdEntered.length() == 0))
            {
                displayErrorMessage(messages.getString("enterWorkerIdError"));
                workerIdBox.requestFocus();
            }
            else if ((workerIdEntered.matches("^\\d+$")!=true))
            {
                displayErrorMessage(messages.getString("workerIdNumericalError"));
                workerIdBox.requestFocus();
            }
            else if ((workerIdEntered.length() != 9))
            {
                displayErrorMessage(messages.getString("workerIdRangeError"));
                workerIdBox.requestFocus();
            }
            else if((firstNameEntered == null) || (firstNameEntered.length() == 0))
            {
                displayErrorMessage(messages.getString("enterFirstNameError"));
                firstNameBox.requestFocus();
            }
            else if((lastNameEntered == null) || (lastNameEntered.length() == 0))
            {
                displayErrorMessage(messages.getString("enterLastNameError"));
                lastNameBox.requestFocus();
            }
            else if((emailEntered == null) || (emailEntered.length() == 0))
            {
                displayErrorMessage(messages.getString("enterEmailError"));
                emailBox.requestFocus();
            }
            else if((phone1Entered.length() == 0) || phone1Entered.length() > 5)
            {
                displayErrorMessage(messages.getString("phoneFormatError"));
                phoneBox1.requestFocus();
            }
            else if((phone2Entered.length() == 0) || phone2Entered.length() > 11)
            {
                displayErrorMessage(messages.getString("phoneFormatError"));
                phoneBox2.requestFocus();
            }
            else if ((phone1Entered.matches("^\\d+$")!=true) || (phone2Entered.matches("^\\d+$")!=true))
            {
                displayErrorMessage(messages.getString("phoneNumericalError"));
                phoneBox1.requestFocus();
            }
            else if((passwordEntered == null) || (passwordEntered.length() == 0))
            {
                displayErrorMessage(messages.getString("enterPasswordError"));
                passwordBox.setText("");
                passwordBox.requestFocus();
            }
            else if(selectedDate == null)
            {
                displayErrorMessage(messages.getString("selectRegDateError"));
            }
            else
            {
                String dateEntered = dateFormatter.format(selectedDate);
                String[] values = new String[11];
                values[0] = firstNameEntered;
                values[1] = lastNameEntered;
                values[2] = emailEntered;
                values[3] = phoneEntered;
                values[4] = credEntered;
                values[5] = passwordEntered;
                values[6] = dateEntered;
                values[7] = notesEntered;
                values[8] = statusEntered;

                DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                Date d = new Date();
                String dateString = dateFormat.format(d);
                values[9] = dateString;
                values[10] = workerIdEntered;

                processInsertion(values);
            }
        }
        else
        if(e.getSource() == done)
        {
            processDone();
        }


    }
    public void processInsertion(String[] values)
    {
        Properties props = new Properties();
        props.setProperty("firstName", values[0]);
        props.setProperty("lastName", values[1]);
        props.setProperty("phoneNumber", values[2]);
        System.out.println(values[2]); //test
        props.setProperty("emailAddress", values[3]);
        props.setProperty("credential", values[4]);
        props.setProperty("password", values[5]);
        props.setProperty("dateOfInitialReg", values[6]);
        props.setProperty("notes", values[7]);
        props.setProperty("status", values[8]);
        props.setProperty("dateStatusUpdated", values[9]);
        props.setProperty("workerId", values[10]);

        myModel.stateChangeRequest("ProcessInsertion", props);
    }
    public void processDone()
    {
        myModel.stateChangeRequest("Done", null);
    }
    public void updateState(String key, Object value)
    {
        switch(key)
        {
            case "UpdateStatusMessage":
                displayErrorMessage((String)value);
                break;
            default:
                //clearErrorMessage();
                break;
        }
    }
}
