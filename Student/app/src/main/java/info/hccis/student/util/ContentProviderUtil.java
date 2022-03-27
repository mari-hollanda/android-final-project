package info.hccis.student.util;

import static android.content.Intent.ACTION_INSERT;
import static android.content.pm.PackageManager.PERMISSION_GRANTED;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.provider.CalendarContract;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;

public class ContentProviderUtil {

    private static int calendarToUse = -1;
    /**
     * Create a calendar event without intent.
     *
     * @param activity
     * @param eventDescription
     * @return the event id
     * @author BJM
     * @since 20220217
     */

    public static long createEvent(Activity activity, String eventDescription) {

        final String description = eventDescription;
        final ContentResolver cr = activity.getContentResolver();

        //*****************************************************************************************
        //SOURCE:
        //https://stackoverflow.com/questions/13709477/how-to-add-calendar-events-to-default-calendar-silently-without-intent-in-andr
        //
        //Was having issues identifying the calendar to update.  The following code will create a
        //dialog which will allow the user to specify which calendar to update.  Once the user specifies
        //the calendar, the event will be created.
        //******************************************************************************************

        //******************************************************************************************
        //If the calendar to use is not set, provide a dialog for the user to let them specifiy the
        //calendar to use.  This will happen each time the user starts the app.
        //todo:  move it to a Shared Preference.
        //******************************************************************************************

        if (calendarToUse < 0) {

            Cursor cursor;
            if (Integer.parseInt(Build.VERSION.SDK) >= 8)
                cursor = cr.query(Uri.parse("content://com.android.calendar/calendars"), new String[]{"_id", "calendar_displayName"}, null, null, null);
            else
                cursor = cr.query(Uri.parse("content://calendar/calendars"), new String[]{"_id", "displayname"}, null, null, null);
            if (cursor != null && cursor.moveToFirst()) {
                final String[] calNames = new String[cursor.getCount()];
                final int[] calIds = new int[cursor.getCount()];
                for (int i = 0; i < calNames.length; i++) {
                    calIds[i] = cursor.getInt(0);
                    calNames[i] = cursor.getString(1);
                    cursor.moveToNext();
                }

                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

                AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                builder.setTitle("Select calendar");
                builder.setSingleChoiceItems(calNames, -1, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        calendarToUse = calIds[which];
                        dialog.cancel();
                    }

                });

                builder.create().show();
            }
            if (cursor != null) {
                cursor.close();
            }
        }

        //**************************************************************************************
        //Now that we are sure to have a calendar id to use...create the event
        //**************************************************************************************

        long eventId = createEvent(activity, calendarToUse, description);
        return eventId;
    }

    public static long createEvent(Activity activity, int calendarToUse, String description) {
        //******************************************************************************
        // CREATE CALENDAR EVENT - start
        //******************************************************************************

        final ContentResolver cr = activity.getContentResolver();

        long calID = 1;  //   <------ default calendar
        long startMillis = 0;
        long endMillis = 0;
        Calendar beginTime = Calendar.getInstance();
        //Note month 0 based
        //beginTime.set(2022, 1, 10, 7, 30);
        startMillis = beginTime.getTimeInMillis(); //Using current time
        Calendar endTime = Calendar.getInstance();
        //endTime.set(2022, 1, 10, 8, 45);
        endMillis = endTime.getTimeInMillis() + 1000; //using current time plus a second

        ContentValues values = new ContentValues();
        values.put(CalendarContract.Events.DTSTART, startMillis);
        values.put(CalendarContract.Events.DTEND, endMillis);
        values.put(CalendarContract.Events.TITLE, "Ticket Order Created");
        values.put(CalendarContract.Events.DESCRIPTION, description);
        //values.put(CalendarContract.Events.CALENDAR_ID, calID);
        values.put(CalendarContract.Events.CALENDAR_ID, calendarToUse); //calendar entered by user
        String[] zones = TimeZone.getAvailableIDs();

        values.put(CalendarContract.Events.EVENT_TIMEZONE, "America/Halifax");
        Uri uri = cr.insert(CalendarContract.Events.CONTENT_URI, values);

        // get the event ID that is the last element in the Uri
        return Long.parseLong(uri.getLastPathSegment());
    }

    /**
     * Access calendar provider without permissions
     *
     * @author ANDROID_content_providers
     * @since 2022-02-10
     */
    public static void createCalendarEventIntent(Activity activity, String eventDescription) {
        //initialize Calendar intent
        Intent newIntent = new Intent(ACTION_INSERT);
        //set the intent datatype
        newIntent.setData(CalendarContract.Events.CONTENT_URI);
        //title of Calendar event
        newIntent.putExtra(CalendarContract.Events.TITLE, "Ticket order created");
        //send number of tickets to event description
        newIntent.putExtra(CalendarContract.Events.DESCRIPTION, eventDescription);
        //make the event not "all day", so user can add start and end time in Calendar app
        newIntent.putExtra(CalendarContract.Events.ALL_DAY, false);
        //check to see if there is an app to support your action and start activity
        if (newIntent.resolveActivity(activity.getPackageManager()) != null) {
            activity.startActivity(newIntent);
            //if there is not, display an appropriate message on the view tickets page
        } else {
            Toast.makeText(activity, "There is no app that supports this action",
                    Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * NOTE reviewing how to get permissions can be important.
     *
     * @param callbackId
     * @param permissionsId
     * @since 20220210
     */

    public static void checkPermission(Activity activity, int callbackId, String... permissionsId) {
        boolean permissions = true;

        for (String p : permissionsId) {
            permissions = permissions && ContextCompat.checkSelfPermission(activity.getApplicationContext(), p) == PERMISSION_GRANTED;
        }

        if (!permissions)
            ActivityCompat.requestPermissions(activity, permissionsId, callbackId);
    }


}
