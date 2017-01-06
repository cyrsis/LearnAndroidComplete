package com.viaviapp.newsapplication;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

import com.example.adapter.AllNewsListAdapter;
import com.example.item.ItemAllNews;
import com.example.util.AlertDialogManager;
import com.example.util.Constant;
import com.example.util.JsonUtils;

public class AllNewsFragment extends Fragment {
	
	ListView lsv_allnews;
	List<ItemAllNews> arrayOfAllnews;
	AllNewsListAdapter objAdapter;
	AlertDialogManager alert = new AlertDialogManager();
	private ItemAllNews objAllBean;
	ArrayList<String> allListCatid,allListCatName,allListCatImageUrl;
	String[] allArrayCatid,allArrayCatname,allArrayCatImageurl;
	 int textlength = 0;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		
		View rootView = inflater.inflate(R.layout.fragment_allnews, container, false);
		lsv_allnews=(ListView)rootView.findViewById(R.id.lsv_allnews);
		arrayOfAllnews=new ArrayList<ItemAllNews>();
		setHasOptionsMenu(true);
		
		allListCatid=new ArrayList<String>();
		allListCatImageUrl=new ArrayList<String>();
		allListCatName=new ArrayList<String>();
		
		allArrayCatid=new String[allListCatid.size()];
		allArrayCatname=new String[allListCatName.size()];
		allArrayCatImageurl=new String[allListCatImageUrl.size()];
 		
		lsv_allnews.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long arg3) {
				// TODO Auto-generated method stub
				objAllBean=arrayOfAllnews.get(position);
				int Catid=objAllBean.getCategoryId();
				Constant.CATEGORY_IDD=objAllBean.getCategoryId();
				Log.e("cat_id",""+Catid);
				Constant.CATEGORY_TITLE=objAllBean.getCategoryName();
				
				Intent intcat=new Intent(getActivity(),NewsList_Fragment.class);
				startActivity(intcat);
			}
		});
		
		
		if (JsonUtils.isNetworkAvailable(getActivity())) {
			new MyTask().execute(Constant.CATEGORY_URL);
		} else {
			showToast("No Network Connection!!!");
//			 alert.showAlertDialog(getActivity(), "Internet Connection Error",
//	                    "Please connect to working Internet connection", false);
		}
		return rootView;
	}
	
	
	private	class MyTask extends AsyncTask<String, Void, String> {

		ProgressDialog pDialog;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();

			pDialog = new ProgressDialog(getActivity());
			pDialog.setMessage("Loading...");
			pDialog.setCancelable(false);
			pDialog.show();
		}

		@Override
		protected String doInBackground(String... params) {
			return JsonUtils.getJSONString(params[0]);
		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);

			if (null != pDialog && pDialog.isShowing()) {
				pDialog.dismiss();
			}

			if (null == result || result.length() == 0) {
				showToast("No data found from web!!!");
				 
			} else {

				try {
					JSONObject mainJson = new JSONObject(result);
					JSONArray jsonArray = mainJson.getJSONArray(Constant.CATEGORY_ARRAY_NAME);
					JSONObject objJson = null;
					for (int i = 0; i < jsonArray.length(); i++) {
						  objJson = jsonArray.getJSONObject(i);

						ItemAllNews objItem = new ItemAllNews();
						objItem.setCategoryName(objJson.getString(Constant.CATEGORY_NAME));
						objItem.setCategoryId(objJson.getInt(Constant.CATEGORY_CID));
						objItem.setCategoryImageurl(objJson.getString(Constant.CATEGORY_IMAGE));
						arrayOfAllnews.add(objItem);
					 

					}
					 
				} catch (JSONException e) {
					e.printStackTrace();
				}
				 
				
				for(int j=0;j<arrayOfAllnews.size();j++)
				{
					objAllBean=arrayOfAllnews.get(j);
					
					allListCatid.add(String.valueOf(objAllBean.getCategoryId()));
					allArrayCatid=allListCatid.toArray(allArrayCatid);
					
					allListCatName.add(objAllBean.getCategoryName());
					allArrayCatname=allListCatName.toArray(allArrayCatname);
					
					allListCatImageUrl.add(objAllBean.getCategoryImageurl());
					allArrayCatImageurl=allListCatImageUrl.toArray(allArrayCatImageurl);
					
				}
 
  			setAdapterToListview();
  		}

		}
	}

	 
 
	public void setAdapterToListview() {
		objAdapter = new AllNewsListAdapter(getActivity(), R.layout.allnews_lsv_item,
				arrayOfAllnews);
		lsv_allnews.setAdapter(objAdapter);
	}

	public void showToast(String msg) {
		Toast.makeText(getActivity(), msg, Toast.LENGTH_LONG).show();
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
	    		arrayOfAllnews.clear();
	    		
	    		for(int i=0;i<allArrayCatname.length;i++)
	    		{
	    			if(textlength <= allArrayCatname[i].length())
	        		{
	    				if(newText.toString().equalsIgnoreCase((String) allArrayCatname[i].subSequence(0, textlength)))
	        			{
	    					ItemAllNews objItem = new ItemAllNews();
	    					objItem.setCategoryId(Integer.parseInt(allArrayCatid[i]));
	    					objItem.setCategoryName(allArrayCatname[i]);
	    					objItem.setCategoryImageurl(allArrayCatImageurl[i]);
	    					
	    					arrayOfAllnews.add(objItem);
	        			}
	        		}
	    		}
	    		
	    		setAdapterToListview();
	    	
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
