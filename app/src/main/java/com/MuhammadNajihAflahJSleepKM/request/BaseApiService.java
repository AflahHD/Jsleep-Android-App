package com.MuhammadNajihAflahJSleepKM.request;

import com.MuhammadNajihAflahJSleepKM.model.Account;
import com.MuhammadNajihAflahJSleepKM.model.BedType;
import com.MuhammadNajihAflahJSleepKM.model.City;
import com.MuhammadNajihAflahJSleepKM.model.Facility;
import com.MuhammadNajihAflahJSleepKM.model.Payment;
import com.MuhammadNajihAflahJSleepKM.model.Renter;
import com.MuhammadNajihAflahJSleepKM.model.Room;
import com.MuhammadNajihAflahJSleepKM.model.Voucher;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface BaseApiService {
    @GET("account/{id}")
    Call<Account> getAccount(@Path("id") int id);

    @POST("account/login")
    Call<Account> login (@Query("email") String email, @Query("password") String password);

    @POST("account/register")
    Call<Account> register  (@Query("name") String name,
                             @Query("email") String email,
                             @Query("password") String password);

    @POST("account/{id}/registerRenter")
    Call<Renter> registerRenter(@Query("id") int id,
                                @Query("username") String username,
                                @Query("address") String address,
                                @Query("phoneNumber") String phoneNumber);

    @POST("account/{id}/topUp")
    Call<Boolean> topUp(@Path("id") int id, @Query("balance") double balance);

    //RoomController BaseApi
    @GET("room/{id}")
    Call<Room> getRoom (@Path("id") int id);

    @GET("room/{id}/renter")
    Call<List<Room>> getRoomByRenter (@Path("id") int id,
                                      @Query("page") int page,
                                      @Query("pageSize") int pageSize);

    @POST("room/create")
    Call<Room> createRoom (@Query("accountId") int accountId,
                           @Query("name") String name,
                           @Query("size") int size,
                           @Query("price") double price,
                           @Query("facility") Facility[] facility,
                           @Query("city") City city,
                           @Query("address") String address,
                           @Query("bedType") BedType bedType);

    @GET("voucher/{id}")
    Call<Voucher> getVoucher (@Path("id") int id);

    @GET("voucher/{id}/isUsed")
    Call<Boolean> isUsed (@Path("id") int id);

    @GET("voucher/{id}/canApply")
    Call<Boolean> canApply (@Path("id") int id, @Query("price") double price);

    @GET("voucher/getAvailable")
    Call<List<Voucher>> getAvailable(@Query("page") int page,
                                     @Query("pageSize") int pageSize);

    @GET("payment/{id}")
    Call<Payment> getPayment(@Path("id") int id);

    @POST("payment/create")
    Call<Payment> createPayment (@Query("buyerId") int buyerId,
                                 @Query("renterId") int renterId,
                                 @Query("roomId") int roomId,
                                 @Query("from") String from,
                                 @Query("to") String to);

    @POST("payment/{id}/accept")
    Call<Boolean> accept (@Path("id") int id);

    @POST("payment/{id}/cancel")
    Call<Boolean> cancel (@Path("id") int id);

    @GET("room/getAllRoom")
    Call<List<Room>> getAllRoom(@Query("page") int page, @Query("pageSize") int pageSize);

}
