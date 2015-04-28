package views;

/**
 * Created by Ryan on 4/2/2015.
 */

import java.awt.FlowLayout;
import java.awt.*;
import java.util.Arrays;
import java.util.Properties;
import java.util.EventObject;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.*;

import impres.impresario.IModel;
import org.jdatepicker.impl.JDatePickerImpl;

public class WorkerView extends View {

    // GUI stuff
    private JTextField workerIdBox;
    private JTextField firstNameBox;
    private JTextField lastNameBox;
    private JTextField countryCodeBox, phoneNumberBox;
    private JTextField emailBox;
    private JComboBox<ComboxItem> credentialBox;
    private JComboBox<ComboxItem> statusBox;
    private JPasswordField passwordBox;
    private JTextArea notesArea;
    private JDatePickerImpl regDatePicker;

    public WorkerView(IModel clerk) {
        super(clerk, "WorkerView");
        subTitleText = "AddWorkerTitle";

        // set the layout for this panel
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        // create our GUI components, add them to this panel
        add(createTitle());
        add(createDataEntryFields());
        add(createNavigationButtons());

        mSubmitWrapper.addAll(Arrays.asList(
                new SubmitWrapper("firstName", firstNameBox, textGetter, empty),
                new SubmitWrapper("workerId", workerIdBox, textGetter, empty),
                new SubmitWrapper("lastName", lastNameBox, textGetter, empty),
                new SubmitWrapper("phoneNumber", phoneNumberBox, textGetter, phoneNumberValidator),
                new SubmitWrapper("countryCode", countryCodeBox, textGetter, countryCodeValidator),
                new SubmitWrapper("emailAddress", emailBox, textGetter, emailValidator),
                new SubmitWrapper("status", statusBox, comboGetter, empty),
                new SubmitWrapper("credential", credentialBox, comboGetter, empty),
                new SubmitWrapper("password", passwordBox, textGetter, empty),
                new SubmitWrapper("dateOfInitialReg", regDatePicker, dateGetter, empty),
                new SubmitWrapper("notes", notesArea, textAreaGetter, ok),
                new SubmitWrapper("dateStatusUpdated", null, dateNowGetter, ok)));
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
    }

    private JPanel createDataEntryFields() {
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
        countryCodeBox = new JTextField(3);
        countryCodeBox.addActionListener(this);
        countryCodeBox.setSize(new Dimension(0,0));

        phoneInputPanel.add(countryCodeBox);
        phoneNumberBox = new JTextField(20);
        //phoneNumberBox.setPreferredSize(new Dimension(800,20));
        phoneInputPanel.add(phoneNumberBox);
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

        credentialBox = new JComboBox<>();
        populateComboxBox(credentialBox, new String[]{"administrator", "user"});

        credentialBox.setSelectedIndex(0);
        credentialBox.addActionListener(this);
        credPanel.add(credentialBox);

        statusBox = new JComboBox<>();
        populateComboxBox(statusBox, new String[]{"active", "inactive"});
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
        countryCodeBox = new JTextField(3);
        countryCodeBox.addActionListener(this);
        countryCodeBox.setSize(new Dimension(0, 0));

        phoneInputPanel.add(countryCodeBox);
        phoneNumberBox = new JTextField(20);
        phoneInputPanel.add(phoneNumberBox);
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
        notesArea = new JTextArea(null,5,40);
        notePanel.add(notesArea);

        temp.add(noteLabelPanel);
        temp.add(notePanel);

        main.add(temp, BorderLayout.CENTER);
        main.add(empty1,BorderLayout.EAST);

        return main;
    }

    @Override
    public void processAction(EventObject e) {
        if (e.getSource() == done) {
            processDone();
        } else if (e.getSource() == submit) {
            processInsertion();

        }
    }

    public void processInsertion() {
        Properties props = getProperties();
        System.err.println(props);
        myModel.stateChangeRequest("ProcessInsertion", props);
    }

    public void processDone() {
        myModel.stateChangeRequest("Done", null);
    }

}
