package com.example.ck.request_api;

import com.example.ck.item_class.cart_item;
import com.example.ck.item_class.productModel.class_product;
import com.example.ck.item_class.productModel.class_review;
import com.example.ck.item_class.userModel.class_cart;
import com.example.ck.item_class.userModel.class_order;
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
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface CallApiUser {
    public static final String LOCALHOST = "172.20.10.2";
    Gson gson = new GsonBuilder().setDateFormat("dd/MM/yyyy").create();
    CallApiUser callApi = new Retrofit.Builder().baseUrl("http://"+LOCALHOST+":3000/").
            addConverterFactory(GsonConverterFactory.create(gson)).
            build().create(CallApiUser.class);

    //Hàm select user
    @GET("http://"+LOCALHOST+":3000/api/v1/users/")
    Call<ArrayList<class_user>> get_ApiUser();
//    //login
//    @FormUrlEncoded
//    @POST("api/v1/login")
//    Call<class_user> login_ApiUser(@Field("u") String username,
//                                  @Field("p") String password);
    //Hàm create user
    @FormUrlEncoded
    @POST("api/v1/signup")
    Call<class_user> post_ApiUser(@Field("u") String username,
                                  @Field("e") String email,
                                  @Field("p") String phone,
                                  @Field("pw") String password,
                                  @Field("rpw") String resetpassword,
                                  @Field("n") String name);

    //Hàm select product home
    @GET("api/v1/products/")
    Call<ArrayList<class_product>> getApiProduct();

    //Hàm select product by category: đồ bóng đa
    @GET("api/v1/products/category/1")
    Call<ArrayList<class_product>> getApiProductDoBongDa();

    //Hàm select product by category: đồ bóng đa
    @GET("api/v1/products/category/2")
    Call<ArrayList<class_product>> getApiProductDoTapGym();

    //Hàm select product by category: đồ bóng đa
    @GET("api/v1/products/category/3")
    Call<ArrayList<class_product>> getApiProductDoTreEm();

    //Hàm load product theo id
    @GET("api/v1/products/{id}")
    Call<ArrayList<class_product>> loadSanPham(@Path("id") String idsp);

    @GET("http://"+LOCALHOST+":3000/api/v1/users/{id}")
    Call<ArrayList<class_user>> getGioHang(@Path("id") String iduser);

    // hàm update giỏ hàng
    @FormUrlEncoded
    @PATCH("api/v1/users/{id}/cart")
    Call<class_cart> updateGioHang(@Path("id") String iduser,
                                              @Field("idProduct") String idsp,
                                              @Field("size") String size,
                                              @Field("quantity") int soluong);
    //Xóa item trong giỏ hàng
    @DELETE("api/v1/users/1/cart/{index}")
    Call<Void> deleteBook(@Path("index") int vitri);

    //Hàm select order
    @GET("api/v1/ordereds/")
    Call<ArrayList<class_order>> getApiOrder();

    //Hàm insert order
    @FormUrlEncoded
    @POST("api/v1/ordereds/")
    Call<cart_item> post_ApiOrder(@Field("rn") String recipient_name,
                                 @Field("rp") String recipient_phone,
                                 @Field("re") String recipient_mail,
                                  @Field("ra") String recipient_adress,
                                 @Field("products") String products,
                                 @Field("sl") String size,
                                  @Field("qt") String quatity,
                                  @Field("d") Integer discount,
                                  @Field("tp") Integer total_price,
                                  @Field("idUser") String idkhach);

    @FormUrlEncoded
    @POST("api/v1/products/{idProduct}/review")
    Call<class_review> post_ApiReview(@Path("idProduct") String idProduct,
                                     @Field("idUser") String idUser,
                                     @Field("rate") String rate,
                                     @Field("feedback") String feedback);

}
