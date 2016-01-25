package com.ytx.library.provider;

import android.util.Log;

import com.google.gson.Gson;
import com.squareup.okhttp.OkHttpClient;
import com.ytx.library.provider.converter.MyGsonConverter;
import com.ytx.library.provider.serverConfig.ServerDomainType;

import java.util.concurrent.TimeUnit;

import retrofit.RestAdapter;
import retrofit.client.OkClient;

/**
 * Created by Bruce on 12/1/14.
 */
public class ApiFactory {
    private static JryApi jryApi;
    private static WwwApi wwwApi;
    private static QuotesApi quotesApi;
    private static MobileServiceApi mobileServiceApi;
    private static AudioApi audioApi;
    private static StatisticsApi statisticsApi;
    private static QuotationLoginApi quotationLoginApi;
    private static UserPermissionApi userPermissionApi;
    private static CrmApi crmApi;
    private static OpenApi openApi;
    private static DomainApi domainApi;
    private static TradePlanApi tradePlanApi;
    private static UserCenterApi userCenterApi;

    public static void init(TokenGetter tokenGetter) {
       RetrofitFactory.setTokenGetter(tokenGetter);
    }

    public static void setTokenGetter() {}

    public synchronized static TradePlanApi getTradePlanApi() {
        if (tradePlanApi == null) {
            tradePlanApi = RetrofitFactory.getRestAdapter(ServerDomainType.TRADE_PLAN).create(TradePlanApi.class);
        }
        return tradePlanApi;
    }

    public synchronized static DomainApi getDomainApi() {
        if (domainApi == null) {
            domainApi = RetrofitFactory.getRestAdapter(ServerDomainType.AGENT).create(DomainApi.class);
        }
        return domainApi;
    }

    public synchronized static JryApi getJryApi() {
        if (jryApi == null) {
            Log.d("ApiFactory", "-----------getJryApi");
            jryApi = RetrofitFactory.getRestAdapter(ServerDomainType.JRY).create(JryApi.class);
        }
        return jryApi;
    }

    public synchronized static UserCenterApi getUserCenterApi() {
        if (userCenterApi == null) {
            Log.d("ApiFactory", "-----------getUserCenterApi");
            userCenterApi = RetrofitFactory.getRestAdapter(ServerDomainType.USER_CENTER).create(UserCenterApi.class);
        }
        return userCenterApi;
    }

    public synchronized static QuotationLoginApi getQuotationLoginApi(){
        if(quotationLoginApi == null){
            quotationLoginApi = RetrofitFactory.getRestAdapter(ServerDomainType.LOGINSERVER).create(QuotationLoginApi.class);
        }
        return quotationLoginApi;
    }

    public synchronized static WwwApi getWwwApi(){
        if(wwwApi == null){
            wwwApi = RetrofitFactory.getRestAdapter(ServerDomainType.WWW).create(WwwApi.class);
        }
        return wwwApi;
    }

    public synchronized static QuotesApi getQuotesApi(){
        if(quotesApi == null){
            quotesApi = RetrofitFactory.getRestAdapter(ServerDomainType.QUOTES).create(QuotesApi.class);
        }
        return quotesApi;
    }

    public synchronized static MobileServiceApi getMobileServiceApi(){
        if(mobileServiceApi == null){
            mobileServiceApi = RetrofitFactory.getRestAdapter(ServerDomainType.MOBILESERVICE).create(MobileServiceApi.class);
        }
        return mobileServiceApi;
    }

    public synchronized static AudioApi getAudioApi(){
        if(audioApi == null){
            audioApi = RetrofitFactory.getRestAdapter(ServerDomainType.AUDIO).create(AudioApi.class);
        }
        return audioApi;
    }

    public synchronized  static StatisticsApi getStatisticsApi() {
        if (statisticsApi == null) {
            RestAdapter.Builder builder = new RestAdapter.Builder()
                    .setEndpoint(ServerDomain.get(ServerDomainType.STATISTICS));
            builder.setLogLevel(RestAdapter.LogLevel.FULL);
            builder.setConverter(new MyGsonConverter(new Gson()));

            OkHttpClient okHttpClient = new OkHttpClient();
            okHttpClient.setConnectTimeout(60, TimeUnit.SECONDS);
            okHttpClient.setReadTimeout(20, TimeUnit.SECONDS);
            OkClient okClient = new OkClient(okHttpClient);
            builder.setClient(okClient);

            statisticsApi = builder.build().create(StatisticsApi.class);
        }
        return statisticsApi;
    }

    public synchronized static UserPermissionApi getUserPermissionApi() {
        if (userPermissionApi == null) {
            userPermissionApi = RetrofitFactory.getRestAdapter(ServerDomainType.USER_PERMISSION).create(UserPermissionApi.class);
        }
        return userPermissionApi;
    }

    public synchronized static CrmApi getCrmApi() {
        if (crmApi == null) {
            crmApi = RetrofitFactory.getRestAdapter(ServerDomainType.CRM).create(CrmApi.class);
        }
        return crmApi;
    }

    public synchronized static OpenApi getOpenApi() {
        if (openApi == null) {
            openApi = RetrofitFactory.getRestAdapter(ServerDomainType.QUERY_USER).create(OpenApi.class);
        }
        return openApi;
    }

    public static void clear() {
        jryApi = null;
        wwwApi = null;
        quotesApi = null;
        quotationLoginApi = null;
        mobileServiceApi = null;
        audioApi = null;
        statisticsApi = null;
        userPermissionApi = null;
        crmApi = null;
        openApi = null;
        tradePlanApi = null;
        userCenterApi = null;
    }


    public static ReportStartApi getReportStartApi() {
        RestAdapter.Builder builder = new RestAdapter.Builder()
                .setEndpoint("http://tt.device.baidao.com");
        builder.setLogLevel(RestAdapter.LogLevel.FULL);

        OkHttpClient okHttpClient = new OkHttpClient();
        okHttpClient.setConnectTimeout(60, TimeUnit.SECONDS);
        okHttpClient.setReadTimeout(20, TimeUnit.SECONDS);
        OkClient okClient = new OkClient(okHttpClient);
        builder.setClient(okClient);

        RestAdapter restAdapter = builder.build();
        return restAdapter.create(ReportStartApi.class);
    }
}
