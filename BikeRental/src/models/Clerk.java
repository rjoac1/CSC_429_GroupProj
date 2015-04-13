//Specify the package
package models;

//System imports
import java.util.*;

//project imports
import impres.impresario.IModel;
import impres.impresario.IView;
import impres.impresario.ModelRegistry;

import impres.exception.InvalidPrimaryKeyException;
import impres.exception.PasswordMismatchException;
import impres.event.Event;
import views.*;

public class Clerk implements IView, IModel
{
    // This class implements all these interfaces (and does NOT extend 'EntityBase')
// because it does NOT play the role of accessing the back-end database tables.
// It only plays a front-end role. 'EntityBase' objects play both roles.
    private Properties dependencies;
    private ModelRegistry myRegistry;

    private Worker myWorker;

    //GUI Components
    private Hashtable myViews;

    private String loginErrorMessage = "";
    private String transactionErrorMessage = "";
    private static Clerk mInstance = new Clerk();

    static public Clerk getInstance() {
        return mInstance;
    }

    public Clerk()
    {
        myViews = new Hashtable();
        myRegistry = new ModelRegistry("Clerk");

        if(myRegistry == null) {
            new Event(Event.getLeafLevelClassName(this), "Teller",
                    "Could not instantiate Registry", Event.ERROR);
        }
        setDependencies();
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
                    System.err.println("flag: " + flag);
                    if (flag == true) {
                        System.err.println("poruquoi");
                        System.err.println(MainFrame.getInstance());
                        MainFrame.getInstance().replaceScene("/views/menu.fxml");
                    }
                }
                break;
            /*case "Checkin":
                processCheckin();
                break;
            case "Checkout":
                processCheckout();
                break;*/
            case "Done":
                MainFrame.getInstance().replaceScene("/views/menu.fxml");
                break;
            case "AddUser":
                MainFrame.getInstance().replaceScene("/views/addUser.fxml");
                createNewUser();
                break;
            case "AddWorker":
                MainFrame.getInstance().replaceScene("/views/addWorker.fxml");
                break;
            case "AddBike":
                MainFrame.getInstance().replaceScene("/views/addBike.fxml");
                break;
            /*case "FndModUser":
                fndModUser();
                break;
            case "FndModWorker":
                fndModWorker();
                break;
            case "FndModBike":
                fndModBike();
                break;*/
//            case "EndTransaction":
//                createAndShowBikeTransactionChoiceView();
//                break;
            case "Logout":
                myWorker = null;
                MainFrame.getInstance().replaceScene("/views/Login.fxml");
//                createAndShowLoginView();
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
            System.err.println(props);
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

    public void createNewUser()
    {
        User user = new User();
        user.subscribe("EndTransaction", this);
        user.stateChangeRequest("ShowDataEntryView", "");
    }

    public void createNewWorker()
    {
        Worker worker = new Worker();
        worker.subscribe("EndTransaction", this);
        worker.stateChangeRequest("ShowDataEntryView", "");
    }

    public void createNewBike()
    {
        Vehicle bike = new Vehicle();
        bike.subscribe("EndTransaction", this);
        bike.stateChangeRequest("ShowDataEntryView", "");
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

}
