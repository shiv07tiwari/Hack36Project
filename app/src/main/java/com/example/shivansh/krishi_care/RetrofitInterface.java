package com.example.shivansh.krishi_care;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface RetrofitInterface {
    @GET("{users_phone_no}/AUTOGEN")
    Call<OTPMessageResponse> sentOTP(@Path("users_phone_no")String phone_no);

    @GET("VERIFY/{session_id}/{otp_entered_by_user}")
    Call<OTPMessageResponse> verifyOTP(@Path("session_id")String session_id,@Path("otp_entered_by_user")String otp_entered_by_user);

    @GET("home")
    Call<SOSResponse> soscheck();

    @GET("/todo/{name}/{description}/{time}/{date}")
    Call<SOSResponse> addTodoTask(@Path("name") String name,@Path("description") String description,@Path("time") String time, @Path("date") String date);

    @GET("/getTODO")
    Call<List<ToDoBody>> getAllToDo();

    @GET("gsfn")
    Call<SOSResponse> getSentiment();


}
