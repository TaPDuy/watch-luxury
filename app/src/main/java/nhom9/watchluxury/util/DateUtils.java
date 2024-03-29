package nhom9.watchluxury.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class DateUtils {

    private static final DateFormat FORMAT = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ", Locale.US);
    private static final DateFormat SIMPLE = new SimpleDateFormat("HH:mm:ss dd/MM/yyyy", Locale.US);

    public static String toSimpleString(Date date) {
        if (date == null)
            return "";

        return SIMPLE.format(date);
    }

    public static String toSimpleString(String date) {
        return toSimpleString(parse(date));
    }

    public static String toString(Date date) {
        if (date == null)
            return "";

        TimeZone tz = TimeZone.getTimeZone("UTC");
        FORMAT.setTimeZone(tz);
        return FORMAT.format(date);
    }

    public static Date parse(String date) {
        Date res;
        try {
            res = FORMAT.parse(date);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        return res;
    }
}
