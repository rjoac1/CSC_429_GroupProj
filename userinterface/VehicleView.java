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
public class VehicleView extends View
{
    //GUI Stuff
    private JTextField make;
    private JTextField modelNumber;
    private JTextField serialNumber;
    private JTextField color;
    private JTextField description;
    private JTextField physicalCondition;
    private JTextField status;
    private JTextField dateStatusUpdated;

    //private JComboBox status;

    private Vector<String> statusOptions;
    private Vector<String> stateCodeOptions;


    private JButton submitButton;
    private JButton doneButton;

    // For showing error message
    private MessageView statusLog;

    public VehicleView(IModel patron)
    {
        super(patron, "VehicleView");

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

        JLabel lbl = new JLabel(messages.getString("AddVehicleTitle"));
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

        JLabel nameLabel = new JLabel(messages.getString("VehicleMake"));
        temp1.add(nameLabel);

        name = new JTextField(35);
        name.addActionListener(this);
        temp1.add(name);

        temp.add(temp1);

        //address
        JPanel temp2 = new JPanel();
        temp2.setLayout(new FlowLayout(FlowLayout.LEFT));

        JLabel addressLabel = new JLabel(messages.getString("VehicleModelNum"));
        temp2.add(addressLabel);

        address = new JTextField(35);
        address.addActionListener(this);
        temp2.add(address);

        temp.add(temp2);

        //city
        JPanel temp3 = new JPanel();
        temp3.setLayout(new FlowLayout(FlowLayout.LEFT));

        JLabel cityLabel = new JLabel(messages.getString("VehicleSerialNum"));
        temp3.add(cityLabel);

        city = new JTextField(35);
        city.addActionListener(this);
        temp3.add(city);

        temp.add(temp3);

        //zip
        JPanel temp5 = new JPanel();
        temp5.setLayout(new FlowLayout(FlowLayout.LEFT));

        JLabel zipLabel = new JLabel(messages.getString("VehicleColor"));
        temp5.add(zipLabel);

        zip = new JTextField(4);
        zip.addActionListener(this);
        temp5.add(zip);

        temp.add(temp5);

        //email
        JPanel temp6 = new JPanel();
        temp6.setLayout(new FlowLayout(FlowLayout.LEFT));

        JLabel emailLabel = new JLabel(messages.getString("VehicleDescription"));
        temp6.add(emailLabel);

        email = new JTextField(35);
        email.addActionListener(this);
        temp6.add(email);

        temp.add(temp6);

        //Date of Birth
        JPanel temp7 = new JPanel();
        temp7.setLayout(new FlowLayout(FlowLayout.LEFT));

        JLabel dobLabel = new JLabel(messages.getString("VehicleLocation"));
        temp7.add(dobLabel);

        //Date of Birth
        JPanel temp7 = new JPanel();
        temp7.setLayout(new FlowLayout(FlowLayout.LEFT));

        JLabel dobLabel = new JLabel(messages.getString("VehiclePhysicalCondition"));
        temp7.add(dobLabel);

        //Date of Birth
        JPanel temp8 = new JPanel();
        temp7.setLayout(new FlowLayout(FlowLayout.LEFT));

        JLabel dobLabel = new JLabel(messages.getString("VehicleStatus"));
        temp8.add(dobLabel);

        //Date of Birth
        JPanel temp8 = new JPanel();
        temp7.setLayout(new FlowLayout(FlowLayout.LEFT));

        JLabel dobLabel = new JLabel(messages.getString("VehicleDateStatus"));
        temp8.add(dobLabel);

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
            String makeText = make.getText();
            String modelNumberText = modelNumber.getText();
            String serialNumberText = serialNumber.getText();
            String colorText = color.getText();
            String descriptionText = description.getText();
            String locationText = loaction.getText();
            String physicalConditionText = physicalCondition.getText();
            //System.out.println("year: " + dobYearEntered);
            String statusText = status.getText();
            //System.out.println("month: " + dobMonthEntered);
            String dateStatusText = dateStatusUpdated.getText();
            //System.out.println("day: " + dobDayEntered);

            if((makeText == null) || (makeText.length() == 0))
            {
                displayErrorMessage("Please enter a make.");
                make.requestFocus();
            }
            else if((modelNumberText == null) || (modelNumberText.length() == 0))
            {
                displayErrorMessage("Please enter a model number.");
                modelNumber.requestFocus();
            }
            else if((serialNumberText == null) || (serialNumberText.length() == 0))
            {
                displayErrorMessage("Please enter a serial number.");
                serialNumber.requestFocus();
            }
            else if((colorText == null) || (colorText.length() == 0))
            {
                displayErrorMessage("Please select a color.");
                color.requestFocus();
            }
            else if((descriptionText == null) || (descriptionText.length() == 0))
            {
                displayErrorMessage("Please enter a description.");
                description.requestFocus();
            }
            else if((locationText == null) || (locationText.length() == 0))
            {
                displayErrorMessage("Zip code cannot exceed 5 characters.");
                location.requestFocus();
            }
            else if((physicalConditionText == null) || (physicalConditionText.length() == 0))
            {
                displayErrorMessage("Zip code must be a numerical value.");
                zip.requestFocus();
            }
            else if((statusText == null) || (statusText.length() == 0))
            {
                displayErrorMessage("Please enter an email address.");
                status.requestFocus();
            }
            else if((dateStatusText == null) || (dateStatusText.length() == 0))
            {
                displayErrorMessage("Please enter a date of birth.");
                dateStatusUpdated.requestFocus();
            }
            else
            {
                Properties props = new Properties();
                props.setProperty("name", nameString);
                props.setProperty("address", addressString);
                props.setProperty("city", cityString);
                props.setProperty("stateCode", stateString);
                props.setProperty("zip", zipString);
                props.setProperty("email", emailString);
                props.setProperty("dateOfBirth", dobString);
                props.setProperty("status", statusString);
                processInsertionOfNewPatron(props);
            }
        }
        else
        if(e.getSource() == doneButton)
        {
            processDone();
        }
    }
    private void processInsertionOfNewPatron(Properties props)
    {
        //String pubyear = String.valueOf(pubyearInt);

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