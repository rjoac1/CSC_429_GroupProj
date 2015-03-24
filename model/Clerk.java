//Specify the package
package model;

//System imports
import java.lang.Exception;
import java.util.*;

import javax.swing.JFrame;
import javax.swing.JPanel;

//project imports
import impresario.IModel;
import impresario.ISlideShow;
import impresario.IView;
import impresario.ModelRegistry;

import exception.InvalidPrimaryKeyException;
import exception.PasswordMismatchException;
import event.Event;
import userinterface.MainFrame;
import userinterface.View;
import userinterface.ViewFactory;
import userinterface.WindowPosition;

public class Clerk implements IView, IModel, ISlideShow
{
    // This class implements all these interfaces (and does NOT extend 'EntityBase')
// because it does NOT play the role of accessing the back-end database tables.
// It only plays a front-end role. 'EntityBase' objects play both roles.
    private Properties dependencies;
    private ModelRegistry myRegistry;

    private Worker myWorker;

    //GUI Components
    private Hashtable myViews;
    private JFrame myFrame;

    private String loginErrorMessage = "";
    private String transactionErrorMessage = "";

    private Locale currentLocale;

    public Clerk(String language, String country)
    {
        myFrame = MainFrame.getInstance();
        myViews = new Hashtable();
        myRegistry = new ModelRegistry("Clerk");

        currentLocale = new Locale(language, country);

        if(myRegistry == null)
        {
            new Event(Event.getLeafLevelClassName(this), "Teller",
                    "Could not instantiate Registry", Event.ERROR);
        }
        setDependencies();

        createAndShowLoginView();
    }
    private void setDependencies()
    {
        dependencies = new Properties();
        dependencies.setProperty("Login", "LoginError");

        myRegistry.setDependencies(dependencies);
    }
    public Object getState(String key)
    {
        if (key.equals("LoginError") == true)
        {
            return loginErrorMessage;
        }
        else
        if (key.equals("TransactionError") == true)
        {
            return transactionErrorMessage;
        }
        else if (key.equals("LastName") == true)
        {
            if (myWorker != null)
            {
                return myWorker.getState("lastName");
            }
            else
                return "Undefined";
        }
        else if (key.equals("FirstName") == true)
        {
            if (myWorker != null)
            {
                return myWorker.getState("firstName");
            }
            else
                return "Undefined";
        }
        else if (key.equals("WorkerId") == true)
        {
            if (myWorker != null)
            {
                return myWorker.getState("workerId");
            }
            else
                return "Undefined";
        }
        else if (key.equals("Credential") == true)
        {
            if (myWorker != null)
            {
                return myWorker.getState("credential");
            }
            else
                return "Undefined";
        }
        else if (key.equals("CurrentLocale"))
        {
            return currentLocale;
        }
        else
            return "";
    }
    public void stateChangeRequest(String key, Object value) {
        // STEP 4: Write the sCR method component for the key you
        // just set up dependencies for
        // DEBUG System.out.println("Teller.sCR: key = " + key);
        switch (key) {
            case "Login":
                if (value != null) {
                    loginErrorMessage = "";

                    boolean flag = loginWorker((Properties) value);
                    if (flag == true) {
                        createAndShowBikeTransactionChoiceView();
                    }
                }
                break;
            /*case "InsertNewBook":
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
                break;*/
            case "CancelTransaction":
                createAndShowBikeTransactionChoiceView();
                break;
            case "Logout":
                myWorker = null;
                createAndShowLoginView();
                break;
        }
        myRegistry.updateSubscribers(key, this);
    }
    public void updateState(String key, Object value)
    {
        // DEBUG System.out.println("Teller.updateState: key: " + key);

        stateChangeRequest(key, value);
    }

    /*
    Login Worker corresponding to worker Id and password
     */
    public boolean loginWorker(Properties props)
    {
        try {
            myWorker = new Worker(props);
            return true;
        }
        catch (InvalidPrimaryKeyException ex)
        {
            loginErrorMessage = "ERROR: " + ex.getMessage();
            return false;
        }
        catch (PasswordMismatchException exec)
        {
            loginErrorMessage = "ERROR" + exec.getMessage();
            return false;
        }
    }
    private void createAndShowLoginView()
    {
        View localView = (View)myViews.get("LoginView");

        if(localView == null)
        {
            //create initial view
            localView = ViewFactory.createView("LoginView", this); //Use View Factory

            myViews.put("LoginView", localView);

            //Make view visible by installing it into the frame
            myFrame.getContentPane().add(localView);//just the main panel in this case
            myFrame.pack();
        }
        else
        {
            swapToView(localView);
        }
    }
    private void createAndShowBikeTransactionChoiceView()
    {
        View localView = (View)myViews.get("BikeTransactionChoiceView");

        if(localView == null)
        {
            //create initial view
            localView = ViewFactory.createView("BikeTransactionChoiceView", this); //Use View Factory

            myViews.put("BikeTransactionChoiceView", localView);

            swapToView(localView);
        }
        else
        {
            swapToView(localView);
        }
    }
    /*private void createAndShowSearchBooksView()
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
    }*/
    //Abstract Methods
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