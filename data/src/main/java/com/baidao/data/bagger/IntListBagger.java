package com.baidao.data.bagger;

import android.os.Parcel;

import com.hannesdorfmann.parcelableplease.ParcelBagger;

import java.util.List;

/**
 * Created by hexi on 16/1/6.
 */
public class IntListBagger implements ParcelBagger<List<Integer>> {

    @Override
    public void write(List<Integer> value, Parcel out, int flags) {
        out.writeList(value);
    }

    @Override
    public List<Integer> read(Parcel in) {
        return in.readArrayList(Integer.class.getClassLoader());
    }
}
