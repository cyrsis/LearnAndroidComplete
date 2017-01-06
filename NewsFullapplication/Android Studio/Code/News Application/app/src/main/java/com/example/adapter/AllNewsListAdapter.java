package com.example.adapter;

import java.util.List;

import com.viaviapp.newsapplication.R;
import com.example.imageloader.ImageLoader;
import com.example.item.ItemAllNews;
import com.example.util.Constant;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class AllNewsListAdapter extends ArrayAdapter<ItemAllNews> {
	
	private Activity activity;
	private List<ItemAllNews> itemsAllnews;
	private ItemAllNews objAllBean;
	private int row;
	public ImageLoader imageLoader; 
	 
	
	public AllNewsListAdapter(Activity act, int resource, List<ItemAllNews> arrayList) {
		super(act, resource, arrayList);
		this.activity = act;
		this.row = resource;
		this.itemsAllnews = arrayList;
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

		if ((itemsAllnews == null) || ((position + 1) > itemsAllnews.size()))
			return view;

		objAllBean = itemsAllnews.get(position);
		
		holder.txt=(TextView)view.findViewById(R.id.txt_allnews_categty);
		holder.img_cat=(ImageView)view.findViewById(R.id.img_cat);
		holder.txt.setText(objAllBean.getCategoryName().toString());

		imageLoader.DisplayImage(Constant.SERVER_IMAGE_CATEGORY+objAllBean.getCategoryImageurl().toString(), holder.img_cat);
		
		return view;
		
	}

	public class ViewHolder {
	 
		public TextView txt;
		public ImageView img_cat;
	}

}
