package com.baidao.androidlibrary;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.baidao.tools.Util;

/**
 * Created by hexi on 16/1/22.
 */
public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void callJD(View view) {
        Util.makeCompanyCall(this);
    }
}
