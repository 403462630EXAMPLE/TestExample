package com.ytx.library.provider;

import com.baidao.data.Bid;
import com.baidao.data.TradePlan;
import com.baidao.data.TradePlanResult;

import retrofit.http.GET;
import rx.Observable;

/**
 * Created by rjhy on 16-1-5.
 */
public interface TradePlanApi {
    @GET("/api/gjs/plans")
    Observable<TradePlanResult.ListResult<TradePlan>> getTradePlanList();

    @GET("/api/gjs/dynamicList")
    Observable<TradePlanResult.List2Result<String>> getDynamicList();

    @GET("/api/gjs/lastBid?token=rO0ABXQADMO/XMKuw6jDgRLCgQ==")
    Observable<TradePlanResult.Result<Bid>> getLastBid();
}
