package info.hccis.student.dao;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import info.hccis.student.entity.Student;

@Database(entities = {Student.class},version = 1)
public abstract class MyAppDatabase extends RoomDatabase {
    public abstract StudentDAO studentDAO();
}

