package info.hccis.student.util;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.util.Log;

import java.util.List;

public class CisSocialMediaUtil {

    public static final String PACKAGE_TWITTER = "com.twitter.android";

    public static Intent shareOnSocialMedia(Activity activity, String shareUsingPackage,  String subject, String message) {

        // the intent "ACTION_SEND" will be used to send the information from one activity to another
        Intent intent = new Intent(Intent.ACTION_SEND);
        // set the type of input you will be sending to the application
        intent.setType("text/plain");

        PackageManager packageManager = activity.getPackageManager();
        List<ResolveInfo> resolveInfoList = packageManager.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);

        boolean resolved = false;

        //This isn't mandatory but a subject line can be added
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);

        //put the text that has been entered into the input
        intent.putExtra(Intent.EXTRA_TEXT, message);

        for (ResolveInfo resolveInfo : resolveInfoList) {
            if (resolveInfo.activityInfo.packageName.startsWith(shareUsingPackage)) {
                intent.setClassName(resolveInfo.activityInfo.packageName, resolveInfo.activityInfo.name);
                resolved = true;
                break;
            }
        }
        if (!resolved) {
            //textView.setText("Error no app found");
            Log.d("BJM", "could not resolve app");
            //This  creates the dialogue menu pop up of the apps that the message can be shared using
            intent = intent.createChooser(intent, "Share message using");
        }

        return intent;

    }
}