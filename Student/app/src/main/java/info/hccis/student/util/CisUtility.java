package info.hccis.student.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

/**
 * Class containing utility methods that are useful for our projects.
 *
 * @author bjmaclean
 * @since 20181115
 */
public class CisUtility {

    private static Scanner input = new Scanner(System.in);

    //The isGUI will be used to determine if JOptionPane is used or console
    private static boolean isGUI = false;

    public static void setIsGUI(boolean isGUI) {
        CisUtility.isGUI = isGUI;
    }


    /*
    Methods to get input from the user
     */

    /**
     * This method will use the Math class to get a random number between 1 and
     * the max inclusive.
     *
     * @author BJ MacLean
     * @param max The upper limit for the random number (inclusive)
     * @since 20181121
     */
    public static int getRandom(int max) {

        //Math.random will give a fraction between 0
        double theFraction = Math.random();
        int theResult = (int) (theFraction * max);
        return 1 + theResult;
    }


    /**
     * Get the formatted date
     * https://stackoverflow.com/questions/1459656/how-to-get-the-current-time-in-yyyy-mm-dd-hhmisec-millisecond-format-in-java
     *
     * @author BJ MacLean
     * @since 20190301
     */
    public static String getCurrentDate(String format) {
        //Set the default format.
        if (format == null || format.length() == 0) {
            format = "yyyy-MM-dd";
        }

        return LocalDateTime.now().format(DateTimeFormatter.ofPattern(format));

    }

    /**
     * This method will return the current time in milliseconds since a specific
     * start of time.
     *
     * https://www.tutorialspoint.com/java/lang/system_currenttimemillis.htm
     *
     * @author BJ MacLean
     * @since 20200127
     */
    public static long getNowMillis() {
        return System.currentTimeMillis();
    }

}
