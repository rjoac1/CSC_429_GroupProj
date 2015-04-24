package views;

//System imports
import java.awt.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import javax.swing.*;

//project imports
import impres.impresario.IModel;
import models.DBContentStrategy;

//==============================================================
public class VehicleView extends View
{
    //GUI Stuff
    private JTextField make;
    private JTextField modelNumber;
    private JTextField serialNumber;
    private JComboBox color;
    private JTextField description;
    private JTextField location;
    private JComboBox<String> physicalCondition;
    private JComboBox<String> status;

    //private JButton submitButton;
    //private JButton doneButton;

    public VehicleView(IModel vehicle)
    {
        super(vehicle, "VehicleView");
        subTitleText = "AddVehicleTitle";
        // set the layout for this panel
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        // create our GUI components, add them to this panel
        add(createTitle());
        add(createDataEntryFields());
        add(createNavigationButtons());

        //error message area
        //add(createStatusLog("                          "));

        populateFields();
        myModel.subscribe("UpdateStatusMessage", this);
    }
    public void paint(Graphics g)
    {
        super.paint(g);
    }


    private JPanel createDataEntryFields()
    {
        JPanel temp = new JPanel();
        //setLayout for panel
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
        tempMain.add(empty, BorderLayout.WEST);

        //
        JPanel temp1 = new JPanel();
        temp1.setLayout(new GridLayout(gridBuffer1, gridBuffer2, gridBuffer3, gridBuffer4));

        JLabel makeLabel = new JLabel(messages.getString("VehicleMake"));
        temp1.add(makeLabel);

        make = new JTextField(35);
        make.addActionListener(this);
        temp1.add(make);

        temp.add(temp1);

        //Model Number
        JPanel temp2 = new JPanel();
        temp2.setLayout(new GridLayout(gridBuffer1, gridBuffer2, gridBuffer3, gridBuffer4));

        JLabel modelNumLabel = new JLabel(messages.getString("VehicleModelNum"));
        temp2.add(modelNumLabel);

        modelNumber = new JTextField(35);
        modelNumber.addActionListener(this);
        temp2.add(modelNumber);

        temp.add(temp2);

        //Serial Number
        JPanel temp3 = new JPanel();
        temp3.setLayout(new GridLayout(gridBuffer1, gridBuffer2, gridBuffer3, gridBuffer4));

        JLabel serialNumLabel = new JLabel(messages.getString("VehicleSerialNum"));
        temp3.add(serialNumLabel);

        serialNumber = new JTextField(35);
        serialNumber.addActionListener(this);
        temp3.add(serialNumber);

        temp.add(temp3);



        //Description
        JPanel temp4 = new JPanel();
        temp4.setLayout(new GridLayout(gridBuffer1, gridBuffer2, gridBuffer3, gridBuffer4));

        JLabel descriptionLabel = new JLabel(messages.getString("VehicleDescription"));
        temp4.add(descriptionLabel);

        description = new JTextField(35);
        description.addActionListener(this);
        temp4.add(description);

        temp.add(temp4);

        //Location
        JPanel temp5 = new JPanel();
        temp5.setLayout(new GridLayout(gridBuffer1, gridBuffer2, gridBuffer3, gridBuffer4));

        JLabel locationLabel = new JLabel(messages.getString("VehicleLocation"));
        temp5.add(locationLabel);

        location = new JTextField(35);
        location.addActionListener(this);
        temp5.add(location);

        temp.add(temp5);

        //Color -mw move?
        JPanel temp6 = new JPanel();
        temp6.setLayout(new GridLayout(gridBuffer1, 3, gridBuffer3, gridBuffer4));

        JLabel colorLabel = new JLabel(messages.getString("VehicleColor"));
        JLabel conditionLabel = new JLabel(messages.getString("VehiclePhysicalCondition"));
        JLabel statusLabel = new JLabel(messages.getString("VehicleStatus"));

        temp6.add(colorLabel);
        temp6.add(conditionLabel);
        temp6.add(statusLabel);

        String colorArr [] = new String[10];
        colorArr[0] = messages.getString("red");
        colorArr[1] = messages.getString("blue");
        colorArr[2] = messages.getString("green");
        colorArr[3] = messages.getString("yellow");
        colorArr[4] = messages.getString("black");
        colorArr[5] = messages.getString("white");
        colorArr[6] = messages.getString("gray");
        colorArr[7] = messages.getString("pink");
        colorArr[8] = messages.getString("purple");
        colorArr[9] = messages.getString("orange");
        color = new JComboBox(colorArr);
        color.addActionListener(this);
        temp6.add(color);

        String[] conditions = new String[4];
        conditions[0] = messages.getString("mint");
        conditions[1] = messages.getString("good");
        conditions[2] = messages.getString("satisfactory");
        conditions[3] = messages.getString("poor");
        physicalCondition = new JComboBox(conditions);
        physicalCondition.setSelectedIndex(0);
        physicalCondition.addActionListener(this);
        temp6.add(physicalCondition);

        String[] statusText = new String[2];
        statusText[0] = messages.getString("active");
        statusText[1] = messages.getString("inactive");
        status = new JComboBox(statusText);
        status.setSelectedIndex(0);
        status.addActionListener(this);
        temp6.add(status);

        temp.add(temp6);
        /*
        //Physical Condition
        JPanel temp7 = new JPanel();
        temp7.setLayout(new GridLayout(gridBuffer1, gridBuffer2, gridBuffer3, gridBuffer4));



        String[] conditions = new String[4];
        conditions[0] = messages.getString("mint");
        conditions[1] = messages.getString("good");
        conditions[2] = messages.getString("satisfactory");
        conditions[3] = messages.getString("poor");
        physicalCondition = new JComboBox(conditions);
        physicalCondition.setSelectedIndex(0);
        physicalCondition.addActionListener(this);
        temp7.add(physicalCondition);

        temp.add(temp7);

        //Status
        JPanel temp8 = new JPanel();
        temp8.setLayout(new GridLayout(gridBuffer1, gridBuffer2, gridBuffer3, gridBuffer4));


        String[] statusText = new String[2];
        statusText[0] = messages.getString("active");
        statusText[1] = messages.getString("inactive");
        status = new JComboBox(statusText);
        status.setSelectedIndex(0);
        status.addActionListener(this);
        temp8.add(status);

        temp.add(temp8);
        */
        tempMain.add(temp, BorderLayout.CENTER);
        tempMain.add(empty1,BorderLayout.EAST);
        return tempMain;
    }
//Put this in super class -RJ
    /*private JPanel createNavigationButtons()
    {
        JPanel temp = new JPanel();		// default FlowLayout is fine
        FlowLayout f1 = new FlowLayout(FlowLayout.CENTER);
        f1.setVgap(10);
        f1.setHgap(25);
        temp.setLayout(f1);

        // create the buttons, listen for events, add them to the panel
        submitButton = new JButton(messages.getString("submit"));
        submitButton.addActionListener(this);
        temp.add(submitButton);

        doneButton = new JButton(messages.getString("back"));
        doneButton.addActionListener(this);
        temp.add(doneButton);

        return temp;
    }*/
    public void populateFields()
    {
        //Not really needed for vehicles unless otherwise noted
    }

    public void processAction(EventObject e)
    {
        //clearErrorMessage();

        if(e.getSource() == submit)
        {
            String makeText = make.getText();
            String modelNumberText = modelNumber.getText();
            String serialNumberText = serialNumber.getText();
            String colorText = DBContentStrategy.getVehicleColorValue(color.getSelectedIndex());
            String descriptionText = description.getText();
            String locationText = location.getText();
            String physicalConditionText = DBContentStrategy.getPhysicalConditionValue(physicalCondition.getSelectedIndex());
            String statusText = DBContentStrategy.getStatusValue(status.getSelectedIndex());


            if((makeText == null) || (makeText.length() == 0))
            {
                displayErrorMessage(messages.getString("VehicleMakeError"));
                make.requestFocus();
            }
            else if((modelNumberText == null) || (modelNumberText.length() == 0))
            {
                displayErrorMessage(messages.getString("VehicleModelNumError"));
                modelNumber.requestFocus();
            }
            else if((serialNumberText == null) || (serialNumberText.length() == 0))
            {
                displayErrorMessage(messages.getString("VehicleSerialNumError"));
                serialNumber.requestFocus();
            }
            else if((colorText == null) || (colorText.length() == 0))
            {
                displayErrorMessage(messages.getString("VehicleColorError"));
                color.requestFocus();
            }/*
            else if((descriptionText == null) || (descriptionText.length() == 0))
            {
                displayErrorMessage(messages.getString("VehicleDescriptionError"));
                description.requestFocus();
            }*/
            else if((locationText == null) || (locationText.length() == 0))
            {
                displayErrorMessage(messages.getString("VehicleLocationError"));
                location.requestFocus();
            }/*
            else if((physicalConditionText == null) || (physicalConditionText.length() == 0))
            {
                displayErrorMessage(messages.getString("VehicleModelNumError"));
                displayErrorMessage("Please enter a physical condition");
            }
            else if((statusText == null) || (statusText.length() == 0))
            {
                displayErrorMessage(messages.getString("VehicleStatusError"));
                status.requestFocus();
            }*/
            else
            {
                DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                Date date = new Date();
                String dateString = dateFormat.format(date);

                Properties props = new Properties();
                props.setProperty("make", makeText);
                props.setProperty("modelNumber", modelNumberText);
                props.setProperty("serialNumber", serialNumberText);
                props.setProperty("color", colorText);
                props.setProperty("description", descriptionText);
                props.setProperty("location", locationText);
                props.setProperty("physicalCondition", physicalConditionText);
                props.setProperty("status", statusText);
                props.setProperty("dateStatusUpdated", dateString);
                processInsertionOfNewVehicle(props);
            }
        }
        else
        if(e.getSource() == done)
        {
            processDone();
        }
    }
    private void processInsertionOfNewVehicle(Properties props)
    {
        myModel.stateChangeRequest("ProcessInsertion", props);
    }
    private void processDone()
    {
        myModel.stateChangeRequest("Done", null);
    }
}

