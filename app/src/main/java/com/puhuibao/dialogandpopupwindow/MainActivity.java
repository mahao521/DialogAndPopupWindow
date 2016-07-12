package com.puhuibao.dialogandpopupwindow;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.puhuibao.dialogandpopupwindow.activity.SelectActivity;
import com.puhuibao.dialogandpopupwindow.activity.TakePhotoActivity;
import com.puhuibao.dialogandpopupwindow.utils.PickerVerticalView;
import com.puhuibao.dialogandpopupwindow.utils.PopupWindowBackGround;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private TextView etPersonNum;
    private String personNumber = "1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        etPersonNum = (TextView) findViewById(R.id.txt_popupwindow);


    }


    public void loopNumber(View view) {

        showPopupWindow();
    }


    public void dateSelector(View view) {

        showTest();
    }

    // 时间选择器
    public void showTest(){

       Intent intent = new Intent(MainActivity.this, SelectActivity.class);
       startActivity(intent);
    }

    //照相
    public void TakePhoto(View view) {

        Intent intent = new Intent(MainActivity.this, TakePhotoActivity.class);
        startActivity(intent);

    }

    //轮询的Number;
    public void showPopupWindow(){


        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(etPersonNum.getWindowToken(), 0);
        View personNumPop = View.inflate(this, R.layout.layout_select_person_num, null);
        final PopupWindow popupWindow = new PopupWindow(personNumPop, ViewGroup.LayoutParams.FILL_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.transparent)));
        popupWindow.setAnimationStyle(R.style.popupWindowAnim);
        PopupWindowBackGround.setBackGroundAlpha(MainActivity.this, 0.7f);
        popupWindow.update();
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(false);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                PopupWindowBackGround.setBackGroundAlpha(MainActivity.this,1.0f);
            }
        });
        TextView tv_confirm = (TextView) personNumPop.findViewById(R.id.tv_confirm);
        tv_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
                etPersonNum.setText(personNumber + "人");
            }
        });

        PickerVerticalView  pickerVerticalView = (PickerVerticalView) personNumPop.findViewById(R.id.pickervertical_view_one);
        List<String> data1 = new ArrayList<String>();
        for (int i = 1; i <= 10; i++) {
            data1.add("" + i);
        }
        pickerVerticalView.setData(data1);
        pickerVerticalView.setOnSelectListener(new onSelectListener() {

            @Override
            public void onSelect(String text) {
                personNumber = text;
            }
        });
        pickerVerticalView.setSelected(0);



        PickerVerticalView pickerVerticalViewTwo = (PickerVerticalView) personNumPop.findViewById(R.id.pickervertical_view_two);
        List<String> data2 = new ArrayList<>();
        data2.add("房子");
        data2.add("美女");
        data2.add("跑车");
        data2.add("妹子");
        data2.add("家人");
        data2.add("旅游");
        data2.add("时尚");
        data2.add("公司");
        data2.add("财富");
        data2.add("魅力");
        data2.add("国家");
        pickerVerticalViewTwo.setData(data2);
        pickerVerticalViewTwo.setOnSelectListener(new onSelectListener() {
            @Override
            public void onSelect(String text) {
                personNumber = text;
            }
        });
        pickerVerticalView.setSelected(0);
        popupWindow.showAsDropDown(etPersonNum);
    }
}
