package com.puhuibao.dialogandpopupwindow.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.ccwant.photo.selector.activity.CCwantPhotoBrowserActivity;
import com.ccwant.photo.selector.activity.CCwantSelectAlbumActivity;
import com.ccwant.photo.selector.adapter.CCwantPublishAdapter;
import com.puhuibao.dialogandpopupwindow.MainActivity;
import com.puhuibao.dialogandpopupwindow.R;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class TakePhotoActivity extends AppCompatActivity {


    //设置照片不能超过6张
    private  final int PHOTO_COUNT = 6;
    /**
     * 打开相册选择activity
     */
    private final int OPEN_SELECT_ALBUM=1;
    /**
     * 打开图片浏览器activity
     */
    private final int OPEN_PHOTO_BROWSER=2;
    /**
     * 你选择的所有图片路径
     */
    private List<String> mData=new ArrayList<String>();

    private GridView mGrvContent;
    private CCwantPublishAdapter mAdapter;

    private TextView mTxtSend;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_photo);


        mAdapter=new CCwantPublishAdapter(this,mData);
        mGrvContent=(GridView)findViewById(R.id.grv_content);
        mGrvContent.setAdapter(mAdapter);

        mGrvContent.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                //如果点击了+则打开相册选择器，否则打开相片浏览界面
                if(position==mAdapter.getMaxPosition()-1){

                        if(mData != null && mData.size() < 5){

                            Intent intent=new Intent(TakePhotoActivity.this,CCwantSelectAlbumActivity.class);
                            intent.putExtra("photoCount",PHOTO_COUNT-mAdapter.getCount());
                            startActivityForResult(intent, OPEN_SELECT_ALBUM);

                        }

                }else{
                    Intent intent=new Intent(TakePhotoActivity.this,CCwantPhotoBrowserActivity.class);
                    intent.putExtra("CCwantPhotoList",(Serializable) mData);
                    intent.putExtra("CCwantPhotoPosition",(Serializable)position);
                    startActivityForResult(intent, OPEN_PHOTO_BROWSER);
                }


            }
        });
        mTxtSend=(TextView)findViewById(R.id.txt_send);
        mTxtSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StringBuilder sb=new StringBuilder();
                for(int i=0;i<mData.size();i++){
                    sb.append(mData.get(i)+"\n");
                }
                Toast.makeText(getApplicationContext(), ""+sb.toString(),Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //处理选择图片后的返回值
        if(requestCode==OPEN_SELECT_ALBUM){
            if (resultCode == RESULT_OK) {
                Bundle bundle = data.getExtras();
                List<String> list=new ArrayList<String>();
                list.addAll(mData);
                list.addAll(bundle.getStringArrayList("result"));
                mData.clear();
                mData.addAll(list);
                mAdapter.notifyDataSetChanged();


            }
        }
        //处理图片浏览的返回值
        if(requestCode==OPEN_PHOTO_BROWSER){
            if (resultCode == RESULT_OK) {
                Bundle bundle = data.getExtras();
                List<String> list=bundle.getStringArrayList("result");
                mData.clear();
                mData.addAll(list);
                mAdapter.notifyDataSetChanged();


            }
        }
    }

}
