package views;

/**
 * Created by Ryan on 4/2/2015.
 */

import java.awt.*;
import java.util.Properties;
import java.util.EventObject;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.*;

import impres.impresario.IModel;

public class RentalView extends View {

    // GUI stuff
    private JComboBox<String> availableVehicles;
    private JTextField renterIDBox;
    //private JTextField checkoutWorkerBox;

    //private JDatePickerImpl regDatePicker;
    //private JButton submit;
    //private JButton done;

    public RentalView(IModel clerk)
    {
        super(clerk, "RentalView");
        subTitleText = "AddRentalTitle";

        // set the layout for this panel
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        // create our GUI components, add them to this panel
        add(createTitle());
        add(createDataEntryFields());
        add(createNavigationButtons());

        //add(createStatusLog("                          "));

        populateFields();

    }

    public void paint(Graphics g)
    {
        super.paint(g);
    }


    private JPanel createDataEntryFields()
    {
        JPanel temp = new JPanel();
        temp.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(8,8,8,8);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 0;
        JLabel vehicleIdLabel = new JLabel(messages.getString("vehicleID"));
        temp.add(vehicleIdLabel, c);

        String avail [];
        avail = (String[]) myModel.getState("VehicleIDs");

        c.insets = new Insets(0,8,8,8);
        c.gridx = 0;
        c.gridy = 1;
        c.gridwidth = 2;
        availableVehicles = new JComboBox<String>(avail);
        availableVehicles.addActionListener(this);
        temp.add(availableVehicles, c);

        c.insets = new Insets(8,8,8,8);
        c.gridwidth = 1;
        c.gridx = 0;
        c.gridy = 2;
        JLabel userIdLabel = new JLabel(messages.getString("userId"));
        temp.add(userIdLabel, c);

        c.insets = new Insets(0,8,8,8);
        c.gridx = 0;
        c.gridy = 3;
        renterIDBox = new JTextField(15);
        renterIDBox.addActionListener(this);
        temp.add(renterIDBox, c);

        return temp;
    }
    //-------------------------------------------------------------
    public void populateFields()
    {

    }
    public void processAction(EventObject e) {

        //clearErrorMessage();

        if(e.getSource() == submit)
        {
            String vehicleIDEntered = (String)availableVehicles.getSelectedItem();
            String renterIDEntered = renterIDBox.getText();
            //String checkoutWorkerEntered = checkoutWorkerBox.getText();

            //Date selectedDate = (Date) regDatePicker.getModel().getValue();

            if((vehicleIDEntered == null) || (vehicleIDEntered.length() == 0))
            {
                displayMessage(messages.getString("enterVehicleIdError"));
                availableVehicles.requestFocus();
            }
            /*else if ((vehicleIDEntered.matches("^\\d+$")!=true))
            {
                displayErrorMessage(messages.getString("workerIdNumericalError"));
                availableVehicles.requestFocus();
            }*/
            else if((renterIDEntered == null) || (renterIDEntered.length() == 0))
            {
                displayMessage(messages.getString("enterWorkerIdError"));
                renterIDBox.requestFocus();
            }
            else
            {
                DateLabelFormatter dateFormater = new DateLabelFormatter();
                //String dateEntered = dateFormatter.format(selectedDate);
                String[] values = new String[11];
                values[0] = vehicleIDEntered;
                values[1] = renterIDEntered;
                values[2] = dateFormater.getCurrentDate(); //date rented
                values[3] = dateFormater.getCurrentTime(); //time rented
                values[4] = dateFormater.getTomorrowDate(); //date due
                values[5] = "12:00:00"; //time due
                values[6] = ""; //date returned
                values[7] = ""; //time returned
                values[8] = (String) myModel.getState("workerId"); //checkout worker


                //DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                //Date d = new Date();
                //String dateString = dateFormat.format(d);
                values[9] = ""; //checkin worker

                processInsertion(values);
            }
        }
        else
        if(e.getSource() == done)
        {
            processDone();
        }


    }
    public void processInsertion(String[] values)
    {
        Properties props = new Properties();
        props.setProperty("vehicleID", values[0]);
        props.setProperty("renterID", values[1]);
        //System.out.println(values[2]); //test
        props.setProperty("dateRented", values[2]);
        props.setProperty("timeRented", values[3]);
        props.setProperty("dateDue", values[4]);
        props.setProperty("timeDue", values[5]);
        props.setProperty("dateReturned", values[6]);
        props.setProperty("timeReturned", values[7]);
        props.setProperty("checkoutWorkerID", values[8]);
        props.setProperty("checkinWorkerID", values[9]);

        myModel.stateChangeRequest("ProcessInsertion", props);
        processDone();
    }

}
