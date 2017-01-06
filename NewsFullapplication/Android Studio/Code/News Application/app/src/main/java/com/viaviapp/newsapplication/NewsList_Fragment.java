package com.viaviapp.newsapplication;

import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;
import com.example.adapter.NewsListAdapter;
import com.example.item.ItemNewsList;
import com.example.util.AlertDialogManager;
import com.example.util.Constant;
import com.example.util.JsonUtils;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.startapp.android.publish.StartAppAd;
import com.viaviapp.newsapplication.R;

public class NewsList_Fragment extends ActionBarActivity {

	ListView lsv_cat;
	List<ItemNewsList> arrayOfNewsList;
	NewsListAdapter objAdapter;
	AlertDialogManager alert = new AlertDialogManager();
	ArrayList<String> allListnews,allListnewsCatName;
	ArrayList<String> allListNewsCId,allListNewsCatId,allListNewsCatImage,allListNewsCatName,allListNewsHeading,allListNewsImage,allListNewsDes,allListNewsDate;
	String[] allArraynews,allArraynewsCatName;
	String[] allArrayNewsCId,allArrayNewsCatId,allArrayNewsCatImage,allArrayNewsCatName,allArrayNewsHeading,allArrayNewsImage,allArrayNewsDes,allArrayNewsDate;
	private ItemNewsList objAllBean;
	private int columnWidth;
	JsonUtils util;
	int textlength = 0;
	private AdView mAdView;
	Toolbar toolbar;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		StartAppAd.init(this, getString(R.string.startapp_dev_id), getString(R.string.startapp_app_id,false));
		setContentView(R.layout.fragment_newslist);
		toolbar = (Toolbar) this.findViewById(R.id.toolbar);
		toolbar.setTitle(Constant.CATEGORY_TITLE);
		this.setSupportActionBar(toolbar);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setDisplayShowHomeEnabled(true);
		
		StartAppAd.showSlider(this);
		
		mAdView = (AdView) findViewById(R.id.adView);
		mAdView.loadAd(new AdRequest.Builder().build());
 	 
		lsv_cat=(ListView)findViewById(R.id.lsv_cat_item);

		arrayOfNewsList=new ArrayList<ItemNewsList>();
		allListnews=new ArrayList<String>();
		allListnewsCatName=new ArrayList<String>();
		allListNewsCId=new ArrayList<String>();
		allListNewsCatId=new ArrayList<String>();
		allListNewsCatImage=new ArrayList<String>();
		allListNewsCatName=new ArrayList<String>();
		allListNewsHeading=new ArrayList<String>();
		allListNewsImage=new ArrayList<String>();
		allListNewsDes=new ArrayList<String>();
		allListNewsDate=new ArrayList<String>();

		allArraynews=new String[allListnews.size()];
		allArraynewsCatName=new String[allListnewsCatName.size()];
		allArrayNewsCId=new String[allListNewsCId.size()];
		allArrayNewsCatId=new String[allListNewsCatId.size()];
		allArrayNewsCatImage=new String[allListNewsCatImage.size()];
		allArrayNewsCatName=new String[allListNewsCatName.size()];
		allArrayNewsHeading=new String[allListNewsHeading.size()];
		allArrayNewsImage=new String[allListNewsImage.size()];
		allArrayNewsDes=new String[allListNewsDes.size()];
		allArrayNewsDate=new String[allListNewsDate.size()];

		util=new JsonUtils(getApplicationContext());


		if (JsonUtils.isNetworkAvailable(NewsList_Fragment.this)) {
			new MyTask().execute(Constant.CATEGORY_ITEM_URL+Constant.CATEGORY_IDD);
		} else {
			showToast("No Network Connection!!!");
//			alert.showAlertDialog(NewsList_Fragment.this, "Internet Connection Error",
//					"Please connect to working Internet connection", false);
		}

		lsv_cat.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long arg3) {
				// TODO Auto-generated method stub

				objAllBean=arrayOfNewsList.get(position);
				int pos=Integer.parseInt(objAllBean.getCatId());

				Intent intplay=new Intent(getApplicationContext(),NewsDetail_Activity.class);
				intplay.putExtra("POSITION", pos);
				intplay.putExtra("CATEGORY_ITEM_CID", allArrayNewsCId);
				intplay.putExtra("CATEGORY_ITEM_NAME", allArrayNewsCatName);
				intplay.putExtra("CATEGORY_ITEM_IMAGE", allArrayNewsCatImage);
				intplay.putExtra("CATEGORY_ITEM_CAT_ID", allArrayNewsCatId);
				intplay.putExtra("CATEGORY_ITEM_NEWSIMAGE", allArrayNewsImage);
				intplay.putExtra("CATEGORY_ITEM_NEWSHEADING", allArrayNewsHeading);
				intplay.putExtra("CATEGORY_ITEM_NEWSDESCRI", allArrayNewsDes);
				intplay.putExtra("CATEGORY_ITEM_NEWSDATE", allArrayNewsDate);

				startActivity(intplay);
			}
		});

	}



	private	class MyTask extends AsyncTask<String, Void, String> {

		ProgressDialog pDialog;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();

			pDialog = new ProgressDialog(NewsList_Fragment.this);
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
				showToast("Server Connection Error");
//				alert.showAlertDialog(NewsList_Fragment.this, "Server Connection Error",
//						"May Server Under Maintaines Or Low Network", false);

			} else {

				try {
					JSONObject mainJson = new JSONObject(result);
					JSONArray jsonArray = mainJson.getJSONArray(Constant.CATEGORY_ARRAY_NAME);
					JSONObject objJson = null;
					for (int i = 0; i < jsonArray.length(); i++) {
						objJson = jsonArray.getJSONObject(i);

						ItemNewsList objItem = new ItemNewsList();

						objItem.setCId(objJson.getString(Constant.CATEGORY_ITEM_CID));
						objItem.setCategoryName(objJson.getString(Constant.CATEGORY_ITEM_NAME));
						objItem.setCategoryImage(objJson.getString(Constant.CATEGORY_ITEM_IMAGE));
						objItem.setCatId(objJson.getString(Constant.CATEGORY_ITEM_CAT_ID));
						objItem.setNewsImage(objJson.getString(Constant.CATEGORY_ITEM_NEWSIMAGE));
						objItem.setNewsHeading(objJson.getString(Constant.CATEGORY_ITEM_NEWSHEADING));
						objItem.setNewsDescription(objJson.getString(Constant.CATEGORY_ITEM_NEWSDESCRI));
						objItem.setNewsDate(objJson.getString(Constant.CATEGORY_ITEM_NEWSDATE));

						arrayOfNewsList.add(objItem);


					}

				} catch (JSONException e) {
					e.printStackTrace();
				}
				for(int j=0;j<arrayOfNewsList.size();j++)
				{

					objAllBean=arrayOfNewsList.get(j);

					allListNewsCatId.add(objAllBean.getCatId());
					allArrayNewsCatId=allListNewsCatId.toArray(allArrayNewsCatId);

					allListNewsCatName.add(objAllBean.getCategoryName());
					allArrayNewsCatName=allListNewsCatName.toArray(allArrayNewsCatName);

					allListNewsCId.add(String.valueOf(objAllBean.getCId()));
					allArrayNewsCId=allListNewsCId.toArray(allArrayNewsCId);

					allListNewsImage.add(String.valueOf(objAllBean.getNewsImage()));
					allArrayNewsImage=allListNewsImage.toArray(allArrayNewsImage);


					allListNewsHeading.add(String.valueOf(objAllBean.getNewsHeading()));
					allArrayNewsHeading=allListNewsHeading.toArray(allArrayNewsHeading);

					allListNewsDes.add(String.valueOf(objAllBean.getNewsDescription()));
					allArrayNewsDes=allListNewsDes.toArray(allArrayNewsDes);

					allListNewsDate.add(String.valueOf(objAllBean.getNewsDate()));
					allArrayNewsDate=allListNewsDate.toArray(allArrayNewsDate);

				}

				setAdapterToListview();
			}

		}
	}



	public void setAdapterToListview() {
		objAdapter = new NewsListAdapter(NewsList_Fragment.this, R.layout.newslist_lsv_item,
				arrayOfNewsList,columnWidth);
		lsv_cat.setAdapter(objAdapter);
	}

	public void showToast(String msg) {
		Toast.makeText(NewsList_Fragment.this, msg, Toast.LENGTH_LONG).show();
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub

		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu_search, menu);

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
				arrayOfNewsList.clear();

				for(int i=0;i< allArrayNewsHeading.length;i++)
				{
					if(textlength <= allArrayNewsHeading[i].length())
					{
						if(newText.toString().equalsIgnoreCase((String) allArrayNewsHeading[i].subSequence(0, textlength)))
						{


							ItemNewsList objItem = new ItemNewsList();

							objItem.setCategoryName(allArrayNewsCatName[i]);
							objItem.setCatId(allArrayNewsCatId[i]);
							objItem.setCId(allArrayNewsCId[i]);
							objItem.setNewsDate(allArrayNewsDate[i]);
							objItem.setNewsDescription(allArrayNewsDes[i]);
							objItem.setNewsHeading(allArrayNewsHeading[i]);
							objItem.setNewsImage(allArrayNewsImage[i]);

							arrayOfNewsList.add(objItem);

						}
					}
				}

				setAdapterToListview();
				return false;
			}

			@Override
			public boolean onQueryTextSubmit(String query) {
				// Do something
				return true;
			}
		});

		 return super.onCreateOptionsMenu(menu);

	}
	@Override
	public boolean onOptionsItemSelected(MenuItem menuItem)
	{       
		switch (menuItem.getItemId()) 
		{
		case android.R.id.home: 
			onBackPressed();
			break;

		default:
			return super.onOptionsItemSelected(menuItem);
		}
		return true;
	}

}

	 