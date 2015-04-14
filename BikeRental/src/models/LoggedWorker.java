package models;

/**
 * Created by vhb on 4/14/15.
 */
public class LoggedWorker {

    static private final LoggedWorker mInstance = new LoggedWorker();

    static public LoggedWorker getInstance() {
        return mInstance;
    }

    private Worker mWorker = null;

    public Worker getCurrentLoggedUser() {
        return mWorker;
    }

    public void setCurrentLoggedUser(final Worker w) {
        mWorker = w;
    }

    public Boolean isAdmin() {
        return mWorker != null ? mWorker.isAdmin() : false;
    }
}
