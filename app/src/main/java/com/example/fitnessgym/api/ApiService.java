package com.example.fitnessgym.api;

import com.example.fitnessgym.AdminActivity.PackageAdmin;
import com.example.fitnessgym.Email;
import com.example.fitnessgym.PTrequest.GetTime;
import com.example.fitnessgym.Giaodich;
import com.example.fitnessgym.Membership.Membership;
import com.example.fitnessgym.OTP;
import com.example.fitnessgym.PTrequest.Response;
import com.example.fitnessgym.PTrequest.SentRequest;
import com.example.fitnessgym.Package.PackageUpdate;
import com.example.fitnessgym.Package.aPackage;
import com.example.fitnessgym.Password;
import com.example.fitnessgym.PaymentHistory;
import com.example.fitnessgym.PaymentRequest;
import com.example.fitnessgym.PaymentRequetsPT;
import com.example.fitnessgym.PtSpinner;
import com.example.fitnessgym.Statistical.Allstatistical;
import com.example.fitnessgym.Statistical.Daystatistical;
import com.example.fitnessgym.Statistical.PTstatistical;
import com.example.fitnessgym.User.LoginRequest;
//import com.example.fitnessgym.User.RegisterRequest;
import com.example.fitnessgym.Package.Package;
import com.example.fitnessgym.User.Ptinfo;
import com.example.fitnessgym.User.RegisterRequest;
import com.example.fitnessgym.User.Userinfo;
import com.example.fitnessgym.User.Userupdate;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface ApiService {
    @POST("user/login.php")
    Call<ApiResponse> login(@Body LoginRequest request);

//    @POST("user/addUser.php")
//    Call<ApiResponse> register(@Body RegisterRequest request);


    @POST("user/register.php")
    Call<ApiResponse> registerWithImage(@Body RegisterRequest request);

    @Multipart
    @POST("user/addUser.php")  // Đảm bảo đường dẫn API đúng với backend
    Call<ApiResponse> registerPt(
            @Part("userName") RequestBody name,
            @Part("phoneNumber") RequestBody phone,
            @Part("email") RequestBody email,
            @Part("password") RequestBody password,
            @Part("role") RequestBody role, // Trường role được gửi ở đây
            @Part MultipartBody.Part image // Thêm tham số image
    );


    @GET("user/getUser.php")  // Đường dẫn đến API PHP của bạn
    Call<List<Userinfo>> getUsers();


    @GET("user/getUser.php")  // Đường dẫn đến API PHP của bạn
    Call<List<Ptinfo>> getPts();


    @GET("package/getPackage.php")
    Call<List<Package>> getPackages();

    @GET("user/getGiaoDich.php")
    Call<List<Giaodich>> getGiaodich();

    @GET("package/getPackage.php")
    Call<List<PackageAdmin>> getAdminPackages();

    @GET("membership/getAllMembership.php")
    Call<List<Membership>> getAllMembership();

    @POST("package/addPackage.php")
    Call<ApiResponse> addWorkoutPackage(@Body aPackage apackage);


    @POST("package/paymentPackage.php")
    Call<ApiResponse> paymentrequest(@Body PaymentRequest paymentRequest);

    @POST("package/paymentPackage.php")
    Call<ApiResponse> paymentrequestpt(@Body PaymentRequetsPT PaymentRequetsPT);

    @PUT("package/updatePackage.php")
    Call<ApiResponse> updatePackage(
            @Query("id") int id,       // ID truyền qua query string
            @Body PackageUpdate body   // Dữ liệu JSON truyền qua body
    );

    @DELETE("package/deletePackage.php")
    Call<ApiResponse> deletePackage(@Query("id") int id);

    @DELETE("user/softDelete.php")
    Call<ApiResponse> deletePT(@Query("id") int id);


    @PUT("user/editUser.php")
    Call<ApiResponse> Userupdatecall(
            @Query("id") int id,       // ID truyền qua query string
            @Body Userupdate body   // Dữ liệu JSON truyền qua body
    );

    @POST("user/forgetPassword.php")
    Call<ApiResponse> sendEmail(@Body Email emailData);

    @POST("user/verifyOtp.php")
    Call<ApiResponse> sendOtp(@Body OTP otpData);


    @PUT("user/changePassword.php")
    Call<ApiResponse> Passwordupdatecall(
            @Query("email") String email,       // ID truyền qua query string
            @Body Password body   // Dữ liệu JSON truyền qua body
    );


    @GET("membership/getOneMembership.php")
    Call<List<Membership>> getMembershipsByUserId(@Query("userId") int userId);

    @GET("user/getHistory.php")
    Call<List<PaymentHistory>> getHistory(@Query("id") int id);

    @GET("user/getpt.php")
    Call<ApiListRequestResponse> getRequest(@Query("user_id") int id);



    @PUT("user/confirm.php")
    Call<ApiResponse> updateTransaction(@Query("id") int id);

    @POST("ptRequest/createPtRequest.php")
    Call<ApiResponse> sendrequest(@Body SentRequest sentRequest);

    @POST("ptRequest/getAvaiableTimePt.php") // Thay đường dẫn API thực tế
    Call<ApiGetTImeAvailable> getOptions(@Body GetTime getTime);

    @GET("ptRequest/getSlotForPt.php")
    Call<ApiGetSlotforPT> getLichday(
            @Query("pt_id") int pt_id,
            @Query("status") String status
    );


    @GET("ptRequest/getSlotForPt.php")
    Call<ApiGetSlotforPT> getRequest(
            @Query("pt_id") int pt_id,
            @Query("status") String status
    );




    @PUT("ptRequest/changeStatusPtRequest.php")
    Call<ApiResponse> changestatusrequest(@Body Response response);

    @GET("ptRequest/getSchedule.php")
    Call<ApiGetSchedule> getSchedule(@Query("user_id") int id);

    @GET("user/getptforspinner.php")
    Call<List<PtSpinner>> getPtSpinner();

    @POST("admin/getptstatistical.php")
    Call<ApiGetstatistical> getPtStatistical(@Body PTstatistical ptstatistical);

    @POST("admin/getdaystatistical.php")
    Call<ApiGetstatistical> getDayStatistical(@Body Daystatistical daystatistical);

    @POST("admin/getallstatistical.php")
    Call<ApiGetstatistical> getAllStatistical(@Body Allstatistical allstatistical);

    @GET("ptRequest/getHocvien.php")
    Call<ApiGetHocvien> getHocvien(@Query("pt_id") int id);

    @PUT("ptRequest/changeStatusPtRequest.php")
    Call<ApiResponse> cancelSchedule(@Body Response response);





}
