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
//project imports
import impresario.IModel;

/**Class containing Librarian View for Library System application*/
//==============================================================
public class LibrarianView extends View
{
    private JButton insertNewBookButton;
    private JButton insertNewPatronButton;
    private JButton insertNewTransactionButton;
    private JButton searchBooksButton;
    private JButton doneButton;

    //For showing error message
    private MessageView statusLog;

    // constructor for this class -- takes a model object
    //-------------------------------------------------------
    public LibrarianView(IModel librarian)
    {
        super(librarian, "LibrarianView");

        //set layout for this panel
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        //create GUI Components and add them to this panel
        add(createTitle());
        add(createNavigationButtons());

        //error message area
        add(createStatusLog("                          "));

        //populateFields();

        // STEP 0: Be sure you tell your model what keys you are interested in
        //myModel.subscribe("LoginError", this);

    }
    // Overide the paint method to ensure we can set the focus when made visible
    //-------------------------------------------------------------
    public void paint(Graphics g)
    {
        super.paint(g);
    }

    //Create title
    private JPanel createTitle()
    {
        JPanel temp = new JPanel();
        temp.setLayout(new FlowLayout(FlowLayout.CENTER));

        JLabel lbl = new JLabel("LIBRARY SYSTEM");
        Font myFont = new Font("Helvetica", Font.BOLD, 20);
        lbl.setFont(myFont);
        temp.add(lbl);

        return temp;
    }

    //create navigation buttons
    private JPanel createNavigationButtons()
    {
        JPanel temp = new JPanel();
        //set layout for this panel
        BoxLayout b1 = new BoxLayout(temp, BoxLayout.Y_AXIS);

        temp.setLayout(b1);

        //create buttons, listen for events, add them to the panel
        insertNewBookButton = new JButton("INSERT NEW BOOK");
        insertNewBookButton.addActionListener(this);
        insertNewBookButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        temp.add(insertNewBookButton);
        temp.add(Box.createRigidArea(new Dimension(0, 10)));

        insertNewPatronButton = new JButton("INSERT NEW PATRON");
        insertNewPatronButton.addActionListener(this);
        insertNewPatronButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        temp.add(insertNewPatronButton);
        temp.add(Box.createRigidArea(new Dimension(0, 10)));

        insertNewTransactionButton = new JButton("INSERT NEW TRANSACTION");
        insertNewTransactionButton.addActionListener(this);
        insertNewTransactionButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        temp.add(insertNewTransactionButton);
        temp.add(Box.createRigidArea(new Dimension(0, 10)));

        searchBooksButton = new JButton("SEARCH BOOKS");
        searchBooksButton.addActionListener(this);
        searchBooksButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        temp.add(searchBooksButton);
        temp.add(Box.createRigidArea(new Dimension(0, 30)));

        doneButton = new JButton("DONE");
        doneButton.addActionListener(this);
        doneButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        temp.add(doneButton);

        return temp;

    }
    // Create the status log field
    //-------------------------------------------------------------
    private JPanel createStatusLog(String initialMessage)
    {

        statusLog = new MessageView(initialMessage);

        return statusLog;
    }

    // IMPRESARIO: Note how we use this method name instead of 'actionPerformed()'
    // now. This is because the super-class View has methods for both action and
    // focus listeners, and both of them delegate to this method. So this method
    // is called when you either have an action (like a button click) or a loss
    // of focus (like tabbing out of a textfield, moving your cursor to something
    // else in the view, etc.)
    // process events generated from our GUI components
    //-------------------------------------------------------------
    public void processAction(EventObject e)
    {
        // DEBUG: System.out.println("TellerView.actionPerformed()");

        clearErrorMessage();

        if(e.getSource() == insertNewBookButton)
        {
            myModel.stateChangeRequest("InsertNewBook", null);
        }
        else
        if(e.getSource() == insertNewPatronButton)
        {
            myModel.stateChangeRequest("InsertNewPatron", null);
        }
        else
        if(e.getSource() == insertNewTransactionButton)
        {
            myModel.stateChangeRequest("InsertNewTransaction", null);
        }
        else
        if(e.getSource() == searchBooksButton)
        {
            myModel.stateChangeRequest("SearchBook", null);
        }
        else
        if(e.getSource() == doneButton)
        {
            processCancel();
        }
    }
    public void processCancel()
    {
        myModel.stateChangeRequest("Done", null);
    }
    //---------------------------------------------------------
    public void updateState(String key, Object value)
    {
        // STEP 6: Be sure to finish the end of the 'perturbation'
        // by indicating how the view state gets updated.
        if (key.equals("LoginError") == true)
        {
            // display the passed text
            displayErrorMessage((String)value);
        }

    }

    /**
     * Display error message
     */
    //----------------------------------------------------------
    public void displayErrorMessage(String message)
    {
        statusLog.displayErrorMessage(message);
    }

    /**
     * Clear error message
     */
    //----------------------------------------------------------
    public void clearErrorMessage()
    {
        statusLog.clearErrorMessage();
    }
}