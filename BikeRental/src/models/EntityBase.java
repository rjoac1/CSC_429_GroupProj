package models;

// tabs=4
//************************************************************
//	COPYRIGHT 2009 Sandeep Mitra and Michael Steves, The
//    College at Brockport, State University of New York. -
//	  ALL RIGHTS RESERVED
//
// This file is the product of The College at Brockport and cannot
// be reproduced, copied, or used in any shape or form without
// the express written consent of The College at Brockport.
//************************************************************
//
// specify the package

// system imports
import java.util.Hashtable;
import java.util.Properties;
import javax.swing.JFrame;
import javax.swing.JPanel;

// project imports
import impres.database.Persistable;
import impres.impresario.ModelRegistry;
import impres.impresario.IModel;
import impres.impresario.IView;
import impres.impresario.ISlideShow;
import impres.event.Event;
import views.MainFrame;
import views.View;
import views.WindowPosition;


/** The superclass for all Fast Trax Model Entities that are also
 *  Persistable */
//==============================================================
public abstract class EntityBase extends Persistable
        implements IModel, ISlideShow
{
    protected ModelRegistry myRegistry;	// registry for entities interested in our events
    protected boolean dirty;		// true if the data has changed
    protected Properties persistentState;	// the field names and values from the database
    private String myTableName;				// the name of our database table

    protected Hashtable<String, View> myViews;
    protected JFrame myFrame;

    protected Properties mySchema;

    // forward declarations
    public abstract Object getState(String key);
    public abstract void stateChangeRequest(String key, Object value);
    protected abstract void initializeSchema(String tableName);

    // constructor for this class
    //----------------------------------------------------------
    protected EntityBase(String tablename)
    {
        myFrame = MainFrame.getInstance();
        myViews = new Hashtable<>();

        // save our table name for later
        myTableName = tablename;

        // extract the schema from the database, calls methods in subclasses
        initializeSchema(myTableName);

        // create a place to hold our state from the database
        persistentState = new Properties();

        // create a registry for subscribers
        myRegistry = new ModelRegistry("EntityBase." + tablename);	// for now

        // indicate the data in persistentState matches the database contents
        dirty = false;
    }

    /** Register objects to receive state updates. */
    //----------------------------------------------------------
    public void subscribe(String key, IView subscriber)
    {
        // DEBUG: System.out.println("EntityBase[" + myTableName + "].subscribe");
        // forward to our registry
        myRegistry.subscribe(key, subscriber);
    }

    /** Unregister previously registered objects. */
    //----------------------------------------------------------
    public void unSubscribe(String key, IView subscriber)
    {
        // DEBUG: System.out.println("EntityBase.unSubscribe");
        // forward to our registry
        myRegistry.unSubscribe(key, subscriber);
    }

    //-----------------------------------------------------------------------------
    public void swapToView(IView otherView) {
        if (otherView == null) {
            new Event(Event.getLeafLevelClassName(this), "swapToView",
                    "Missing view for display ", Event.ERROR);
            return;
        }

        if (otherView instanceof JPanel) {
            JPanel currentView = (JPanel)myFrame.getContentPane().getComponent(0);
            // and remove it
            myFrame.getContentPane().remove(currentView);
            // add our view
            myFrame.getContentPane().add((JPanel)otherView);
            //pack the frame and show it
            myFrame.pack();
            //Place in center
            WindowPosition.placeCenter(myFrame);
        }//end of SwapToView
        else {
            new Event(Event.getLeafLevelClassName(this), "swapToView",
                    "Non-displayable view object sent ", Event.ERROR);
        }
    }

}

