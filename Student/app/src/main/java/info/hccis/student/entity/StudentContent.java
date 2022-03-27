package info.hccis.student.entity;

import android.util.Log;

import java.util.List;

import info.hccis.student.MainActivity;

public class StudentContent {
    public static void reloadStudentsInRoom(List<Student> students)
    {
        MainActivity.getMyAppDatabase().studentDAO().deleteAll();
        for(Student current : students)
        {
            MainActivity.getMyAppDatabase().studentDAO().insert(current);
        }
        Log.d("MHCP Room","loading students into Room");
    }

    public static List<Student> getStudentFromRoom()
    {
        Log.d("MHCP Room","Loading students from Room");

        List<Student> studentBack = MainActivity.getMyAppDatabase().studentDAO().selectAllStudents();
        Log.d("MHCP Room","Number of students loaded from Room: " + studentBack.size());
        for(Student current : studentBack)
        {
            Log.d("MHCP Room",current.toString());
        }
        return studentBack;
    }

}