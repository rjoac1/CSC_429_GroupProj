//Specify the package
package userinterface;

//System imports
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Component;
import java.awt.Dimension;
import java.util.Properties;
import java.util.EventObject;
import javax.swing.JComboBox;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.Box;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.text.NumberFormat;
import java.util.Vector;
//project imports
import impresario.IModel;

/**Class containing Librarian View for Library System application*/
//==============================================================
public class PatronView extends View
{
    //GUI Stuff
    private JTextField name;
    private JTextField address;
    private JTextField city;
    private JTextField email;
    private JTextField zip;
    private JTextField dobYear;
    private JTextField dobMonth;
    private JTextField dobDay;

    private JComboBox status;
    private JComboBox stateCode;


    private Vector<String> statusOptions;
    private Vector<String> stateCodeOptions;


    private JButton submitButton;
    private JButton doneButton;

    // For showing error message
    private MessageView statusLog;

    public PatronView(IModel patron)
    {
        super(patron, "PatronView");

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        add(createTitle());
        add(createDataEntryFields());
        add(createNavigationButtons());

        //error message area
        add(createStatusLog("                                        "));

        populateFields();

        myModel.subscribe("UpdateStatusMessage", this);
    }
    public void paint(Graphics g)
    {
        super.paint(g);
    }

    //create Title
    private JPanel createTitle()
    {
        JPanel temp = new JPanel();
        temp.setLayout(new FlowLayout(FlowLayout.CENTER));

        JLabel lbl = new JLabel("INSERT NEW PATRON");
        Font myFont = new Font("Helvetica", Font.BOLD, 20);
        lbl.setFont(myFont);
        temp.add(lbl);

        return temp;
    }

    private JPanel createDataEntryFields()
    {
        JPanel temp = new JPanel();
        //setLayout for panel
        temp.setLayout(new BoxLayout(temp, BoxLayout.Y_AXIS));

        //data entry Fields
        //Name
        JPanel temp1 = new JPanel();
        temp1.setLayout(new FlowLayout(FlowLayout.LEFT));

        JLabel nameLabel = new JLabel("Name : ");
        temp1.add(nameLabel);

        name = new JTextField(35);
        name.addActionListener(this);
        temp1.add(name);

        temp.add(temp1);

        //address
        JPanel temp2 = new JPanel();
        temp2.setLayout(new FlowLayout(FlowLayout.LEFT));

        JLabel addressLabel = new JLabel("Address : ");
        temp2.add(addressLabel);

        address = new JTextField(35);
        address.addActionListener(this);
        temp2.add(address);

        temp.add(temp2);

        //city
        JPanel temp3 = new JPanel();
        temp3.setLayout(new FlowLayout(FlowLayout.LEFT));

        JLabel cityLabel = new JLabel("City : ");
        temp3.add(cityLabel);

        city = new JTextField(35);
        city.addActionListener(this);
        temp3.add(city);

        temp.add(temp3);

        //state JCombobox
        JPanel temp4 = new JPanel();
        temp4.setLayout(new FlowLayout(FlowLayout.LEFT));

        JLabel stateCodeLabel = new JLabel("State : ");
        temp4.add(stateCodeLabel);

        stateCodeOptions = new Vector<String>();
        stateCodeOptions.add("--");
        stateCodeOptions.add("AL");
        stateCodeOptions.add("AK");
        stateCodeOptions.add("AZ");
        stateCodeOptions.add("AR");
        stateCodeOptions.add("CA");
        stateCodeOptions.add("CO");
        stateCodeOptions.add("CT");
        stateCodeOptions.add("DE");
        stateCodeOptions.add("DC");
        stateCodeOptions.add("FL");
        stateCodeOptions.add("GA");
        stateCodeOptions.add("HI");
        stateCodeOptions.add("ID");
        stateCodeOptions.add("IL");
        stateCodeOptions.add("IN");
        stateCodeOptions.add("IA");
        stateCodeOptions.add("KS");
        stateCodeOptions.add("KY");
        stateCodeOptions.add("LA");
        stateCodeOptions.add("ME");
        stateCodeOptions.add("MD");
        stateCodeOptions.add("MA");
        stateCodeOptions.add("MI");
        stateCodeOptions.add("MN");
        stateCodeOptions.add("MS");
        stateCodeOptions.add("MO");
        stateCodeOptions.add("MT");
        stateCodeOptions.add("NE");
        stateCodeOptions.add("NV");
        stateCodeOptions.add("NH");
        stateCodeOptions.add("NJ");
        stateCodeOptions.add("NM");
        stateCodeOptions.add("NY");
        stateCodeOptions.add("NC");
        stateCodeOptions.add("ND");
        stateCodeOptions.add("OH");
        stateCodeOptions.add("OK");
        stateCodeOptions.add("OR");
        stateCodeOptions.add("PA");
        stateCodeOptions.add("RI");
        stateCodeOptions.add("SC");
        stateCodeOptions.add("SD");
        stateCodeOptions.add("TN");
        stateCodeOptions.add("TX");
        stateCodeOptions.add("UT");
        stateCodeOptions.add("VT");
        stateCodeOptions.add("VA");
        stateCodeOptions.add("WA");
        stateCodeOptions.add("WV");
        stateCodeOptions.add("WI");
        stateCodeOptions.add("WY");

        stateCode = new JComboBox(stateCodeOptions);
        stateCode.setMaximumRowCount(5);
        stateCode.addActionListener(this);
        temp4.add(stateCode);

        temp.add(temp4);

        //zip
        JPanel temp5 = new JPanel();
        temp5.setLayout(new FlowLayout(FlowLayout.LEFT));

        JLabel zipLabel = new JLabel("ZipCode : ");
        temp5.add(zipLabel);

        zip = new JTextField(4);
        zip.addActionListener(this);
        temp5.add(zip);

        temp.add(temp5);

        //email
        JPanel temp6 = new JPanel();
        temp6.setLayout(new FlowLayout(FlowLayout.LEFT));

        JLabel emailLabel = new JLabel("Email : ");
        temp6.add(emailLabel);

        email = new JTextField(35);
        email.addActionListener(this);
        temp6.add(email);

        temp.add(temp6);

        //Date of Birth
        JPanel temp7 = new JPanel();
        temp7.setLayout(new FlowLayout(FlowLayout.LEFT));

        JLabel dobLabel = new JLabel("Date Of Birth : ");
        temp7.add(dobLabel);

        JLabel dobSpacer = new JLabel(" - ");
        JLabel dobSpacer2 = new JLabel(" - ");

        dobYear = new JTextField(4);
        dobYear.addActionListener(this);
        temp7.add(dobYear);
        temp7.add(dobSpacer);
        dobMonth = new JTextField(2);
        dobMonth.addActionListener(this);
        temp7.add(dobMonth);
        temp7.add(dobSpacer2);
        dobDay = new JTextField(2);
        dobDay.addActionListener(this);
        temp7.add(dobDay);

        temp.add(temp7);

        JPanel temp8 = new JPanel();
        temp8.setLayout(new FlowLayout(FlowLayout.LEFT));

        JLabel statusLabel = new JLabel("Status : ");
        temp8.add(statusLabel);

        statusOptions = new Vector<String>();
        statusOptions.add("Active");
        statusOptions.add("Inactive");
        status = new JComboBox(statusOptions);
        status.addActionListener(this);
        temp8.add(status);

        temp.add(temp8);

        return temp;
    }

    private JPanel createNavigationButtons()
    {
        JPanel temp = new JPanel();		// default FlowLayout is fine
        FlowLayout f1 = new FlowLayout(FlowLayout.CENTER);
        f1.setVgap(1);
        f1.setHgap(25);
        temp.setLayout(f1);

        // create the buttons, listen for events, add them to the panel
        submitButton = new JButton("Submit");
        submitButton.addActionListener(this);
        temp.add(submitButton);

        doneButton = new JButton("Done");
        doneButton.addActionListener(this);
        temp.add(doneButton);

        return temp;
    }
    private JPanel createStatusLog(String initialMessage)
    {
        statusLog = new MessageView(initialMessage);

        return statusLog;
    }
    public void populateFields()
    {
        name.setText("");
        address.setText("");
        city.setText("");
        zip.setText("");
        email.setText("");
        dobYear.setText("yyyy");
        dobMonth.setText("mm");
        dobDay.setText("dd");
    }

    public void processAction(EventObject e)
    {
        clearErrorMessage();

        if(e.getSource() == submitButton)
        {
            String nameEntered = name.getText();
            String addressEntered = address.getText();
            String cityEntered = city.getText();
            String stateCodeEntered = (String)stateCode.getSelectedItem();
            String zipEntered = zip.getText();
            String emailEntered = email.getText();
            String dobYearEntered = dobYear.getText();
            //System.out.println("year: " + dobYearEntered);
            String dobMonthEntered = dobMonth.getText();
            //System.out.println("month: " + dobMonthEntered);
            String dobDayEntered = dobDay.getText();
            //System.out.println("day: " + dobDayEntered);
            String dobEntered = dobYear.getText() + "-" + dobMonth.getText() + "-" + dobDay.getText();
            //System.out.println("dob: " + dobEntered);
            String statusEntered = (String)status.getSelectedItem();

            if((nameEntered == null) || (nameEntered.length() == 0))
            {
                displayErrorMessage("Please enter an name.");
                name.requestFocus();
            }
            else if((addressEntered == null) || (addressEntered.length() == 0))
            {
                displayErrorMessage("Please enter an address.");
                address.requestFocus();
            }
            else if((cityEntered == null) || (cityEntered.length() == 0))
            {
                displayErrorMessage("Please enter a city.");
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
    private void processInsertionOfNewPatron(String nameString, String addressString, String cityString, String stateString, String zipString, String emailString, String dobString, String statusString)
    {
        //String pubyear = String.valueOf(pubyearInt);

        Properties props = new Properties();
        props.setProperty("name", nameString);
        props.setProperty("address", addressString);
        props.setProperty("city", cityString);
        props.setProperty("stateCode", stateString);
        props.setProperty("zip", zipString);
        props.setProperty("email", emailString);
        props.setProperty("dateOfBirth", dobString);
        props.setProperty("status", statusString);

        myModel.stateChangeRequest("ProcessInsertion", props);

        //This should not be here, go through update state
        //displayMessage((String) myModel.getState("UpdateStatusMessage"));

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
    private void processDone()
    {
        myModel.stateChangeRequest("Done", null);
    }

    public void clearErrorMessage()
    {
        statusLog.clearErrorMessage();
    }
    public void displayErrorMessage(String message)
    {
        statusLog.displayErrorMessage(message);
    }
    public void displayMessage(String message)
    {
        statusLog.displayMessage(message);
    }

}