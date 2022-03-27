package info.hccis.student;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.room.Room;

import com.google.android.material.navigation.NavigationView;

import info.hccis.student.api.ApiWatcher;
import info.hccis.student.dao.MyAppDatabase;
import info.hccis.student.databinding.ActivityMainBinding;
import info.hccis.student.receiver.StudentBroadcastReceiver;
import info.hccis.student.ui.about.AboutActivity;
import info.hccis.student.ui.settings.SettingsActivity;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityMainBinding binding;
    private static ApiWatcher apiWatcher;
    private StudentBroadcastReceiver studentBroadcastReceiver = new StudentBroadcastReceiver();
    private static NavController navController = null;

    //Room Database
    private static MyAppDatabase myAppDatabase;

    //Provide a getter for the database to be used throughout the app.
    public static MyAppDatabase getMyAppDatabase() {
        return myAppDatabase;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

/*        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);*/

        //************************************************************************************
        // Listener for FAB here
        //************************************************************************************

//        binding.fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_student,R.id.nav_student_list,
                R.id.nav_social_media)
                .setDrawerLayout(drawer)
                .build();
        navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
        myAppDatabase = Room.databaseBuilder(getApplicationContext(), MyAppDatabase.class, "student").allowMainThreadQueries().build();


        apiWatcher = new ApiWatcher();
        apiWatcher.setActivity(this);
        apiWatcher.start();  //Start the background thread

        //****************************************************************************************
        //Set the database attribute
        //****************************************************************************************
        myAppDatabase = Room.databaseBuilder(getApplicationContext(), MyAppDatabase.class, "student").allowMainThreadQueries().build();

        //**************************************************************************************
        //register receiver
        //**************************************************************************************
        IntentFilter filter = new IntentFilter();
        filter.addAction("info.hccis.student.addstudent");
        //registerReceiver(exampleReceiver, filter);
        LocalBroadcastManager lbm = LocalBroadcastManager.getInstance(this);
        lbm.registerReceiver(studentBroadcastReceiver, filter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LocalBroadcastManager lbm = LocalBroadcastManager.getInstance(this);
        Log.d("MHCP receiver", "unregistering");
        lbm.unregisterReceiver(studentBroadcastReceiver);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    /**
     * Code here for reacting to user selecting menu items.
     *
     * @param item
     * @return true/false
     * @author BJM
     * @since 20220129
     */

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings || id == R.id.nav_settings) {
            Log.d("MainActivity MHCP", "Option selected Settings");
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
            return true;
        } else if (id == R.id.action_about) {
            Log.d("MainActivity MHCP", "Option selected About");
            Intent intent = new Intent(this, AboutActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }
}