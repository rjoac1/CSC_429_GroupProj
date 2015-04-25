// tabs=4
//************************************************************
//	COPYRIGHT 2009 Sandeep Mitra and Michael Steves, The
//    College at Brockport, State University of New York. - 
//	  ALL RIGHTS RESERVED
//
// This file is the product of The College at Brockport and cannot 
// be reproduced, copied, or used in any shape or form without 
// the express written consent of The College at Brockport.
//************************************************************
//
// specify the package
package views;

// system imports
import java.awt.*;
import java.util.*;
import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.FocusListener;
import java.awt.event.FocusEvent;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;

// project imports
import impres.impresario.IView;
import impres.impresario.IModel;
import impres.impresario.IControl;
import impres.impresario.ControlRegistry;
import models.LocaleStore;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import models.LocaleStore;


//==============================================================
public abstract class View extends JPanel
        implements IView, IControl, ActionListener, FocusListener
{
    // private data
    protected MainFrame mainFrame;
    protected IModel myModel;
    protected ControlRegistry myRegistry;
    protected ResourceBundle messages;
    protected MessageView statusLog;
    protected String subTitleText = "";

    //protected JDatePickerImpl datePicker;
    protected String datePattern = "yyyy-MM-dd";
    protected SimpleDateFormat dateFormatter = new SimpleDateFormat(datePattern);

    final protected int gridBuffer1 = 2;
    final protected int gridBuffer2 = 2;
    final protected int gridBuffer3 = 10;
    final protected int gridBuffer4 = 0;

    final protected int eastWestBufferParam1 = 60;
    final protected int eastWestBufferParam2 = 0;


    protected Font myFont = new Font("Helvetica", Font.BOLD, 20);
    protected Font myFont2 = new Font("Helvetica", Font.BOLD, 15);

    protected JButton submit;
    protected JButton done;

    // forward declaration
    protected abstract void processAction(EventObject evt);
    //protected abstract JPanel createSubTitle();

    // GUI components


    // Class constructor
    //----------------------------------------------------------
    public View(IModel model, String classname)
    {
        myModel = model;
        messages = LocaleStore.getLocale().getResourceBundle();
        myRegistry = new ControlRegistry(classname);

        myModel.subscribe("UpdateStatusMessage", this);
        myModel.subscribe("UpdateStatusMessageError", this);
    }

    protected JPanel createTitle()
    {
        JPanel temp = new JPanel();
        temp.setLayout(new BoxLayout(temp, BoxLayout.Y_AXIS));

        JPanel temp1 = new JPanel();
        temp1.setLayout(new FlowLayout(FlowLayout.CENTER));
        JLabel lbl = new JLabel(messages.getString("brockportTitle"));
        lbl.setFont(myFont);
        temp1.add(lbl);

        temp.add(temp1);

        JPanel temp2 = new JPanel();
        temp2.setLayout(new FlowLayout(FlowLayout.CENTER));

        JLabel lbl1 = new JLabel(messages.getString("fastTracksTitle"));
        lbl1.setFont(myFont2);
        temp2.add(lbl1);
        temp.add(temp2);

        JPanel subTitle = createSubTitle();
        if(subTitle!=null) {
            temp.add(createSubTitle());
        }

        return temp;
    }

    //Create SubTitle
    //-------------------------------------------------------------
    protected JPanel createSubTitle()
    {
        JPanel temp = new JPanel();
        temp.setLayout(new FlowLayout(FlowLayout.CENTER));

        JLabel lbl = new JLabel(messages.getString(subTitleText));
        lbl.setFont(myFont2);
        temp.add(lbl);

        return temp;
    }
    protected JPanel createNavigationButtons()
    {
        JPanel temp = new JPanel();
        temp.setLayout(new BoxLayout(temp, BoxLayout.Y_AXIS));


        JPanel temp1 = new JPanel();
        temp1.setLayout(new FlowLayout(FlowLayout.CENTER));

        submit = new JButton(messages.getString("submit"));
        submit.addActionListener(this);
        temp1.add(submit);
        //temp1.add(submit);
        //temp1.add(done);
        temp.add(temp1);

        JPanel temp2 = new JPanel();
        temp1.setLayout(new FlowLayout(FlowLayout.CENTER));
        JLabel empty = new JLabel();
        temp2.add(empty);
        temp.add(temp2);


        JPanel temp3 = new JPanel();
        temp3.setLayout(new FlowLayout(FlowLayout.LEFT));
        done = new JButton(messages.getString("back"));
        done.addActionListener(this);
        temp3.add(done);
        temp.add(temp3);

        return temp;
    }

    protected JDatePickerImpl getDatePicker()
    {
        UtilDateModel model = new UtilDateModel();
        //model.setDate(20,04,2014);
        // Need this...
        Properties p = new Properties();
        p.put("text.today", "Today");
        p.put("text.month", "Month");
        p.put("text.year", "Year");

        JDatePanelImpl datePanel = new JDatePanelImpl(model, p);
        datePanel.setLocale(LocaleStore.getLocale().getLocaleObject());
        // Don't know about the formatter, but there it is...
        JDatePickerImpl datePicker = new JDatePickerImpl(datePanel, new DateLabelFormatter());

        datePicker.setLocale(LocaleStore.getLocale().getLocaleObject());

        //System.out.println(datePicker.getLocale()); //test
        return datePicker;
    }
    public void updateState(String key, Object value)
    {
        switch(key)
        {
            case "UpdateStatusMessage":
                displayMessage((String)value);
                break;
            case "UpdateStatusMessageError":
                displayErrorMessage((String)value);
                break;
            default:
                //clearErrorMessage();
                break;
        }
    }

    // process events generated from our GUI components
    //-------------------------------------------------------------
    public void actionPerformed(ActionEvent evt)
    {
        // DEBUG: System.out.println("View.actionPerformed(): " + evt.toString());

        processAction(evt);
    }

    // Same as hitting return in a field, fire postStateChange
    //----------------------------------------------------------
    public void focusLost(FocusEvent evt)
    {
        // DEBUG: System.out.println("CustomerView.focusLost()");
        // ignore temporary events
        if(evt.isTemporary() == true)
            return;

        processAction(evt);
    }


    // ignore gaining focus, we don't care
    //----------------------------------------------------------
    public void focusGained(FocusEvent evt)
    {
        // placeholder
    }

    //----------------------------------------------------------
    public void setRegistry(ControlRegistry registry)
    {
        myRegistry = registry;
    }

    // Allow models to register for state updates
    //----------------------------------------------------------
    public void subscribe(String key,  IModel subscriber)
    {
        myRegistry.subscribe(key, subscriber);
    }


    // Allow models to unregister for state updates
    //----------------------------------------------------------
    public void unSubscribe(String key, IModel subscriber)
    {
        myRegistry.unSubscribe(key, subscriber);
    }

    //----------------------------------------------------------
    protected String mapMonthToString(int month)
    {
        if (month == Calendar.JANUARY)
        {
            return "January";
        }
        else
        if (month == Calendar.FEBRUARY)
        {
            return "February";
        }
        else
        if (month == Calendar.MARCH)
        {
            return "March";
        }
        else
        if (month == Calendar.APRIL)
        {
            return "April";
        }
        else
        if (month == Calendar.MAY)
        {
            return "May";
        }
        else
        if (month == Calendar.JUNE)
        {
            return "June";
        }
        else
        if (month == Calendar.JULY)
        {
            return "July";
        }
        else
        if (month == Calendar.AUGUST)
        {
            return "August";
        }
        else
        if (month == Calendar.SEPTEMBER)
        {
            return "September";
        }
        else
        if (month == Calendar.OCTOBER)
        {
            return "October";
        }
        else
        if (month == Calendar.NOVEMBER)
        {
            return "November";
        }
        else
        if (month == Calendar.DECEMBER)
        {
            return "December";
        }

        return "";
    }

    //----------------------------------------------------------
    protected int mapMonthNameToIndex(String monthName)
    {
        if (monthName.equals("January") == true)
        {
            return Calendar.JANUARY;
        }
        else
        if (monthName.equals("February") == true)
        {
            return Calendar.FEBRUARY;
        }
        else
        if (monthName.equals("March") == true)
        {
            return Calendar.MARCH;
        }
        else
        if (monthName.equals("April") == true)
        {
            return Calendar.APRIL;
        }
        else
        if (monthName.equals("May") == true)
        {
            return Calendar.MAY;
        }
        else
        if (monthName.equals("June") == true)
        {
            return Calendar.JUNE;
        }
        else
        if (monthName.equals("July") == true)
        {
            return Calendar.JULY;
        }
        else
        if (monthName.equals("August") == true)
        {
            return Calendar.AUGUST;
        }
        else
        if (monthName.equals("September") == true)
        {
            return Calendar.SEPTEMBER;
        }
        else
        if (monthName.equals("October") == true)
        {
            return Calendar.OCTOBER;
        }
        else
        if (monthName.equals("November") == true)
        {
            return Calendar.NOVEMBER;
        }
        else
        if (monthName.equals("December") == true)
        {
            return Calendar.DECEMBER;
        }

        return -1;
    }


    //----------------------------------------------------
    protected boolean checkProperLetters(String value)
    {
        for (int cnt = 0; cnt < value.length(); cnt++)
        {
            char ch = value.charAt(cnt);

            if ((ch >= 'A') && (ch <= 'Z') || (ch >= 'a') && (ch <= 'z'))
            {
            }
            else
            if ((ch == '-') || (ch == ',') || (ch == '.') || (ch == ' '))
            {
            }
            else
            {
                return false;
            }
        }

        return true;
    }

    //----------------------------------------------------
    protected boolean checkProperPhoneNumber(String value)
    {
        if ((value == null) || (value.length() < 7))
        {
            return false;
        }

        for (int cnt = 0; cnt < value.length(); cnt++)
        {
            char ch = value.charAt(cnt);

            if ((ch >= '0') && (ch <= '9'))
            {
            }
            else
            if ((ch == '-') || (ch == '(') || (ch == ')') || (ch == ' '))
            {
            }
            else
            {
                return false;
            }
        }

        return true;
    }

    //----------------------------------------------------------
    protected String convertToDefaultDateFormat(Date theDate)
    {

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");

        String valToReturn = formatter.format(theDate);

        return valToReturn;

    }
    public void displayErrorMessage(String message)
    {
        JOptionPane.showMessageDialog(this, message, "Fast Trax", JOptionPane.ERROR_MESSAGE);
    }
    public void displayMessage(String message)
    {
        JOptionPane.showMessageDialog(this, message, "Fast Trax", JOptionPane.PLAIN_MESSAGE);
    }
}

