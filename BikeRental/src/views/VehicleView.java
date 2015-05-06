package views;

//System imports
import java.awt.*;
import java.util.*;
import javax.swing.*;

//project imports
import impres.impresario.IModel;


public class VehicleView extends View {
    //GUI Stuff
    private JTextField make;
    private JTextField modelNumber;
    private JTextField serialNumber;
    private JTextField description;
    private JTextField location;
    private JComboBox<ComboxItem> physicalCondition;
    private JComboBox<ComboxItem> color;
    private JComboBox<ComboxItem> status;

    public VehicleView(IModel vehicle, String subTitle) {
        super(vehicle, "VehicleView");
        subTitleText = subTitle;        //"AddVehicleTitle";
        // set the layout for this panel
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        // create our GUI components, add them to this panel
        add(createTitle());
        add(createDataEntryFields());
        add(createNavigationButtons());

        mSubmitWrapper.addAll(Arrays.asList(
                new SubmitWrapper("make", make, textGetter, empty),
                new SubmitWrapper("modelNumber", modelNumber, textGetter, empty),
                new SubmitWrapper("serialNumber", serialNumber, textGetter, empty),
                new SubmitWrapper("color", color, comboGetter, empty),
                new SubmitWrapper("location", location, textGetter, empty),
                new SubmitWrapper("description", description, textGetter, ok),
                new SubmitWrapper("physicalCondition", physicalCondition, comboGetter, empty),
                new SubmitWrapper("status", status, comboGetter, empty),
                new SubmitWrapper("dateStatusUpdated", null, dateNowGetter, ok)
        ));

    }

    private JPanel createDataEntryFields() {
        JPanel temp = new JPanel();
        temp.setLayout(new BoxLayout(temp, BoxLayout.Y_AXIS));

        JPanel tempMain = new JPanel();
        tempMain.setLayout(new BorderLayout());

        JPanel tempSetup = new JPanel();
        tempSetup.setLayout(new BoxLayout(tempSetup, BoxLayout.Y_AXIS));
        JPanel empty = new JPanel();
        empty.setPreferredSize(new Dimension(eastWestBufferParam1, eastWestBufferParam2));

        JPanel empty1 = new JPanel();
        empty1.setPreferredSize(new Dimension(eastWestBufferParam1, eastWestBufferParam2));
        tempMain.add(empty, BorderLayout.WEST);

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

        color = new JComboBox<>();
        populateComboxBox(color, new String[] {
                "red", "blue", "green", "yellow", "black", "white",
                "gray", "pink", "purple", "orange"
        });
        color.addActionListener(this);
        temp6.add(color);

        physicalCondition = new JComboBox<>();
        populateComboxBox(physicalCondition, new String[] {
                "mint", "good", "satisfactory", "poor"
        });
        physicalCondition.setSelectedIndex(0);
        physicalCondition.addActionListener(this);
        temp6.add(physicalCondition);

        status = new JComboBox<>();
        populateComboxBox(status, new String[] {"active", "inactive"});
        status.setSelectedIndex(0);
        status.addActionListener(this);
        temp6.add(status);

        temp.add(temp6);
        tempMain.add(temp, BorderLayout.CENTER);
        tempMain.add(empty1,BorderLayout.EAST);
        return tempMain;
    }

    public void processAction(EventObject e) {
        if (e.getSource() == submit) {
            processInsertionOfNewVehicle();
        } else if (e.getSource() == done) {
            processDone();
        }
    }

    private void processInsertionOfNewVehicle() {
        final Properties props = getProperties();
        if (props != null)
            myModel.stateChangeRequest("ProcessInsertion", props);
    }

    private void processDone() {
        myModel.stateChangeRequest("Done", null);
    }

}

