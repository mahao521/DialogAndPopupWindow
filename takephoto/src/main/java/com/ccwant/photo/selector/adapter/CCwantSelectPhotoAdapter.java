/*******************************************************************************
 * Copyright 2015 CCwant
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
package com.ccwant.photo.selector.adapter;


import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.Toast;

import com.ccwant.album.R;
import com.ccwant.photo.selector.bean.CCwantPhoto;
import com.ccwant.photo.selector.load.CCwantImageLoader;

import java.util.List;

public class CCwantSelectPhotoAdapter extends BaseAdapter{

	private SelectCallback mSelectCallback;
	private Context mContext;
	private LayoutInflater inflater;
	List<CCwantPhoto> mData;

	PhotoCountListener mPhotoCountListener;

	public void setPhotoCountListener(PhotoCountListener photoCountListener){

		this.mPhotoCountListener = photoCountListener;
	}
	int countPhoto = 0;


	public CCwantSelectPhotoAdapter(Context context, List<CCwantPhoto> list) {
		mContext = context;
		inflater = LayoutInflater.from(context);
		mData = list;
	}
	public void setSelectCallback(SelectCallback callback) {
		mSelectCallback = callback;
	}

	@Override
	public int getCount() {
		return mData.size();
	}

	@Override
	public Object getItem(int position) {
		return position;
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final int coord = position;
		final ViewHolder holder;
		if (convertView == null) {

			convertView = inflater.inflate(R.layout.ccwant_item_select_photo, parent,
					false);
			holder = new ViewHolder();
			holder.imgPhoto = (ImageView) convertView.findViewById(R.id.ccwant_img_photo_select);
			holder.imgSelect = (ImageView) convertView.findViewById(R.id.ccwant_img_is_selected_select);
		
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		final CCwantPhoto photo=mData.get(position);
		final String path=photo.photoPath;

		CCwantImageLoader.getIntance(mContext).ImageLoader(path, holder.imgPhoto);

		holder.imgPhoto.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {

				photo.isSelected = !photo.isSelected;

				if (photo.isSelected) {// 如果相册被选择

					if(countPhoto <= mPhotoCountListener.getPhotoCount() && mPhotoCountListener.getPhotoCount() != 0){

						holder.imgSelect.setImageResource(R.drawable.ccwant_select_flag);
						holder.imgPhoto.setAlpha(100);

						if (mSelectCallback != null) {
							mSelectCallback.onSelectListen(photo.photoPath, false);
							countPhoto++;
						}

					}else{

						Toast.makeText(mContext,"照片不能超过"+mPhotoCountListener.getPhotoCount()+"张",Toast.LENGTH_SHORT).show();
					}

				} else {

					if (mSelectCallback != null) {
						mSelectCallback.onSelectListen(photo.photoPath, true);
					}

					holder.imgSelect.setImageDrawable(new ColorDrawable(0x00000000));
					holder.imgPhoto.setAlpha(255);
					countPhoto--;
					if(countPhoto < 0){
						countPhoto = 0;
					}
				}
			}
		});
		if (photo.isSelected) {//如果相册被选择
			holder.imgSelect.setImageResource(R.drawable.ccwant_select_flag);
			holder.imgPhoto.setAlpha(100);
		}else{
			holder.imgSelect.setImageDrawable(new ColorDrawable(0x00000000));
			holder.imgPhoto.setAlpha(255);
		}
		return convertView;
	}
	class ViewHolder {
		private ImageView imgPhoto;
		private ImageView imgSelect;
		
	}
	public static interface SelectCallback {
		public void onSelectListen(String path, boolean isCancel);
	}
	
    public interface PhotoCountListener{

		public int getPhotoCount();
	}
	
}
