package views; /**
 * Created by Ryan on 4/13/2015.
 */
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.swing.JFormattedTextField.AbstractFormatter;

public class DateLabelFormatter extends AbstractFormatter {

    private String datePattern = "yyyy-MM-dd";
    private String timePattern = "HH:mm:ss";
    private SimpleDateFormat dateFormatter = new SimpleDateFormat(datePattern);
    private SimpleDateFormat timeFormatter = new SimpleDateFormat(timePattern);
    private Date date = new Date();

    @Override
    public Object stringToValue(String text) throws ParseException {
        return dateFormatter.parseObject(text);
    }

    @Override
    public String valueToString(Object value) throws ParseException {
        if (value != null) {
            Calendar cal = (Calendar) value;
            return dateFormatter.format(cal.getTime());
        }

        return "";
    }

    public String getCurrentDate(){
        return dateFormatter.format(date);
    }
    public String getCurrentTime(){
        return timeFormatter.format(date);
    }

    public String getTomorrowDate(){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, 1);
        Date tomorrow = calendar.getTime();
        return dateFormatter.format(tomorrow);
    }

}