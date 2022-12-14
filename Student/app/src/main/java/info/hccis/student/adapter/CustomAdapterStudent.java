package info.hccis.student.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import info.hccis.student.R;
import info.hccis.student.entity.Student;

/**
 * Student Custom Adapter
 *
 * @author cis2250
 * @since 2022
 * @modified 20220303
 * @author mariannahollanda
 */
public class CustomAdapterStudent extends RecyclerView.Adapter<CustomAdapterStudent.StudentViewHolder> {

    private List<Student> studentArrayList;

    public CustomAdapterStudent(List<Student> studentBOArrayList) {
        this.studentArrayList = studentBOArrayList;
    }

    @NonNull
    @Override
    public StudentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_view, parent, false);
        return new StudentViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull StudentViewHolder holder, int position) {
        String fName = "" + studentArrayList.get(position).getFirstName();
        String lName = "" + studentArrayList.get(position).getLastName();
        String pNum = "" + studentArrayList.get(position).getPhoneNumber();
        String email = "" + studentArrayList.get(position).getEmailAddress();
        String program = "" + studentArrayList.get(position).getProgramCode();
        holder.textViewStudentFirstNameListItem.setText(fName);
        holder.textViewStudentLastNameListItem.setText(lName);
        holder.textViewPhoneNumberListItem.setText(pNum);
        holder.textViewEmailListItem.setText(email);
        holder.textViewProgramListItem.setText(program);
    }

    @Override
    public int getItemCount() { return studentArrayList.size(); }

    public class StudentViewHolder extends RecyclerView.ViewHolder {
        private TextView textViewStudentFirstNameListItem;
        private TextView textViewStudentLastNameListItem;
        private TextView textViewPhoneNumberListItem;
        private TextView textViewEmailListItem;
        private TextView textViewProgramListItem;

        public StudentViewHolder(final View view) {
            super(view);
            textViewStudentFirstNameListItem = view.findViewById(R.id.textViewStudentFirstNameListItem);
            textViewStudentLastNameListItem = view.findViewById(R.id.textViewStudentLastNameListItem);
            textViewPhoneNumberListItem = view.findViewById(R.id.textViewPhoneNumberListItem);
            textViewEmailListItem = view.findViewById(R.id.textViewEmailListItem);
            textViewProgramListItem = view.findViewById(R.id.textViewProgramListItem);
        }
    }
}