package com.samwelopiyo.financialtrack.financialtrack;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by root on 5/5/16.
 */
public class settings extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);
    }
    public void onBackPressedSettings(){
        super.onBackPressed();
    }
}
