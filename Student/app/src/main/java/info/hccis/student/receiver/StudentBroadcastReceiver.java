package info.hccis.student.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

/**
 * Student BroadcastReceiver
 *
 * @author mariannahollanda
 * @since 20220303
 */
public class StudentBroadcastReceiver extends BroadcastReceiver {
    public StudentBroadcastReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, "Action: " + intent.getAction(), Toast.LENGTH_SHORT).show();
        Log.d("MHCP receiver", "Order Created...Action: " + intent.getAction());
    }
}