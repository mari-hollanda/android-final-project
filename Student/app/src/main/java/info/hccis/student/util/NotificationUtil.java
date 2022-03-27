package info.hccis.student.util;

import android.app.Activity;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import info.hccis.student.R;


public class NotificationUtil {

    /**
     * Send a notification to the user
     * @param title
     * @param message
     * @since 20220217
     * @author BJM
     */

    public static synchronized void sendNotification(String title, String message) {
        //Channel Id is ignored on lower APIs
        Log.d("BJM notification", "Sending a notification");

        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(NotificationApplication.getContext(), NotificationApplication.MEMBER_CHANNEL_ID)
                        .setSmallIcon(R.drawable.ic_heart)
                        .setContentTitle(title)
                        .setContentText(message);

        NotificationManager notificationManager = (NotificationManager)NotificationApplication.getContext().getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0, builder.build());
    }

    /**
     * Send a notification to the user.  This version of the method will also setup a pending intent
     * which will be used if the user clicks on the notification.
     * @since 20220217
     * @author BJM
     * @param title Title of notification
     * @param message Message of notification
     * @param activity Originating activity
     * @param destinationClass Destination to send user when they click the notification
     */
    public static synchronized void sendNotification(String title, String message, Activity activity, Class destinationClass) {

        // Create an explicit intent for an Activity in your app
        Intent intent = new Intent(activity, destinationClass);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

        //******************************************************************************************
        //Setting up the intent to be launched with the user clicks on the notification.
        //******************************************************************************************
        PendingIntent pendingIntent = PendingIntent.getActivity(NotificationApplication.getContext(), 0, intent, 0);

        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(NotificationApplication.getContext(), NotificationApplication.MEMBER_CHANNEL_ID)
                        .setSmallIcon(R.drawable.ic_heart)
                        .setContentTitle(title)
                        .setContentText(message)
                        .setContentIntent(pendingIntent);

        NotificationManager notificationManager = (NotificationManager) NotificationApplication.getContext().getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0, builder.build());
    }

}
