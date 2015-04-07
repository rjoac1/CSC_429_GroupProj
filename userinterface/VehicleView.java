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

/**Class containing Vehicle View for Bike System application*/
//==============================================================
public class VehicleView extends View
{
    //GUI Stuff
    private JTextField make;
    private JTextField modelNumber;
    private JTextField serialNumber;
    private JTextField color;
    private JTextField description;
    private JTextField location;
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
        //Make
        JPanel temp1 = new JPanel();
        temp1.setLayout(new FlowLayout(FlowLayout.LEFT));

        JLabel makeLabel = new JLabel(messages.getString("VehicleMake"));
        temp1.add(makeLabel);

        make = new JTextField(35);
        make.addActionListener(this);
        temp1.add(make);

        temp.add(temp1);

        //Model Number
        JPanel temp2 = new JPanel();
        temp2.setLayout(new FlowLayout(FlowLayout.LEFT));

        JLabel modelNumLabel = new JLabel(messages.getString("VehicleModelNum"));
        temp2.add(modelNumLabel);

        modelNumber = new JTextField(35);
        modelNumber.addActionListener(this);
        temp2.add(modelNumber);

        temp.add(temp2);

        //Serial Number
        JPanel temp3 = new JPanel();
        temp3.setLayout(new FlowLayout(FlowLayout.LEFT));

        JLabel serialNumLabel = new JLabel(messages.getString("VehicleSerialNum"));
        temp3.add(serialNumLabel);

        serialNumber = new JTextField(35);
        serialNumber.addActionListener(this);
        temp3.add(serialNumber);

        temp.add(temp3);

        //Color
        JPanel temp4 = new JPanel();
        temp4.setLayout(new FlowLayout(FlowLayout.LEFT));

        JLabel colorLabel = new JLabel(messages.getString("VehicleColor"));
        temp4.add(colorLabel);

        color = new JTextField(4);
        color.addActionListener(this);
        temp5.add(color);

        temp.add(temp5);

        //Description
        JPanel temp5 = new JPanel();
        temp5.setLayout(new FlowLayout(FlowLayout.LEFT));

        JLabel descriptionLabel = new JLabel(messages.getString("VehicleDescription"));
        temp5.add(descriptionLabel);

        description = new JTextField(35);
        description.addActionListener(this);
        temp5.add(description);

        temp.add(temp5);

        //Location
        JPanel temp6 = new JPanel();
        temp6.setLayout(new FlowLayout(FlowLayout.LEFT));

        JLabel locationLabel = new JLabel(messages.getString("VehicleLocation"));
        temp6.add(locationLabel);

        location = new JTextField(35);
        location.addActionListener(this);
        temp6.add(location);

        temp.add(temp6);

        //Physical Condition
        JPanel temp7 = new JPanel();
        temp7.setLayout(new FlowLayout(FlowLayout.LEFT));

        JLabel conditionLabel = new JLabel(messages.getString("VehiclePhysicalCondition"));
        temp7.add(conditionLabel);

        physicalCondition = new JTextField(35);
        physicalCondition.addActionListener(this);
        temp7.add(physicalCondition);

        temp.add(temp7);

        //Status
        JPanel temp8 = new JPanel();
        temp8.setLayout(new FlowLayout(FlowLayout.LEFT));

        JLabel statusLabel = new JLabel(messages.getString("VehicleStatus"));
        temp8.add(statusLabel);

        status = new JTextField(35);
        status.addActionListener(this);
        temp8.add(status);

        temp.add(temp8);

        //Date of Status Change
        JPanel temp9 = new JPanel();
        temp9.setLayout(new FlowLayout(FlowLayout.LEFT));

        JLabel dateStatusLabel = new JLabel(messages.getString("VehicleDateStatus"));
        temp9.add(dateStatusLabel);

        dateStatusUpdated = new JTextField(4);
        dateStatusUpdated.addActionListener(this);
        temp9.add(dateStatusUpdated);

        temp.add(temp9);

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
        submitButton = new JButton(messages.getString("submit"));
        submitButton.addActionListener(this);
        temp.add(submitButton);

        doneButton = new JButton(messages.getString("cancel"));
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
        make.setText("");
        modelNumber.setText("");
        serialNumber.setText("");
        color.setText("");
        description.setText("");
        location.setText("");
        physicalCondition.setText("");
        status.setText("");
        dateStatusUpdated.setText("mm/dd/yyyy");
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
            String locationText = location.getText();
            String physicalConditionText = physicalCondition.getText();
            //System.out.println("year: " + dobYearEntered);
            String statusText = status.getText();
            //System.out.println("month: " + dobMonthEntered);
            String dateStatusText = dateStatusUpdated.getText();
            //System.out.println("day: " + dobDayEntered);

            if((makeText == null) || (makeText.length() == 0))
            {
                displayErrorMessage("Please enter a make");
                make.requestFocus();
            }
            else if((modelNumberText == null) || (modelNumberText.length() == 0))
            {
                displayErrorMessage("Please enter a model number");
                modelNumber.requestFocus();
            }
            else if((serialNumberText == null) || (serialNumberText.length() == 0))
            {
                displayErrorMessage("Please enter a serial number");
                serialNumber.requestFocus();
            }
            else if((colorText == null) || (colorText.length() == 0))
            {
                displayErrorMessage("Please select a color");
                color.requestFocus();
            }
            else if((descriptionText == null) || (descriptionText.length() == 0))
            {
                displayErrorMessage("Please enter a description");
                description.requestFocus();
            }
            else if((locationText == null) || (locationText.length() == 0))
            {
                displayErrorMessage("Please enter a location");
                location.requestFocus();
            }
            else if((physicalConditionText == null) || (physicalConditionText.length() == 0))
            {
                displayErrorMessage("Please enter a physical condition");
                zip.requestFocus();
            }
            else if((statusText == null) || (statusText.length() == 0))
            {
                displayErrorMessage("Please enter a status");
                status.requestFocus();
            }
            else if((dateStatusText == null) || (dateStatusText.length() == 0))
            {
                displayErrorMessage("Please enter a date of status update");
                dateStatusUpdated.requestFocus();
            }
            else
            {
                Properties props = new Properties();
                props.setProperty("make", makeText);
                props.setProperty("modelNumber", modelNumberText);
                props.setProperty("serialNumber", serialNumberText);
                props.setProperty("color", colorText);
                props.setProperty("description", descriptionText);
                props.setProperty("location", locationText);
                props.setProperty("physicalCondition", physicalConditionText);
                props.setProperty("status", statusText);
                props.setProperty("dateStatusUpdated", dateStatusText);
                processInsertionOfNewVehicle(props);
            }
        }
        else
        if(e.getSource() == doneButton)
        {
            processDone();
        }
    }
    private void processInsertionOfNewVehicle(Properties props)
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