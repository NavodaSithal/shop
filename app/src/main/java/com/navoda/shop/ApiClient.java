package com.navoda.shop;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {

    public static final String BASE_URL = "http://lahiruat-29044.portmap.io:29044/grocery-core/api/auth/";
    private static Retrofit retrofit = null;
    private static ApiClient clientInstance;

    private ApiClient(){
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static synchronized ApiClient getInstance(){
        if (clientInstance == null){
            clientInstance = new ApiClient();
        }

        return clientInstance;
    }

    public ApiInterface getApi(){
        return retrofit.create(ApiInterface.class);
    }
}
