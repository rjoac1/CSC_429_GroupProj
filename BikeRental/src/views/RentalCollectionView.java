package views;

import impres.impresario.IModel;
import models.Rental;
import models.RentalCollection;
import models.RentalTableModel;

import javax.swing.*;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Enumeration;
import java.util.EventObject;
import java.util.Vector;

/**
 * Created by Ryan on 4/23/2015.
 */
//==============================================================================
public class RentalCollectionView extends View implements ActionListener, MouseListener {
    protected JTable tableOfRentals;
    protected Vector rentalVector;

    protected JButton mSearchBtn;
    protected JTextField mSearchField;

    //--------------------------------------------------------------------------
    public RentalCollectionView(IModel wsc)
    {
        super(wsc, "RentalCollectionView");
        subTitleText = "rentalTitle";

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        rentalVector = new Vector();

        add(createTitle());
        add(createDataEntryFields());
        add(createNavigationButtons());

        populateFields();
    }
    //--------------------------------------------------------------------------
    protected void populateFields()
    {
        getEntryTableModelValues();
    }

    protected void getEntryTableModelValues()
    {
        rentalVector.removeAllElements();

        try
        {
            RentalCollection rentalCollection = (RentalCollection)myModel.getState("RentalList");

            Vector entryList = (Vector)rentalCollection.getState("Rentals");
            Enumeration entries = entryList.elements();

            while (entries.hasMoreElements())
            {
                Rental nextRental = (Rental)entries.nextElement();
                Vector view = nextRental.getEntryListView();

                // add this list entry to the list
                rentalVector.add(view);
            }
        }
        catch (Exception e) {//SQLException e) {
            // TODO: Need to handle this exception
            e.printStackTrace();
        }
    }

    protected JPanel createDataEntryFields()
    {
        JPanel entries = new JPanel();
        entries.setLayout(new BoxLayout(entries, BoxLayout.Y_AXIS));

        JPanel tablePan = new JPanel();
        tablePan.setLayout(new FlowLayout(FlowLayout.CENTER));

        TableModel myData = new RentalTableModel(rentalVector);

        tableOfRentals = new JTable(myData);
        tableOfRentals.addMouseListener(this);
        tableOfRentals.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);


        tableOfRentals.setPreferredScrollableViewportSize(new Dimension(1000, 100));
        tableOfRentals.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

        TableColumn column;
        for (int i = 0; i < myData.getColumnCount(); i++) {
            column = tableOfRentals.getColumnModel().getColumn(i);
            column.setPreferredWidth(100);
        }
        // Renter First Name

        //tablePan.add(tableOfAccounts);
        //adds scrollbar
        JScrollPane scrollPane = new JScrollPane(tableOfRentals);
        tablePan.add(scrollPane);

        entries.add(tablePan);
        mSearchBtn = new JButton("Search");
        entries.add(mSearchBtn);
        mSearchField = new JTextField();

        return entries;
    }

    //--------------------------------------------------------------------------
    protected void processAction(EventObject evt)
    {
        if(evt.getSource() == done) {
            processDone();
        }
        else if(evt.getSource() == submit) {
            processRentalSelected();
        }
        else if (evt.getSource() == mSearchBtn) {
            processSearch();
        }
    }

    private void processSearch() {

    }

    protected void processRentalSelected() {
        int selectedIndex = tableOfRentals.getSelectedRow();
        if(selectedIndex >= 0)
        {
            Vector selectedRental = (Vector)rentalVector.elementAt(selectedIndex);

            myModel.stateChangeRequest("ProcessReturn", selectedRental.elementAt(0));
            myModel.stateChangeRequest("Done", null);

        }
    }

    private void processDone() {
        myModel.stateChangeRequest("Done", null);
    }

    //--------------------------------------------------------------------------
    public void mouseClicked(MouseEvent click)
    {
        if(click.getClickCount() >= 2)
        {
            processRentalSelected();
        }
    }

    //----------- These are not used ------------------------------
    public void mousePressed(MouseEvent click) {}
    public void mouseExited(MouseEvent arg0) {}
    public void mouseEntered(MouseEvent arg0) {}
    public void mouseReleased(MouseEvent arg0) {}
}