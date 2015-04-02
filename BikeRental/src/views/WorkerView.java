package views;

/**
 * Created by Ryan on 4/2/2015.
 */

import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.*;
import java.util.Properties;
import java.util.EventObject;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.*;

import impres.impresario.IModel;

public class WorkerView extends View{

    // GUI stuff
    private JTextField firstNameBox;
    private JTextField lastNameBox;
    private JTextField phoneBox1,phoneBox2,phoneBox3;
    private JTextField emailBox;
    private JTextField userTypeBox;
    private JTextField memExpireBox;
    private JTextField registrationDateBox;
    private JTextField updateDateBox;
    private JTextArea notesArea;
    private JComboBox statBox;
    private JButton submit;
    private JButton done;

    public WorkerView(IModel clerk)
    {
        super(clerk, "WorkerView");

        // set the layout for this panel
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        // create our GUI components, add them to this panel
        add(createTitle());
        add(createDataEntryFields());
        add(createNavigationButtons());

        add(createStatusLog("                          "));

        populateFields();
    }

    public void paint(Graphics g)
    {
        super.paint(g);
    }
    protected JPanel createSubTitle()
    {
        JPanel temp = new JPanel();
        temp.setLayout(new FlowLayout(FlowLayout.CENTER));

        JLabel lbl = new JLabel(messages.getString("AddWorkerTitle"));
        Font myFont = new Font("Helvetica", Font.BOLD, 15);
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

        JLabel firstNameLabel = new JLabel(messages.getString("firstName"));
        temp1.add(firstNameLabel);

        firstNameBox = new JTextField(25);
        firstNameBox.addActionListener(this);
        temp1.add(firstNameBox);

        JPanel temp2 = new JPanel();
        temp2.setLayout(new FlowLayout(FlowLayout.LEFT));

        JLabel lastNameLabel = new JLabel(messages.getString("lastName"));
        temp2.add(lastNameLabel);

        lastNameBox = new JTextField(4);
        lastNameBox.addActionListener(this);
        temp2.add(lastNameBox);

        temp.add(temp2);

        return temp;
    }
    private JPanel createNavigationButtons()
    {
        JPanel temp = new JPanel();
        return temp;
    }
    //-------------------------------------------------------------
    public void populateFields()
    {
        //userid.setText("");
        //password.setText("");
    }
    public void processAction(EventObject evt) {

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
}
