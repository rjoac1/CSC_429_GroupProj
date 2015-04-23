package views;

/**
 * Created by Ryan on 4/2/2015.
 */

import java.awt.FlowLayout;
import java.awt.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import java.util.EventObject;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.*;

import impres.impresario.IModel;
import models.DBContentStrategy;
import org.jdatepicker.impl.JDatePickerImpl;

public class RentalView extends View{

    // GUI stuff
    private JTextField vehicleIdBox;
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
        JPanel main = new JPanel();
        main.setLayout(new BorderLayout());

        //Create blank JPanels for side bars
        JPanel empty = new JPanel();
        empty.setPreferredSize(new Dimension(60, 0));

        JPanel empty1 = new JPanel();
        empty1.setPreferredSize(new Dimension(60, 0));

        main.add(empty,BorderLayout.WEST);

        JPanel temp = new JPanel();
        //temp.setLayout(new GridBagLayout());
        temp.setLayout(new BoxLayout(temp, BoxLayout.Y_AXIS));

        //WorkerId Field
        JPanel temp0 = new JPanel();
        temp0.setLayout(new GridLayout(2,0,0,0));

        JLabel workerIdLabel = new JLabel(messages.getString("workerId"));
        temp0.add(workerIdLabel);

        vehicleIdBox = new JTextField(25);
        vehicleIdBox.addActionListener(this);
        temp0.add(vehicleIdBox);

        temp.add(temp0);
        //First Name Field
        JPanel temp1 = new JPanel();
        temp1.setLayout(new GridLayout(2,2,10,0));   // changed layout style to be more like userview -mw


        JLabel firstNameLabel = new JLabel(messages.getString("firstName"));
        temp1.add(firstNameLabel);

        JLabel lastNameLabel = new JLabel(messages.getString("lastName"));
        temp1.add(lastNameLabel);

        renterIDBox = new JTextField(15);
        renterIDBox.addActionListener(this);
        temp1.add(renterIDBox);

        //checkoutWorkerBox = new JTextField(15);
        //checkoutWorkerBox.addActionListener(this);
        //temp1.add(checkoutWorkerBox);

        temp.add(temp1);
        main.add(temp);
        main.add(empty,BorderLayout.EAST);
        return main;
    }
    //-------------------------------------------------------------
    public void populateFields()
    {

    }
    public void processAction(EventObject e) {

        //clearErrorMessage();

        if(e.getSource() == submit)
        {
            String vehicleIDEntered = vehicleIdBox.getText();
            String renterIDEntered = renterIDBox.getText();
            //String checkoutWorkerEntered = checkoutWorkerBox.getText();

            //Date selectedDate = (Date) regDatePicker.getModel().getValue();

            if((vehicleIDEntered == null) || (vehicleIDEntered.length() == 0))
            {
                displayErrorMessage(messages.getString("enterVehicleIdError"));
                vehicleIdBox.requestFocus();
            }
            else if ((vehicleIDEntered.matches("^\\d+$")!=true))
            {
                displayErrorMessage(messages.getString("workerIdNumericalError"));
                vehicleIdBox.requestFocus();
            }
            else if((renterIDEntered == null) || (renterIDEntered.length() == 0))
            {
                displayErrorMessage(messages.getString("enterFirstNameError"));
                renterIDBox.requestFocus();
            }
            else
            {
                //String dateEntered = dateFormatter.format(selectedDate);
                String[] values = new String[11];
                values[0] = vehicleIDEntered;
                values[1] = renterIDEntered;
                values[2] = ""; //date rented
                values[3] = ""; //time rented
                values[4] = ""; //date due
                values[5] = ""; //time due
                values[6] = ""; //date returned
                values[7] = ""; //time returned
                values[8] = ""; //checkout worker


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
        props.setProperty("rentalID", values[0]);
        props.setProperty("vehicleID", values[1]);
        props.setProperty("renterID", values[2]);
        //System.out.println(values[2]); //test
        props.setProperty("dateRented", values[3]);
        props.setProperty("timeRented", values[4]);
        props.setProperty("dateDue", values[5]);
        props.setProperty("timeDue", values[6]);
        props.setProperty("dateReturned", values[7]);
        props.setProperty("timeReturned", values[8]);
        props.setProperty("checkoutWorkerID", values[9]);
        props.setProperty("checkinWorkerID", values[10]);

        myModel.stateChangeRequest("ProcessInsertion", props);
    }
    public void processDone()
    {
        myModel.stateChangeRequest("Done", null);
    }
    public void updateState(String key, Object value)
    {
        switch(key)
        {
            case "UpdateStatusMessage":
                displayErrorMessage((String)value);
                break;
            default:
                //clearErrorMessage();
                break;
        }
    }
}
