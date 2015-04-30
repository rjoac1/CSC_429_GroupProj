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
import java.text.SimpleDateFormat;
import java.util.List;

// project imports
import impres.impresario.IView;
import impres.impresario.IModel;
import impres.impresario.IControl;
import impres.impresario.ControlRegistry;
import models.LocaleStore;
import org.jdatepicker.JDatePicker;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import utils.EmailValidator;


//==============================================================
public abstract class View extends JPanel
        implements IView, IControl, ActionListener, FocusListener
{
    // Static Datas
    @FunctionalInterface
    protected interface Getter {
        String get(JComponent ctrl);
    }

    @FunctionalInterface
    protected interface Validator {
        Boolean validate(String content);
    }

    @FunctionalInterface
    protected interface InvalidParameterHandler {
        void handle(JComponent c, String s, Boolean focus);
    }

    // FIXME
    static final protected InvalidParameterHandler errorHandler =
            (v, s, focus) -> {
            };

    protected class SubmitWrapper {
        public final String propertyName;
        public final JComponent control;
        public final Getter getter;
        public final Validator validator;

        public SubmitWrapper(String name, JComponent ctl, Getter get,
                             Validator v) {
            propertyName = name;
            validator = v;
            control = ctl;
            getter = get;
            if (control != null)
                control.setInputVerifier(new ValidateInput(v, get, name + "Error"));
        }

    }

    // Components contents getter
    static protected Getter textGetter =
            (ctrl) -> ((JTextField)ctrl).getText();

    static final protected Getter comboGetter = (v) -> {
        ComboxItem value = (ComboxItem)((JComboBox<ComboxItem>)v).getSelectedItem();
        return value.getKey();
    };

    static final protected  Getter textAreaGetter =
            (v) -> ((JTextArea)v).getText();

    static protected String datePattern = "yyyy-MM-dd";
    static protected SimpleDateFormat dateFormatter = new SimpleDateFormat(datePattern);

    static protected Getter dateNowGetter = (v) ->
            dateFormatter.format(new Date());

    static protected Getter dateGetter = (v) -> {
        final Date date = (Date)((JDatePicker)v).getModel().getValue();
        return date == null ? "" : dateFormatter.format(date);
    };

    // Validators
    static final protected Validator empty = (s) ->
            s != null && s.length() > 0;
    static final protected Validator phoneNumberValidator = (s) ->
            s != null && s.length() >= 9 && s.length() <= 11
                    && s.matches("[0-9]+");
    static final protected Validator countryCodeValidator = (s) ->
            s != null && s.length() >= 1 && s.length() <= 4
                    && s.matches("[0-9]+");
    static final protected Validator emailValidator = (s) ->
            s != null && EmailValidator.validate(s);
    static final protected Validator ok = (s) -> true;

    // protected datas
    protected IModel myModel;
    protected ControlRegistry myRegistry;
    protected ResourceBundle messages;
    protected String subTitleText = "";

    final protected int gridBuffer1 = 2;
    final protected int gridBuffer2 = 2;
    final protected int gridBuffer3 = 10;
    final protected int gridBuffer4 = 0;

    final protected int eastWestBufferParam1 = 60;
    final protected int eastWestBufferParam2 = 0;

    final protected List<SubmitWrapper> mSubmitWrapper = new ArrayList<>();

    final protected Font myFont = new Font("Helvetica", Font.BOLD, 20);
    final protected Font myFont2 = new Font("Helvetica", Font.BOLD, 15);

    protected JButton submit;
    protected JButton done;

    // forward declaration
    protected abstract void processAction(EventObject evt);
    //protected abstract JPanel createSubTitle();

    // Class constructor
    //----------------------------------------------------------
    public View(final IModel model, final String className)
    {
        myModel = model;
        messages = LocaleStore.getLocale().getResourceBundle();
        myRegistry = new ControlRegistry(className);

        myModel.subscribe("UpdateStatusMessage", this);
        //myModel.subscribe("UpdateStatusMessageError", this);
    }

    protected void populateComboxBox(JComboBox<ComboxItem> c,
                                     String[] keys) {
        Arrays.stream(keys).map(ComboxItem::new)
                .forEach(c::addItem);
    }

    protected Properties getProperties() {
        Properties value = new Properties();
        for (SubmitWrapper i : mSubmitWrapper) {
            System.err.println(i.propertyName);
            final String s = i.getter.get(i.control);
            if (!i.validator.validate(s)) {
                errorHandler.handle(i.control, i.propertyName, false);
                value = null;
            }
            if (value != null && s != null) {
                value.setProperty(i.propertyName, s);
            }
        }
        return value;
    }

    protected JPanel createTitle() {
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
    protected JPanel createSubTitle() {
        JPanel temp = new JPanel();
        temp.setLayout(new FlowLayout(FlowLayout.CENTER));

        JLabel lbl = new JLabel(messages.getString(subTitleText));
        lbl.setFont(myFont2);
        temp.add(lbl);

        return temp;
    }

    protected JPanel createNavigationButtons() {
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

    protected JDatePickerImpl getDatePicker() {
        UtilDateModel model = new UtilDateModel();

        //model.setDate(20,04,2014);
        // Need this...

        Properties p = new Properties();
        p.put("text.today", messages.getString("today"));
        p.put("text.month", messages.getString("month"));
        p.put("text.year", messages.getString("year"));

        JDatePanelImpl datePanel = new JDatePanelImpl(model, p);
        datePanel.setLocale(LocaleStore.getLocale().getLocaleObject());

        // Don't know about the formatter, but there it is...
        JDatePickerImpl datePicker = new JDatePickerImpl(datePanel, new DateLabelFormatter());

        datePicker.setLocale(LocaleStore.getLocale().getLocaleObject());

        return datePicker;
    }

    public void updateState(String key, Object value) {
        switch(key) {
            case "UpdateStatusMessage":
                displayMessage((String)value);
                break;
            default:
                //clearErrorMessage();
                break;
        }
    }

    // process events generated from our GUI components
    //-------------------------------------------------------------
    public void actionPerformed(ActionEvent evt) {
        // DEBUG: System.out.println("View.actionPerformed(): " + evt.toString());

        processAction(evt);
    }

    // Same as hitting return in a field, fire postStateChange
    //----------------------------------------------------------
    public void focusLost(FocusEvent evt) {
        // DEBUG: System.out.println("CustomerView.focusLost()");
        // ignore temporary events
        if(evt.isTemporary() == true)
            return;

        processAction(evt);
    }


    // ignore gaining focus, we don't care
    //----------------------------------------------------------
    public void focusGained(FocusEvent evt) {
        // placeholder
    }

    //----------------------------------------------------------
    public void setRegistry(ControlRegistry registry) {
        myRegistry = registry;
    }

    // Allow models to register for state updates
    //----------------------------------------------------------
    public void subscribe(String key,  IModel subscriber) {
        myRegistry.subscribe(key, subscriber);
    }


    // Allow models to unregister for state updates
    //----------------------------------------------------------
    public void unSubscribe(String key, IModel subscriber) {
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
        if (monthName.equals("January"))
        {
            return Calendar.JANUARY;
        }
        else
        if (monthName.equals("February"))
        {
            return Calendar.FEBRUARY;
        }
        else
        if (monthName.equals("March"))
        {
            return Calendar.MARCH;
        }
        else
        if (monthName.equals("April"))
        {
            return Calendar.APRIL;
        }
        else
        if (monthName.equals("May"))
        {
            return Calendar.MAY;
        }
        else
        if (monthName.equals("June"))
        {
            return Calendar.JUNE;
        }
        else
        if (monthName.equals("July"))
        {
            return Calendar.JULY;
        }
        else
        if (monthName.equals("August"))
        {
            return Calendar.AUGUST;
        }
        else
        if (monthName.equals("September"))
        {
            return Calendar.SEPTEMBER;
        }
        else
        if (monthName.equals("October"))
        {
            return Calendar.OCTOBER;
        }
        else
        if (monthName.equals("November"))
        {
            return Calendar.NOVEMBER;
        }
        else
        if (monthName.equals("December"))
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
    protected String convertToDefaultDateFormat(Date theDate) {

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");

        String valToReturn = formatter.format(theDate);

        return valToReturn;

    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
    }

    public void displayMessage(String message) {
        JOptionPane.showMessageDialog(this, message, "Fast Trax",
                JOptionPane.PLAIN_MESSAGE);
    }
}

