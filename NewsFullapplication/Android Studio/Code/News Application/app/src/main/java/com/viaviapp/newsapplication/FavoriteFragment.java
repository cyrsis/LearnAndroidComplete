package com.viaviapp.newsapplication;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.example.adapter.FavoriteAdapter;
import com.example.favorite.DatabaseHandler;
import com.example.favorite.DatabaseHandler.DatabaseManager;
import com.example.favorite.Pojo;
import com.example.util.JsonUtils;

public class FavoriteFragment extends Fragment {
	
 
	ListView list_fav;
	DatabaseHandler db;
	private DatabaseManager dbManager;
	FavoriteAdapter adapter;
	TextView txt_no;
 	JsonUtils util;
	List<Pojo> allData;
	ArrayList<String> allListnews,allListnewsCatName;
	ArrayList<String> allListNewsCId,allListNewsCatId,allListNewsCatImage,allListNewsCatName,allListNewsHeading,allListNewsImage,allListNewsDes,allListNewsDate;
	String[] allArraynews,allArraynewsCatName;
	String[] allArrayNewsCId,allArrayNewsCatId,allArrayNewsCatImage,allArrayNewsCatName,allArrayNewsHeading,allArrayNewsImage,allArrayNewsDes,allArrayNewsDate;
	 int textlength = 0;
	 Pojo pojo;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		
		View rootView = inflater.inflate(R.layout.fragment_favorite, container, false);
		setHasOptionsMenu(true);
		list_fav=(ListView)rootView.findViewById(R.id.lsv_favorite);
		txt_no=(TextView)rootView.findViewById(R.id.textView1);
		db=new DatabaseHandler(getActivity());
		dbManager = DatabaseManager.INSTANCE;
		dbManager.init(getActivity());
		util=new JsonUtils(getActivity());
	
		allData=db.getAllData();
		adapter=new FavoriteAdapter(this.getActivity(), R.layout.favorite_lsv_item, allData);
		list_fav.setAdapter(adapter);
		if(allData.size()==0)
		{
			txt_no.setVisibility(View.VISIBLE);
		}
		else
		{
			txt_no.setVisibility(View.INVISIBLE);
		}
		
		list_fav.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long arg3) {
				// TODO Auto-generated method stub
				pojo=allData.get(position);
				int pos=Integer.parseInt(pojo.getCatId());
				
				Intent intplay=new Intent(getActivity(),NewsDetail_Activity.class);
				intplay.putExtra("POSITION", pos);
 				intplay.putExtra("CATEGORY_ITEM_CID", allArrayNewsCId);
				intplay.putExtra("CATEGORY_ITEM_NAME", allArrayNewsCatName);
				//intplay.putExtra("CATEGORY_ITEM_IMAGE", allArrayNewsCatImage);
				intplay.putExtra("CATEGORY_ITEM_CAT_ID", allArrayNewsCatId);
				intplay.putExtra("CATEGORY_ITEM_NEWSIMAGE", allArrayNewsImage);
				intplay.putExtra("CATEGORY_ITEM_NEWSHEADING", allArrayNewsHeading);
				intplay.putExtra("CATEGORY_ITEM_NEWSDESCRI", allArrayNewsDes);
				intplay.putExtra("CATEGORY_ITEM_NEWSDATE", allArrayNewsDate);
				
				startActivity(intplay);
				
				
				
			}
		});
		return rootView;
	}
	 
	
	public void onDestroyView() {
		
		//Log.e("OnDestroy", "called");
		if(!dbManager.isDatabaseClosed())
			dbManager.closeDatabase();
		super.onDestroyView();
	}
	@Override
	public void onPause() {
	super.onPause();
	//Log.e("OnPaused", "called");
	if(!dbManager.isDatabaseClosed())
	dbManager.closeDatabase();
	}
	
	@Override
	public void onResume() {
	super.onResume();
	//Log.e("OnResume", "called");
	//when back key pressed or go one tab to another we update the favorite item so put in resume
	allData=db.getAllData();
	adapter=new FavoriteAdapter(this.getActivity(), R.layout.favorite_lsv_item, allData);
	list_fav.setAdapter(adapter);
	if(allData.size()==0)
	{
		txt_no.setVisibility(View.VISIBLE);
	}
	else
	{
		txt_no.setVisibility(View.INVISIBLE);
	}
	
	allListnews=new ArrayList<String>();
	allListnewsCatName=new ArrayList<String>();
	allListNewsCId=new ArrayList<String>();
	allListNewsCatId=new ArrayList<String>();
	//allListNewsCatImage=new ArrayList<String>();
	allListNewsCatName=new ArrayList<String>();
	allListNewsHeading=new ArrayList<String>();
	allListNewsImage=new ArrayList<String>();
	allListNewsDes=new ArrayList<String>();
	allListNewsDate=new ArrayList<String>();
	
	allArraynews=new String[allListnews.size()];
	allArraynewsCatName=new String[allListnewsCatName.size()];
	allArrayNewsCId=new String[allListNewsCId.size()];
	allArrayNewsCatId=new String[allListNewsCatId.size()];
	//allArrayNewsCatImage=new String[allListNewsCatImage.size()];
	allArrayNewsCatName=new String[allListNewsCatName.size()];
	allArrayNewsHeading=new String[allListNewsHeading.size()];
	allArrayNewsImage=new String[allListNewsImage.size()];
	allArrayNewsDes=new String[allListNewsDes.size()];
	allArrayNewsDate=new String[allListNewsDate.size()];
	
	for(int j=0;j<allData.size();j++)
	{
		 Pojo objAllBean=allData.get(j);
		 
		    allListNewsCatId.add(objAllBean.getCatId());
			allArrayNewsCatId=allListNewsCatId.toArray(allArrayNewsCatId);
			
			
			allListNewsCId.add(String.valueOf(objAllBean.getCId()));
			allArrayNewsCId=allListNewsCId.toArray(allArrayNewsCId);
			
			allListNewsCatName.add(objAllBean.getCategoryName());
			allArrayNewsCatName=allListNewsCatName.toArray(allArrayNewsCatName);
 			
 			allListNewsHeading.add(String.valueOf(objAllBean.getNewsHeading()));
			allArrayNewsHeading=allListNewsHeading.toArray(allArrayNewsHeading);
			
			allListNewsImage.add(String.valueOf(objAllBean.getNewsImage()));
			allArrayNewsImage=allListNewsImage.toArray(allArrayNewsImage);
			
			allListNewsDes.add(String.valueOf(objAllBean.getNewsDesc()));
			allArrayNewsDes=allListNewsDes.toArray(allArrayNewsDes);
			
			allListNewsDate.add(String.valueOf(objAllBean.getNewsDate()));
			allArrayNewsDate=allListNewsDate.toArray(allArrayNewsDate);
	}
	}
	
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		super.onCreateOptionsMenu(menu, inflater);
		inflater.inflate(R.menu.main, menu);


		final SearchView searchView = (SearchView) menu.findItem(R.id.search)
				.getActionView();

		final MenuItem searchMenuItem = menu.findItem(R.id.search);
		searchView.setOnQueryTextFocusChangeListener(new View.OnFocusChangeListener() {

			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				// TODO Auto-generated method stub
				if(!hasFocus) {
					searchMenuItem.collapseActionView();
					searchView.setQuery("", false);
				}
			}
		});

		searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

			@Override
			public boolean onQueryTextChange(String newText) {
				// TODO Auto-generated method stub
				textlength=newText.length();
	        	allData.clear();
	        	
	        	for(int i=0;i< allArrayNewsHeading.length;i++)
	        	{
	        		if(textlength <= allArrayNewsHeading[i].length())
	        		{
	        			if(newText.toString().equalsIgnoreCase((String) allArrayNewsHeading[i].subSequence(0, textlength)))
	        			{
	        				
	        				
	        				Pojo objItem = new Pojo();
	        				
	        				objItem.setCatId(allArrayNewsCatId[i]);
	        				objItem.setCId(allArrayNewsCId[i]);
 	        				objItem.setCategoryName(allArrayNewsCatName[i]);
 	        				objItem.setNewsHeading(allArrayNewsHeading[i]);
	        				objItem.setNewsImage(allArrayNewsImage[i]);
	        				objItem.setNewsDesc(allArrayNewsDes[i]);
	        				objItem.setNewsDate(allArrayNewsDate[i]);
	        				 
 	        				allData.add(objItem);
	        				
	        			}
	        		}
	        	}
	        	 
	        	adapter=new FavoriteAdapter(getActivity(), R.layout.favorite_lsv_item, allData);
	        	list_fav.setAdapter(adapter);
	        	 
	        	return false;
	        }

			@Override
			public boolean onQueryTextSubmit(String query) {
				// TODO Auto-generated method stub
				return false;
			}
		});


	}

	@Override
	public boolean onOptionsItemSelected(MenuItem menuItem)
	{       
		switch (menuItem.getItemId()) 
		{
		case R.id.menu_about: 
			Intent intab=new Intent(getActivity(),AboutActivity.class);
			startActivity(intab);
			break;
			
		
		case R.id.menu_moreapp:

			startActivity(new Intent(
					Intent.ACTION_VIEW,
					Uri.parse(getString(R.string.play_more_apps))));

			return true;

		case R.id.menu_rateapp:

			final String appName = getActivity().getPackageName();//your application package name i.e play store application url
			try {
				startActivity(new Intent(Intent.ACTION_VIEW,
						Uri.parse("market://details?id="
								+ appName)));
			} catch (android.content.ActivityNotFoundException anfe) {
				startActivity(new Intent(
						Intent.ACTION_VIEW,
						Uri.parse("http://play.google.com/store/apps/details?id="
								+ appName)));
			}
			return true;

		default:
			return super.onOptionsItemSelected(menuItem);
		}
		return true;
	}

}
