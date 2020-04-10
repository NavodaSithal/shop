package com.navoda.shop;

//import info.androidhive.retrofit.model.MoviesResponse;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;


public interface ApiInterface {

    @FormUrlEncoded
    @POST("signin/")
    Call<tokenObj> signin(
            @Field("username") String username,
            @Field("password") String password
    );


}
