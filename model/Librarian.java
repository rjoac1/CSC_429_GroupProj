//Specify the package
package model;

//System imports
import java.lang.Exception;
import java.util.Hashtable;
import java.util.Properties;

import javax.swing.JFrame;
import javax.swing.JPanel;

//project imports
import impresario.IModel;
import impresario.ISlideShow;
import impresario.IView;
import impresario.ModelRegistry;

import event.Event;
import userinterface.MainFrame;
import userinterface.View;
import userinterface.ViewFactory;
import userinterface.WindowPosition;

public class Librarian implements IView, IModel, ISlideShow
{
// This class implements all these interfaces (and does NOT extend 'EntityBase')
// because it does NOT play the role of accessing the back-end database tables.
// It only plays a front-end role. 'EntityBase' objects play both roles.
    private Properties dependencies;
    private ModelRegistry myRegistry;

    //GUI Components
    private Hashtable myViews;
    private JFrame myFrame;


    public Librarian()
    {
        myFrame = MainFrame.getInstance();
        myViews = new Hashtable();
        myRegistry = new ModelRegistry("Librarian");


        createAndShowLibrarianView();
    }

    private void createAndShowLibrarianView()
    {
        View localView = (View)myViews.get("LibrarianView");

        if(localView == null)
        {
            //create initial view
            localView = ViewFactory.createView("LibrarianView", this); //Use View Factory

            myViews.put("LibrarianView", localView);

            //Make view visible by installing it into the frame
            myFrame.getContentPane().add(localView);//just the main panel in this case
            myFrame.pack();
        }
        else
        {
            swapToView(localView);
        }
    }
    private void createAndShowSearchBooksView()
    {
        View localView = (View)myViews.get("SearchBooksView");

        if(localView == null)
        {
            //create initial view
            localView = ViewFactory.createView("SearchBooksView", this); //Use View Factory

            myViews.put("SearchBooksView", localView);

            swapToView(localView);
        }
        else
        {
            swapToView(localView);
        }
    }
    public void createNewBook()
    {
        Book book = new Book();
        book.subscribe("End", this);
        book.stateChangeRequest("ShowDataEntryView", "");
    }
    public void createNewPatron()
    {
        Patron patron = new Patron();
        patron.subscribe("End", this);
        patron.stateChangeRequest("ShowDataEntryView", "");
    }
    public void createNewTransaction()
    {
        Trans trans = new Trans();
        trans.subscribe("End", this);
        trans.stateChangeRequest("ShowDataEntryView", "");
    }
    public void createNewBookCollection(String title)
    {
        BookCollection bc = new BookCollection();
        bc.findBooksWithTitleLike(title);
        bc.subscribe("End", this);
        bc.stateChangeRequest("CreateAndShowView", "");
    }
//Abstract Methods
    public void updateState(String key, Object value)
    {
        // DEBUG System.out.println("Teller.updateState: key: " + key);

        stateChangeRequest(key, value);
    }
    public void stateChangeRequest(String key, Object value) {
        // STEP 4: Write the sCR method component for the key you
        // just set up dependencies for
        // DEBUG System.out.println("Teller.sCR: key = " + key);
        switch (key) {
            case "InsertNewBook":
                createNewBook();
                break;
            case "InsertNewPatron":
                createNewPatron();
                break;
            case "InsertNewTransaction":
                createNewTransaction();
                break;
            case "SearchBook":
                createAndShowSearchBooksView();
                break;
            case "ProcessSearchBook":
                createNewBookCollection((String)value);
                break;
            case "End":
                createAndShowLibrarianView();
                break;
        }
        myRegistry.updateSubscribers(key, this);
    }
    public Object getState(String key)
    {
        return null;
    }

    /** Unregister previously registered objects. */
    //----------------------------------------------------------
    public void unSubscribe(String key, IView subscriber)
    {
        // DEBUG: System.out.println("Cager.unSubscribe");
        // forward to our registry
        myRegistry.unSubscribe(key, subscriber);
    }

    /** Register objects to receive state updates. */
    //----------------------------------------------------------
    public void subscribe(String key, IView subscriber)
    {
        // DEBUG: System.out.println("Cager[" + myTableName + "].subscribe");
        // forward to our registry
        myRegistry.subscribe(key, subscriber);
    }

    //----------------------------------------------------------------------------
    protected void swapToPanelView(JPanel otherView)
    {
        JPanel currentView = (JPanel)myFrame.getContentPane().getComponent(0);
        // and remove it
        myFrame.getContentPane().remove(currentView);
        // add our view
        myFrame.getContentPane().add(otherView);
        //pack the frame and show it
        myFrame.pack();
        //Place in center
        WindowPosition.placeCenter(myFrame);
    }

    //-----------------------------------------------------------------------------
    public void swapToView(IView otherView)
    {

        if (otherView == null)
        {
            new Event(Event.getLeafLevelClassName(this), "swapToView",
                    "Missing view for display ", Event.ERROR);
            return;
        }

        if (otherView instanceof JPanel)
        {
            swapToPanelView((JPanel)otherView);
        }//end of SwapToView
        else
        {
            new Event(Event.getLeafLevelClassName(this), "swapToView",
                    "Non-displayable view object sent ", Event.ERROR);
        }
    }


}