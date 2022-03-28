package info.hccis.tennislessons.bo

import info.hccis.tennislessons.bo.CisUtility
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

/**
 * Class containing utility methods that are useful for our projects.
 *
 * @author bjmaclean
 * @since 20181115
 */
object CisUtility {
    private val input = Scanner(System.`in`)

    //The isGUI will be used to determine if JOptionPane is used or console
    private var isGUI = false
    fun setIsGUI(isGUI: Boolean) {
        CisUtility.isGUI = isGUI
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
    fun getRandom(max: Int): Int {

        //Math.random will give a fraction between 0
        val theFraction = Math.random()
        val theResult = (theFraction * max).toInt()
        return 1 + theResult
    }

    /**
     * Get the formatted date
     * https://stackoverflow.com/questions/1459656/how-to-get-the-current-time-in-yyyy-mm-dd-hhmisec-millisecond-format-in-java
     *
     * @author BJ MacLean
     * @since 20190301
     */
    fun getCurrentDate(format: String?): String {
        //Set the default format.
        var format = format
        if (format == null || format.length == 0) {
            format = "yyyy-MM-dd"
        }
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern(format))
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
    val nowMillis: Long
        get() = System.currentTimeMillis()
}