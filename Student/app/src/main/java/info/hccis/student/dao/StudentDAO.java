package info.hccis.student.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import info.hccis.student.entity.Student;

/**
 * Student DAO
 *
 * @author mariannahollanda
 * @since 20220303
 */
@Dao
public interface StudentDAO {
    @Insert
    void insert(Student student);

    @Query("SELECT * FROM Student")
    List<Student> selectAllStudents();

    @Query("delete from Student")
    void deleteAll();
}
