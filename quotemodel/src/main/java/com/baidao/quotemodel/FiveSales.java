package com.baidao.quotemodel;

import android.os.Parcel;
import android.os.Parcelable;

import com.baidao.data.Jsonable;
import com.google.gson.Gson;
import com.hannesdorfmann.parcelableplease.annotation.ParcelablePlease;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hexi on 15/12/15.
 */
@ParcelablePlease
public class FiveSales implements Parcelable, Jsonable {
    @Override
    public String toJson() {
        return new Gson().toJson(this);
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        FiveSalesParcelablePlease.writeToParcel(this, dest, flags);
    }

    public static final Creator<FiveSales> CREATOR = new Creator<FiveSales>() {
        public FiveSales createFromParcel(Parcel source) {
            FiveSales target = new FiveSales();
            FiveSalesParcelablePlease.readFromParcel(target, source);
            return target;
        }

        public FiveSales[] newArray(int size) {
            return new FiveSales[size];
        }
    };

    @ParcelablePlease
    public static class Order implements Parcelable, Jsonable {
        public double value;
        public int volume;

        @Override
        public String toJson() {
            return new Gson().toJson(this);
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            com.baidao.quotemodel.OrderParcelablePlease.writeToParcel(this, dest, flags);
        }

        public static final Creator<Order> CREATOR = new Creator<Order>() {
            public Order createFromParcel(Parcel source) {
                Order target = new Order();
                com.baidao.quotemodel.OrderParcelablePlease.readFromParcel(target, source);
                return target;
            }

            public Order[] newArray(int size) {
                return new Order[size];
            }
        };

    }

    public List<Order> buy = new ArrayList<>();
    public List<Order> sell = new ArrayList<>();

    public FiveSales() {
        for (int i = 0; i < 5; i++) {
            buy.add(new Order());
        }

        for (int i = 0; i < 5; i++) {
            sell.add(new Order());
        }
    }
}
