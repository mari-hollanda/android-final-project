package info.hccis.student.ui.student;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.navigation.fragment.NavHostFragment;

import com.google.android.material.snackbar.Snackbar;

import info.hccis.student.R;
import info.hccis.student.databinding.FragmentAddStudentBinding;
import info.hccis.student.entity.Student;
import info.hccis.student.entity.StudentRepository;
import info.hccis.student.entity.StudentViewModel;
import info.hccis.student.util.ContentProviderUtil;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddStudentsFragment extends Fragment {

    public static final String KEY = "info.hccis.student.student";
    private FragmentAddStudentBinding binding;
    private Student student;
    private StudentViewModel studentViewModel;
    private StudentRepository studentRepository;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("AddStudentFragment MHCP", "onCreate triggered");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        Log.d("AddStudentFragment MHCP", "onCreateView triggered");
        binding = FragmentAddStudentBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.d("AddStudentFragment MHCP", "onViewCreated triggered");
        studentViewModel = new ViewModelProvider(getActivity()).get(StudentViewModel.class);
        final int callbackId = 42;
        ContentProviderUtil.checkPermission(getActivity(), callbackId, Manifest.permission.READ_CALENDAR, Manifest.permission.WRITE_CALENDAR);
        studentRepository = studentRepository.getInstance();
        binding.buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("AddStudentFragment MHCP", "Submit was clicked");
                try {
                    addStudent();
                    Bundle bundle = new Bundle();
                    bundle.putSerializable(KEY, student);
                    studentRepository.getStudentService().createStudent(student).enqueue(new Callback<Student>() {
                        @Override
                        public void onResponse(Call<Student> call, Response<Student> r) {
                            Toast.makeText(getContext(), "Student is successfully added!", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onFailure(Call<Student> call, Throwable t) {
                            Toast.makeText(getContext(), "Error adding student: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });

/*                    Snackbar.make(view, "Have added a student. Want to add this new student to the database. " +
                            "How should we handle this?", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();*/

                    long eventID = ContentProviderUtil.createEvent(getActivity(), student.toString());
                    Toast.makeText(getActivity(), "Calendar Event Created (" + eventID + ")", Toast.LENGTH_SHORT);
                    Log.d("MHCP Calendar", "Calendar Event Created (" + eventID + ")");

                    LocalBroadcastManager lbm = LocalBroadcastManager.getInstance(view.getContext());
                    Intent intent = new Intent();
                    intent.setAction("info.hccis.student.student");
                    lbm.sendBroadcast(intent);
                    getActivity().sendBroadcast(intent);

                    NavHostFragment.findNavController(AddStudentsFragment.this)
                            .navigate(R.id.action_nav_student_to_nav_student_list, bundle);
                } catch (Exception e) {
                   Log.d("AddStudentFragment MHCP", "Error submitting: " + e.getMessage());
                }
            }
        });
    }

    public void addStudent() throws Exception {

        Log.d("MHCP-MainActivity", "First Name entered =" + binding.fragmentAddStudentFirstName.getText().toString());
        Log.d("MHCP-MainActivity", "Last Name entered =" + binding.fragmentAddStudentLastName.getText().toString());
        Log.d("MHCP-MainActivity", "Phone Number entered =" + binding.fragmentAddStudentPhoneNumber.getText().toString());
        Log.d("MHCP-MainActivity", "Email Address entered =" + binding.fragmentAddStudentEmail.getText().toString());
        Log.d("MHCP-MainActivity", "Program Code entered =" + binding.fragmentAddStudentProgram.getText().toString());


        student = new Student();
        student.setFName(binding.fragmentAddStudentFirstName.getText().toString());
        student.setLName(binding.fragmentAddStudentLastName.getText().toString());
        student.setPNum(binding.fragmentAddStudentPhoneNumber.getText().toString());
        student.setEMail(binding.fragmentAddStudentEmail.getText().toString());
        int programCode;
        try {
            programCode = Integer.parseInt(binding.fragmentAddStudentProgram.getText().toString());
        } catch (Exception e) {
            programCode = 1;
        }
        student.setProg(programCode);
    }

}
