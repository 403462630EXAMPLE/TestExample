package com.baidao.quotemodel;

import android.os.Parcel;
import android.os.Parcelable;

import com.baidao.data.Jsonable;
import com.baidao.tools.BigDecimalUtil;
import com.google.gson.Gson;
import com.hannesdorfmann.parcelableplease.annotation.ParcelablePlease;

/**
 * Created by hexi on 14/12/8.
 */
@ParcelablePlease
public class Quote implements Parcelable, Jsonable
{
    public String id;
    public String quoteName;
    public String market;
    public double now;
    public double preClose;
    public double open;
    public double high;
    public double low;
    public double buy;
    public double sell;
    public int decimalDigits;
    public double average;
    public double totalTradeVolume;//上海金only
    public double tradeVolume;
    public String reservedString;
    public long dateTime;

    public Category category;
    /**
     * 委差
     */
    public double committeeDiffer;
    /**
     * 委比
     */
    public String committeeRatio;

    public FiveSales fiveSales;


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        QuoteParcelablePlease.writeToParcel(this, dest, flags);
    }

    public static final Creator<Quote> CREATOR = new Creator<Quote>() {
        public Quote createFromParcel(Parcel source) {
            Quote target = new Quote();
            QuoteParcelablePlease.readFromParcel(target, source);
            return target;
        }

        public Quote[] newArray(int size) {
            return new Quote[size];
        }
    };

    public static Quote build(Category category) {
        Quote quote = new Quote();
        quote.quoteName = category.name;
        quote.id = category.nickName;
        quote.market = category.market;
        quote.updatePreClose(category);
        quote.decimalDigits = category.decimalDigits;
        quote.reservedString = category.reserveString_1;
        quote.category = category;
        return quote;
    }


    public void update(Category category) {
        updatePreClose(category);
        this.category = category;
    }

    private void updatePreClose(Category category){
        this.preClose = category.getPreClose();
    }

    public void update(Snapshot snapshot) {
        if (snapshot.latestPx > 0) {
            now = snapshot.latestPx;
        }
        if (snapshot.openPx > 0) {
            open = snapshot.openPx;
        }
        buy = snapshot.bidPx1;
        if (snapshot.highPx > 0) {
            high = snapshot.highPx;
        }
        if (snapshot.lowPx > 0) {
            low = snapshot.lowPx;
        }
        sell = snapshot.askPx1;
        if (snapshot.avgPx > 0) {
            this.average = snapshot.avgPx;
        }
        this.dateTime = snapshot.dateTime * 1000;

        setSGEProperties(snapshot);
    }

    private void setSGEProperties(Snapshot snapshot) {
        if (snapshot instanceof SHGSnapshot) {
            final SHGSnapshot shgSnapshot = (SHGSnapshot) snapshot;
            if (shgSnapshot.totalTradeVolume > 0) {
                this.totalTradeVolume = shgSnapshot.totalTradeVolume;
            }
            this.tradeVolume = shgSnapshot.tradeVolume;

            if (fiveSales == null) {
                fiveSales = new FiveSales();
            }
            if (shgSnapshot.bidPx1 > 0) {
                fiveSales.buy.get(0).value = shgSnapshot.bidPx1;
            }
            if (shgSnapshot.bidVolume1 > 0) {
                fiveSales.buy.get(0).volume = (int)shgSnapshot.bidVolume1;
            }
            if (shgSnapshot.bidPx2 > 0) {
                fiveSales.buy.get(1).value = shgSnapshot.bidPx2;
            }
            if (shgSnapshot.bidVolume2 > 0) {
                fiveSales.buy.get(1).volume = (int)shgSnapshot.bidVolume2;
            }
            if (shgSnapshot.bidPx3 > 0) {
                fiveSales.buy.get(2).value = shgSnapshot.bidPx3;
            }
            if (shgSnapshot.bidVolume3 > 0) {
                fiveSales.buy.get(2).volume = (int)shgSnapshot.bidVolume3;
            }
            if (shgSnapshot.bidPx4 > 0) {
                fiveSales.buy.get(3).value = shgSnapshot.bidPx4;
            }
            if (shgSnapshot.bidVolume4 > 0) {
                fiveSales.buy.get(3).volume = (int)shgSnapshot.bidVolume4;
            }
            if (shgSnapshot.bidPx5 > 0) {
                fiveSales.buy.get(4).value = shgSnapshot.bidPx5;
            }
            if (shgSnapshot.bidVolume5 > 0) {
                fiveSales.buy.get(4).volume = (int)shgSnapshot.bidVolume5;
            }

            if (shgSnapshot.askPx1 > 0) {
                fiveSales.sell.get(0).value = shgSnapshot.askPx1;
            }
            if (shgSnapshot.askVolume1 > 0) {
                fiveSales.sell.get(0).volume = (int)shgSnapshot.askVolume1;
            }
            if (shgSnapshot.askPx2 > 0) {
                fiveSales.sell.get(1).value = shgSnapshot.askPx2;
            }
            if (shgSnapshot.askVolume2 > 0) {
                fiveSales.sell.get(1).volume = (int)shgSnapshot.askVolume2;
            }
            if (shgSnapshot.askPx3 > 0) {
                fiveSales.sell.get(2).value = shgSnapshot.askPx3;
            }
            if (shgSnapshot.askVolume3 > 0) {
                fiveSales.sell.get(2).volume = (int)shgSnapshot.askVolume3;
            }
            if (shgSnapshot.askPx4 > 0) {
                fiveSales.sell.get(3).value = shgSnapshot.askPx4;
            }
            if (shgSnapshot.askVolume4 > 0) {
                fiveSales.sell.get(3).volume = (int)shgSnapshot.askVolume4;
            }
            if (shgSnapshot.askPx5 > 0) {
                fiveSales.sell.get(4).value = shgSnapshot.askPx5;
            }
            if (shgSnapshot.askVolume5 > 0) {
                fiveSales.sell.get(4).volume = (int)shgSnapshot.askVolume5;
            }

            double sumBuy = BigDecimalUtil.add(shgSnapshot.bidVolume1, shgSnapshot.bidVolume2, shgSnapshot.bidVolume3, shgSnapshot.bidVolume4, shgSnapshot.bidVolume5);
            double sumSell = BigDecimalUtil.add(shgSnapshot.askVolume1, shgSnapshot.askVolume2, shgSnapshot.askVolume3, shgSnapshot.askVolume4, shgSnapshot.askVolume5);
            if (sumBuy == 0 || sumSell == 0) {
                return;
            }
            this.committeeDiffer = BigDecimalUtil.sub(sumBuy, sumSell);
            double committeeRatio = BigDecimalUtil.div(BigDecimalUtil.mul(this.committeeDiffer, 100D), BigDecimalUtil.add(sumBuy, sumSell), 2);
            if (committeeRatio > 0) {
                this.committeeRatio = "+" + committeeRatio + "%";
            } else {
                this.committeeRatio = committeeRatio + "%";
            }
        }
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }

    public String getSid() {
        return this.market + "." + this.id;
    }

    @Override
    public String toJson() {
        return new Gson().toJson(this);
    }

    public boolean isUp() {
        return (now - preClose) >= 0;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

}
