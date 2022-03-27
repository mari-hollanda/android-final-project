package info.hccis.student.entity;

import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

public class StudentViewModel extends ViewModel {

    private List<Student> students = new ArrayList();

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students.clear();
        this.students.addAll(students);
    }
}
