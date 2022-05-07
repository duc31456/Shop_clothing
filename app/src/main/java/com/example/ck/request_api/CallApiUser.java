package com.example.ck.request_api;

import com.example.ck.item_class.productModel.class_product;
import com.example.ck.item_class.userModel.class_user;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface CallApiUser {

    Gson gson = new GsonBuilder().setDateFormat("dd/MM/yyyy").create();
    CallApiUser callApi = new Retrofit.Builder().baseUrl("http://192.168.1.7:3000/").
            addConverterFactory(GsonConverterFactory.create(gson)).
            build().create(CallApiUser.class);

    //Hàm select user
    @GET("http://192.168.1.7:3000/api/v1/users/")
    Call<List<class_user>> get_ApiUser();

    //Hàm create user có ảnh
    @Multipart
    @POST("api/v1/signup")
    Call<List<class_user>> post_ApiUser(@Path("u") RequestBody username,
                                       @Path("e") RequestBody email,
                                       @Path("p") RequestBody phone,
                                       @Path("pw") RequestBody password,
                                       @Path("rpw") RequestBody resetpassword,
                                       @Path("n") RequestBody name,
                                       @Path("l") MultipartBody.Part avatar);

    //Hàm put user có ảnh
    @Multipart
    @PUT("api/v1/signup")
    Call<List<class_user>> put_ApiUser(@Path("u") RequestBody username,
                                       @Path("e") RequestBody email,
                                       @Path("p") RequestBody phone,
                                       @Path("pw") RequestBody password,
                                       @Path("rpw") RequestBody resetpassword,
                                       @Path("n") RequestBody name,
                                       @Path("l") MultipartBody.Part avatar);

    //Hàm select product
    @GET("http://192.168.1.7:3000/api/v1/products/")
    Call<ArrayList<class_product>> getApiProduct();

}
