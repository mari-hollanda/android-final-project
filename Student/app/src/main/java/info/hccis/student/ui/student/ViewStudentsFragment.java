package info.hccis.student.ui.student;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import info.hccis.student.MainActivity;
import info.hccis.student.R;
import info.hccis.student.adapter.CustomAdapterStudent;
import info.hccis.student.databinding.FragmentViewStudentsBinding;
import info.hccis.student.entity.Student;
import info.hccis.student.entity.StudentViewModel;
import info.hccis.student.util.NotificationApplication;
import info.hccis.student.util.NotificationUtil;

/**
 * View Students Fragment
 *
 * @author cis2250
 * @since 2022
 * @modified 20220303
 * @author mariannahollanda
 */
public class ViewStudentsFragment extends Fragment {

    private static Context context;
    private FragmentViewStudentsBinding binding;
    private List<Student> studentArrayList;
    private static RecyclerView recyclerView;

    public static RecyclerView getRecyclerView() {
        return recyclerView;
    }

    public static void notifyDataChanged(String message) {
        Log.d("mhcp", "Data changed: " + message);
        try {
            recyclerView.getAdapter().notifyDataSetChanged();
        } catch (Exception e) {
            Log.d("mhcp api", "Exception when trying to notify that the data set has changed.");
        }
    }

    public static void notifyDataChanged(String message, Activity activity, Class destinationClass) {
        Log.d("mhcp", "Data changed: " + message);
        try {
            notifyDataChanged(message);
            NotificationApplication.setContext(context);
            NotificationUtil.sendNotification("Students Data Update", message, activity, MainActivity.class);
        } catch (Exception e) {
            Log.d("mhcp notification", "Exception occurred when notifying. " + e.getMessage());
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentViewStudentsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        StudentViewModel studentViewModel = new ViewModelProvider(getActivity()).get(StudentViewModel.class);
        context = getView().getContext();
        recyclerView = binding.recyclerView;
        studentArrayList = studentViewModel.getStudents();
        setAdapter();
        binding.buttonAddStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(ViewStudentsFragment.this)
                        .navigate(R.id.action_nav_student_list_to_nav_student);
            }
        });
    }

    private void setAdapter() {
        CustomAdapterStudent adapter = new CustomAdapterStudent(studentArrayList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}