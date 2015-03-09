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
    public class BookView extends View
    {
    //GUI Stuff
    private JTextField author;
    private JTextField title;
    private JTextField pubyear;

    private Vector<String> statusOptions;
    private JComboBox status;

    private JButton submitButton;
    private JButton doneButton;

    // For showing error message
    private MessageView statusLog;

    public BookView(IModel book)
    {
        super(book, "BookView");

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        add(createTitle());
        add(createDataEntryFields());
        add(createNavigationButtons());

        //error message area
        add(createStatusLog("                                              "));

        populateFields();

        myModel.subscribe("UpdateStatusMessage", this);
    }
    // Overide the paint method to ensure we can set the focus when made visible
    public void paint(Graphics g)
    {
        super.paint(g);
    }

    //create Title
    private JPanel createTitle()
    {
        JPanel temp = new JPanel();
        temp.setLayout(new FlowLayout(FlowLayout.CENTER));

        JLabel lbl = new JLabel("INSERT NEW BOOK");
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
        //author
        JPanel temp1 = new JPanel();
        temp1.setLayout(new FlowLayout(FlowLayout.LEFT));

        JLabel authorLabel = new JLabel("Author : ");
        temp1.add(authorLabel);

        author = new JTextField(35);
        author.addActionListener(this);
        temp1.add(author);

        temp.add(temp1);

        //title
        JPanel temp2 = new JPanel();
        temp2.setLayout(new FlowLayout(FlowLayout.LEFT));

        JLabel titleLabel = new JLabel("Title : ");
        temp2.add(titleLabel);

        title = new JTextField(35);
        title.addActionListener(this);
        temp2.add(title);

        temp.add(temp2);

        //publication year
        JPanel temp3 = new JPanel();
        temp3.setLayout(new FlowLayout(FlowLayout.LEFT));
        JLabel pubyearLabel = new JLabel("Publication Year : ");
        temp3.add(pubyearLabel);

        pubyear = new JTextField(4);
        pubyear.addActionListener(this);
        temp3.add(pubyear);

        temp.add(temp3);

        //status jcombobox
        JPanel temp4 = new JPanel();
        temp4.setLayout(new FlowLayout(FlowLayout.LEFT));

        JLabel statusLabel = new JLabel("Status : ");
        temp4.add(statusLabel);

        statusOptions = new Vector<String>();
        statusOptions.add("Active");
        statusOptions.add("Inactive");
        status = new JComboBox(statusOptions);
        status.addActionListener(this);
        temp4.add(status);

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
        author.setText("");
        title.setText("");
        pubyear.setText("");
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
        clearErrorMessage();

        if(e.getSource() == submitButton)
        {
            String authorEntered = author.getText();
            String titleEntered = title.getText();
            String pubyearEntered = pubyear.getText();
            String statusEntered = (String)status.getSelectedItem();

            if((authorEntered == null) || (authorEntered.length() == 0))
            {
                displayErrorMessage("Please enter an author.");
                author.requestFocus();
            }
            else if((titleEntered == null) || (titleEntered.length() == 0))
            {
                displayErrorMessage("Please enter an title.");
                title.requestFocus();
            }
            else if((pubyearEntered == null) || (pubyearEntered.length() == 0))
            {
                displayErrorMessage("Please enter a publication year.");
                pubyear.requestFocus();
            }
            else if((pubyearEntered.matches("^\\d+$") != true))
            {
                displayErrorMessage("Publication year must be a numerical value.");
                pubyear.requestFocus();
            }
            else if (pubyearEntered.length() > 4)
            {
                displayErrorMessage("Publication year must not exceed 4 characters.");
                pubyear.requestFocus();
            }
            else if((Integer.parseInt(pubyearEntered) <= 1800) || (Integer.parseInt(pubyearEntered) > 2015))
            {
                displayErrorMessage("Please enter a publication year between 1800 and 2015.");
                pubyear.requestFocus();
            } else {
                processInsertionOfNewBook(authorEntered, titleEntered, pubyearEntered, statusEntered);
            }
        }
        else
        if(e.getSource() == doneButton)
        {
            processDone();
        }


    }

    private void processInsertionOfNewBook(String authorString, String titleString, String pubyear, String statusString)
    {
        //String pubyear = String.valueOf(pubyearInt);

        Properties props = new Properties();
        props.setProperty("author", authorString);
        props.setProperty("title", titleString);
        props.setProperty("pubYear", pubyear);
        props.setProperty("status", statusString);

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