package info.hccis.student.api;

import android.content.SharedPreferences;
import android.util.Log;

import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.preference.PreferenceManager;

import java.util.ArrayList;
import java.util.List;

import info.hccis.student.MainActivity;
import info.hccis.student.R;
import info.hccis.student.ui.student.ViewStudentsFragment;
import info.hccis.student.entity.Student;
import info.hccis.student.entity.StudentContent;
import info.hccis.student.entity.StudentViewModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Student API Watcher
 *
 * @author cis2250
 * @since 2022
 * @modified 20220303
 * @author mariannahollanda
 */
public class ApiWatcher extends Thread {

    public static final String API_BASE_URL = "http://10.0.2.2:8081/api/StudentService/";

    private int lengthLastCall = -1;
    private FragmentActivity activity = null;

    public void setActivity(FragmentActivity activity) {
        this.activity = activity;
    }

    @Override
    public void run() {
        super.run();
        try {
            do {
                Log.d("MHCP api", "running");
                Retrofit retrofit = new Retrofit.Builder().baseUrl(ApiWatcher.API_BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
                JsonStudentApi jsonStudentApi = retrofit.create(JsonStudentApi.class);
                Call<List<Student>> call = jsonStudentApi.getStudent();
                StudentViewModel studentViewModel = new ViewModelProvider(activity).get(StudentViewModel.class);

                call.enqueue(new Callback<List<Student>>() {

                    @Override
                    public void onResponse(Call<List<Student>> call, Response<List<Student>> response) {
                        Log.d("MHCP api", "found ticket order view model. size=" + studentViewModel.getStudents().size());
                        if (!response.isSuccessful()) {
                            Log.d("MHCP api", "MHCP not successful response from rest for ticket orders Code=" + response.code());

                            loadFromRoomIfPreferred(studentViewModel);
                            lengthLastCall = -1; //Indicate couldn't load from api will trigger reload next time
                        } else {
                            ArrayList<Student> studentsTemp = new ArrayList(response.body()); //note gson will be used implicitly
                            int lengthThisCall = studentsTemp.size();

                            Log.d("MHCP api", "back from api, size=" + lengthThisCall);
                            if (lengthLastCall == -1) {
                                lengthLastCall = lengthThisCall;
                                studentViewModel.setStudents(studentsTemp); //Will addAll
                                Log.d("MHCP api ", "First load of ticket orders from the api");
                                activity.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Log.d("MHCP api", "trying to notify adapter that things have changed");
                                        ViewStudentsFragment.notifyDataChanged("Found more rows");
                                    }
                                });
                                StudentContent.reloadStudentsInRoom(studentsTemp);
                            } else if (lengthThisCall != lengthLastCall) {
                                Log.d("MHCP api", "Data has changed");
                                lengthLastCall = lengthThisCall;
                                studentViewModel.getStudents().clear();
                                studentViewModel.getStudents().addAll(studentsTemp);

                                activity.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Log.d("mhcp api", "trying to notify adapter that things have changed");
                                        Log.d("mhcp api", "Also using method to send a notification to the user");
                                        ViewStudentsFragment.notifyDataChanged("Update - the data has changed", activity, MainActivity.class);
                                    }
                                });

                                StudentContent.reloadStudentsInRoom(studentsTemp);

                            } else {
                                Log.d("mhcp api", "Data has not changed");
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<List<Student>> call, Throwable t) {
                        Log.d("mhcp api", "api call failed");
                        Log.d("mhcp api", t.getMessage());

                        lengthLastCall = -1; //Indicate couldn't load from api will trigger reload next time
                        loadFromRoomIfPreferred(studentViewModel);
                    }
                });

                final int SLEEP_TIME = 10000;
                Log.d("MHCP Sleep", "Sleeping for " + (SLEEP_TIME / 1000) + " seconds");
                Thread.sleep(SLEEP_TIME); //Check api every 10 seconds

            } while (true);
        } catch (InterruptedException e) {
            Log.d("MHCP api", "Thread interrupted. Stopping in the thread.");
        }
    }

    /**
     * Check the shared preferences and load from the db if the setting is set to do such a thing.
     *
     * @param studentViewModel
     * @author BJM
     * @since 20220211
     * @modified 20220303
     * @author mariannahollanda
     */
    public void loadFromRoomIfPreferred(StudentViewModel studentViewModel) {
        Log.d("MHCP room", "Check to see if should load from room");
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(activity.getApplicationContext());
        boolean loadFromRoom = sharedPref.getBoolean(activity.getString(R.string.preference_load_from_room), true);
        Log.d("MHCP room", "Load from Room=" + loadFromRoom);
        if (loadFromRoom) {
            List<Student> testList = MainActivity.getMyAppDatabase().studentDAO().selectAllStudents();
            Log.d("MHCP room", "Obtained ticket orders from the db: " + testList.size());
            studentViewModel.setStudents(testList); //Will add all ticket orders

            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Log.d("mhcp api", "trying to notify adapter that things have changed");
                    ViewStudentsFragment.notifyDataChanged("Found more rows");
                }
            });
        }
    }
}
