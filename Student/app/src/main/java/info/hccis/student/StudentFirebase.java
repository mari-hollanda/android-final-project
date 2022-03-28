package info.hccis.student;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import info.hccis.student.entity.Student;

public class StudentFirebase {
    private DatabaseReference databaseReference;

    public StudentFirebase(){
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        databaseReference = db.getReference(Student.class.getSimpleName());
    }

    public void addStudentDatabase(Student student){
        databaseReference.push().setValue(student);
    }
}
