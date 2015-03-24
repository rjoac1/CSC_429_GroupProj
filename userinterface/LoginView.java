// specify the package
package userinterface;

// system imports
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.util.*;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.text.NumberFormat;

// project imports
import impresario.IModel;

/** The class containing the Teller View  for the ATM application */
//==============================================================
public class LoginView extends View
{

    // GUI stuff
    private JTextField userid;
    private JPasswordField password;
    private JButton submitButton;

    // For showing error message
    private MessageView statusLog;

    // resource bundle for internationalization
    private ResourceBundle messages;

    // constructor for this class -- takes a model object
    //----------------------------------------------------------
    public LoginView(IModel clerk)
    {

        super(clerk, "LoginView");
        Locale currentLocale = (Locale)myModel.getState("CurrentLocale");
        messages = ResourceBundle.getBundle("MessagesBundle", currentLocale);

        // set the layout for this panel
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        // create our GUI components, add them to this panel
        add(createTitle());
        add(createDataEntryFields());
        add(createNavigationButtons());

        // Error message area
        add(createStatusLog("                          "));

        populateFields();

        // STEP 0: Be sure you tell your model what keys you are interested in
        myModel.subscribe("LoginError", this);
    }

    // Overide the paint method to ensure we can set the focus when made visible
    //-------------------------------------------------------------
    public void paint(Graphics g)
    {
        super.paint(g);
        userid.requestFocus();
    }

    // Create the labels and fields
    //-------------------------------------------------------------
    private JPanel createTitle()
    {
        JPanel temp = new JPanel();
        temp.setLayout(new BoxLayout(temp, BoxLayout.Y_AXIS));

        JPanel temp1 = new JPanel();
        temp1.setLayout(new FlowLayout(FlowLayout.CENTER));
        JLabel lbl = new JLabel(messages.getString("brockportTitle"));
        Font myFont = new Font("Helvetica", Font.BOLD, 20);
        lbl.setFont(myFont);
        temp1.add(lbl);

        temp.add(temp1);

        JPanel temp2 = new JPanel();
        temp2.setLayout(new FlowLayout(FlowLayout.CENTER));

        JLabel lbl1 = new JLabel(messages.getString("fastTracksTitle"));
        Font myFont2 = new Font("Helvetica", Font.BOLD, 15);
        lbl1.setFont(myFont2);
        temp2.add(lbl1);

        temp.add(temp2);

        return temp;
    }

    // Create the main data entry fields
    //-------------------------------------------------------------
    private JPanel createDataEntryFields()
    {
        JPanel temp = new JPanel();
        // set the layout for this panel
        temp.setLayout(new BoxLayout(temp, BoxLayout.Y_AXIS));

        // data entry fields
        JPanel temp1 = new JPanel();
        temp1.setLayout(new FlowLayout(FlowLayout.LEFT));

        JLabel useridLabel = new JLabel(messages.getString("userId"));
        temp1.add(useridLabel);

        userid = new JTextField(20);
        userid.addActionListener(this);
        temp1.add(userid);

        temp.add(temp1);

        JPanel temp2 = new JPanel();
        temp2.setLayout(new FlowLayout(FlowLayout.LEFT));

        JLabel passwordLabel = new JLabel(messages.getString("password"));
        temp2.add(passwordLabel);

        password = new JPasswordField(20);
        password.addActionListener(this); 		//Adds listener for when you hit the enter key
        temp2.add(password);

        temp.add(temp2);

        return temp;
    }

    // Create the navigation buttons
    //-------------------------------------------------------------
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

        return temp;
    }

    // Create the status log field
    //-------------------------------------------------------------
    private JPanel createStatusLog(String initialMessage)
    {

        statusLog = new MessageView(initialMessage);

        return statusLog;
    }

    //-------------------------------------------------------------
    public void populateFields()
    {
        userid.setText("");
        password.setText("");
    }

    // IMPRESARIO: Note how we use this method name instead of 'actionPerformed()'
    // now. This is because the super-class View has methods for both action and
    // focus listeners, and both of them delegate to this method. So this method
    // is called when you either have an action (like a button click) or a loss
    // of focus (like tabbing out of a textfield, moving your cursor to something
    // else in the view, etc.)
    // process events generated from our GUI components
    //-------------------------------------------------------------
    public void processAction(EventObject evt)
    {
        // DEBUG: System.out.println("TellerView.actionPerformed()");

        clearErrorMessage();

        String useridEntered = userid.getText();

        if ((useridEntered == null) || (useridEntered.length() == 0))
        {
            displayErrorMessage(messages.getString("enterUserIDErrorMessage"));
            userid.requestFocus();
        }
        else
        {
            char[] passwordValueEntered = password.getPassword();
            String passwordEntered = new String(passwordValueEntered);

            for (int cnt = 0; cnt < passwordValueEntered.length; cnt++)
            {
                passwordValueEntered[cnt] = 0;
            }

            processUserIDAndPassword(useridEntered, passwordEntered);
        }

    }

    /**
     * Process userid and pwd supplied when Submit button is hit.
     * Action is to pass this info on to the teller object
     */
    //----------------------------------------------------------
    private void processUserIDAndPassword(String useridString,
                                          String passwordString)
    {
        Properties props = new Properties();
        props.setProperty("ID", useridString);
        props.setProperty("Password", passwordString);

        // clear fields for next time around
        userid.setText("");
        password.setText("");

        myModel.stateChangeRequest("Login", props);
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

