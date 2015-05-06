//Specify the package
package models;

//System imports
import java.util.*;

import javax.swing.JFrame;
import javax.swing.JPanel;

//project imports
import impres.impresario.IModel;
import impres.impresario.ISlideShow;
import impres.impresario.IView;
import impres.impresario.ModelRegistry;

import impres.exception.InvalidPrimaryKeyException;
import impres.exception.PasswordMismatchException;
import impres.event.Event;
import views.MainFrame;
import views.View;
import views.ViewFactory;
import views.WindowPosition;

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
    private String findRentalsErrorMessage = "";

    //Used in log out process
    private boolean loginChange = false;

    public Clerk()
    {
        myFrame = MainFrame.getInstance();
        myViews = new Hashtable();
        myRegistry = new ModelRegistry("Clerk");

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
        dependencies.setProperty("ModifyUser", "TransactionError");
        dependencies.setProperty("ModifyWorker", "TransactionError");
        dependencies.setProperty("ModifyBike", "TransactionError");

        myRegistry.setDependencies(dependencies);
    }

    public Object getState(String key)
    {
        if (key.equals("LoginError") == true)
        {
            return loginErrorMessage;
        }
        else if(key.equals("NoRentalsFoundError"))
        {
            return findRentalsErrorMessage;
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
            case "Rent":
                createNewRental();
                break;
            case "Return":
                processReturn();
                break;
            case "AddUser":
                createNewUser();
                break;
            case "AddWorker":
                createNewWorker();
                break;
            case "AddBike":
                createNewBike();
                break;
            case "fndModUser":
                createSearchView("UserSearchView");
                break;
            case "fndModWorker":
                createSearchView("WorkerSearchView");
                break;
            case "fndModBike":
                createSearchView("BikeSearchView");
                break;
            case "ModifyUser":
                modifyUser((String) value);
                break;
            case "ModifyWorker":
                modifyWorker((String) value);
                break;
            case "ModifyBike":
                modifyBike((String) value);
                break;
            case "EndTransaction":
                createAndShowBikeTransactionChoiceView();
                break;
            case "Logout":
                myWorker = null;
                loginChange = true;
                createAndShowLoginView();
                break;
        }
        myRegistry.updateSubscribers(key, this);
    }

    public void updateState(String key, Object value) {
        // DEBUG System.out.println("Teller.updateState: key: " + key);

        stateChangeRequest(key, value);
    }

    /*
    Login Worker corresponding to worker Id and password
     */
    public boolean loginWorker(Properties props) {
        try {
            myWorker = new Worker(props);
            return true;
        }
        catch (InvalidPrimaryKeyException ex) {
            loginErrorMessage = ex.getMessage();
            return false;
        }
        catch (PasswordMismatchException exec) {
            loginErrorMessage = exec.getMessage();
            return false;
        }
    }

//    private <T extends ModelBase> void createNewModel() {
//        T v = new T();
//        v.subscribe("EndTransaction", this);
//        v.stateChangeRequest("ShowDataEntryView", "");
//    }

    private void createNewUser() {
        User user = new User();
        user.subscribe("EndTransaction", this);
        user.stateChangeRequest("ShowDataEntryView", "");
    }

    private void createNewWorker()
    {
        Worker worker = new Worker();
        worker.subscribe("EndTransaction", this);
        worker.stateChangeRequest("ShowDataEntryView", "");
    }
    private void createNewBike()
    {
        Vehicle bike = new Vehicle();
        bike.subscribe("EndTransaction", this);
        bike.stateChangeRequest("ShowDataEntryView", "");
    }
    private void createNewRental()
    {
        Rental rental = new Rental(myWorker);
        rental.subscribe("EndTransaction", this);
        rental.stateChangeRequest("ShowDataEntryView", "");
    }
    private void modifyUser(String id)
    {
        String message = "";
        try{
            User user = new User(id);
            user.subscribe("EndTransaction", this);
            user.stateChangeRequest("ShowDataEntryViewWithValues", "");
        }
        catch(InvalidPrimaryKeyException e)
        {
            message = e.getMessage();
        }
        transactionErrorMessage = message;
    }
    private void modifyWorker(String id)
    {
        String message = "";
        try{
            Worker worker = new Worker(id);
            worker.subscribe("EndTransaction", this);
            worker.stateChangeRequest("ShowDataEntryViewWithValues", "");
        }
        catch(InvalidPrimaryKeyException e)
        {
            message = e.getMessage();
        }
        transactionErrorMessage = message;
    }
    private void modifyBike(String id)
    {
        String message = "";
        try{
            Vehicle vehicle = new Vehicle(id);
            vehicle.subscribe("EndTransaction", this);
            vehicle.stateChangeRequest("ShowDataEntryViewWithValues", "");
        }
        catch(InvalidPrimaryKeyException e)
        {
            message = e.getMessage();
        }
        transactionErrorMessage = message;
    }

    private void processReturn()
    {
        try {
            RentalCollection rc = new RentalCollection(myWorker);
            rc.findActiveRentals();
            rc.subscribe("EndTransaction", this);
            rc.stateChangeRequest("ShowDataEntryView", "");
        }
        catch(InvalidPrimaryKeyException e)
        {
            findRentalsErrorMessage = e.getMessage();
        }
    }

    private void createSearchView(String viewName)
    {
        View localView = (View)myViews.get(viewName);
        //System.out.println(localView);
        if(localView == null)
        {
            localView = ViewFactory.createView(viewName, this); //Use View Factory
            myViews.put(viewName, localView);
            swapToView(localView);
        }
        else
        {
            swapToView(localView);
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

        if(localView == null || loginChange == true)
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
