package com.ytx.library.provider;

import com.baidao.data.CheckUserResult;
import com.baidao.data.LoginResult;
import com.baidao.data.Register;
import com.baidao.data.Result;
import com.baidao.data.Retrieve;
import com.baidao.data.SSOLogin;
import com.baidao.data.User;
import com.squareup.okhttp.RequestBody;

import retrofit.http.Body;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.Multipart;
import retrofit.http.POST;
import retrofit.http.Part;
import retrofit.http.Query;
import rx.Observable;

/**
 * Created by hexi on 16/1/12.
 */
public interface UserCenterApi {

    @FormUrlEncoded
    @POST("/jry-device/dx/login")
    public Observable<Result<LoginResult>> login(@Field("username") String username,
                                                 @Field("password") String password,
                                                 @Field("appVersion") String appVersion);

    @POST("/jry-device/dx/ajax/register")
    public Observable<Result<LoginResult>> register(@Body Register register);


    @GET("/jry-device/dx/ajax/user/updateUserDetail.do")
    public Observable<Result> updateUserDetail(@Query("nickname") String nickname, @Query("token") String token);


    @Multipart
    @POST("/jry-device/dx/ajax/user/uploadUserImage")
    public Observable<Result<User>> uploadAvatar(@Query("project") String project,
                                                 @Part("attachment") RequestBody file);


    @POST("/jry-device/dx/ajax/user/resetPassword")
    public Observable<Result<LoginResult>> resetPassword(@Body Retrieve retrieve);


    /**
     * 获取(注册)验证码
     * @param phoneNumber
     * @return
     */
    @FormUrlEncoded
    @POST("/jry-device/dx/ajax/verificationCode")
    public Observable<Result> getRegisterVerifyCode(@Field("phoneNumber") String phoneNumber);


    /**
     * 获取(修改密码)验证码
     * @param phoneNumber
     * @param serverId
     * @return
     */
    @FormUrlEncoded
    @POST("/jry-device/dx/ajax/user/verificationCode")
    public Observable<Result> getRetrieveVerifyCode(@Field("phoneNumber") String phoneNumber, @Field("serverId") int serverId);

    @GET("/jry-device/dx/ajax/user/getUserByToken")
    public Observable<Result<LoginResult>> getUserByToken(@Query("token") String token);


    @FormUrlEncoded
    @POST("/jry-device/dx/ajax/user/bindPhonePassword")
    public Observable<LoginResult> bindPhone(@Field("token") String token,
                                             @Field("phoneNumber") String phoneNumber,
                                             @Field("verificationCode") String verificationCode,
                                             @Field("password") String password);


    /**
     * 第三方注册
     * @param ssoLogin
     * @return
     */
    @POST("/jry-device/dx/authentications/open")
    public Observable<LoginResult> ssoLogin(@Body SSOLogin ssoLogin);

    /**
     * 游客注册
     * @return
     */
    @POST("/jry-device/dx/ajax/guest/register")
    public Observable<LoginResult> guestLogin();

    @GET("/jry-device/dx/ajax/needLogout")
    public Observable<CheckUserResult> needLogout(@Query("username") String username);
}
