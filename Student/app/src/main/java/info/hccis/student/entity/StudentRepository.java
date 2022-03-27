package info.hccis.student.entity;

import info.hccis.student.api.ApiWatcher;
import info.hccis.student.api.JsonStudentApi;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class StudentRepository {

    private static StudentRepository instance;

    private JsonStudentApi jsonStudentApi;

    public static StudentRepository getInstance() {
        if (instance == null) {
            instance = new StudentRepository();
        }
        return instance;
    }

    public StudentRepository() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiWatcher.API_BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        jsonStudentApi = retrofit.create(JsonStudentApi.class);
    }

    public JsonStudentApi getStudentService() {
        return jsonStudentApi;
    }

}
