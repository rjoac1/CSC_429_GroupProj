package views;


import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import java.util.EventObject;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.*;

// project imports
import impres.impresario.IModel;
import models.LocaleStore;

public class UserView extends View{

    // GUI stuff
    private JTextField firstNameBox;
    private JTextField lastNameBox;
    private JTextField phoneBox1,phoneBox2,phoneBox3;
    private JTextField emailBox;
   // private JTextField userTypeBox;
    private JTextField memExpireBox;
    private JTextField memExpireDateBox1, memExpireDateBox2, memExpireDateBox3;
    private JTextField regDateBox1, regDateBox2, regDateBox3;
  //  private JTextField updateDateBox;
    private JTextArea notesArea;
    private JComboBox userTypeBox;
    private JComboBox statBox;
    private JButton submit;
    private JButton done;
    private JPanel temp;

    // For showing error message
    private MessageView statusLog;

    public UserView(IModel clerk){
        super(clerk, "UserView");

        // set the layout for this panel
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        // create our GUI components, add them to this panel
        add(createTitle());
        add(createDataEntryFields());
        add(createNavigationButtons());

        // Error message area
        add(createStatusLog("                          "));

        populateFields();

        // STEP 0: Be sure you tell your model what keys you are interested in
        myModel.subscribe("UpdateStatusMessage", this);

    }

    // Overide the paint method to ensure we can set the focus when made visible
    //-------------------------------------------------------------
    public void paint(Graphics g)
    {
        super.paint(g);
        //userid.requestFocus();
    }

    // Create the labels and fields
    //-------------------------------------------------------------
    protected JPanel createSubTitle()
    {
        JPanel temp = new JPanel();
        temp.setLayout(new FlowLayout(FlowLayout.CENTER));

        JLabel lbl = new JLabel(messages.getString("InsertUsers"));
        Font myFont = new Font("Helvetica", Font.BOLD, 20);
        lbl.setFont(myFont);
        temp.add(lbl);

        return temp;
    }

    // Create the main data entry fields
    //-------------------------------------------------------------
    private JPanel createDataEntryFields()
    {
        temp = new JPanel();
        // set the layout for this panel
        temp.setLayout(new BoxLayout(temp, BoxLayout.Y_AXIS));

        JPanel temp2 = new JPanel();
        temp2.setLayout(new GridLayout(2,1,0,0));
        JLabel firstName = new JLabel(messages.getString("firstName"));
        firstNameBox = new JTextField(20);
        temp2.add(firstName);
        temp2.add(firstNameBox);
        temp.add(temp2);

        JPanel temp3 = new JPanel();
        temp3.setLayout(new GridLayout(2,1,0,0));
        JLabel lastNameLabel = new JLabel(messages.getString("lastName"));
        lastNameBox = new JTextField(20);
        temp3.add(lastNameLabel);
        temp3.add(lastNameBox);
        temp.add(temp3);

        JPanel temp4 = new JPanel();
        temp4.setLayout(new GridLayout(2,3,10,10));
        JLabel phoneLabel = new JLabel(messages.getString("phone"));
        phoneBox1 = new JTextField(3);
        phoneBox2 = new JTextField(3);
        phoneBox3 = new JTextField(4);
        temp4.add(phoneLabel);
        temp4.add(phoneBox1);
        temp4.add(phoneBox2);
        temp4.add(phoneBox3);
        temp.add(temp4);

        JPanel temp5 = new JPanel();
        temp5.setLayout(new GridLayout(1,0,0,0));
        JLabel emailLabel = new JLabel(messages.getString("email"));
        emailBox = new JTextField(20);
        temp5.add(emailLabel);
        temp5.add(emailBox);
        temp.add(temp5);

        JPanel temp6 = new JPanel();
        temp6.setLayout(new FlowLayout(FlowLayout.LEFT));
        JLabel userTypeLabel = new JLabel(messages.getString("userType"));
        String[] userTypeChoices = new String[2];
        userTypeChoices[0] = messages.getString("student");
        userTypeChoices[1] = messages.getString("faculty");
        userTypeBox = new JComboBox(userTypeChoices);
        userTypeBox.setSelectedIndex(1);
        temp6.add(userTypeLabel);
        temp6.add(userTypeBox);
        temp.add(temp6);

        JPanel temp7 = new JPanel();
        JPanel temp7a = new JPanel();
        temp7.setLayout(new GridLayout(2, 1, 0, 0));
        temp7a.setLayout(new GridLayout(1,3,0,0));
        JLabel memExpireLabel = new JLabel(messages.getString("membershipExpire"));
        memExpireDateBox1 = new JTextField(2);
        memExpireDateBox2 = new JTextField(2);
        memExpireDateBox3 = new JTextField(4);
        temp7.add(memExpireLabel);
        temp7a.add(memExpireDateBox1);
        temp7a.add(memExpireDateBox2);
        temp7a.add(memExpireDateBox3);
        temp7.add(temp7a);
        temp.add(temp7);

        JPanel temp8 = new JPanel();
        JPanel temp8a = new JPanel();
        temp8.setLayout(new GridLayout(2,1,0,0));
        temp8a.setLayout(new GridLayout(1,3,0,0));
        JLabel registrationDateLabel = new JLabel(messages.getString("dateOfInitialReg"));
        regDateBox1 = new JTextField(2);
        regDateBox2 = new JTextField(2);
        regDateBox3 = new JTextField(4);
        temp8.add(registrationDateLabel);
        temp8a.add(regDateBox1);
        temp8a.add(regDateBox2);
        temp8a.add(regDateBox3);
        temp8.add(temp8a);
        temp.add(temp8);


        JPanel temp10 = new JPanel();
        temp10.setLayout(new FlowLayout(FlowLayout.LEFT));
        JLabel statLabel = new JLabel(messages.getString("status"));
        String[] choices = new String[2];
        choices[0] = messages.getString("active");
        choices[1] = messages.getString("inactive");
        statBox = new JComboBox(choices);
        statBox.setSelectedIndex(1);
        temp10.add(statLabel);
        temp10.add(statBox);
        temp.add(temp10);
/*
        JPanel temp11 = new JPanel();
        temp11.setLayout(new GridLayout(2,1,0,0));
        JLabel updateDateLabel = new JLabel(messages.getString("dateStatusUpdated"));
        updateDateBox = new JTextField(20);
        temp11.add(updateDateLabel);
        temp11.add(updateDateBox);
        temp.add(temp11);
*/
        JPanel temp9 = new JPanel();
        temp9.setLayout(new GridLayout(2,1,0,0));
        JLabel notesLabel = new JLabel(messages.getString("notes"));
        notesArea = new JTextArea(null,5,10);
        temp9.add(notesLabel);
        temp9.add(notesArea);
        temp.add(temp9);

        return temp;
    }

    // Create the navigation buttons
    //-------------------------------------------------------------
    private JPanel createNavigationButtons()
    {

        JPanel temp1 = new JPanel();
        temp1.setLayout(new FlowLayout(FlowLayout.CENTER));
        submit = new JButton(messages.getString("submit"));
        submit.addActionListener(this);
        done = new JButton(messages.getString("done"));
        done.addActionListener(this);
        temp1.add(done);
        temp1.add(submit);
        temp.add(temp1);

        //JPanel temp6 = new JPanel();
        //temp6.setLayout(new FlowLayout(FlowLayout.CENTER));
        //statusLog = new MessageView("");
        //temp6.add(statusLog);
        //temp.add(createStatusLog("     "));

        return temp;

    }
    //-------------------------------------------------------------
    public void populateFields()
    {
        //set date fields based on the locale*****
        if(LocaleStore.getLocale().getLang().equals("fr") && LocaleStore.getLocale().getCountry().equals("FR"))
        {
            memExpireDateBox1.setText("dd");
            memExpireDateBox2.setText("mm");
            regDateBox1.setText("dd");
            regDateBox2.setText("mm");
        }
        else{
            memExpireDateBox1.setText("mm");
            memExpireDateBox2.setText("dd");
            regDateBox1.setText("mm");
            regDateBox2.setText("dd");
        }
        memExpireDateBox3.setText("yyyy");
        regDateBox3.setText("yyyy");
        //userid.setText("");
        //password.setText("");
    }

    // IMPRESARIO: Note how we use this method name instead of 'actionPerformed()'
    // now. This is because the super-class View has methods for both action and
    // focus listeners, and both of them delegate to this method. So this method
    // is called when you either have an action (like a button click) or a loss
    // of focus (like tabbing out of a textfield, moving your cursor to something
    // else in the view, etc.)
    // process events generated from our GUI components
    //-------------------------------------------------------------
    public void processAction(EventObject evt)
    {
        String memExpireDateDayEntered = "";
        String memExpireDateMonthEntered = "";
        String memExpireDateYearEntered = "";
        String regDateDayEntered = "";
        String regDateMonthEntered = "";
        String regDateYearEntered = "";

        if(LocaleStore.getLocale().getLang().equals("fr") && LocaleStore.getLocale().getCountry().equals("FR"))
        {
            memExpireDateMonthEntered = memExpireDateBox1.getText();
            memExpireDateDayEntered = memExpireDateBox2.getText();
            regDateMonthEntered = regDateBox1.getText();
            regDateDayEntered = regDateBox2.getText();
        }
        else{
            memExpireDateDayEntered = memExpireDateBox1.getText();
            memExpireDateMonthEntered = memExpireDateBox2.getText();
            regDateDayEntered = regDateBox1.getText();
            regDateMonthEntered = regDateBox2.getText();

        }
        memExpireDateYearEntered = memExpireDateBox3.getText();
        regDateYearEntered = regDateBox3.getText();
        clearErrorMessage();
        String firstName,lastName,phoneNumber,emailAddress,userType,
                memExpireDate, memRegistrationDateDay,memRegistrationDateMonth,memRegistrationDateYear,statusUpdateDate, notes;
        String[] values = new String[12];
        values[0] = firstNameBox.getText();
        values[1] = lastNameBox.getText();
        values[2] = phoneBox1.getText()+phoneBox2.getText()+phoneBox3.getText();
        values[3] = emailBox.getText();
        values[4] = ""+userTypeBox.getSelectedItem();
        values[5] = memExpireDateDayEntered;
        values[6] = memExpireDateMonthEntered;
        values[7] = memExpireDateYearEntered;
        values[8] = regDateDayEntered;
        values[9] = regDateMonthEntered;
        values[10] = regDateYearEntered;
        values[11] = notesArea.getText();

        // DEBUG: System.out.println("UserView.actionPerformed()");
        if(evt.getSource() == done)
        {
            //Librarian librarian = new Librarian();
            myModel.stateChangeRequest("Done", null);
        }
        else if(evt.getSource() == submit){
            if (values[0].length() == 0)
            {
                displayErrorMessage(messages.getString("enterFirstNameError"));
                firstNameBox.requestFocus();
            }
            else if (values[1].length() == 0)
            {
                displayErrorMessage(messages.getString("enterLastNameError"));
                lastNameBox.requestFocus();
            }
            else if (values[2].length() == 0 || phoneBox1.getText().length() != 3
                        || phoneBox2.getText().length() != 3 ||  phoneBox3.getText().length() != 4)
            {
                displayErrorMessage(messages.getString("phoneFormatError"));
                phoneBox1.requestFocus();
            }
            else if ((phoneBox1.getText().matches("^\\d+$")!=true) || (phoneBox2.getText().matches("^\\d+$")!=true) || (phoneBox3.getText().matches("^\\d+$") != true))
            {
                displayErrorMessage(messages.getString("phoneNumericalError"));
                phoneBox1.requestFocus();
            }
            else if (values[3].length() == 0)
            {
                displayErrorMessage(messages.getString("enterEmailError"));
                emailBox.requestFocus();
            }
            else if (values[4].length() == 0)
            {
                displayErrorMessage(messages.getString("userTypeError"));
                userTypeBox.requestFocus();
            }
           // else if (values[5].length() == 0)
            //{
            //    displayErrorMessage(messages.getString("membershipExpireError"));
            //    memExpireBox.requestFocus();
           // }


         //***************************************************
            else if (values[5].length() == 0)
            {
                displayErrorMessage(messages.getString("memExpireDateError"));// check bundle
                memExpireDateBox1.requestFocus();
            }
            else if (values[6].length() == 0)
            {
                displayErrorMessage(messages.getString("memExpireDateError"));// check bundle
                memExpireDateBox2.requestFocus();
            }
            else if (values[7].length() == 0)
            {
                displayErrorMessage(messages.getString("memExpireDateError"));// check bundle
                memExpireDateBox3.requestFocus();
            }

            else if((values[5].matches("^\\d+$") != true) || (values[6].matches("^\\d+$") != true) || (values[7].matches("^\\d+$") != true))
            {
                displayErrorMessage(messages.getString("memExpireDateNumericalError"));
                memExpireDateBox1.requestFocus();
            }
            else if(values[7].length() > 4)
            {
                displayErrorMessage(messages.getString("memExpireDateYearLengthError"));
                memExpireDateBox3.requestFocus();
            }
            else if(values[6].length() > 2)
            {
                displayErrorMessage(messages.getString("memExpireMonthLengthError"));
                memExpireDateBox2.requestFocus();
            }
            else if(values[5].length() > 2)
            {
                displayErrorMessage(messages.getString("memExpireDayLengthError"));
                memExpireDateBox1.requestFocus();
            }
            else if((Integer.parseInt(values[6]) < 1) || (Integer.parseInt(values[6]) > 12))
            {
                displayErrorMessage(messages.getString("memExpireMonthRangeError"));
                regDateBox2.requestFocus();
            }
            else if((Integer.parseInt(values[5]) < 1) || (Integer.parseInt(values[5]) > 31))
            {
                displayErrorMessage(messages.getString("memExpireDayRangeError"));
                regDateBox1.requestFocus();
            }
         //****************************************************
            else if (values[8].length() == 0)
            {
                displayErrorMessage(messages.getString("regDateError"));// check bundle
                regDateBox1.requestFocus();
            }
            else if (values[9].length() == 0)
            {
                displayErrorMessage(messages.getString("regDateError"));// check bundle
                regDateBox2.requestFocus();
            }
            else if (values[10].length() == 0)
            {
                displayErrorMessage(messages.getString("regDateError"));// check bundle
                regDateBox3.requestFocus();
            }

            else if((values[8].matches("^\\d+$") != true) || (values[9].matches("^\\d+$") != true) || (values[10].matches("^\\d+$") != true))
            {
                displayErrorMessage(messages.getString("regDateNumericalError"));
                regDateBox1.requestFocus();
            }
            else if(values[10].length() > 4)
            {
                displayErrorMessage(messages.getString("regDateYearLengthError"));
                regDateBox3.requestFocus();
            }
            else if(values[9].length() > 2)
            {
                displayErrorMessage(messages.getString("regDateMonthLengthError"));
                regDateBox2.requestFocus();
            }
            else if(values[8].length() > 2)
            {
                displayErrorMessage(messages.getString("regDateDayLengthError"));
                regDateBox1.requestFocus();
            }
            else if((Integer.parseInt(values[9]) < 1) && (Integer.parseInt(values[9]) > 12))
            {
                displayErrorMessage(messages.getString("regDateMonthRangeError"));
                regDateBox2.requestFocus();
            }
            else if((Integer.parseInt(values[8]) < 1) && (Integer.parseInt(values[8]) > 31))
            {
                displayErrorMessage(messages.getString("regDateDayRangeError"));
                regDateBox1.requestFocus();
            }
            else
            {
                processUser(values);
            }
        }
    }

    /**
     * Process userid and pwd supplied when Submit button is hit.
     * Action is to pass this info on to the teller object
     */
    //----------------------------------------------------------
    private void processUser(String[] values)
    {
        Properties props = new Properties();
        props.setProperty("firstName", values[0]);
        props.setProperty("lastName", values[1]);
        props.setProperty("phoneNumber", values[2]);
        props.setProperty("emailAddress", values[3]);
        props.setProperty("userType", values[4]);
        props.setProperty("dateOfMembershipExpired", values[7] + "-" +  values[6] + "-" +  values[5] );
        props.setProperty("dateOfMembershipReg", values[10] + "-" +  values[9] + "-" +  values[8] );
        props.setProperty("status",""+statBox.getSelectedItem());

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        String dateString = dateFormat.format(date);

        props.setProperty("dateStatusUpdated",dateString);
        props.setProperty("notes", values[9]);

        myModel.stateChangeRequest("ProcessInsertion", props);
    }

    //---------------------------------------------------------
    public void updateState(String key, Object value)
    {
        // STEP 6: Be sure to finish the end of the 'perturbation'
        // by indicating how the view state gets updated.
        if (key.equals("UpdateStatusMessage") == true)
        {            // display the passed text
            displayErrorMessage((String)value);
        }
        else
        {
            clearErrorMessage();
        }

    }
}

