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
public class SearchBooksView extends View
{
    private JTextField title;

    private JButton submitButton;
    private JButton doneButton;

    // For showing error message
    private MessageView statusLog;

    public SearchBooksView(IModel librarian)
    {
        super(librarian, "BookView");

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        add(createTitle());
        add(createDataEntryFields());
        add(createNavigationButtons());
        //error message area
        add(createStatusLog("                       "));

        populateFields();
    }
    public void paint(Graphics g)
    {
        super.paint(g);
    }

    //Create title
    private JPanel createTitle()
    {
        JPanel temp = new JPanel();
        temp.setLayout(new FlowLayout(FlowLayout.CENTER));

        JLabel lbl = new JLabel("Search Books");
        Font myFont = new Font("Helvetica", Font.BOLD, 20);
        lbl.setFont(myFont);
        temp.add(lbl);

        return temp;
    }

    private JPanel createDataEntryFields()
    {
        JPanel temp = new JPanel();
        temp.setLayout(new BoxLayout(temp, BoxLayout.Y_AXIS));

        JPanel temp1 = new JPanel();
        temp1.setLayout(new FlowLayout(FlowLayout.LEFT));

        JLabel titleLabel = new JLabel("Title : ");
        temp1.add(titleLabel);

        title = new JTextField(35);
        title.addActionListener(this);
        temp1.add(title);

        temp.add(temp1);

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
        title.setText("");
    }


    public void processAction(EventObject e)
    {
        clearErrorMessage();

        if(e.getSource() == submitButton)
        {
            String titleEntered = title.getText();

            if((titleEntered == null) || (titleEntered.length() == 0))
            {
                displayErrorMessage("Please enter an title.");
                title.requestFocus();
            }
            else
            {
                processSearchBook(titleEntered);
            }
        }
        else if(e.getSource() == doneButton)
        {
            processDone();
        }
    }
    private void processSearchBook(String titleString)
    {
        //Clear text field for next time around
        title.setText("");

        myModel.stateChangeRequest("ProcessSearchBook", titleString);
    }
    public void updateState(String key, Object value)
    {
        clearErrorMessage();
    }


    private void processDone()
    {
        myModel.stateChangeRequest("End", null);
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