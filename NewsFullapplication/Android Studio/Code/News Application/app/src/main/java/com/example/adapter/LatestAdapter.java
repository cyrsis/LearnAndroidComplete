package com.example.adapter;

import java.util.List;

import com.viaviapp.newsapplication.R;
import com.example.imageloader.ImageLoader;
import com.example.item.ItemLatest;
import com.example.util.Constant;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class LatestAdapter extends ArrayAdapter<ItemLatest>{
	
	
	private Activity activity;
	private List<ItemLatest> itemsLatest;
	private ItemLatest objLatestBean;
	private int row;
	public ImageLoader imageLoader; 
	 
	 public LatestAdapter(Activity act, int resource, List<ItemLatest> arrayList, int columnWidth) {
			super(act, resource, arrayList);
			this.activity = act;
			this.row = resource;
			this.itemsLatest = arrayList;
			imageLoader=new ImageLoader(activity);
			 
			 
		}
	 
	 @Override
		public View getView(final int position, View convertView, ViewGroup parent) {
			View view = convertView;
			ViewHolder holder;
			if (view == null) {
				LayoutInflater inflater = (LayoutInflater) activity
						.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				view = inflater.inflate(row, null);

				holder = new ViewHolder();
				view.setTag(holder);
			} else {
				holder = (ViewHolder) view.getTag();
			}

			if ((itemsLatest == null) || ((position + 1) > itemsLatest.size()))
				return view;

			objLatestBean = itemsLatest.get(position);

			holder.txt_newsheadinglatest=(TextView)view.findViewById(R.id.txt_newslistheadinglatest);
			holder.img_newslatest=(ImageView)view.findViewById(R.id.img_newslistlatest);
			holder.txt_newsheadinglatest.setText(objLatestBean.getNewsHeading().toString());

			imageLoader.DisplayImage(Constant.SERVER_IMAGE_NEWSLIST_THUMBS+objLatestBean.getNewsImage().toString(), holder.img_newslatest);
			
			return view;
			 
		}

		public class ViewHolder {
		 
			public ImageView img_newslatest;
			public  TextView txt_newsheadinglatest;
			 
		}
}
