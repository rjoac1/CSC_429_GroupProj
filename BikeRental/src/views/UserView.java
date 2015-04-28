package views;


import java.awt.FlowLayout;
import java.awt.*;
import java.util.Arrays;
import java.util.Properties;
import java.util.EventObject;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.*;

// project imports
import impres.impresario.IModel;
import org.jdatepicker.impl.JDatePickerImpl;

public class UserView extends View{

    // GUI stuff
    private JTextField firstNameBox;
    private JTextField lastNameBox;
    private JTextField phoneNumberBox, countryCodeBox;
    private JTextField emailBox;
    private JTextArea notesArea;
    private JComboBox<ComboxItem> userTypeBox;
    private JComboBox<ComboxItem> statBox;
    private JDatePickerImpl memExpireDatePicker;
    private JDatePickerImpl regDatePicker;

    public UserView(IModel clerk) {
        super(clerk, "UserView");
        subTitleText = "InsertUsers";

        // set the layout for this panel
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        // create our GUI components, add them to this panel
        add(createTitle());
        add(createDataEntryFields());
        add(createNavigationButtons());

        mSubmitWrapper.addAll(Arrays.asList(
            new SubmitWrapper("firstName", firstNameBox, textGetter, empty),
            new SubmitWrapper("lastName", lastNameBox, textGetter, empty),
            new SubmitWrapper("countryCode", phoneNumberBox, textGetter, countryCodeValidator),
            new SubmitWrapper("phoneNumber", countryCodeBox, textGetter, phoneNumberValidator),
            new SubmitWrapper("emailAddress", emailBox, textGetter, emailValidator),
            new SubmitWrapper("userType", userTypeBox, comboGetter, empty),
            new SubmitWrapper("status", statBox, comboGetter, empty),
            new SubmitWrapper("dateOfMembershipExpired", memExpireDatePicker, dateGetter, empty),
            new SubmitWrapper("dateOfMembershipReg", regDatePicker, dateGetter, empty),
            new SubmitWrapper("notes", notesArea, textAreaGetter, ok),
            new SubmitWrapper("dateStatusUpdated", null, dateNowGetter, ok)
        ));
    }

    // Overide the paint method to ensure we can set the focus when made visible
    //-------------------------------------------------------------
    @Override
    public void paint(Graphics g) {
        super.paint(g);
    }

    // Create the main data entry fields
    //-------------------------------------------------------------
    private JPanel createDataEntryFields() {
        JPanel temp = new JPanel();
        // set the layout for this panel
        temp.setLayout(new BoxLayout(temp, BoxLayout.Y_AXIS));

        JPanel tempMain = new JPanel();
        tempMain.setLayout(new BorderLayout());

        JPanel tempSetup = new JPanel();
        tempSetup.setLayout(new BoxLayout(tempSetup, BoxLayout.Y_AXIS));
       // tempSetup.setBorder(new EmptyBorder(0, 0, 0, 0) );
        JPanel empty = new JPanel();
        empty.setPreferredSize(new Dimension(eastWestBufferParam1, eastWestBufferParam2));

        JPanel empty1 = new JPanel();
        empty1.setPreferredSize(new Dimension(eastWestBufferParam1, eastWestBufferParam2));
        tempMain.add(empty,BorderLayout.WEST);

        //add first name
        JPanel namePanel = new JPanel();
        namePanel.setLayout(new GridLayout(2,2,8,0));
       // namePanel.setBorder(new EmptyBorder(80, 0,80, 0) );
        JLabel firstName = new JLabel(messages.getString("firstName"));
        namePanel.add(firstName);

        //add last name
        JLabel lastNameLabel = new JLabel(messages.getString("lastName"));
        namePanel.add(lastNameLabel);
        firstNameBox = new JTextField(15);
        namePanel.add(firstNameBox);
        lastNameBox = new JTextField(15);
        namePanel.add(lastNameBox);
        tempSetup.add(namePanel);

        //add email
        JPanel emailPanel = new JPanel();
        emailPanel.setLayout(new GridLayout(2,0,0,0));
        //emailPanel.setBorder(new EmptyBorder(80, 0, 80, 0) );
        JLabel emailLabel = new JLabel(messages.getString("email"));
        emailPanel.add(emailLabel);
        emailBox = new JTextField(31);
        emailPanel.add(emailBox);
        tempSetup.add(emailPanel);

        //add userType and status
        JPanel dropPanel = new JPanel();
        dropPanel.setLayout(new GridLayout(2,2,10,0));
        JLabel userTypeLabel = new JLabel(messages.getString("userType"));

        userTypeBox = new JComboBox<>();
        populateComboxBox(userTypeBox, new String[] {"student", "faculty"});
        userTypeBox.setPreferredSize(new Dimension(167,25));
        userTypeBox.setSelectedIndex(1);

        JLabel statLabel = new JLabel(messages.getString("status"));

        statBox = new JComboBox<>();
        populateComboxBox(statBox, new String[] {"active", "inactive"});
        statBox.setPreferredSize(new Dimension(167,25));
        statBox.setSelectedIndex(1);

        dropPanel.add(userTypeLabel);
        dropPanel.add(statLabel);
        dropPanel.add(userTypeBox);
        dropPanel.add(statBox);
        tempSetup.add(dropPanel);

        JPanel temp7 = new JPanel();
        //JPanel temp7a = new JPanel();
        temp7.setLayout(new GridLayout(2,2, 10, 0));


        JLabel registrationDateLabel = new JLabel(messages.getString("dateOfInitialReg"));
        temp7.add(registrationDateLabel);
        JLabel memExpireLabel = new JLabel(messages.getString("membershipExpire"));
        temp7.add(memExpireLabel);

        //JPanel dateTemp1 = new JPanel();
        //dateTemp1.setLayout(new FlowLayout((FlowLayout.LEFT)));
        regDatePicker = getDatePicker();
        //dateTemp1.add(regDatePicker);
        temp7.add(regDatePicker);

        //JPanel dateTemp2 = new JPanel();
        //dateTemp2.setLayout(new FlowLayout(FlowLayout.LEFT));
        memExpireDatePicker = getDatePicker();
        //dateTemp2.add(memExpireDatePicker);
        temp7.add(memExpireDatePicker);
        tempSetup.add(temp7);


        //JPanel temp8 = new JPanel();
       // temp8.setLayout(new GridLayout(2,1,0,0));
        //JLabel registrationDateLabel = new JLabel(messages.getString("dateOfInitialReg"));
        //temp7.add(registrationDateLabel);

       // JPanel dateTemp2 = new JPanel();
        //dateTemp2.setLayout(new FlowLayout((FlowLayout.LEFT)));
        //regDatePicker = getDatePicker();
        //dateTemp2.add(regDatePicker);
        //temp7.add(dateTemp2);
        //temp8.add(registrationDateLabel);

        //temp8.add(regDatePicker);
        tempSetup.add(temp7);


        //add phone
        JPanel phonePanel = new JPanel();
        phonePanel.setLayout(new GridLayout(2,1,0,0));
       // phonePanel.setBorder(new EmptyBorder(80, 0, 80, 0) );
        JLabel phoneLabel = new JLabel(messages.getString("phone"));
        phonePanel.add(phoneLabel);

        tempSetup.add(phonePanel);

        JPanel phoneInputPanel = new JPanel();
        phoneInputPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        //phonePanel.add(new JLabel());
        //phonePanel.add(new JLabel());
        JLabel phonePlus = new JLabel("+");

        phoneInputPanel.add(phonePlus);
        phoneNumberBox = new JTextField(3);
        phoneNumberBox.setSize(new Dimension(0, 0));

        phoneInputPanel.add(phoneNumberBox);
        countryCodeBox = new JTextField(20);
        //countryCodeBox.setPreferredSize(new Dimension(800,20));
        phoneInputPanel.add(countryCodeBox);
        phonePanel.add(phoneInputPanel);
        tempSetup.add(phonePanel);

        JPanel noteLabelPanel = new JPanel();
        noteLabelPanel.setLayout(new BorderLayout());
        JLabel notesLabel = new JLabel(messages.getString("notes"));
        noteLabelPanel.add(notesLabel, BorderLayout.NORTH);

        notesArea = new JTextArea(null,5,42);

        noteLabelPanel.add(notesArea,BorderLayout.CENTER);
        tempSetup.add(noteLabelPanel);

        tempMain.add(tempSetup, BorderLayout.CENTER);
        tempMain.add(empty1,BorderLayout.EAST);
        temp.add(tempMain);
        return temp;
    }

    @Override
    public void processAction(EventObject e) {
        if (e.getSource() == submit) {
            processInsertionOfNewUser();
        } else if (e.getSource() == done) {
            processDone();
        }
    }
    private void processInsertionOfNewUser() {
        final Properties props = getProperties();
        if (props != null)
            myModel.stateChangeRequest("ProcessInsertion", props);
    }
    private void processDone() {
        myModel.stateChangeRequest("Done", null);
    }

}

