package info.hccis.student.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import androidx.annotation.RequiresApi;

/**
 * Broadcast Receiver
 *
 * @author cis2250
 * @since 2021
 * @modified 20220303
 * @author mariannahollanda
 */
public class MyBroadCastReceiver extends BroadcastReceiver {
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED)) {
            Intent serviceIntent = new Intent(context, MyForegService.class);
            context.startForegroundService(serviceIntent);
        }
    }
}