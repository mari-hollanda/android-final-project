package info.hccis.student.api;

import java.util.List;

import info.hccis.student.entity.Student;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface JsonStudentApi {
    @GET("student")
    Call<List<Student>> getStudent();
    @POST("student")
    Call<Student> createStudent(@Body Student student);
}
