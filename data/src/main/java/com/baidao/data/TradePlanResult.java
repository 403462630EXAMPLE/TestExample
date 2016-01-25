package com.baidao.data;

import java.util.List;

/**
 * Created by rjhy on 16-1-5.
 */
public class TradePlanResult<T> {

    public int code;

    public boolean isSuccess() {
        return code == 1;
    }

    public static class ListResult<T> extends TradePlanResult<T> {
        public List<T> plans;
        public List<T> myPlans;
        public long now;
    }

    public static class List2Result<T> extends TradePlanResult<T> {
        public List<T> data;
    }

    public static class Result<T> extends TradePlanResult<T> {
        public T data;
    }
}
