package info.hccis.student.ui.socialmedia;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import info.hccis.student.R;
import info.hccis.student.util.CisSocialMediaUtil;

/**
 * Social Media Activity
 *
 * @author cis2250
 * @since 2022
 * @modified 20220303
 * @author mariannahollanda
 */
public class SocialMediaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sendmessage);

        EditText editTextMessage = findViewById(R.id.editTextMessage);

        Button buttonSendMessage = findViewById(R.id.buttonSendMessage);
        Activity thisActivity = this;
        buttonSendMessage.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                String message = editTextMessage.getText().toString();
                //******************************************************************************
                //Social Media
                //******************************************************************************
                startActivity(CisSocialMediaUtil.shareOnSocialMedia(thisActivity, CisSocialMediaUtil.PACKAGE_TWITTER, "Student", message));
            }
        });
    }
}