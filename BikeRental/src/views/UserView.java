package views;


import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.*;
import java.util.Properties;
import java.util.EventObject;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.*;

// project imports
import impres.impresario.IModel;

public class UserView extends View{

    // GUI stuff
    private JTextField firstNameBox;
    private JTextField lastNameBox;
    private JTextField phoneBox1,phoneBox2,phoneBox3;
    private JTextField emailBox;
    private JTextField userTypeBox;
    private JTextField memExpireBox;
    private JTextField registrationDateBox;
    private JTextField updateDateBox;
    private JTextArea notesArea;
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
        //myModel.subscribe("BackToLibrary", this);

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
        temp4.setLayout(new GridLayout(1,4,0,0));
        JLabel phoneLabel = new JLabel(messages.getString("phone"));
        phoneBox1 = new JTextField(3);
        //phoneBox1.setSize( new Dimension( 30,10 ) );
        phoneBox2 = new JTextField(3);
        // phoneBox2.setSize( new Dimension( 30, 10) );
        phoneBox3 = new JTextField(4);
        // phoneBox3.setSize( new Dimension( 30, 10 ) );
        temp4.add(phoneLabel);
        temp4.add(phoneBox1);
        temp4.add(phoneBox2);
        temp4.add(phoneBox3);
        temp.add(temp4);


        JPanel temp5 = new JPanel();
        temp5.setLayout(new GridLayout(2,1,0,0));
        JLabel emailLabel = new JLabel(messages.getString("email"));
        emailBox = new JTextField(20);
        temp5.add(emailLabel);
        temp5.add(emailBox);
        temp.add(temp5);


        JPanel temp6 = new JPanel();
        temp6.setLayout(new GridLayout(2,1,0,0));
        JLabel userTypeLabel = new JLabel(messages.getString("userType"));
        userTypeBox = new JTextField(20);
        temp6.add(userTypeLabel);
        temp6.add(userTypeBox);
        temp.add(temp6);


        JPanel temp7 = new JPanel();
        temp7.setLayout(new GridLayout(2,1,0,0));
        JLabel memExpireLabel = new JLabel(messages.getString("membershipExpire"));
        memExpireBox = new JTextField(20);
        temp7.add(memExpireLabel);
        temp7.add(memExpireBox);
        temp.add(temp7);

        JPanel temp8 = new JPanel();
        temp8.setLayout(new GridLayout(2,1,0,0));
        JLabel registrationDateLabel = new JLabel(messages.getString("dateOfInitialReg"));
        registrationDateBox = new JTextField(20);
        temp8.add(registrationDateLabel);
        temp8.add(registrationDateBox);
        temp.add(temp8);


        JPanel temp10 = new JPanel();
        temp10.setLayout(new FlowLayout(FlowLayout.CENTER));
        JLabel statLabel = new JLabel(messages.getString("status"));
        String[] choices = new String[2];
        choices[0] = messages.getString("active");
        choices[1] = messages.getString("inactive");
        statBox = new JComboBox(choices);
        statBox.setSelectedIndex(1);
        temp10.add(statLabel);
        temp10.add(statBox);
        temp.add(temp10);


        JPanel temp11 = new JPanel();
        temp11.setLayout(new GridLayout(2,1,0,0));
        JLabel updateDateLabel = new JLabel(messages.getString("dateStatusUpdated"));
        updateDateBox = new JTextField(20);
        temp11.add(updateDateLabel);
        temp11.add(updateDateBox);
        temp.add(temp11);


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
        clearErrorMessage();
        String firstName,lastName,phoneNumber,emailAddress,userType,
                memExpireDate, memRegistrationDate,statusUpdateDate, notes;
        String[] values = new String[9];
        values[0] = firstNameBox.getText();
        values[1] = lastNameBox.getText();
        values[2] = phoneBox1.getText()+phoneBox2.getText()+phoneBox3.getText();
        //System.out.println(""+ emailBox.getText());
        values[3] = emailBox.getText();
        //System.out.println(""+ values[3]);
        values[4] = userTypeBox.getText();
        values[5] = memExpireBox.getText();
        values[6] = registrationDateBox.getText();
        values[7] = updateDateBox.getText();
        values[8] = notesArea.getText();

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
            else if (values[2].length() == 0)
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
            else if (values[5].length() == 0)
            {
                displayErrorMessage(messages.getString("membershipExpireError"));
                memExpireBox.requestFocus();
            }
            else if (values[6].length() == 0)
            {
                displayErrorMessage(messages.getString("regDateError"));// check bundle
                registrationDateBox.requestFocus();
            }
            else if (values[7].length() == 0)
            {
                displayErrorMessage("Please enter an user");
                updateDateBox.requestFocus();
            }
            /* //wont need
            else if (values[8].length() == 0)
            {
                displayErrorMessage(messages.getString("regDateError"));
                notesArea.requestFocus();
            }
            */
            else
            {
                processUser(values);
                displayErrorMessage("User added Successfully");
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
        for(int i = 0; i <values.length;i++){
            System.out.println(values[i]);
        }
        System.out.println(statBox.getSelectedItem());

        Properties props = new Properties();
        props.setProperty("firstName", values[0]);
        props.setProperty("lastName", values[1]);
        props.setProperty("phoneNumber", values[2]);
        props.setProperty("emailAddress", values[3]);
        props.setProperty("userType", values[4]);
        props.setProperty("dateOfMembershipExpired", values[5]);
        props.setProperty("dateOfMembershipReg", values[6]);
        props.setProperty("status",""+statBox.getSelectedItem());
        props.setProperty("dateStatusUpdated", values[7]);
        props.setProperty("notes", values[8]);


        myModel.stateChangeRequest("ProcessUser", props);
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

