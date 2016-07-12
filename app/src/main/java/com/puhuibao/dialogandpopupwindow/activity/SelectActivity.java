package com.puhuibao.dialogandpopupwindow.activity;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.puhuibao.dialogandpopupwindow.R;
import com.puhuibao.dialogandpopupwindow.databinding.ActivitySelectBinding;

public class SelectActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivitySelectBinding selectBinding = DataBindingUtil.setContentView(SelectActivity.this, R.layout.activity_select);
        selectBinding.setClick(this);

    }

    @Override
    public void onClick(View v) {

        int id = v.getId();
        switch(id){

            case R.id.picker_day:
                break;
            case R.id.picker_month:
                break;
            case R.id.picker_year:
                break;
            case R.id.picker_half_day:
                break;
        }
    }
}
