//Specify the package
package userinterface;

//System imports
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Component;
import java.awt.Dimension;
import java.lang.Exception;
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
public class TransView extends View
{
    //GUI Stuff
    private JTextField bookId;
    private JTextField patronId;
    private JTextField dateYear;
    private JTextField dateMonth;
    private JTextField dateDay;

    private JComboBox transType;
    private Vector<String> transTypeOptions;

    private JButton submitButton;
    private JButton doneButton;

    // For showing error message
    private MessageView statusLog;

    public TransView(IModel trans)
    {
        super(trans, "TransView");

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

        JLabel lbl = new JLabel("INSERT NEW TRANSACTION");
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
        //bookid
        JPanel temp1 = new JPanel();
        temp1.setLayout(new FlowLayout(FlowLayout.LEFT));

        JLabel bookIdLabel = new JLabel("Book Id : ");
        temp1.add(bookIdLabel);

        bookId = new JTextField(35);
        bookId.addActionListener(this);
        temp1.add(bookId);

        temp.add(temp1);

        //patronId
        JPanel temp2 = new JPanel();
        temp2.setLayout(new FlowLayout(FlowLayout.LEFT));

        JLabel patronIdLabel = new JLabel("Patron Id : ");
        temp2.add(patronIdLabel);

        patronId = new JTextField(35);
        patronId.addActionListener(this);
        temp2.add(patronId);

        temp.add(temp2);

        //Transaction type
        JPanel temp3 = new JPanel();
        temp3.setLayout(new FlowLayout(FlowLayout.LEFT));

        JLabel transTypeLabel = new JLabel("Transaction Type : ");
        temp3.add(transTypeLabel);

        transTypeOptions = new Vector<String>();
        transTypeOptions.add("Rent");
        transTypeOptions.add("Return");
        transType = new JComboBox(transTypeOptions);
        transType.addActionListener(this);
        temp3.add(transType);

        temp.add(temp3);

        //Date of Transaction
        JPanel temp4 = new JPanel();
        temp4.setLayout(new FlowLayout(FlowLayout.LEFT));

        JLabel dateOfTransLabel = new JLabel("Date Of Transaction : ");
        temp4.add(dateOfTransLabel);

        JLabel dateSpacer = new JLabel(" - ");
        JLabel dateSpacer2 = new JLabel(" - ");

        dateYear = new JTextField(4);
        dateYear.addActionListener(this);
        temp4.add(dateYear);
        temp4.add(dateSpacer);
        dateMonth = new JTextField(2);
        dateMonth.addActionListener(this);
        temp4.add(dateMonth);
        temp4.add(dateSpacer2);
        dateDay = new JTextField(2);
        dateDay.addActionListener(this);
        temp4.add(dateDay);

        temp.add(temp4);

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
        bookId.setText("");
        patronId.setText("");
        dateYear.setText("yyyy");
        dateMonth.setText("mm");
        dateDay.setText("dd");
    }
    public void processAction(EventObject e)
    {
        clearErrorMessage();

        if (e.getSource() == submitButton)
        {
            String bookIdEntered = bookId.getText();
            String patronIdEntered = patronId.getText();
            String transTypeEntered = (String) transType.getSelectedItem();
            String dateYearEntered = dateYear.getText();
            String dateMonthEntered = dateMonth.getText();
            String dateDayEntered = dateDay.getText();
            String dateEntered = dateYear.getText() + "-" + dateMonth.getText() + "-" + dateDay.getText();


            if ((bookIdEntered == null) || (bookIdEntered.length() == 0))
            {
                displayErrorMessage("Please enter a book Id.");
                bookId.requestFocus();
            }
            else if (bookIdEntered.matches("^\\d+$") != true)
            {
                displayErrorMessage("Book Id field must be numerical value.");
                bookId.requestFocus();
            }
            else if ((patronIdEntered == null) || (patronIdEntered.length() == 0))
            {
                displayErrorMessage("Please enter a patron Id.");
                patronId.requestFocus();
            }
            else if (patronIdEntered.matches("^\\d+$") != true)
            {
                displayErrorMessage("Patron Id field must be numerical value.");
                patronId.requestFocus();
            }
            else if ((dateEntered.length() == 0)) {
                displayErrorMessage("Please enter a date of birth.");
                dateYear.requestFocus();
            }
            else if((dateYearEntered.matches("^\\d+$") != true) || (dateMonthEntered.matches("^\\d+$") != true) || (dateDayEntered.matches("^\\d+$") != true))
            {
                displayErrorMessage("Date of birth must be a numerical value.");
                dateYear.requestFocus();
            }
            else if(dateYearEntered.length() > 4)
            {
                displayErrorMessage("'Year' field in date of birth cannot exceed 4 characters.");
                dateYear.requestFocus();
            }
            else if(dateMonthEntered.length() > 2)
            {
                displayErrorMessage("'Month' field in date of birth cannot exceed 2 characters.");
                dateMonth.requestFocus();
            }
            else if(dateDayEntered.length() > 2)
            {
                displayErrorMessage("'Day' field in date of birth cannot exceed 2 characters.");
                dateDay.requestFocus();
            }
            else if((Integer.parseInt(dateMonthEntered) < 1) || (Integer.parseInt(dateMonthEntered) > 12))
            {
                displayErrorMessage("'Month' field in date of birth must be between 1 and 12.");
                dateMonth.requestFocus();
            }
            else if((Integer.parseInt(dateDayEntered) < 1) || (Integer.parseInt(dateDayEntered) > 31))
            {
                displayErrorMessage("'Day' field in date of birth must be between 1 - 31.");
                dateDay.requestFocus();
            }
            else if((Integer.parseInt(dateYearEntered) < 2000) || (Integer.parseInt(dateYearEntered) >= 2015))
            {
                displayErrorMessage("Date of birth must be between 2000-01-01 and 2015-01-01.");
                dateYear.requestFocus();
            } else
            {
                processInsertionOfNewTrans(bookIdEntered, patronIdEntered, transTypeEntered, dateEntered);
            }

        } else if (e.getSource() == doneButton) {
            processDone();
        }
    }
    private void processInsertionOfNewTrans(String bookIdString, String patronIdString, String transTypeString, String dateOfTransString)
    {
        //String pubyear = String.valueOf(pubyearInt);

        Properties props = new Properties();
        props.setProperty("bookId", bookIdString);
        props.setProperty("patronId", patronIdString);
        props.setProperty("transType", transTypeString);
        props.setProperty("dateOfTrans", dateOfTransString);

        myModel.stateChangeRequest("ProcessInsertion", props);
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