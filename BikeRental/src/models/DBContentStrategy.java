package models;

/**
 * Created by Ryan on 4/13/2015.
 */
public class DBContentStrategy {
    private static final String[] status = {"Active", "Inactive"};
    private static final String[] vehicleColor = {"Red", "Blue", "Green",
            "Yellow", "Black", "White", "Gray", "Pink","Purple", "Orange"};
    private static final String[] credentials = {"Administrator", "User"};
    private static final String[] userType = {"Student", "Faculty"};
    private static final String[] physicalCondition = {"Mint", "Good", "Satisfactory", "Poor"};

    public static String getStatusValue(int i)
    {
        return status[i];
    }
    public static String getVehicleColorValue(int i)
    {
        return vehicleColor[i];
    }
    public static String getCredentialValue(int i)
    {
        return credentials[i];
    }
    public static String getUserTypeValue(int i)
    {
        return userType[i];
    }
    public static String getPhysicalConditionValue(int i)
    {
        return physicalCondition[i];
    }
}
