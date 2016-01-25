package com.baidao.data.bagger;

import android.os.Parcel;

import com.hannesdorfmann.parcelableplease.ParcelBagger;

import java.util.List;

/**
 * Created by hexi on 16/1/6.
 */
public class StringListBagger implements ParcelBagger<List<String>> {

    @Override
    public void write(List<String> value, Parcel out, int flags) {
        out.writeStringList(value);
    }

    @Override
    public List<String> read(Parcel in) {
        return in.readArrayList(String.class.getClassLoader());
    }
}
