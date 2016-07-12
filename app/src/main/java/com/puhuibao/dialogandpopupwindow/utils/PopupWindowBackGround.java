package com.puhuibao.dialogandpopupwindow.utils;

import android.app.Activity;
import android.view.WindowManager;

/**
 * Created by mahao on 16/7/5.
 */
public class PopupWindowBackGround {


    public static void setBackGroundAlpha(Activity activity,float alpha){

        WindowManager.LayoutParams layoutParams = activity.getWindow().getAttributes();
        layoutParams.alpha = alpha;
        activity.getWindow().setAttributes(layoutParams);

    }
}
