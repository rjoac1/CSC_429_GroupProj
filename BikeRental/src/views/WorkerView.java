package views;

/**
 * Created by Ryan on 4/2/2015.
 */

import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.*;
import java.util.Locale;
import java.util.Properties;
import java.util.EventObject;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.*;

import impres.impresario.IModel;
import models.LocaleStore;

public class WorkerView extends View{

    // GUI stuff
    private JTextField firstNameBox;
    private JTextField lastNameBox;
    private JTextField phoneBox1,phoneBox2,phoneBox3;
    private JTextField emailBox;
    private JComboBox<String> credentialBox;
    private JPasswordField passwordBox;
    private JTextField regDateBox1, regDateBox2, regDateBox3;
    private JTextArea notesArea;
    private JComboBox statusBox;
    private JButton submit;
    private JButton done;

    public WorkerView(IModel clerk)
    {
        super(clerk, "WorkerView");

        // set the layout for this panel
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        // create our GUI components, add them to this panel
        add(createTitle());
        add(createDataEntryFields());
        add(createNavigationButtons());

        add(createStatusLog("                          "));

        populateFields();
    }

    public void paint(Graphics g)
    {
        super.paint(g);
    }
    protected JPanel createSubTitle()
    {
        JPanel temp = new JPanel();
        temp.setLayout(new FlowLayout(FlowLayout.CENTER));

        JLabel lbl = new JLabel(messages.getString("AddWorkerTitle"));
        Font myFont = new Font("Helvetica", Font.BOLD, 15);
        lbl.setFont(myFont);
        temp.add(lbl);

        return temp;
    }
    private JPanel createDataEntryFields()
    {
        JPanel temp = new JPanel();
        temp.setLayout(new BoxLayout(temp, BoxLayout.Y_AXIS));

        //First Name Field
        JPanel temp1 = new JPanel();
        temp1.setLayout(new FlowLayout(FlowLayout.LEFT));

        JLabel firstNameLabel = new JLabel(messages.getString("firstName"));
        temp1.add(firstNameLabel);

        firstNameBox = new JTextField(25);
        firstNameBox.addActionListener(this);
        temp1.add(firstNameBox);

        //Last Name Field
        JPanel temp2 = new JPanel();
        temp2.setLayout(new FlowLayout(FlowLayout.LEFT));

        JLabel lastNameLabel = new JLabel(messages.getString("lastName"));
        temp2.add(lastNameLabel);

        lastNameBox = new JTextField(30);
        lastNameBox.addActionListener(this);
        temp2.add(lastNameBox);

        temp.add(temp2);

        //add email field
        JPanel temp3 = new JPanel();
        temp3.setLayout(new FlowLayout(FlowLayout.LEFT));

        JLabel emailLabel = new JLabel(messages.getString("email"));
        temp3.add(emailLabel);

        emailBox = new JTextField(30);
        emailBox.addActionListener(this);
        temp3.add(emailBox);

        temp.add(temp3);

        //add phone field
        JPanel temp4 = new JPanel();
        temp4.setLayout(new FlowLayout(FlowLayout.LEFT));

        JLabel phoneLabel = new JLabel(messages.getString("phone"));
        temp4.add(phoneLabel);

        phoneBox1 = new JTextField(3);
        phoneBox1.addActionListener(this);
        temp4.add(phoneBox1);

        phoneBox2 = new JTextField(3);
        phoneBox2.addActionListener(this);
        temp4.add(phoneBox2);

        phoneBox3 = new JTextField(4);
        phoneBox3.addActionListener(this);
        temp4.add(phoneBox3);

        temp.add(temp4);

        //Add credential field
        JPanel temp5 = new JPanel();
        temp5.setLayout(new FlowLayout(FlowLayout.LEFT));

        JLabel credentialPanel = new JLabel(messages.getString("credential"));
        temp5.add(credentialPanel);

        credentialBox = new JComboBox<String>();
        credentialBox.addItem(messages.getString("administrator"));
        credentialBox.addItem(messages.getString("user"));
        credentialBox.addActionListener(this);
        temp5.add(credentialBox);

        temp.add(temp5);

        //Add password Field
        JPanel temp6 = new JPanel();
        temp6.setLayout(new FlowLayout(FlowLayout.LEFT));

        JLabel passwordPanel = new JLabel(messages.getString("password"));
        temp6.add(passwordPanel);

        passwordBox = new JPasswordField();
        passwordBox.addActionListener(this);

        temp6.add(passwordBox);

        temp.add(temp6);

        //Add Date of initial registration field
        JPanel temp7 = new JPanel();
        temp7.setLayout(new FlowLayout(FlowLayout.LEFT));

        JLabel dateOfInitialRegPanel = new JLabel(messages.getString("dateOfInitialReg"));
        temp7.add(dateOfInitialRegPanel);

        regDateBox1 = new JTextField(2);
        regDateBox1.addActionListener(this);
        temp7.add(regDateBox1);

        regDateBox2 = new JTextField(2);
        regDateBox2.addActionListener(this);
        temp7.add(regDateBox2);

        regDateBox3 = new JTextField(4);
        regDateBox3.addActionListener(this);
        temp7.add(regDateBox3);

        temp.add(temp7);

        //Add notes field
        JPanel temp8 = new JPanel();
        temp8.setLayout(new FlowLayout(FlowLayout.LEFT));

        JLabel notesLabel = new JLabel(messages.getString("notes"));
        temp8.add(notesLabel);

        notesArea = new JTextArea(null,5,10);
        temp8.add(notesArea);

        temp.add(temp8);

        //add Status box
        JPanel temp9 = new JPanel();
        temp9.setLayout(new FlowLayout(FlowLayout.LEFT));

        JLabel statusLabel = new JLabel(messages.getString("status"));
        temp9.add(statusLabel);

        String[] choices = new String[2];
        choices[1] = messages.getString("active");
        choices[2] = messages.getString("inactive");
        statusBox = new JComboBox(choices);
        statusBox.setSelectedIndex(1);
        statusBox.addActionListener(this);
        temp9.add(statusBox);

        temp.add(temp9);

        return temp;
    }
    private JPanel createNavigationButtons()
    {
        JPanel temp1 = new JPanel();
        temp1.setLayout(new FlowLayout(FlowLayout.CENTER));
        submit = new JButton("Submit");
        submit.addActionListener(this);
        done = new JButton("Done");
        done.addActionListener(this);
        temp1.add(submit);
        temp1.add(done);
        return temp1;
    }
    //-------------------------------------------------------------
    public void populateFields()
    {
        //userid.setText("");
        //password.setText("");
    }
    public void processAction(EventObject e) {

        clearErrorMessage();

        if(e.getSource() == done)
        {
            String firstNameEntered = firstNameBox.getText();
            String lastNameEntered = lastNameBox.getText();
            String emailEntered = emailBox.getText();
            String phone1Entered = phoneBox1.getText();
            String phone2Entered = phoneBox2.getText();
            String phone3Entered = phoneBox3.getText();
            String credEntered = (String)credentialBox.getSelectedItem();

            char[] passwordValueEntered = passwordBox.getPassword();
            String passwordEntered = new String(passwordValueEntered);

            for (int cnt = 0; cnt < passwordValueEntered.length; cnt++)
            {
                passwordValueEntered[cnt] = 0;
            }

            String regDateMonthEntered = "";
            String regDateDayEntered = "";

            if(LocaleStore.getLocale().equals(new Locale("fr","FR")))
            {
                regDateDayEntered = regDateBox1.getText();
                regDateMonthEntered = regDateBox2.getText();
            }
            else{
                regDateMonthEntered = regDateBox1.getText();
                regDateDayEntered = regDateBox2.getText();
            }

            String regDateYearEntered = regDateBox3.getText();

            String notesEntered = notesArea.getText();
            String statusEntered = (String)statusBox.getSelectedItem();

            if((firstNameEntered == null) || (firstNameEntered.length() == 0))
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
                city.requestFocus();
            }
            else if((stateCodeEntered == "--"))
            {
                displayErrorMessage("Please select a state.");
                stateCode.requestFocus();
            }
            else if((zipEntered == null) || (zipEntered.length() == 0))
            {
                displayErrorMessage("Please enter a zip code.");
                zip.requestFocus();
            }
            else if((zipEntered.length() > 5))
            {
                displayErrorMessage("Zip code cannot exceed 5 characters.");
                zip.requestFocus();
            }
            else if((zipEntered.matches("^\\d+$") != true))
            {
                displayErrorMessage("Zip code must be a numerical value.");
                zip.requestFocus();
            }
            else if((emailEntered == null) || (emailEntered.length() == 0))
            {
                displayErrorMessage("Please enter an email address.");
                email.requestFocus();
            }
            else if((dobEntered.length() == 0))
            {
                displayErrorMessage("Please enter a date of birth.");
                dobYear.requestFocus();
            }
            else if((dobYearEntered.matches("^\\d+$") != true) || (dobMonthEntered.matches("^\\d+$") != true) || (dobDayEntered.matches("^\\d+$") != true))
            {
                displayErrorMessage("Date of birth must be a numerical value.");
                dobYear.requestFocus();
            }
            else if(dobYearEntered.length() > 4)
            {
                displayErrorMessage("'Year' field in date of birth cannot exceed 4 characters.");
                dobYear.requestFocus();
            }
            else if(dobMonthEntered.length() > 2)
            {
                displayErrorMessage("'Month' field in date of birth cannot exceed 2 characters.");
                dobMonth.requestFocus();
            }
            else if(dobDayEntered.length() > 2)
            {
                displayErrorMessage("'Day' field in date of birth cannot exceed 2 characters.");
                dobDay.requestFocus();
            }
            else if((Integer.parseInt(dobMonthEntered) < 1) || (Integer.parseInt(dobMonthEntered) > 12))
            {
                displayErrorMessage("'Month' field in date of birth must be between 1 and 12.");
                dobMonth.requestFocus();
            }
            else if((Integer.parseInt(dobDayEntered) < 1) || (Integer.parseInt(dobDayEntered) > 31))
            {
                displayErrorMessage("'Day' field in date of birth must be between 1 - 31.");
                dobDay.requestFocus();
            }
            else if ((Integer.parseInt(dobYearEntered) < 1913) || (Integer.parseInt(dobYearEntered) >= 1996))
            {
                displayErrorMessage("Please enter a date of birth between 1913-01-01 and 1996-01-01.");
                dobYear.requestFocus();
            }
            else
            {
                processInsertionOfNewPatron(nameEntered, addressEntered, cityEntered, stateCodeEntered, zipEntered, emailEntered, dobEntered, statusEntered);
            }
        }
        else
        if(e.getSource() == doneButton)
        {
            processDone();
        }


    }
    public void updateState(String key, Object value)
    {
        switch(key)
        {
            case "UpdateStatusMessage":
                displayErrorMessage((String)value);
                break;
            default:
                clearErrorMessage();
                break;
        }
    }
}
