package views;

// specify the package

import impres.impresario.IModel;

import javax.swing.*;
import java.awt.*;
import java.util.EventObject;
import java.util.Properties;

// system imports
import java.awt.*;
import java.awt.Font;
import java.awt.Graphics;
import java.util.*;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.text.NumberFormat;

// project imports
import impres.impresario.IModel;
import models.LocaleStore;
import models.User;

/** The class containing the Teller View  for the ATM application */
//==============================================================
public class SearchView extends View
{
    //private MainFrame mainFrame;
    // GUI stuff
    private JLabel searchTypeLabel;
    private JTextField searchKeyBox;
    private JButton submitButton;
    String entityType = "";

    // For showing error message
    //private MessageView statusLog;

    // constructor for this class -- takes a model object
    //----------------------------------------------------------
    public SearchView(IModel clerk, String entityType)
    {

        super(clerk, "SearchView");
        subTitleText = "Search View";
        this.entityType = entityType;
        // set the layout for this panel
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        // create our GUI components, add them to this panel
       add(createTitle());
       add(createDataEntryFields());
       add(createNavigationButtons());


        //populateFields();

        // STEP 0: Be sure you tell your model what keys you are interested in
        myModel.subscribe("SearchError", this);
    }

    // Overide the paint method to ensure we can set the focus when made visible
    //-------------------------------------------------------------
    public void paint(Graphics g)
    {
        super.paint(g);
        //userid.requestFocus();
    }

    // Create the labels and fields
    //-------------------------------------------------------------
    protected JPanel createSubTitle()
    {
        return null;
    }

    // Create the main data entry fields
    //-------------------------------------------------------------
    private JPanel createDataEntryFields()
    {
        //System.out.println("HELLO");
        JPanel temp = new JPanel();

        // set the layout for this panel
        temp.setLayout(new BoxLayout(temp, BoxLayout.Y_AXIS));

        // data entry fields
        JPanel temp1 = new JPanel();
        temp1.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        System.out.println(entityType);
        if(entityType == "User" || entityType == "Worker")
            searchTypeLabel = new JLabel(messages.getString("BannerID"));
        else
            searchTypeLabel = new JLabel(messages.getString("vehicleID"));
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 0;
        c.insets = new Insets(4, 6, 0, 4);
        temp1.add(searchTypeLabel, c);


        searchKeyBox = new JTextField(20);
        searchKeyBox.addActionListener(this);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 1;
        c.insets = new Insets(0, 4, 4, 4);
        temp1.add(searchKeyBox,c);

        temp.add(temp1);

        return temp;
    }

    public void processAction(EventObject e) {
        if (e.getSource() == submit) {
            validateInput();
        } else if (e.getSource() == done) {
            processDone();
        }
    }

    /**
     * Process userid and pwd supplied when Submit button is hit.
     * Action is to pass this info on to the teller object
     */

    //---------------------------------------------------------
    public void updateState(String key, Object value)
    {
        // STEP 6: Be sure to finish the end of the 'perturbation'
        // by indicating how the view state gets updated.
        if (key.equals("SearchError") == true && !value.equals(""))
        {
            // display the passed text
            displayMessage((String) value);
        }

    }

    private void validateInput(){
        if(searchKeyBox.getText() == null ||searchKeyBox.getText() == "" ){
            //displayMessage();     -mw need Victor to show me how he handles error messages now
        }
        else{ processSubmit(); }
    }

    public void processSubmit(){
        switch (entityType){
            case "User":
                myModel.stateChangeRequest("ModifyUser",searchKeyBox.getText() );
                break;
            case "Worker":
                myModel.stateChangeRequest("ModifyWorker",searchKeyBox.getText() );
                break;
            case "Vehicle":
                myModel.stateChangeRequest("ModifyBike",searchKeyBox.getText() );
                break;
        }
    }

    public void processDone()
    {
        myModel.stateChangeRequest("EndTransaction", null);
    }

}

