package info.hccis.student.ui.settings;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

import info.hccis.student.R;

/**
 * Settings Activity
 *
 * @author cis2250
 * @since 2022
 * @modified 20220303
 * @author mariannahollanda
 */
public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        Switch switch_button = (Switch) findViewById(R.id.switchLoadStudentsFromRoom);

        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        boolean loadFromRoom = sharedPref.getBoolean(getString(R.string.preference_load_from_room), true);
        switch_button.setChecked(loadFromRoom);

        switch_button.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    Toast.makeText(getApplicationContext(),
                            "Will load from local database if no network", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(),
                            "Will NOT load from local database if no network", Toast.LENGTH_LONG).show();
                }
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putBoolean(getString(R.string.preference_load_from_room), isChecked);
                editor.commit();
            }
        });
    }
}